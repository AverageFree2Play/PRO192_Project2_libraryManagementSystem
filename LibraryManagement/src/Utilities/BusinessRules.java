package Utilities;
import Entites.Book;
import Entites.BorrowRecord;
import Entites.Member;
import java.util.List;
import java.time.LocalDate;

public class BusinessRules {
 /**
 * ==============================================================================
 *                         SYSTEM BUSINESS RULES MAP
 * ==============================================================================
 * BR1: Each Book ID and Member ID must be unique and cannot be modified.
 *      -> Implemented in: BookManagement.add() and MemberManagement.add() 
 *         (Checks for existing IDs). The Entity.java parent class has no public 
 *         setter for the ID after initialization, preventing modification.
 * 
 * BR2: Book title, author, and genre must not be empty.
 *      -> Implemented in: DataInput.java (using loops to prevent empty string 
 *         inputs) and BookManagement.add() / update().
 * 
 * BR3: A member must exist before borrowing a book.
 *      -> Implemented in: BorrowManagement.add() (Validates the inputted 
 *         Member ID against the MemberManagement data before proceeding).
 * 
 * BR4: A book must be available (stock > 0) before it can be borrowed.
 *      -> Implemented in: BorrowManagement.add() (Checks the target Book's 
 *         quantity variable. Throws an error/cancels if it is 0).
 * 
 * BR5: A book can only be borrowed if the member has not exceeded their 
 *      borrowing limit (e.g., 3 books at a time).
 *      -> Implemented in: BorrowManagement.add() (Loops through active 
 *         borrow records and counts how many unreturned books the member has).
 * 
 * BR6: Borrow date must be current or in the past; return date must be after 
 *      borrow date.
 *      -> Implemented in: BorrowManagement.add() (Forces Borrow Date to 
 *         LocalDate.now(), and Due Date to LocalDate.now().plusDays(14), 
 *         making it mathematically impossible to violate).
 * 
 * BR7: Overdue fine is calculated based on the number of days past the due date.
 *      -> Implemented in: BorrowManagement.returnOverdueBook() (Uses 
 *         ChronoUnit.DAYS.between() multiplied by FINE_PER_DAY).
 * 
 * BR8: Book stock is reduced upon borrowing and increased upon returning.
 *      -> Implemented in: BorrowManagement.add() (Calls book.setQuantity(q-1)) 
 *         and BorrowManagement.returnBook() (Calls book.setQuantity(q+1)).
 * 
 * BR9: All inputs must be validated before processing.
 *      -> Implemented in: DataInput.java (Centralized try-catch loops ensuring 
 *         no invalid data crashes the application).
 * 
 * BR10: Popular books are determined by the total number of times they have 
 *       been borrowed.
 *       -> Implemented in: Reporting.java (Scans the borrow list and uses 
 *          a counting algorithm to rank the highest frequency Book IDs).
 * ==============================================================================
 */

    public static final int NORMAL_BORROW_LIMIT = 3;     // BR5: Giới hạn mượn 3 cuốn
    public static final int PREMIUM_BORROW_LIMIT = 5;

    public static final int NORMAL_BORROW_DAYS = 14;
    public static final int PREMIUM_BORROW_DAYS = 30;
    
    public static final int NORMAL_EXTEND_DAYS = 7;
    public static final int PREMIUM_EXTEND_DAYS = 14;

    public static final double FINE_PER_DAY = 5000.0;   // BR7: Phạt 5,000 VND/ngày

    //--------------- BUSINESS RULE METHODS -------------------

    // BR2: Book title, author, and genre must not be empty.
    public static void validateBookDetails(String title, String author, String genre) throws Exception {
        if (title == null || title.trim().isEmpty()) {
            throw new Exception("BR2 Violation: Book title cannot be empty.");
        }
        if (author == null || author.trim().isEmpty()) {
            throw new Exception("BR2 Violation: Book author cannot be empty.");
        }
        if (genre == null || genre.trim().isEmpty()) {
            throw new Exception("BR2 Violation: Book genre cannot be empty.");
        }
    }

    
    // BR3: A member must exist before borrowing a book.
    public static void checkMemberExists(Member member, String memberId) throws Exception {
        if (member == null) {
            throw new Exception("BR3 Violation: Member ID '" + memberId + "' does not exist in the system.");
        }
    }

    // BR4: A book must be available (stock > 0) before it can be borrowed.
    public static void checkBookAvailability(Book book, String bookId) throws Exception {
        if (book == null) {
            throw new Exception("BR4 Violation: Book ID '" + bookId + "' does not exist.");
        }
        if (book.getQuantity() <= 0) {
            throw new Exception("BR4 Violation: Book '" + book.getTitle() + "' is currently out of stock.");
        }
    }

    // BR5: A book can only be borrowed if the member has not exceeded their borrowing limit.
    public static void checkBorrowLimit(List<BorrowRecord> allBorrows, String memberId, int limit) throws Exception {
        int activeBorrows = 0;
        for (BorrowRecord record : allBorrows) {
            if (record.getMemberId().equalsIgnoreCase(memberId) && !record.isReturned()) {
                activeBorrows++;
            }
        }
        if (activeBorrows >= limit) {
            throw new Exception("BR5 Violation: Member has reached the maximum borrowing limit (" + limit + " books).");
        }
    }

    // BR6: Borrow date must be current or in the past; return date must be after borrow date.
    public static void validateDates(LocalDate borrowDate, LocalDate returnDate) throws Exception {
        LocalDate today = LocalDate.now();
        if (borrowDate.isAfter(today)) {
            throw new Exception("BR6 Violation: Borrow date cannot be in the future.");
        }
        if (returnDate != null && !returnDate.isAfter(borrowDate)) {
            throw new Exception("BR6 Violation: Return/Due date must be strictly after the borrow date.");
        }
    }

    // BR7: Overdue fine is calculated based on the number of days past the due date.
    public static double calculateOverdueFine(long daysLate) {
        if (daysLate <= 0) {
            return 0.0;
        }
        return daysLate * FINE_PER_DAY;
    }
}
