package Entites;

import Utilities.DataValidation;

public class Member {
    private String ID,Name,Phone,Email;
    private boolean IsPremium;

    public Member() {
        ID = "";
        Name = "N/A";
        Phone = "000.000.0000";
        Email = "N/A";
        IsPremium = false;
    }
    // CONSTRUCTOR
    public Member(String ID, String Name, String Phone, String Email,boolean IsPremium) {
        this.ID = ID;
        this.Name = Name;
        this.Phone = Phone;
        this.Email = Email;
        this.IsPremium = IsPremium;
    }
    // METHODS
    public String getMemberID() {
        return ID;
    }
    public void setID(String ID) throws Exception {
        if(!DataValidation.checkStringWithFormat(ID.toUpperCase(),"M\\d{3}")){
            throw new Exception("Invalid ID. Format: Mxxx. Got:" + ID);
        }        
        this.ID = ID;
    }

    public String getName(){
        return Name;
    }
    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }
    
    
}
