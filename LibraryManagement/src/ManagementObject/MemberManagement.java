package ManagementObject;
import java.util.Scanner;
public class MemberManagement {
    public void memManage(){
        Scanner sc = new Scanner(System.in);
        int choice;
        System.out.println("You have enter Member Management session!");
        do{
            System.out.println("=====MEMBER=====");
            System.out.println("1. Add member\n2. Edit member\n3. Remove member\n4. Back");
            System.out.println("Choose an option: ");
            choice = sc.nextInt();
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
            
        }while(choice!=4);
    }
}
