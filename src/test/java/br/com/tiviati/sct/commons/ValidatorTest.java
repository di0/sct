package br.com.tiviati.sct.commons;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidatorTest {
    @Test
    public void shouldValidateCpfFormatSuccessfully() {
        final String validCpf = "355.133.043-41";
        assertTrue(Validator.isValidCpf(validCpf));
    }

    @Test
    public void shouldValidateCpfFormatUnSuccessfully() {
        final String validCpf = "355";
        assertFalse(Validator.isValidCpf(validCpf));
    }

    @Test
    public void shouldValidateTelephoneFormatSuccessfully() {
        final String validTelephoneNumber = "11 2040-4040";
        assertTrue(Validator.isValidTelephoneNumberFormat(validTelephoneNumber));
    }

    @Test
    public void shouldValidateTelephoneFormatUnSuccessfully() {
        final String validTelephoneNumber = "111 211040-4040";
        assertFalse(Validator.isValidTelephoneNumberFormat(validTelephoneNumber));
    }

    @Test
    public void shouldValidateDateBirthSuccessfully() throws ParseException {
        final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        final Date dateBirthDay = formatter.parse("14/01/2008"); // Quem nasceu ha pelo menos 16 anos.
        assertTrue(Validator.isDateBirthOk(dateBirthDay));
    }

    @Test
    public void shouldValidateDateBirthUnSuccessfully() throws ParseException {
        final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        // Testa cadastratros de data atuais em aniversarios.
        final Date dateBirthDay = formatter.parse("14/01/2024");
        assertFalse(Validator.isDateBirthOk(dateBirthDay));
    }
}
