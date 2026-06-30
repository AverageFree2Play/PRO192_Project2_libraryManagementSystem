package Entites;

/**
 *
 * @author Trung Kien
 */
import java.time.LocalDate;
import java.time.format.DateTimeFormatter; // UPDATED

public class BorrowRecord {
    private String bookId;
    private String memberId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private boolean isReturned;

    // ADDED: Standard formatter for saving to text file
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
    public void setReturned(boolean returned) { this.isReturned = returned; }

    // Format for saving to the text file (UPDATED with formatter)
    public String toFileString() {
        return bookId + "|" + memberId + "|" + borrowDate.format(DATE_FORMAT) + "|" + dueDate.format(DATE_FORMAT) + "|" + isReturned;
    }
}