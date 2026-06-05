package com.microinfra.parking.message.processor;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.microinfra.parking.message.StatusMessage;

import io.thingshub.client.MessageProcessor;
import io.thingshub.client.ThingshubClient;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StatusMessageProcesser implements MessageProcessor<StatusMessage> {

	@Resource
	private ThingshubClient thingshubClient;

	public static final String REPLY_MESSAGE_NAME = "status_ack";

	@Override
	public String getMessageName() {
		return "status";
	}

	@Override
	public void process(String sn, String messageId, StatusMessage payload) {
		// TODO process device status data

		log.info("Processing Status Data====================");

		thingshubClient.reply("HY-001", sn, REPLY_MESSAGE_NAME, messageId, null);

//		thingshubClient.replyWithError("HY-001", sn, REPLY_MESSAGE_NAME, messageId, 500, "error message");

	}

}