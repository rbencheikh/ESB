package tn.besoftilys.messages.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.besoftilys.messages.entity.Message;
import tn.besoftilys.messages.repository.MessageRepository;

@Service
public class MessageService implements IMessage{
    @Autowired
    MessageRepository messageRepository;
    @Override
    public Message saveMessage(Message message) {
       return messageRepository.save(message);
    }
}
