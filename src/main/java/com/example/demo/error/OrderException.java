package com.example.demo.error;

public class OrderException extends RuntimeException{
    private String message;

    public OrderException(String message){
       this.message = message;
   }

    @Override
    public String getMessage() {
        return message;
    }
}
