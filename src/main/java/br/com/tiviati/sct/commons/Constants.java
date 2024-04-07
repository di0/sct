package br.com.tiviati.sct.commons;

public class Constants {
    public static final String API_KEY_HEADER_NAME = "X-API-KEY";

    public static final String TOKEN_EMPTY = "Token is empty.";

    public static final String KEY_LOADED_SUCCESSFUL = "Key loaded successful.";

    public static final String KEY_LOADED_UNSUCCESSFUL = "Key loaded unsuccessful.";

    public static final String  UNAUTHORIZED_JSON_PAYLOAD = "{\"UNAUTHORIZED\": \"Invalid API Key.\"}";

    public static final String  UNAUTHORIZED_PAYLOAD = "UNAUTHORIZED";

    public static final String UNAUTHORIZED_PAYLOAD_REASON = "Invalid API Key";

    public static final String BENEFICIARY_NOT_EXISTS = "This beneficiary does not exist with specific" +
            " ID(or without it).";

    public static final String DOCUMENT_NOT_EXISTS = "This document does not exist with ID ";

    public static final String INVALID_CPF = "Invalid CPF format.";

    public static final String INVALID_PHONE_NUMBER = "Contact number invalid.";

    public static final String INVALID_BIRTHDAY = "Invalid birthday format or correspond date.";

    public static final String DATASOURCE_BEAN_ERROR = "Embedded dataSource bean cannot be created!";

    public static final String API_KEY_IS_NULL = "The given API Key is null.";

    private Constants() {
        /* Only statics fields */
    }
}
