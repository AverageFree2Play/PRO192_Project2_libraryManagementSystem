package MainProgram;
/**
 *
 * @author Trung Kien
 */
import ManagementObject.BookManagement;
import ManagementObject.BorrowManagement;
import ManagementObject.MemberManagement;
import ManagementObject.Reporting;

import Utilities.Constants;
import Utilities.DataInput;

import DataObjects.BookDAO;
import DataObjects.IBookDAO;

import Utilities.Menu;

public class Main {

    public static void main(String[] args) {
        // CLASSES
        BorrowManagement brwMgmt = new BorrowManagement();
        MemberManagement mbMgmt = new MemberManagement();
        Constants con = new Constants();
        
        // VARIABLES
        boolean run = true;
        int choice = 0;
        
        System.out.println(con.separator + "\n");
        
        do {
            try { 
                IBookDAO bookService = new BookDAO();
                
                System.out.println("LIBRARY MANAGEMENT SYSTEM");
                System.out.println(con.separator);
                System.out.println("Welcome back!");
                System.out.println("1. Manage Books\n2. Manage Members\n3. Borrowing/Returning\n4. Reports\n5. Exit");
                System.out.println(con.separator);
                
                choice = DataInput.getIntegerNumber("Choose an option: ");

                switch (choice) {
                    case 1:
                        Menu.manageBook(bookService);
                        break;
                    case 2:
                        mbMgmt.memberMenu();
                        break;
                    case 3:
                        brwMgmt.borrowMenu();
                        break;
                    case 4:
                        Menu.reporting(bookService);
                        break;
                    case 5:
                        System.out.println("Good bye!\n");
                        run = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again!\n\n");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage() + " Please enter a valid number.\n");
            }
        } while (run);
    }
}