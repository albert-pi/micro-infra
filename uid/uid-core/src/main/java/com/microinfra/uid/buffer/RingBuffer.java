package com.microinfra.uid.buffer;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.util.Assert;

import com.microinfra.commons.lang.PaddedAtomicLong;
import com.microinfra.commons.lang.ServiceException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RingBuffer {

	private static final int START_POINT = -1;

	private static final long CAN_PUT_FLAG = 0L;

	private static final long CAN_TAKE_FLAG = 1L;

	public static final int DEFAULT_PADDING_PERCENT = 50;

	private final int bufferSize;

	private final long indexMask;

	private final long[] slots;

	private final PaddedAtomicLong[] flags;

	private final AtomicLong tail = new PaddedAtomicLong(START_POINT);// 填充sequence的最后位置

	private final AtomicLong cursor = new PaddedAtomicLong(START_POINT);// 当前消费sequence的位置

	private final int paddingThreshold;

	public RingBuffer(int bufferSize) {
		this(bufferSize, DEFAULT_PADDING_PERCENT);
	}

	public RingBuffer(int bufferSize, int paddingFactor) {
		Assert.isTrue(bufferSize > 0L, "RingBuffer size must be positive");
		Assert.isTrue(Integer.bitCount(bufferSize) == 1, "RingBuffer size must be a power of 2");
		Assert.isTrue(paddingFactor > 0 && paddingFactor < 100, "RingBuffer size must be positive");

		this.bufferSize = bufferSize;
		this.indexMask = bufferSize - 1;
		this.slots = new long[bufferSize];
		this.flags = initFlags(bufferSize);

		this.paddingThreshold = bufferSize * paddingFactor / 100;
	}

	public synchronized boolean put(long uid, BufferFullHandler bufferFullHandler) {
		long currentTail = tail.get();
		long currentCursor = cursor.get();

		long distance = currentTail - (currentCursor == START_POINT ? 0 : currentCursor);
		if (distance == bufferSize - 1) {
			bufferFullHandler.handle(this, uid);
			return false;
		}

		int nextTailIndex = calcSlotIndex(currentTail + 1);
		if (flags[nextTailIndex].get() != CAN_PUT_FLAG) {
			bufferFullHandler.handle(this, uid);
			return false;
		}

		slots[nextTailIndex] = uid;
		flags[nextTailIndex].set(CAN_TAKE_FLAG);
		tail.incrementAndGet();

		return true;
	}

	public long take(BufferThresholdHandler paddingThresholdHandler, BufferExhaustedHandler bufferExhaustedHandler) throws ServiceException {
		long currentCursor = cursor.get();
		long nextCursor = cursor.updateAndGet(old -> old == tail.get() ? old : old + 1);

		Assert.isTrue(nextCursor >= currentCursor, "Curosr can't move back");

		long currentTail = tail.get();
		if (currentTail - nextCursor < paddingThreshold) {
			log.info("Reach the padding threshold:{}. tail:{}, cursor:{}, rest:{}", paddingThreshold, currentTail, nextCursor, currentTail - nextCursor);
			paddingThresholdHandler.handle();
		}

		if (nextCursor == currentCursor) {
			bufferExhaustedHandler.handle(this);
		}

		int nextCursorIndex = calcSlotIndex(nextCursor);
		Assert.isTrue(flags[nextCursorIndex].get() == CAN_TAKE_FLAG, "Curosr not in can take status");

		long uid = slots[nextCursorIndex];
		flags[nextCursorIndex].set(CAN_PUT_FLAG);

		return uid;
	}

	protected int calcSlotIndex(long sequence) {
		return (int) (sequence & indexMask);
	}

	private PaddedAtomicLong[] initFlags(int bufferSize) {
		PaddedAtomicLong[] flags = new PaddedAtomicLong[bufferSize];
		for (int i = 0; i < bufferSize; i++) {
			flags[i] = new PaddedAtomicLong(CAN_PUT_FLAG);
		}

		return flags;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RingBuffer [bufferSize=").append(bufferSize).append(", tail=").append(tail).append(", cursor=").append(cursor)
				.append(", paddingThreshold=").append(paddingThreshold).append("]");

		return builder.toString();
	}

}
