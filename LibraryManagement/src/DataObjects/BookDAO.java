package DataObjects;

import Entites.Book;
import Utilities.Constants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class BookDAO implements IBookDAO{
    private ArrayList<Book> bookList = new ArrayList<>();
    private FileManager filemanager = new FileManager("books.txt");
    
    public BookDAO() throws Exception{
        loadFromFile();
    }
    
    @Override
    public void saveBooksToFile(){
        StringBuilder strb = new StringBuilder();
        for (Book b : bookList){
            strb.append(b.getId()).append("|");
            strb.append(b.getTitle()).append("|");
            strb.append(b.getAuthor()).append("|");
            strb.append(b.getGenre()).append("|");
            strb.append(b.getPubYear()).append("|");
            strb.append(b.getQuantity()).append("|");
            strb.append(System.lineSeparator());
        }
        try{
            filemanager.saveDataToFile(strb.toString());
            System.out.println("Book list saved!");
        }catch (IOException e){
            System.out.println("Fail to save: "+e.getMessage());
        }
    }
    public void loadFromFile(){
        bookList.clear();
        try{
            List<String> lines = filemanager.readDataFromFile();
            for (String line : lines){
                try{
                    String[] parts = line.split("\\|");
                    Book b = new Book(parts[0],parts[1],parts[2],parts[3],Integer.parseInt(parts[4]), Integer.parseInt(parts[5]));
                    bookList.add(b);
                }catch(Exception e){
                    System.out.println("Corrupted data in: "+e.getMessage());
                }
            }
        }catch(IOException e){
            System.out.println("No data found!");
        }    
    }
    
    @Override
    public List<Book> getBooks() throws Exception{
        Collections.sort(bookList,(e1,e2)->e1.getId().compareTo(e2.getId()));
        return bookList;
    }
    
    @Override 
    public Book getBookById(String id) throws Exception{
        if(bookList.isEmpty()){
            getBooks();
        }
        Book book = bookList.stream()
                .filter(c->c.getId()
                .equalsIgnoreCase(id)).findAny().orElse(null);
        return book;
    }
    
    @Override
    public void addBook(Book book) throws Exception{
        bookList.add(book);
    }
    
    @Override
    public void updateBook(Book book) throws Exception{
        Book b = getBookById(book.getId());
        if(b != null){
            b.setTitle(book.getTitle());
            b.setGenre(book.getGenre());
            b.setAuthor(book.getAuthor());
            b.setPubYear(book.getPubYear());
            b.setQuantity(book.getQuantity());
        }
    }
    
    @Override
    public void removeBook(Book book) throws Exception{
        Book b = getBookById(book.getId());
        if(b!= null){
            bookList.remove(b);
        }
    }
    
    @Override
    public List<Book> searchBook(String keyword) {
        ArrayList<Book> foundBooks = new ArrayList<>();
        
        for (Book b : bookList) {
            if (b.getTitle().toLowerCase().contains(keyword)) {
                foundBooks.add(b);
            }
        }
        
        return foundBooks;
    }
}