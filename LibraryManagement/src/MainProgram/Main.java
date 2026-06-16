package MainProgram;

/**
 *
 * @author MSI-PC
 */
import Utilities.Menu;
import ManagementObject.BookManagement;
import ManagementObject.BorrowManagement;
import ManagementObject.MemberManagement;
import ManagementObject.Reporting;
import Utilities.Constants;
//import java.io.DataInput;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // CLASSES
        Scanner sc = new Scanner(System.in);
        BookManagement bookMgmt = new BookManagement();
        Constants con = new Constants();
        
        BorrowManagement brwMgmt = new BorrowManagement();
        MemberManagement mbMgmt = new MemberManagement();
        Reporting rp = new Reporting();
        // VARIABLES
        boolean run = true;
        int choice;
        String Seperator = con.Seperator;
        
        System.out.println(Seperator+"\n");

        do {
            System.out.println("LIBRARY MANAGEMENT SYSTEM");
            System.out.println(Seperator);
            System.out.println("Welcome back!");
            System.out.println("1.Manage Books\n2.Manage Members\n3.Borrowing/Returning\n4.Reports\n5.Exit");
            System.out.println(Seperator);
            System.out.println("Choose an option: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    bookMgmt.bookMenu();
                    break;
                case 2:
                    mbMgmt.memMenu();
                    break;
                case 3:
                    brwMgmt.borrowMenu();
                    break;
                case 4:
                    rp.reportMenu();
                    break;
                case 5:
                    System.out.println("Good bye!\n");
                    run = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again!\n\n");
            }
        } while (run);
        sc.close();
    }

}
