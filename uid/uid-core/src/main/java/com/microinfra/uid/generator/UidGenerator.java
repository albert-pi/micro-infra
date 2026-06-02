package com.microinfra.uid.generator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.microinfra.commons.lang.LongId;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.commons.lang.SysException;
import com.microinfra.uid.UidException;
import com.microinfra.uid.UidConfig.SnowflakeConfig;
import com.microinfra.uid.buffer.BufferPaddingExecutor;
import com.microinfra.uid.buffer.RingBuffer;
import com.microinfra.uid.facade.UidService;
import com.microinfra.uid.worker.WorkerIdAssigner;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UidGenerator implements UidService {

	private long epochInSeconds = TimeUnit.MILLISECONDS.toSeconds(1704508113000L);// "2024-01-06"

	private long workerId;

	private RingBuffer ringBuffer;

	private BitsAllocator bitsAllocator;

	private BufferPaddingExecutor bufferPaddingExecutor;

	@Resource
	private WorkerIdAssigner workerIdAssigner;

	@Resource
	private SnowflakeConfig snowflakeConfig;

	@PostConstruct
	public void init() throws ParseException {
		this.bitsAllocator = new BitsAllocator(snowflakeConfig.getTimestampBits(), snowflakeConfig.getWorkerBits(), snowflakeConfig.getSequenceBits());

		this.workerId = workerIdAssigner.assignWorkerId();
		if (this.workerId > bitsAllocator.getMaxWorkerId()) {
			throw new SysException("Worker id " + workerId + " exceeds the max " + bitsAllocator.getMaxWorkerId());
		}

		if (!ObjectUtils.isEmpty(snowflakeConfig.getEpoch())) {
			this.epochInSeconds = TimeUnit.MILLISECONDS.toSeconds(DateUtils.parseDate(snowflakeConfig.getEpoch(), "yyyy-MM-dd").getTime());
		}

		int bufferSize = ((int) bitsAllocator.getMaxSequence() + 1) << snowflakeConfig.getBoostPower();
		this.ringBuffer = new RingBuffer(bufferSize, snowflakeConfig.getPaddingFactor());
		this.bufferPaddingExecutor = new BufferPaddingExecutor(ringBuffer, this::produceUidsInSecond, snowflakeConfig.getScheduleInterval());
		this.bufferPaddingExecutor.paddingBuffer();
		this.bufferPaddingExecutor.start();
	}

	@Override
	public Long generate() throws ServiceException {
		return ringBuffer.take(bufferPaddingExecutor::asyncPadding, this::onBufferExhausted);
	}

	@Override
	public String parse(LongId uid) throws ServiceException {
		long totalBits = BitsAllocator.TOTAL_BITS;
		long signBits = bitsAllocator.getSignBits();
		long timestampBits = bitsAllocator.getTimestampBits();
		long workerIdBits = bitsAllocator.getWorkerIdBits();
		long sequenceBits = bitsAllocator.getSequenceBits();

		long sequence = (uid.getId() << (totalBits - sequenceBits)) >>> (totalBits - sequenceBits);
		long workerId = (uid.getId() << (timestampBits + signBits)) >>> (totalBits - workerIdBits);
		long deltaSeconds = uid.getId() >>> (workerIdBits + sequenceBits);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedTime = sdf.format(new Date(TimeUnit.SECONDS.toMillis(epochInSeconds + deltaSeconds)));

		return String.format("{\"UID\":\"%d\",\"timestamp\":\"%s\",\"workerId\":\"%d\",\"sequence\":\"%d\"}", uid, formattedTime, workerId, sequence);
	}

	@PreDestroy
	public void destroy() throws Exception {
		bufferPaddingExecutor.shutdown();
	}

	private List<Long> produceUidsInSecond(long currentSecond) {
		int listSize = (int) bitsAllocator.getMaxSequence() + 1;
		List<Long> uids = new ArrayList<>(listSize);

		long firstSeqUid = bitsAllocator.allocate(currentSecond - epochInSeconds, workerId, 0L);
		for (int offset = 0; offset < listSize; offset++) {
			uids.add(firstSeqUid + offset);
		}

		return uids;
	}

	private void onBufferExhausted(RingBuffer ringBuffer) throws UidException {
		log.warn("Buffer is exhausted: {}", ringBuffer);

		throw new UidException("Buffer is exhausted");
	}

}
