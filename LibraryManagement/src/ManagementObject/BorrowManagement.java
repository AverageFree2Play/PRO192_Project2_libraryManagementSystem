package ManagementObject;

/**
 *
 * @author Trung Kien
 */
import DataObjects.FileManager;
import Entites.BorrowRecord;
import Utilities.Constants;
import Utilities.DataInput;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class BorrowManagement implements BaseManagement<BorrowRecord> {

    private ArrayList<BorrowRecord> borrowList = new ArrayList<>();
    private FileManager fileManager = new FileManager("borrows.txt");

    // Constructor loads existing data
    public BorrowManagement() {
        loadFromFile();
    }

    public void borrowMenu() {
        int choice = 0;
        System.out.println("You have entered Manage Borrow/Return session!\n");
        
        do {
            System.out.println(Constants.Seperator + " BORROW/RETURN MENU " + Constants.Seperator);
            System.out.println("1. Borrow book (Add)");
            System.out.println("2. Return book");
            System.out.println("3. Return overdue book");
            System.out.println("4. Edit a record manually (Update)");
            System.out.println("5. Delete a record (Delete)");
            System.out.println("6. View all records (Read)");
            System.out.println("7. Back\n");
            
            try {
                choice = DataInput.getIntegerNumber("Choose an option(1-7): ");
                
                switch(choice) {
                    case 1: add(); break;
                    case 2: returnBook(); break;
                    case 3: returnOverdueBook(); break;
                    case 4: update(); break; 
                    case 5: delete(); break; 
                    case 6: viewRecords(); break;
                    case 7: System.out.println("Exiting Borrow/Return menu...\n"); break;
                    default: System.out.println("Invalid choice. Please choose a number between 1 and 7!\n");
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a valid number.\n");
            }
            
        } while(choice != 7);
    }

    // CRUD IMPLEMENTATIONS
    @Override
    public void add() {
        System.out.println("\n--- Borrow a Book ---");
        String bookId = DataInput.getString("Enter Book ID: ").toUpperCase();
        String memberId = DataInput.getString("Enter Member ID: ").toUpperCase();

        // 14-day borrow period
        LocalDate borrowDate = LocalDate.now();
        LocalDate dueDate = borrowDate.plusDays(14); 

        BorrowRecord record = new BorrowRecord(bookId, memberId, borrowDate, dueDate, false);
        borrowList.add(record);
        saveToFile();

        System.out.println("Successfully borrowed!");
        System.out.println("Borrow Date: " + borrowDate);
        System.out.println("Due Date: " + dueDate + "\n");
    }

    @Override
    public void update() {
        System.out.println("\n--- Edit Borrow Record ---");
        String bookId = DataInput.getString("Enter Book ID of the record: ").toUpperCase();
        String memberId = DataInput.getString("Enter Member ID of the record: ").toUpperCase();
        
        BorrowRecord recordToEdit = null;
        
        for (BorrowRecord record : borrowList) {
            if (record.getBookId().equals(bookId) && record.getMemberId().equals(memberId) && !record.isReturned()) {
                recordToEdit = record;
                break; // Grabs the active borrow
            }
        }
        
        if (recordToEdit == null) {
            System.out.println("No active borrow record found for that combination.\n");
            return;
        }
        
        System.out.println("Record found! Current Due Date: " + recordToEdit.getDueDate());
        String extendStr = DataInput.getString("Do you want to extend the due date by 7 days? (y/n): ");
        
        if (extendStr.equalsIgnoreCase("y")) {
            LocalDate newDate = recordToEdit.getDueDate().plusDays(7);
            recordToEdit.setDueDate(newDate);
            System.out.println("Due date extended! New Due Date: " + newDate + "\n");
            saveToFile();
        } else {
            System.out.println("Update cancelled.\n");
        }
    }

    @Override
    public void delete() {
        System.out.println("\n--- Delete Borrow Record ---");
        String bookId = DataInput.getString("Enter Book ID of the record: ").toUpperCase();
        String memberId = DataInput.getString("Enter Member ID of the record: ").toUpperCase();
        
        BorrowRecord recordToRemove = null;
        for (BorrowRecord record : borrowList) {
            if (record.getBookId().equals(bookId) && record.getMemberId().equals(memberId)) {
                recordToRemove = record;
                break;
            }
        }
        
        if (recordToRemove == null) {
            System.out.println("Record not found.\n");
            return;
        }
        
        String confirm = DataInput.getString("Are you sure you want to completely delete this transaction? (y/n): ");
        if (confirm.equalsIgnoreCase("y")) {
            borrowList.remove(recordToRemove);
            saveToFile();
            System.out.println("Record permanently deleted!\n");
        } else {
            System.out.println("Deletion cancelled.\n");
        }
    }

    @Override
    public ArrayList<BorrowRecord> get() {
        return borrowList;
    }

    // LIBRARY LOGIC METHODS
    private void returnBook() {
        System.out.println("\n--- Return a Book ---");
        String bookId = DataInput.getString("Enter Book ID to return: ").toUpperCase();
        String memberId = DataInput.getString("Enter Member ID: ").toUpperCase();

        boolean found = false;
        
        for (BorrowRecord record : borrowList) {
            if (record.getBookId().equals(bookId) && record.getMemberId().equals(memberId) && !record.isReturned()) {
                record.setReturned(true);
                found = true;
                saveToFile();
                System.out.println("Book " + bookId + " successfully returned!");

                LocalDate today = LocalDate.now();
                if (today.isAfter(record.getDueDate())) {
                    long daysLate = ChronoUnit.DAYS.between(record.getDueDate(), today);
                    System.out.println("WARNING: This book was returned " + daysLate + " days late.");
                }
                System.out.println();
                break; 
            }
        }

        if (!found) {
            System.out.println("No active borrow record found for this Book ID and Member ID.\n");
        }
    }

    private void returnOverdueBook() {
        System.out.println("\n--- Process Overdue Books ---");
        LocalDate today = LocalDate.now();
        boolean hasOverdue = false;

        System.out.println("Currently Overdue Records:");
        for (BorrowRecord record : borrowList) {
            if (!record.isReturned() && today.isAfter(record.getDueDate())) {
                hasOverdue = true;
                long daysLate = ChronoUnit.DAYS.between(record.getDueDate(), today);
                System.out.println("- Book ID: " + record.getBookId() + 
                                   " | Member ID: " + record.getMemberId() + 
                                   " | Days Late: " + daysLate);
            }
        }

        if (!hasOverdue) {
            System.out.println("There are currently no overdue books to process.\n");
            return;
        }

        String bookId = DataInput.getString("\nEnter the Book ID to process the overdue return (or press Enter to cancel): ").toUpperCase();
        if (bookId.trim().isEmpty()) {
            System.out.println("Action canceled.\n");
            return;
        }

        String memberId = DataInput.getString("Enter the Member ID: ").toUpperCase();

        boolean found = false;
        for (BorrowRecord record : borrowList) {
            if (record.getBookId().equals(bookId) && record.getMemberId().equals(memberId) && !record.isReturned() && today.isAfter(record.getDueDate())) {
                record.setReturned(true);
                found = true;
                saveToFile();
                System.out.println("Overdue Book " + bookId + " successfully returned.");
                System.out.println("*** Please collect applicable late fees from the member. ***\n");
                break;
            }
        }

        if (!found) {
            System.out.println("Could not find an active overdue record matching those details.\n");
        }
    }

    private void viewRecords() {
        if (borrowList.isEmpty()) {
            System.out.println("No records found.\n");
            return;
        }
        System.out.println(Constants.LongSeperator);
        System.out.format("%-10s | %-10s | %-12s | %-12s | %-10s%n", "Book ID", "Member ID", "Borrow Date", "Due Date", "Status");
        System.out.println(Constants.LongSeperator);
        for (BorrowRecord r : borrowList) {
            System.out.format("%-10s | %-10s | %-12s | %-12s | %-10s%n", 
                r.getBookId(), r.getMemberId(), r.getBorrowDate(), r.getDueDate(), (r.isReturned() ? "Returned" : "Active"));
        }
        System.out.println(Constants.LongSeperator + "\n");
    }

    // FILE I/O METHODS
    private void saveToFile() {
        StringBuilder strb = new StringBuilder();
        for (BorrowRecord record : borrowList) {
            strb.append(record.toFileString()).append(System.lineSeparator());
        }
        try {
            fileManager.saveDataToFile(strb.toString());
        } catch (IOException e) {
            System.out.println("Failed to save borrow records: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        try {
            List<String> lines = fileManager.readDataFromFile();
            for (String line : lines) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    BorrowRecord record = new BorrowRecord(
                        parts[0], 
                        parts[1], 
                        LocalDate.parse(parts[2]), 
                        LocalDate.parse(parts[3]), 
                        Boolean.parseBoolean(parts[4])
                    );
                    borrowList.add(record);
                }
            }
        } catch (IOException e) {
            // Normal behavior on first run
        } catch (Exception e) {
            System.out.println("Corrupted data found in borrows.txt: " + e.getMessage());
        }
    }
}