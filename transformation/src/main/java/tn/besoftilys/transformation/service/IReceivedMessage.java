package tn.besoftilys.transformation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import tn.besoftilys.transformation.dto.MessageDto;

import java.util.Map;

public interface IReceivedMessage {

    String transformMessage(MessageDto messageDto, String key)throws JsonProcessingException;

}
