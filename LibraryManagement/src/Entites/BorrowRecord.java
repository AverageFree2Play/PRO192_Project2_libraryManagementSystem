package Entites;
/**
 *
 * @author Trung Kien
 */
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Inherit from abstract Entity class
public class BorrowRecord extends Entity {
    
    private String bookId;
    private String memberId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private boolean isReturned;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // 2. Updated Constructor matching Book pattern
    public BorrowRecord(String recordId, String bookId, String memberId, LocalDate borrowDate, LocalDate dueDate, boolean isReturned) throws Exception {
        super(recordId); // Passes the ID to the Entity parent class
        
        setId(recordId); // Uses Entity's setter
        setBookId(bookId);
        setMemberId(memberId);
        setBorrowDate(borrowDate);
        setDueDate(dueDate);
        setReturned(isReturned);
    }

    // --- Getters and Setters ---
    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }

    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }

    public LocalDate getBorrowDate() { return borrowDate; }
    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }
    
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; } 
    
    public boolean isReturned() { return isReturned; }
    public void setReturned(boolean returned) { this.isReturned = returned; }

    // 3. Updated File format to include the inherited ID
    public String toFileString() {
        return getId() + "|" + bookId + "|" + memberId + "|" + borrowDate.format(DATE_FORMAT) + "|" + dueDate.format(DATE_FORMAT) + "|" + isReturned;
    }
}