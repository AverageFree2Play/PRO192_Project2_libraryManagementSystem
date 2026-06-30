package Utilities;

import java.time.Year;
import java.util.regex.Pattern;

public final class DataValidation {

    //======================================================
    public static boolean checkNumberInMinMax(int number, int min, int max) {

        return number >= min && number <= max;

    }

    //======================================================
    public static boolean checkStringEmpty(String value) {

        if (value == null) {
            return false;
        }

        return !value.trim().isEmpty();

    }

    //======================================================
    public static boolean checkStringLengthInRange(String value,
                                                   int min,
                                                   int max) {

        if (!checkStringEmpty(value)) {
            return false;
        }

        int length = value.trim().length();

        return length >= min && length <= max;

    }

    //======================================================
    public static boolean checkStringWithFormat(String value,
                                                String pattern) {

        if (value == null) {
            return false;
        }

        return value.matches(pattern);

    }

    //======================================================
    public static boolean checkIfValidYear(int year) {

        int currentYear = Year.now().getValue();

        return year >= 1 && year <= currentYear;

    }

    //======================================================
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^\\d{3}\\.\\d{3}\\.\\d{4}$");

    public static boolean checkIfValidPhoneNumber(String phoneNumber) {

        if (phoneNumber == null) {
            return false;
        }

        return PHONE_PATTERN.matcher(phoneNumber).matches();

    }

    //======================================================
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public static boolean checkIfValidEmail(String email) {

        if (email == null) {
            return false;
        }

        return EMAIL_PATTERN.matcher(email).matches();

    }

}