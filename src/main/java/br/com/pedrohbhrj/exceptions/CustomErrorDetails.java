package br.com.pedrohbhrj.exceptions;



import java.time.LocalDateTime;

public record CustomErrorDetails (
        String message,
        LocalDateTime timeStamp,
        String path,
        int status
){
}
