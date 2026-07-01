package ManagementObject;
import ManagementObject.BorrowManagement;
import Entites.Book;
import Entites.BorrowRecord;
import Utilities.DataInput;
import Utilities.Constants;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
public class Reporting {
    private Constants con = new Constants();
    public void reportMenu(){
        
        int choice;
        try{
        do{
            System.out.println(con.separator+"REPORTING"+con.separator);
            System.out.println("1.Currently borrowed books\n2.Overdue books\n3.Most popular\n4.Most borrowings members\n5.Back");
            choice=DataInput.getIntegerNumber();
            switch(choice){
                case 1:
                    currentBorrow();break;
                case 2:
                    overdueBooks();break;
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
//  Generate currently borrowed
    public void currentBorrow(){
        BookManagement bookMgmt = new BookManagement();
        bookMgmt.loadFromFile();
        
        BorrowManagement brrwMgmt = new BorrowManagement();
        ArrayList<BorrowRecord> records = brrwMgmt.get();
        
        // Count active borrows, sorted by ID
        Map<String, Integer> borrowedQtyByBookId = new TreeMap<>();
        for (BorrowRecord record : records) {
            if (!record.isReturned()) {
                String bookId = record.getBookId().toUpperCase();
                borrowedQtyByBookId.merge(bookId, 1, Integer::sum);
            }
        }
 
        if (borrowedQtyByBookId.isEmpty()) {
            System.out.println("No books are currently borrowed.\n");
            return;
        }
        System.out.println(con.longSeparator);
        System.out.format("%-5s | %-30s | %s%n", "ID", "Title", "Borrowed Qty");
        System.out.println(con.longSeparator);
        
        for (Map.Entry<String, Integer>entry:borrowedQtyByBookId.entrySet()){
            String bookId = entry.getKey();
            int qty = entry.getValue();
            Book book = bookMgmt.findBookByID(bookId);
            String title = (book != null)? book.getTitle():"(Unknown title)";
            System.out.printf("%-5s | %-30s | %d%n", bookId, title, qty);
        }
        System.out.println(con.longSeparator+"\n");
    }
//  Overdue books
    public void overdueBooks(){
        BookManagement bookMgmt = new BookManagement();
        bookMgmt.loadFromFile();
        BorrowManagement brrwMgmt = new BorrowManagement();
        ArrayList<BorrowRecord> records = brrwMgmt.get();
        LocalDate today = LocalDate.now();
        boolean hasOverdue = false;
        System.out.println(con.longSeparator);
        System.out.format("%-5s | %-25s | %-10s | %-12s | %s%n", "ID", "Title","Member ID","Due Date","Overdue for");
        System.out.println(con.longSeparator);
        for (BorrowRecord r : records){
            if(!r.isReturned() && today.isAfter(r.getDueDate())){
                hasOverdue = true;
                long daysLate = ChronoUnit.DAYS.between(r.getDueDate(), today);
                Book book = bookMgmt.findBookByID(r.getBookId());
                String title = (book!=null)?book.getTitle():"(Unknown title)";
                System.out.format("%-5s | %-25s | %-10s | %-12s | %d days%n",
                        r.getBookId(),
                        title,
                        r.getMemberId(),
                        r.getDueDate(),
                        daysLate);
            }
        }if(!hasOverdue){
            System.out.println("No overdue books yet.");
        }
        System.out.println("\n");
    }
}
