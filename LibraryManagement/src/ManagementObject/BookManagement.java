package ManagementObject;

import Entites.Book;

import Utilities.DataInput;
import Utilities.Constants;
import Utilities.Menu;
import java.util.ArrayList;
import java.util.Comparator;
import DataObjects.FileManager;
import java.io.IOException;
import java.util.List;

public class BookManagement implements BaseManagement{

    private ArrayList<Book> bookList = new ArrayList<>();
    private Constants con = new Constants();
    private FileManager filemanager = new FileManager("books.txt");
    
    /* Initializer*/
    public void bookMenu() {
        BookManagement bookManagement = new BookManagement();
        loadFromFile();
        int choice;
        
        System.out.println("You have enter Manage Books session!\n");
        try{
        do {
            System.out.println(con.separator + "BOOK MANAGE MENU" + con.separator);
            System.out.println("1. Add book\n2. Update book\n3. Remove book\n4. View all books\n5. Search books\n6. Back\n");
            System.out.println("Choose an option(1-6): ");

            choice = DataInput.getIntegerNumber();
            switch (choice) {
                case 1:
                    add();
                    break;
                case 2:
                    System.out.println("You chose Update book!");
                    update();
                    break;
                case 3:
                    delete();
                    break;
                    
                case 4:
                    System.out.println("Books list: ");
                    viewBookList();
                    break;
                case 5:
                    System.out.println("You chose Search books!");
                    searchBook();
                    break;
                case 6:
                    break;
                default:    
                    System.out.println("Invalid choice. Please try again!\n\n");
            }
        } while (choice != 6);
    }catch (Exception ex){
            System.out.println(ex.getMessage());
    }
    }

    /* CRUD */
    @Override
    public void add(){
        try {
            Book newBook = inputBook();
            if (newBook == null) {
                System.out.println("Adding book cancelled or invalid input.");
                return; 
            }
            if (findBookByID(newBook.getId()) != null) {
                System.out.println("ID " + newBook.getId() + " is already existed!");
                return;
            }
            bookList.add(newBook);
            saveToFile();
            System.out.println("Book added successfully!");
            System.out.println(newBook.getTitle() + " has been added!" + "\n");
        } catch (Exception e) {
            System.out.println("Failed to add: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }
    
    @Override
    public ArrayList<Book> get() {
        bookList.sort(Comparator.comparing(Book::getId).reversed());
        return bookList;
    }
    
    @Override
    public void delete(){
        String id = DataInput.getString("Enter book's ID to remove: ");
        Book toRemove = findBookByID(id);
        
        if(toRemove == null){
            System.out.println("Book not found.");
            return;
        }
        System.out.println("Found: " + toRemove); 
        String confirm = DataInput.getString("Do you really want to delete this book? (y/n): ");
        if(confirm.equalsIgnoreCase("y")){bookList.remove(toRemove);
        System.out.println("Book removed!");
        saveToFile();}
        else{
            System.out.println("Remove cancelled!");
        }
        
    }
    
    @Override
    public void update() {
        String id = DataInput.getString("Enter book's ID to update: ");
        Book toUpdate = findBookByID(id);
        
        if (toUpdate == null) {
            System.out.println("Book not found.");
            return;
        }
        
        System.out.println("Found book: " + toUpdate);
        System.out.println("Enter new information (Press Enter to keep current data):");
        
        try {
            // Cập nhật Title
            String newTitle = DataInput.getString("Enter new title: ");
            if (!newTitle.trim().isEmpty()) toUpdate.setTitle(newTitle);
            
            // Cập nhật Author
            String newAuthor = DataInput.getString("Enter new author: ");
            if (!newAuthor.trim().isEmpty()) toUpdate.setAuthor(newAuthor);
            
            // Cập nhật Genre
            String newGenre = DataInput.getString("Enter new genre: ");
            if (!newGenre.trim().isEmpty()) toUpdate.setGenre(newGenre);
            
            saveToFile();
            System.out.println("Book updated successfully!");
            
        } catch (Exception e) {
            System.out.println("Failed to update: " + e.getMessage());
        }
    }
    
    /* File & Basic IO */
    public void saveToFile(){
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
   
    public Book inputBook() throws Exception {
        String ID = DataInput.getString("Enter book's id:");
        String Title = DataInput.getString("Enter book's title:");
        String Author = DataInput.getString("Enter book's author:");
        String Genre = DataInput.getString("Enter book's genre:");
        int PubYear = DataInput.getIntegerNumber("Enter public year:");
        int Quantity = DataInput.getIntegerNumber("Enter recent amount:");
        
        return new Book(ID, Title, Author, Genre, PubYear, Quantity);
    }
    
    public void viewBookList() {
        if(bookList.isEmpty()){
            System.out.println("No books in the list.\n");
            return;
        }
        System.out.println(con.longSeparator);
        System.out.format("%-5s | %-30s | %-20s | %-15s | %4s | %s%n", "ID", "Title", "Author", "Genre", "Year", "Amount");
        System.out.println(con.longSeparator);
        for (Book book : bookList) {
            System.out.println(book);
        }
        System.out.println(con.longSeparator);
    }

    public Book findBookByID(String id) {
        for (Book bk : bookList) {
            if (bk.getId().equalsIgnoreCase(id)) {
                return bk;
            }
        }
        return null;
    }

    public void searchBook() {
        if(bookList.isEmpty()){
            System.out.println("No books in the list.\n");
            return;
        }
        
        String keyword = DataInput.getString("Enter Book Title to search: ").toLowerCase();
        boolean found = false;
        
        System.out.println(con.longSeparator);
        System.out.format("%-5s | %-30s | %-20s | %-15s | %4s | %s%n", "ID", "Title", "Author", "Genre", "Year", "Amount");
        System.out.println(con.longSeparator);
        
        for (Book b : bookList) {
            if (b.getTitle().toLowerCase().contains(keyword)) {
                System.out.println(b);
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No books match your search.");
        }
        System.out.println(con.longSeparator);
    }


    
}
