package com.alexmau.suggestionapp.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DataResponse<T extends Serializable> {

    private T data;

    private List<ResponseMessage> messages;
}
