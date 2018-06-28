package com.fooddepot.exception;



/**
 * Created by ravisha on 11/12/16.
 */
public class TestException extends GenericException {

    public TestException(Exception exception){
        super(exception);

    }


    public TestException(String message){
        super(message);
    }

    public TestException(String errorCode, Exception exception){
        super(errorCode,exception);

    }
}

