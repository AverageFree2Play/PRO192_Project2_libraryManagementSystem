package ManagementObject;
import ManagementObject.BorrowManagement;
import Utilities.DataInput;
import Utilities.Constants;
public class Reporting {
    private Constants con = new Constants();
    public void reportMenu(){
        
        int choice;
        System.out.println("You have enter Reporting page!");
        try{
        do{
            System.out.println(con.separator+"REPORTING"+con.separator);
            System.out.println("1.Currently borrowed books\n2.Overdue books\n3.Most popular\n4.Most borrowings members\n5.Back");
            choice=DataInput.getIntegerNumber();
            switch(choice){
                case 1:
                    System.out.println("You chose print currently borrowed books!");break;
                case 2:
                    System.out.println("You chose print overdue books!");break;
                case 3:
                    System.out.println("You chose print most popular books!");break;
                case 4:
                    System.out.println("You chose print members with most borrowings!"); break;
                case 5:
                    break;
                default:
                    System.out.println("Invalid choice. Please try again!");
            }
        }while(choice!=5);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
