package ManagementObject;
import ManagementObject.BorrowManagement;

import DataObjects.IBookDAO;

import ManagementObject.MemberManagement;
import Entites.Book;
import Entites.BorrowRecord;
import Entites.Member;

import Utilities.DataInput;
import Utilities.Constants;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.List;

public class Reporting {
    private Constants con = new Constants();
    IBookDAO bookDAO;
    
    public Reporting(IBookDAO bookDAO){
        this.bookDAO = bookDAO;
    }
    
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
                    mostPopularBooks();break;
                case 4:
                    mostBorrowingMember(); break;
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
        try{
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
                Book book = bookDAO.getBookById(bookId);
                String title = (book != null)? book.getTitle():"(Unknown title)";
                System.out.printf("%-5s | %-30s | %d%n", bookId, title, qty);
            }
            System.out.println(con.longSeparator+"\n");
        }
        catch (Exception e){
            System.out.println("Failed to remove: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
        
    }
//  Overdue books
    public void overdueBooks(){
        try{
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
                    Book book = bookDAO.getBookById(r.getBookId());
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
        catch (Exception e){
            System.out.println("Failed to get overdue books: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
        
    }
//  Most popular books
    public void mostPopularBooks(){
        try{
            BorrowManagement brrwMgmt = new BorrowManagement();
            ArrayList<BorrowRecord> records = brrwMgmt.get();
            if(records.isEmpty()){
                System.out.println("No borrow records found.\n");
                return;
            }
            Map<String, Integer> countByBookId = new HashMap<>();
            for (BorrowRecord r : records) {
                countByBookId.merge(r.getBookId().toUpperCase(), 1, Integer::sum);
            }
            List<Map.Entry<String, Integer>> sorted = new ArrayList<>(countByBookId.entrySet());
            sorted.sort((a, b) -> b.getValue() - a.getValue());
            System.out.println(con.longSeparator);
            System.out.format("%-4s | %-5s | %-30s | %s%n", "Rank", "ID", "Title", "Total Borrows");
            System.out.println(con.longSeparator);
            int rank = 1;
            for (Map.Entry<String, Integer> entry : sorted) {
                Book book = bookDAO.getBookById(entry.getKey());
                String title = (book != null) ? book.getTitle() : "(Unknown title)";
                System.out.format("%-4d | %-5s | %-30s | %d%n",
                    rank++, entry.getKey(), title, entry.getValue());
            }
            System.out.println("\n");
        }catch (Exception e){
            System.out.println("Failed to get most popular books: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }
//  Most borrowing members
    public void mostBorrowingMember(){
        try{
            BorrowManagement brrwMgmt = new BorrowManagement();
            MemberManagement mbMgmt = new MemberManagement();
            mbMgmt.loadFromFile();
            ArrayList<BorrowRecord> rec = brrwMgmt.get();
            if(rec.isEmpty()){
                System.out.println("No borrow records found! \n"); return;
            }
            Map<String, Integer> countByMemberId = new HashMap<>();
            for(BorrowRecord r : rec){
                countByMemberId.merge(r.getMemberId().toUpperCase(), 1, Integer::sum);
            }
            List<Map.Entry<String, Integer>> sorted = new ArrayList<>(countByMemberId.entrySet());
            sorted.sort((a,b)->b.getValue()- a.getValue());
            System.out.println(con.longSeparator);
            System.out.format("%-4s | %-5s | %-25s | %s%n", "Rank", "ID", "Name", "Total Borrow");
            System.out.println(con.longSeparator);
            int rank = 1;
            for (Map.Entry<String, Integer> entry:sorted){
                Member mem = mbMgmt.findMemberByID(entry.getKey());
                String name = (mem != null) ? mem.getName():"(Unknown member)";
                System.out.format("%-4d | %-5s | %-25s | %d%n", rank++, entry.getKey(),name,entry.getValue());
            }
            System.out.println("\n");
        }catch(Exception e){
            System.out.println("Failed to get most borrowing members: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }
}

