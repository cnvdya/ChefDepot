package com.fooddepot.exception;

/**
 * Created by ravisha on 4/26/18.
 */

public class ItemException extends GenericException {

    public ItemException(Exception exception){
        super(exception);

    }


    public ItemException(String message){
        super(message);
    }

    public ItemException(String errorCode, Exception exception){
        super(errorCode,exception);

    }
}