package Entites;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BorrowRecord extends Entity {
    
    private String bookId;
    private String memberId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private boolean isReturned;
    private LocalDate actualReturnDate; // Lưu ngày trả thực tế

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public BorrowRecord(String recordId, String bookId, String memberId, LocalDate borrowDate, LocalDate dueDate, boolean isReturned, LocalDate actualReturnDate) throws Exception {
        super(recordId); // Gọi constructor của lớp cha Entity
        
        setId(recordId); 
        setBookId(bookId);
        setMemberId(memberId);
        setBorrowDate(borrowDate);
        setDueDate(dueDate);
        setReturned(isReturned);
        this.actualReturnDate = actualReturnDate;
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

    public LocalDate getActualReturnDate() { return actualReturnDate; }
    public void setActualReturnDate(LocalDate actualReturnDate) { this.actualReturnDate = actualReturnDate; }

    // Format lưu file (7 trường dữ liệu)
    public String toFileString() {
        String returnDateStr = (actualReturnDate != null) ? actualReturnDate.format(DATE_FORMAT) : "NULL";
        return getId() + "|" + bookId + "|" + memberId + "|" + borrowDate.format(DATE_FORMAT) + "|" + dueDate.format(DATE_FORMAT) + "|" + isReturned + "|" + returnDateStr;
    }
}