package br.com.pedrohbhrj.exceptions;



import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record CustomErrorDetails<T>(
        String message,
        LocalDateTime timeStamp,
        String path,
        int status,
        T fieldErrors
){
}
