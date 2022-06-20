package org.accolite.exception;


public class FieldEmptyException extends RuntimeException{
    public FieldEmptyException(String message){
        super(message);
    }
}
