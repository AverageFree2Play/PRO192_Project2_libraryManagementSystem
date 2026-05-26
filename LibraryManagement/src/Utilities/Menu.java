//package Utilities;
//
//import java.util.Arrays;
//import java.util.List;
//
///**
// *
// * @author MSI'
// */
//public class Menu {
//    public static void printMenu(String st) {
//        List<String> menuList = Array.asList(st.split("\\|"));
//        menuList.forEach(menuItem -> {
//            if (menuItem.equalsIgnoreCase("Select")){
//                System.out.print(menuItem);
//            }else{
//                System.out.println(menuItem);
//            }
//        });
//    }
//    public static int getUserChoise(){
//        int number=0;
//        try{
//            number = DataInput.getIntegerNumber();
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//        return number;
//    }
//}
