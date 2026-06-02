package com.microinfra.parking.message.processor;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.microinfra.parking.message.StartupMessage;

import io.thingshub.client.MessageProcessor;
import io.thingshub.client.ThingshubClient;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StartupMessageProcessor implements MessageProcessor<StartupMessage> {

	@Resource
	private ThingshubClient thingshubClient;

	public static final String REPLY_MESSAGE_NAME = "startup_ack";

	@Override
	public String getMessageName() {
		return "startup";
	}

	@Override
	public void process(String sn, String messageId, StartupMessage payload) {
		// TODO process device startup event

		log.info("Processing Startup Event====================");

		thingshubClient.reply("HY-001", sn, REPLY_MESSAGE_NAME, messageId, null);
	}

}