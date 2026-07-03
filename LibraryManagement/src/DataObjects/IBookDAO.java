package DataObjects;

import Entites.Book;
import java.util.List;

public interface IBookDAO {
    List<Book> getBooks() throws Exception;
    Book getBookById(String id) throws Exception;
    
    void addBook(Book book) throws Exception;
    void updateBook(Book book) throws Exception;
    void removeBook(Book book) throws Exception;
    void saveBooksToFile() throws Exception;
    
    List<Book> searchBook(String keyword) throws Exception;
}
