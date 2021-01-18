package com.alexmau.suggestionapp.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ListResponse<T extends Serializable> {

    private List<T> listResult;

    private List<ResponseMessage> messages;

    private StatusType status;

    public ResponseMessage addMessage(ResponseMessage responseMessage) {
        if (messages == null) {
            messages = new ArrayList<>();
        }
        messages.add(responseMessage);
        return responseMessage;
    }
}
