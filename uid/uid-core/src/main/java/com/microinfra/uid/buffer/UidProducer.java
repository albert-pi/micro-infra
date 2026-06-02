package com.microinfra.uid.buffer;

import java.util.List;

@FunctionalInterface
public interface UidProducer {

	List<Long> produce(long momentInSecond);

}
