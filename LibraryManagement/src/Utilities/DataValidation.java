package Utilities;
/**
 * @author SwordLake
 */
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
    //More the methods here..........
}
