/**
 *
 * @author MSI-PC
 */
//import Utilities.Menu;
//import java.io.DataInput;
import java.util.Scanner;
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        System.out.println("Library Management System");
            do {
                
                System.out.println("***************Main Menu***************");
                System.out.println("1.Book Management|2.Member Management|3.Borrowing Management|4.Reporting|5.Exit|Select:");
                
                choice = sc.nextInt();
                
                switch (choice) {
                    case 1 :                                           
                        System.out.println("You chose Book Management\n");
                    break;
                    case 2 :                       
                        System.out.println("You chose Member Management\n");
                    break;
                    case 3 :
                        System.out.println("You chose Borrowing Management\n");
                    break;
                    case 4 :
                        System.out.println("You chose Reporting\n");
                    break;
                    case 5 :
                        System.out.println("Good bye!\n");
                    break;
                    default :
                        System.out.println("Invalid choice. Please try again!\n\n");
                }
            } while (choice!=5);

        }
    }
    
