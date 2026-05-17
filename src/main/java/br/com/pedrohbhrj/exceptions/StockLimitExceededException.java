package br.com.pedrohbhrj.exceptions;

public class StockLimitExceededException extends RuntimeException {
    public StockLimitExceededException(String message) {
        super(message);
    }
}
