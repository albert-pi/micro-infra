package com.microinfra.parking.message.processor;

import org.springframework.stereotype.Component;

import io.thingshub.client.MessageProcessor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RebootAckMessageProcessor implements MessageProcessor<Void> {

	@Override
	public String getMessageName() {
		return "reboot_ack";
	}

	@Override
	public void process(String sn, String messageId, Void payload) {
		log.info("device {} reboot successfully", sn);
	}

	@Override
	public void onError(String sn, Integer code, String error) {
		log.error("reboot device {} error: ", sn, error);
	}

}