package Utilities;

import java.time.Year;
import java.util.regex.Pattern;

public final class DataValidation {

    /* ================= NUMBER RANGE ================= */
    public static boolean checkNumberInMinMax(int number, int min, int max) {
        return number >= min && number <= max;
    }

    /* ================= EMPTY STRING ================= */
    public static boolean checkStringEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    /* ================= LENGTH RANGE ================= */
    public static boolean checkStringLengthInRange(String value, int min, int max) {

        if (!checkStringEmpty(value)) return false;

        int length = value.trim().length();
        return length >= min && length <= max;
    }

    /* ================= YEAR VALIDATION ================= */
    public static boolean checkIfValidYear(int year) {

        int currentYear = Year.now().getValue();
        return year >= 1900 && year <= currentYear;
    }

    /* ================= PHONE VALIDATION ================= */
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^\\d{10}$");

    public static boolean checkIfValidPhoneNumber(String phoneNumber) {

        if (!checkStringEmpty(phoneNumber)) return false;

        return PHONE_PATTERN.matcher(phoneNumber.trim()).matches();
    }

    /* ================= EMAIL VALIDATION ================= */
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public static boolean checkIfValidEmail(String email) {

        if (!checkStringEmpty(email)) return false;

        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /* ================= FORMAT VALIDATION (GENERIC) ================= */
    public static boolean checkStringWithFormat(String value, String pattern) {

        if (!checkStringEmpty(value)) return false;

        return value.trim().matches(pattern);
    }
}