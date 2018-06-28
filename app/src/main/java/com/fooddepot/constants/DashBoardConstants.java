package com.fooddepot.constants;

import java.text.SimpleDateFormat;

/**
 * Dashboard related constants are declared here.
 * Created by ravisha on 12/12/16.
 */
public interface DashBoardConstants {
    String PARENT_DASHBOARD_KEY = "school-classid-studentid";
    /**
     * DASHBOARD SCHEDULER CONSTANTS
     */
    int PARENT_EXAM_SCHEDULER_ID = 11;
    int PARENT_FEE_SCHEDULER_ID = 12;
    /**
     * DASHBOARD MESSAGES DISPLAYED ON UI
     */
    String NO_EXAMS = "No upcoming exams";
    String IN_PROGRESS= "In progress";
    String LAST_DAY = " - Today is Lastday";
    String NO_DUE = "No Fees Due in next ";
    String DUE_TOMMOROW = " is due Tomorrow ";
    String DUE_ON = " is due on ";

    String NO_RESULTS_DECLARED = "No Results Declared";
    String RESULTS_DECLARED = " Results Declared" ;


    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");


    /**
     * EXCEPTION MESSAGES
     */

    String ERROR_MESSAGE = "Student or class or school id is missing.";

    String DEFAULT= "default";
    String DEFAULT_SCHEDULING =  "defaultScheduling";
    String FEE_DISPLAY_DAYS = "feeDisplayDays";
    String RESULTS_DISPLAY_DAYS = "resultDisplayDays";
    int DEFAULT_CONFIGURATION_PERIOD = 5000;
     int MSG_SERVICE_OBJ = 2;
}
