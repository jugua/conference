package com.epam.cm.base;

/**
 * Created by Kateryna_Buhaichuk on 10/10/2017.
 */
public class ConferenceConstants {
    public static final int LEAST_NUMBER_OF_CONFERENCES = 1;
    public static final int FIELDS_NUMBER_OF_CONFERENCES_JSON = 9;

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String LOCATION = "location";
    public static final String START_DATE = "start_date";
    public static final String END_DATE = "end_date";
    public static final String CALL_FOR_PAPER_START_DATE = "call_for_paper_start_date";
    public static final String CALL_FOR_PAPER_END_DATE = "call_for_paper_end_date";


    public static final String LOGIN_AUTH_ERR = "{\"error\":\"login_auth_err\"}";






    public static final String CONFERENCE_BODY_JSON = "{\"id\": 9, \"title\": \"TestConferenceTested\", \"description\": \"Test\"," +
            " \"location\": \"Kyiv\", \"start_date\": \"2017-09-09\", \"end_date\": \"2017-11-29\", " +
            "\"call_for_paper_start_date\": \"2017-10-09\", \"call_for_paper_end_date\": \"2017-10-29\"," +
            "\"cfp_active\": true, \"new\": 0, \"in-progress\": 0, \"approved\": 0, \"rejected\": 0 }";

    public static final String NON_EXISTING_CONFERENCE_BODY_JSON = "{\"id\": 505, \"title\": \"No Conference\", \"description\": \"Test\"," +
            " \"location\": \"Kyiv\", \"start_date\": \"2017-09-09\", \"end_date\": \"2017-11-29\", " +
            "\"call_for_paper_start_date\": \"2017-10-09\", \"call_for_paper_end_date\": \"2017-10-29\"," +
            "\"cfp_active\": true, \"new\": 0, \"in-progress\": 0, \"approved\": 0, \"rejected\": 0 }";

}
