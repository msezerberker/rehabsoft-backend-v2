package com.hacettepe.rehabsoft.util;


public class Constants {

    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5*60*60;
    public static final String SIGNING_KEY = "rehabsoft";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String AUTHORITIES_KEY = "scopes";
    public static final Long PATIENT_ROLE_ID = 1L;
    public static final Long ADMIN_ROLE_ID = 2L;
    public static final Long DOCTOR_ROLE_ID = 3L;
}