package com.microinfra.uid.buffer;

import com.microinfra.uid.UidException;

@FunctionalInterface
public interface BufferExhaustedHandler {

	void handle(RingBuffer ringBuffer) throws UidException;

}
