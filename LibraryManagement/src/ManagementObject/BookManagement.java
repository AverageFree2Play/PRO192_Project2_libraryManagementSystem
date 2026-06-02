package ManagementObject;
import java.util.Scanner;
public class BookManagement {
    public void bookManage(){
        Scanner sc = new Scanner(System.in);
        
        int choice;
        System.out.println("You have enter Manage Books session!\n");
        do{
            System.out.println("=====BOOK MANAGE MENU=====");
            System.out.println("1. Add book\n2. Update book\n3. Remove book\n4. View all books\n5. Search books\n6. Back\n");
            System.out.println("Choose an option(1-6): ");
      
            choice = sc.nextInt();
            switch(choice){
                case 1:
                    System.out.println("You chose Add book!"); break;
                case 2:
                    System.out.println("You chose Update book!"); break;
                case 3:
                    System.out.println("You chose Remove book!"); break;
                case 4:
                    System.out.println("You chose View all books!"); break;
                case 5:
                    System.out.println("You chose Search books!"); break;
                case 6:
                    break;
                default:
                    System.out.println("Invalid choice. Please try again!\n\n");
            }
        }while(choice!=6);
    }
}
