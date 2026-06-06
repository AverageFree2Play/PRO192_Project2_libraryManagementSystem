package ManagementObject;
import java.util.Scanner;
public class Reporting {
    public void reporting(){
        Scanner sc = new Scanner(System.in);
        int choice;
        System.out.println("You have enter Reporting page!");
        do{
            System.out.println("=====REPORTING=====");
            System.out.println("1.Currently borrowed books\n2.Overdue books\n3.Most popular\n4.Most borrowings members\n5.Back");
            choice=sc.nextInt();
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
    }
}
