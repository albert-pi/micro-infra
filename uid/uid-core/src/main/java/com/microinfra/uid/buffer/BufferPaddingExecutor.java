package com.microinfra.uid.buffer;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import com.microinfra.commons.lang.PaddedAtomicLong;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BufferPaddingExecutor {

	@Data
	public static class NamingThreadFactory implements ThreadFactory {

		private String name;

		private final ConcurrentHashMap<String, AtomicLong> sequences;

		public NamingThreadFactory(String name) {
			this.name = name;
			this.sequences = new ConcurrentHashMap<String, AtomicLong>();
		}

		@Override
		public Thread newThread(Runnable r) {
			Thread thread = new Thread(r);
			thread.setDaemon(false);

			String prefix = this.name;
			if (StringUtils.isBlank(prefix)) {
				prefix = getInvoker(2);
			}
			thread.setName(prefix + "-" + getSequence(prefix));

			return thread;
		}

		private String getInvoker(int depth) {
			Exception e = new Exception();
			StackTraceElement[] stes = e.getStackTrace();
			if (stes.length > depth) {
				return ClassUtils.getShortClassName(stes[depth].getClassName());
			}
			return getClass().getSimpleName();
		}

		private long getSequence(String invoker) {
			AtomicLong r = this.sequences.get(invoker);
			if (r == null) {
				r = new AtomicLong(0);
				AtomicLong previous = this.sequences.putIfAbsent(invoker, r);
				if (previous != null) {
					r = previous;
				}
			}

			return r.incrementAndGet();
		}

	};

	private static final String WORKER_NAME = "RingBuffer-Padding-Worker";

	private static final String SCHEDULE_NAME = "RingBuffer-Padding-Schedule";

	private final AtomicBoolean running;

	private final PaddedAtomicLong lastSecond;

	private final RingBuffer ringBuffer;

	private final UidProducer uidProvider;

	private final ExecutorService bufferPadExecutors;

	private final ScheduledExecutorService bufferPadSchedule;

	private Long scheduleInterval;

	public BufferPaddingExecutor(RingBuffer ringBuffer, UidProducer uidProvider) {
		this(ringBuffer, uidProvider, null);
	}

	public BufferPaddingExecutor(RingBuffer ringBuffer, UidProducer uidProvider, Long scheduleInterval) {
		this.running = new AtomicBoolean(false);
		this.lastSecond = new PaddedAtomicLong(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
		this.ringBuffer = ringBuffer;
		this.uidProvider = uidProvider;

		int cores = Runtime.getRuntime().availableProcessors();
		bufferPadExecutors = Executors.newFixedThreadPool(cores * 2, new NamingThreadFactory(WORKER_NAME));

		if (scheduleInterval != null) {
			bufferPadSchedule = Executors.newSingleThreadScheduledExecutor(new NamingThreadFactory(SCHEDULE_NAME));
		} else {
			bufferPadSchedule = null;
		}
	}

	public void start() {
		if (bufferPadSchedule != null) {
			bufferPadSchedule.scheduleWithFixedDelay(() -> paddingBuffer(), scheduleInterval, scheduleInterval, TimeUnit.SECONDS);
		}
	}

	public void shutdown() {
		if (!bufferPadExecutors.isShutdown()) {
			bufferPadExecutors.shutdownNow();
		}

		if (bufferPadSchedule != null && !bufferPadSchedule.isShutdown()) {
			bufferPadSchedule.shutdownNow();
		}
	}

	public boolean isRunning() {
		return running.get();
	}

	public void asyncPadding() {
		bufferPadExecutors.submit(this::paddingBuffer);
	}

	public void paddingBuffer() {
		log.info("Ready to padding buffer lastSecond:{}. {}", lastSecond.get(), ringBuffer);

		if (!running.compareAndSet(false, true)) {
			log.info("Padding buffer is still running. {}", ringBuffer);
			return;
		}

		boolean isFull = false;
		while (!isFull) {
			List<Long> uidList = uidProvider.produce(lastSecond.incrementAndGet());
			for (Long uid : uidList) {
				isFull = !ringBuffer.put(uid, this::onBufferFull);
				if (isFull) {
					break;
				}
			}
		}

		running.compareAndSet(true, false);

		log.info("End to padding buffer lastSecond:{}. {}", lastSecond.get(), ringBuffer);
	}

	private void onBufferFull(RingBuffer ringBuffer, long uid) {
		log.warn("Buffer is full and reject putting to buffer for uid:{}. {}", uid, ringBuffer);
	}

}
