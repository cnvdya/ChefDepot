package com.fooddepot.constants;

/**
 * Created by ravisha on 10/10/16.
 */
public enum MessageType {
    STUDENT_MESSAGES("student"),CLASS_MESSAGES("class"),SCHOOL_MESSAGES("school"),ALL_MESSAGES("all");

    private String id;

    private MessageType(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

}
