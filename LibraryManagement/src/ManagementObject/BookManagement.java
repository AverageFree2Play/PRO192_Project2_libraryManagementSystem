package ManagementObject;

import Entites.Book;

import Utilities.DataInput;
import Utilities.Constants;
import Utilities.Menu;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import DataObjects.FileManager;
import java.io.IOException;
import java.util.List;

public class BookManagement {

    private ArrayList<Book> bookList = new ArrayList<>();
    private Constants con = new Constants();
    private FileManager filemanager = new FileManager("books.txt");
    public void bookMenu() {
        Scanner sc = new Scanner(System.in);        
        BookManagement bookManagement = new BookManagement();
        loadFromFile();
        int choice;
        int yesnochoice;
        System.out.println("You have enter Manage Books session!\n");
        do {
            System.out.println(con.Seperator + "BOOK MANAGE MENU" + con.Seperator);
            System.out.println("1. Add book\n2. Update book\n3. Remove book\n4. View all books\n5. Search books\n6. Back\n");
            System.out.println("Choose an option(1-6): ");

            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    System.out.println("You chose Update book!");
                    break;
                case 3:
                    String id = DataInput.getString("Enter the book's ID to remove: ");
                    System.out.println("Do you really want to remove this book?(1/0): ");
                    yesnochoice = sc.nextInt();
                    if(yesnochoice == 0){
                        break;
                    }
                    else{
                        removeBook(id);
                        saveToFile();
                    }
                    break;
                case 4:
                    System.out.println("Books list: ");
                    viewBookList();
                    break;
                case 5:
                    System.out.println("You chose Search books!");
                    break;
                case 6:
                    break;
                default:    
                    System.out.println("Invalid choice. Please try again!\n\n");
            }
        } while (choice != 6);
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

    public void addBook() {
        try {
            Book newBook = inputBook();
            if (findBookByID(newBook.getBookID()) != null) {
                System.out.println("ID " + newBook.getBookID() + " is already existed!");
                return;
            }
            bookList.add(newBook);
            saveToFile();
            System.out.println("Book added successfully!");
            System.out.println(newBook.getTitle() + " has been added!" + "\n");
        } catch (Exception e) {
            System.out.println("Failed to add: " + e.getMessage() + "\n");
        }
    }
    
    public void saveToFile(){
        StringBuilder strb = new StringBuilder();
        for (Book b : bookList){
            strb.append(b.getBookID()).append("|");
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
    public void viewBookList() {
        if(bookList.isEmpty()){
            System.out.println("No books in the list.\n");
            return;
        }
        System.out.println(con.LongSeperator);
        System.out.format("%-5s | %-30s | %-20s | %-15s | %4s | %s%n", "ID", "Title", "Author", "Genre", "Year", "Amount");
        System.out.println(con.LongSeperator);
        for (Book book : bookList) {
            System.out.println(book);
        }
        System.out.println(con.LongSeperator);
    }

    public Book findBookByID(String id) {
        for (Book bk : bookList) {
            if (bk.getBookID().equalsIgnoreCase(id)) {
                return bk;
            }
        }
        return null;
    }

    public ArrayList<Book> getBookList() {
        bookList.sort(Comparator.comparing(Book::getBookID).reversed());
        return bookList;
    }
    
    public void removeBook(String id){
        Book book = findBookByID(id);
        
        if(book == null){
            System.out.println("Book not found.");
            return;
        }
        bookList.remove(book);
        System.out.println("Book removed!");
    }

    
}
