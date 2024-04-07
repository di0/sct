package br.com.tiviati.sct.commons;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static boolean isValidCpf(final String cpf) {
        Pattern pattern = Pattern.compile("(^\\d{3}\\.\\d{3}.\\d{3}-\\d{2}$)");
        Matcher matcher = pattern.matcher(cpf);
        return matcher.find();
    }

    public static boolean isValidTelephoneNumberFormat(final String telephone) {
        Pattern pattern = Pattern.compile("(^(\\d{2})\\D*(\\d{5}|\\d{4})\\D*(\\d{4})$)");
        Matcher matcher = pattern.matcher(telephone);
        return matcher.find();
    }

    public static boolean isDateBirthOk(final Date dateBirthday) {
        LocalDate currentDate = LocalDate.now();
        // Idade que pode ter CPF. Isso evita que datas,
        // como atuais ou maiores que atuais, ou ainda, menores
        // que 16 sejam cadastradas no sistema.
        Date date = Date.from(currentDate.minusYears(16)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());
        return (dateBirthday.before(date));
    }
}
