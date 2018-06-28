package com.fooddepot.exception;

/**
 * Created by ravisha on 11/12/16.
 */
public class GenericException extends Exception {

    public GenericException(Exception exception){
        super(exception);

    }

    public GenericException(String message){
        super(message);

    }


    public GenericException(String message,Exception exception){
        super(message,exception);
    }


    public String toString(){
        String name = getClass().getName();
        String message = getMessage();
        return (message!= null) ? (name+" : "+message): name;
    }
}
