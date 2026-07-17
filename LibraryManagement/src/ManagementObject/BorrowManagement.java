package ManagementObject;

import DataObjects.FileManager;
import Entites.BorrowRecord;
import Entites.Member; // Import thêm class Member
import Utilities.Constants;
import Utilities.DataInput;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class BorrowManagement implements BaseManagement<BorrowRecord> {

    private ArrayList<BorrowRecord> borrowList = new ArrayList<>();
    private FileManager fileManager = new FileManager("borrows.txt");
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private Constants con = new Constants();
    
    private MemberManagement memberMgmt = new MemberManagement();

    public BorrowManagement() {
        loadFromFile();
        memberMgmt.loadFromFile(); 
    }

    private String generateTransactionId() {
        if (borrowList.isEmpty()) {
            return "TR001";
        }
        int maxId = 0;
        for (BorrowRecord record : borrowList) {
            try {
                int idNum = Integer.parseInt(record.getId().substring(2));
                if (idNum > maxId) {
                    maxId = idNum;
                }
            } catch (Exception e) {
            }
        }
        return String.format("TR%03d", maxId + 1);
    }

    // MENU
    public void borrowMenu() {
        int choice = 0;
        System.out.println("You have entered Manage Borrow/Return session!\n");
        
        do {
            System.out.println(con.separator + " BORROW/RETURN MENU " + con.separator);
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

    // CRUD & BUSINESS LOGIC
    @Override
    public void add() {
        System.out.println("\n--- Borrow a Book ---");
        try {
            String memberId = DataInput.getString("Enter Member ID: ").toUpperCase();
            
            memberMgmt.loadFromFile();
            Member foundMember = memberMgmt.findMemberByID(memberId);
            
            if (foundMember == null) {
                System.out.println(">>> ERROR: Member ID '" + memberId + "' does not exist in the system!");
                System.out.println(">>> Transaction cancelled.\n");
                return;
            }

            String recordId = generateTransactionId();
            System.out.println("Transaction ID auto-generated: " + recordId);
            System.out.println("Borrower Name: " + foundMember.getName()); // In tên cho thân thiện!
            
            boolean isPremium = foundMember.isPremium();
            int borrowLimit = isPremium ? 5 : 3; // Premium borrow 5, normal 3
            int borrowDays = isPremium ? 30 : 14; // Premium borrow for a month, normal 2 weeks
            
            if (isPremium) {
                System.out.println("*** PREMIUM MEMBER DETECTED: Limit " + borrowLimit + " books, " + borrowDays + " days ***");
            }

            // 5. BORROW LIMIT
            int activeBorrows = 0;
            for (BorrowRecord record : borrowList) {
                if (record.getMemberId().equals(memberId) && !record.isReturned()) {
                    activeBorrows++;
                }
            }
            
            if (activeBorrows >= borrowLimit) {
                System.out.println(">>> DENIED: Member " + memberId + " has reached the limit of " + borrowLimit + " unreturned books.");
                System.out.println(">>> Please return a book before borrowing a new one.\n");
                return; 
            }

            String bookId = DataInput.getString("Enter Book ID: ").toUpperCase();

            // 6. CALCULATE DAYS RETURN
            LocalDate borrowDate = LocalDate.now();
            LocalDate dueDate = borrowDate.plusDays(borrowDays); 

            BorrowRecord record = new BorrowRecord(recordId, bookId, memberId, borrowDate, dueDate, false, null);
            borrowList.add(record);
            saveToFile();

            System.out.println("Successfully borrowed!");
            System.out.println("Borrow Date: " + borrowDate.format(DATE_FORMAT));
            System.out.println("Due Date: " + dueDate.format(DATE_FORMAT) + "\n");
        } catch (Exception e) {
            System.out.println("Error creating record: " + e.getMessage());
        }
    }

    @Override
    public void update() {
        System.out.println("\n--- Edit/Extend Borrow Record ---");
        String bookId = DataInput.getString("Enter Book ID of the record: ").toUpperCase();
        String memberId = DataInput.getString("Enter Member ID of the record: ").toUpperCase();
        
        BorrowRecord recordToEdit = null;
        
        for (BorrowRecord record : borrowList) {
            if (record.getBookId().equals(bookId) && record.getMemberId().equals(memberId) && !record.isReturned()) {
                recordToEdit = record;
                break; 
            }
        }
        
        if (recordToEdit == null) {
            System.out.println("No active borrow record found for that combination.\n");
            return;
        }
        
        // CHECK PREMIUM FOR LONGER EXTEND
        memberMgmt.loadFromFile();
        Member foundMember = memberMgmt.findMemberByID(memberId);
        boolean isPremium = (foundMember != null) && foundMember.isPremium();
        
        int extendDays = isPremium ? 14 : 7; // Premium extend 2 weeks, normal a week
        
        System.out.println("Record found! Current Due Date: " + recordToEdit.getDueDate().format(DATE_FORMAT));
        String extendStr = DataInput.getString("Extend the due date by " + extendDays + " days? (y/n): ");
        
        if (extendStr.equalsIgnoreCase("y")) {
            LocalDate newDate = recordToEdit.getDueDate().plusDays(extendDays);
            recordToEdit.setDueDate(newDate); 
            System.out.println("Due date extended! New Due Date: " + newDate.format(DATE_FORMAT) + "\n");
            saveToFile();
        } else {
            System.out.println("Update cancelled.\n");
        }
    }

    @Override
    public ArrayList<BorrowRecord> get() {
        return borrowList;
    }

    private void returnBook() {
        System.out.println("\n--- Return a Book ---");
        String bookId = DataInput.getString("Enter Book ID to return: ").toUpperCase();
        String memberId = DataInput.getString("Enter Member ID: ").toUpperCase();

        boolean found = false;
        
        for (BorrowRecord record : borrowList) {
            if (record.getBookId().equals(bookId) && record.getMemberId().equals(memberId) && !record.isReturned()) {
                
                record.setReturned(true);
                LocalDate today = LocalDate.now();
                record.setActualReturnDate(today);
                
                found = true;
                saveToFile();
                
                System.out.println(">>> SUCCESS: Book " + bookId + " has been returned.");
                System.out.println(">>> Returned By (Member ID): " + memberId);
                System.out.println(">>> Return Date: " + today.format(DATE_FORMAT));

                // HANDLE OVERDUE & FINE
                if (today.isAfter(record.getDueDate())) {
                    long daysLate = ChronoUnit.DAYS.between(record.getDueDate(), today);
                    double fineAmount = daysLate * 5000.0;
                    
                    System.out.println("-------------------------------------------------");
                    System.out.println(" [!] WARNING: RETURN BOOK OVERDUED FOR " + daysLate + " DAYS!");
                    System.out.println(" [!] FINE AMOUNT TO BE COLLECTED: " + String.format("%,.0f", fineAmount) + " VND");
                    System.out.println("-------------------------------------------------");
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
                double estimatedFine = daysLate * 5000.0;
                
                System.out.println("- Trans ID: " + record.getId() + 
                                   " | Book ID: " + record.getBookId() + 
                                   " | Member ID: " + record.getMemberId() + 
                                   " | Days Late: " + daysLate +
                                   " | Estimated Fine: " + String.format("%,.0f", estimatedFine) + " VND");
            }
        }

        if (!hasOverdue) {
            System.out.println("There are currently no overdue books to process.\n");
            return;
        }

        String transId = DataInput.getString("\nEnter the Transaction ID to process the overdue return (or press Enter to cancel): ").toUpperCase();
        if (transId.trim().isEmpty()) {
            System.out.println("Action canceled.\n");
            return;
        }

        boolean found = false;
        for (BorrowRecord record : borrowList) {
            if (record.getId().equals(transId) && !record.isReturned() && today.isAfter(record.getDueDate())) {
                record.setReturned(true);
                record.setActualReturnDate(today);
                found = true;
                saveToFile();
                
                long daysLate = ChronoUnit.DAYS.between(record.getDueDate(), today);
                double finalFine = daysLate * 5000.0;
                
                System.out.println(">>> SUCCESS: Overdue Trans ID " + transId + " successfully returned.");
                System.out.println(">>> Returned By (Member ID): " + record.getMemberId());
                System.out.println("-------------------------------------------------");
                System.out.println(" TOTAL FINE AMOUNT TO BE COLLECTED DIRECTLY: " + String.format("%,.0f", finalFine) + " VND");
                System.out.println("-------------------------------------------------\n");
                break;
            }
        }

        if (!found) {
            System.out.println("Could not find an active overdue record matching that Transaction ID.\n");
        }
    }

    private void viewRecords() {
        if (borrowList.isEmpty()) {
            System.out.println("No records found.\n");
            return;
        }
        System.out.println(con.longSeparator);
        System.out.format("%-8s | %-10s | %-10s | %-12s | %-12s | %-10s | %-12s%n", 
            "Trans ID", "Book ID", "Member ID", "Borrow Date", "Due Date", "Status", "Return Date");
        System.out.println(con.longSeparator);
        for (BorrowRecord r : borrowList) {
            String returnDateStr = (r.getActualReturnDate() != null) ? r.getActualReturnDate().format(DATE_FORMAT) : "Not yet";
            
            System.out.format("%-8s | %-10s | %-10s | %-12s | %-12s | %-10s | %-12s%n", 
                r.getId(), r.getBookId(), r.getMemberId(), r.getBorrowDate().format(DATE_FORMAT), 
                r.getDueDate().format(DATE_FORMAT), (r.isReturned() ? "Returned" : "Active"), returnDateStr);
        }
        System.out.println(con.longSeparator + "\n");
    }

    // FILE I/O
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
                
                // Đọc format 7 trường dữ liệu
                if (parts.length == 7) {
                    LocalDate actualReturn = parts[6].equals("NULL") ? null : LocalDate.parse(parts[6], DATE_FORMAT);
                    
                    BorrowRecord record = new BorrowRecord(
                        parts[0], // recordId
                        parts[1], // bookId
                        parts[2], // memberId
                        LocalDate.parse(parts[3], DATE_FORMAT), 
                        LocalDate.parse(parts[4], DATE_FORMAT), 
                        Boolean.parseBoolean(parts[5]),
                        actualReturn
                    );
                    borrowList.add(record);
                }
            }
        } catch (IOException e) {
        } catch (Exception e) {
            System.out.println("Corrupted data found in borrows.txt: " + e.getMessage());
        }
    }
}