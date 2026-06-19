package Utilities;

import java.time.Year;
import java.util.regex.Pattern;

public final class DataValidation {
     //------------------------------------------------------
     public static boolean checkNumberInMinMax(int number,int min, int max){
         boolean result = true;
         if(number < min || number > max){
             result = false;                     
         }
         return result;
    }
    //------------------------------------------------------
    public static boolean checkStringEmpty(String value) {
        boolean result = true;
         if(value.isEmpty()){
             result = false;                     
         }
         return result;        
    }
    //------------------------------------------------------
    public static boolean checkStringLengthInRange(String value, int min, int max) {
        boolean result = true;
        int length;
        if (!checkStringEmpty(value)) {
            result = false;
        } else {
            length = value.length();
            if (length < min || length > max) {
                result = false;
            }
        }
        return result;
    }
     //------------------------------------------------------
     public static boolean checkStringWithFormat(String value,String pattern){
         boolean result = false;
         if(value.matches(pattern)){
             result = true;                     
         }
         return result;
    }
    //--------------------------------------------------  
    public static boolean checkIfValidYear(int year) {
        int currentYear = Year.now().getValue();
      
        return year >= 1 && year <= currentYear + 10;
    }
    //--------------------------------------------------  
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{3}\\.\\d{3}\\.\\d{4}$");

    public static boolean checkIfValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }
        return PHONE_PATTERN.matcher(phoneNumber).matches();
    }
}
