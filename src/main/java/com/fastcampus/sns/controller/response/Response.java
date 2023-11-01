package com.fastcampus.sns.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Response<T> {

    private String resultCode;
    private T result;

    public static <T> Response<T> error(String errorCode){
        return new Response<>(errorCode, null);
    }
    public static <T> Response<T> success(T result){
        return new Response<>("SUCCESS",result );
    }
}
