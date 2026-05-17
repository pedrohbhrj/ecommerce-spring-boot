package br.com.pedrohbhrj.infra.payment;

import java.util.UUID;

public class StripeFakePayment {
    public static String processPayment(){
        return UUID.randomUUID().toString();
    }
}
