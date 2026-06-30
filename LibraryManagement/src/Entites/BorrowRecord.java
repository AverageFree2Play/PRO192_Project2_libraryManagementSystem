package Entites;

/**
 *
 * @author Trung Kien
 */
import java.time.LocalDate;

public class BorrowRecord {
    private String bookId;
    private String memberId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private boolean isReturned;

    public BorrowRecord(String bookId, String memberId, LocalDate borrowDate, LocalDate dueDate, boolean isReturned) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.isReturned = isReturned;
    }

    // --- Getters and Setters ---
    public String getBookId() { return bookId; }
    public String getMemberId() { return memberId; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; } 
    
    public boolean isReturned() { return isReturned; }
    public void setReturned(boolean returned) { isReturned = returned; }

    // Format for saving to the text file
    public String toFileString() {
        return bookId + "|" + memberId + "|" + borrowDate + "|" + dueDate + "|" + isReturned;
    }
}