package com.microinfra.uid.buffer;

@FunctionalInterface
public interface BufferFullHandler {

	void handle(RingBuffer ringBuffer, long uid);

}
