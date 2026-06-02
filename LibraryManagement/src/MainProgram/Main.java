package MainProgram;

/**
 *
 * @author MSI-PC
 */
import Utilities.Menu;
import ManagementObject.BookManagement;
//import java.io.DataInput;
import java.util.Scanner;
public class Main {

    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BookManagement bookMgmt = new BookManagement();
        boolean run = true;
        int choice;
        System.out.println("**********************");
        System.out.println("LIBRARY MANAGEMENT SYSTEM");
            do {
                
                System.out.println("**********************");
                System.out.println("Welcome back!");
                System.out.println("1.Manage Books\n2.Manage Members\n3.Borrowing/Returning\n4.Reports\n5.Exit");
                System.out.println("----------------------");
                System.out.println("Choose an option: ");
                choice = sc.nextInt();
                
                switch (choice) {
                    case 1 :                                           
                        bookMgmt.bookManage(); break;
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
                        run = false;
                    break;
                    default :
                        System.out.println("Invalid choice. Please try again!\n\n");
                }
            } while (run);
            sc.close();

        }

    public void main() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    }
    
