package DataObjects;

import Entites.Book;

import java.util.List;
import java.util.ArrayList;

public interface IBookDAO {
    ArrayList<Book> getBooks();
    Book getBookById(String id) throws Exception;
    
    void addBook(Book book) throws Exception;
    void updateBook(Book book) throws Exception;
    void removeBook(Book book) throws Exception;
    void saveBooksToFile() throws Exception;
    
    ArrayList<Book> searchBook(String keyword) throws Exception;
}
