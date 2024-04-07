package br.com.tiviati.sct.exception;

public class InvalidParameterException extends RuntimeException {
    public InvalidParameterException() {
        super();
    }

    public InvalidParameterException(final String message) {
        super(message);
    }
}
