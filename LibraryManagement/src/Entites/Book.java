package Entites;

import Utilities.DataValidation;

public class Book extends Entity {
    private String ID, Title, Author, Genre;
    private int Quantity, PubYear;
    
    // CONSTRUCTORS
    public Book(String ID, String Title, String Author, String Genre, int PubYear, int Quantity) throws Exception {
        setId(ID);
        setTitle(Title);
        setAuthor(Author);
        setGenre(Genre);
        setPubYear(PubYear);
        setQuantity(Quantity);
    }
    
    // METHODS
    
    @Override
    public void setId(String ID) throws Exception {
        if (!DataValidation.checkStringWithFormat(ID.toUpperCase(), "B\\d{3}")) {
            throw new Exception("Invalid ID. Format: Bxxx. Got:" + ID);
        }        
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }
    
    public void setTitle(String Title) throws Exception {
        if (Title == null || Title.trim().isEmpty()) {
            throw new Exception("Title cannot be empty!");
        }
        this.Title = Title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String Author) throws Exception {
        if (Author == null || Author.trim().isEmpty()) {
            throw new Exception("Author cannot be empty!");
        }
        this.Author = Author;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String Genre) throws Exception {
        if (Genre == null || Genre.trim().isEmpty()) {
            throw new Exception("Genre cannot be empty!");
        }
        this.Genre = Genre;
    }

    public int getPubYear() {
        return PubYear;
    }

    public void setPubYear(int PubYear) throws Exception {
        if (!DataValidation.checkIfValidYear(PubYear)) {
            throw new Exception("Invalid year. Got:" + PubYear);
        }
        this.PubYear = PubYear;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) throws Exception {
        if (Quantity < 0) {
            throw new Exception("Quantity cannot be a negative number!");
        }
        this.Quantity = Quantity;
    } 

    @Override
    public String toString() {
        return String.format("%-5s | %-30s | %-20s | %-15s | %4d | %d",
        ID, Title, Author, Genre, PubYear, Quantity);
    }
}
