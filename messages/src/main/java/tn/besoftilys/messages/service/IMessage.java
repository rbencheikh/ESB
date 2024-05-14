package tn.besoftilys.messages.service;

import tn.besoftilys.messages.entity.Message;

import java.util.Map;

public interface IMessage {
    Message saveMessage(Message message);
    Map<String, Long> countMessagesByContentType();
}
