package Entites;

import Utilities.DataValidation;

public class Member extends Entity {

    private String name;
    private String phone;
    private String email;
    private boolean isPremium;

    public Member() {
        super(""); 
        
        this.name = "None";
        this.phone = "0000000000"; 
        this.email = "None@mail.com"; 
        this.isPremium = false;
    }

    public Member(String id,
                  String name,
                  String phone,
                  String email,
                  boolean isPremium) throws Exception {
        super(id); 

        setId(id);
        setName(name);
        setPhone(phone);
        setEmail(email);
        setPremium(isPremium);
    }

    @Override
    public void setId(String id) throws Exception {
        if (id == null) {
            throw new Exception("ID cannot be null");
        }

        id = id.trim().toUpperCase();

        if (!DataValidation.checkStringWithFormat(id, "M\\d{3}")) {
            throw new Exception("Invalid Member ID format (Mxxx). Example: M001");
        }

        super.setId(id); 
    }

    @Override
    public String getId() {
        return super.getId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if (name == null || !DataValidation.checkStringEmpty(name)) {
            throw new Exception("Member name cannot be empty!");
        }
        this.name = name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) throws Exception {
        if (phone == null || !DataValidation.checkIfValidPhoneNumber(phone)) {
            throw new Exception("Invalid phone number (must be 10 digits).");
        }
        this.phone = phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws Exception {
        if (email == null || !DataValidation.checkIfValidEmail(email)) {
            throw new Exception("Invalid email format.");
        }
        this.email = email.trim().toLowerCase();
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean isPremium) {
        this.isPremium = isPremium;
    }

    @Override
    public String toString() {
        
        return String.format(
                "%-10s | %-25s | %-15s | %-30s | %-15s",
                getId(),
                name,
                phone,
                email,
                isPremium ? "VIP" : "None"// new update?
        );
    }
}
