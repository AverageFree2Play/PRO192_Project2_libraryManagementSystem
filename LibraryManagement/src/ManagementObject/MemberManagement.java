package ManagementObject;
import java.util.Scanner;
import Utilities.Constants;
import Utilities.DataInput;
public class MemberManagement {
    private Constants con = new Constants();
    public void memMenu(){
        
        int choice;
        
        System.out.println("You have enter Member Management session!");
        try{do{
            System.out.println(con.Seperator+"MEMBER" + con.Seperator);
            System.out.println("1. Add member\n2. Edit member\n3. Remove member\n4. Back");
            System.out.println("Choose an option: ");
            choice = DataInput.getIntegerNumber();
            switch(choice){
                case 1:
                    System.out.println("You chose Add member!"); break;
                case 2:
                    System.out.println("You chose Edit member!"); break;
                case 3:
                    System.out.println("You chose Remove member!"); break;
                case 4:
                    break;
                default:
                    System.out.println("Invalid choice. Please try again!\n\n");
            }
            
        }while(choice!=4);}
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
