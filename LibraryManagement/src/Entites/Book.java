package Entites;

import Utilities.DataValidation;

public class Book {
    private String ID,Title,Author,Genre,PubYear;
    private int Quantity;
    // CONSTRUCTORS
    public Book() {
        ID = "";
        Title = "";
        Author = "";
        Genre = "";
        PubYear = "";
        Quantity = 1;
    }

    public Book(String ID, String Title, String Author, String Genre, String PubYear, int Quantity) throws Exception {
        setID(ID);
        setTitle(Title);
        setAuthor(Author);
        setGenre(Genre);
        setPubYear(PubYear);
        setQuantity(Quantity);
    }
    // METHODS
    public String getID() {
        return ID;
    }
    public void setID(String ID) throws Exception {
        if(!DataValidation.checkStringWithFormat(ID.toUpperCase(),"B\\d{3}")){
            throw new Exception("Invalid ID. Format: Bxxx. Got:" + ID);
        }        
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }
    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getAuthor() {
        return Author;
    }
    public void setAuthor(String Author) {
        this.Author = Author;
    }

    public String getGenre() {
        return Genre;
    }
    public void setGenre(String Genre) {
        this.Genre = Genre;
    }

    public String getPubYear() {
        return PubYear;
    }
    public void setPubYear(String PubYear) {
        this.PubYear = PubYear;
    }

    public int getQuantity() {
        return Quantity;
    }
    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }    
}
