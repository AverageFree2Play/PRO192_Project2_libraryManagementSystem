package ManagementObject;

import java.util.Scanner;

public class BorrowManagement {
    public void borrowMenu(){
        Scanner sc = new Scanner(System.in);
        
        int choice;
        System.out.println("You have enter Manage Borrow/Return session!\n");
        do{
            System.out.println("=====BORROW/RETURN MENU=====");
            System.out.println("1. Borrow book\n2. Return book\n3. Return overdue book\n4. Back\n");
            System.out.println("Choose an option(1-6): ");
      
            choice = sc.nextInt();
            switch(choice){
                case 1:
                    System.out.println("You chose Borrow book!"); break;
                case 2:
                    System.out.println("You chose Return book!"); break;
                case 3:
                    System.out.println("You chose Return overdue book!"); break;
                
                case 4:
                    break;
                default:
                    System.out.println("Invalid choice. Please try again!\n\n");
            }
        }while(choice!=4);
    }
}
