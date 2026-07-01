package Entites;

import Utilities.DataValidation;

public class Member extends Entity {

    private String name;
    private String phone;
    private String email;
    private boolean isPremium;

    /* ================= DEFAULT CONSTRUCTOR ================= */
    public Member() {
        super("");

        this.name = "N/A";
        this.phone = "0000000000";
        this.email = "N/A";
        this.isPremium = false;
    }

    /* ================= MAIN CONSTRUCTOR ================= */
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

    /* ================= ID (BR1 - UNIQUE, IMMUTABLE RULE) ================= */
    @Override
    public void setId(String id) throws Exception {

        if (id == null) {
            throw new Exception("ID cannot be null");
        }

        id = id.trim().toUpperCase();

        if (!DataValidation.checkStringWithFormat(id, "M\\d{3}")) {
            throw new Exception("Invalid Member ID format (Mxxx). Example: M001");
        }

        super.setId(id); // ✔ FIX: đúng encapsulation
    }

    @Override
    public String getId() {
        return super.getId();
    }

    /* ================= NAME (BR2) ================= */
    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {

        if (!DataValidation.checkStringEmpty(name)) {
            throw new Exception("Member name cannot be empty!");
        }

        this.name = name.trim();
    }

    /* ================= PHONE ================= */
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) throws Exception {

        if (!DataValidation.checkIfValidPhoneNumber(phone)) {
            throw new Exception("Invalid phone number (must be 10 digits).");
        }

        this.phone = phone.trim();
    }

    /* ================= EMAIL ================= */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws Exception {

        if (!DataValidation.checkIfValidEmail(email)) {
            throw new Exception("Invalid email format.");
        }

        this.email = email.trim().toLowerCase();
    }

    /* ================= PREMIUM ================= */
    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean isPremium) {
        this.isPremium = isPremium;
    }

    /* ================= DISPLAY ================= */
    @Override
    public String toString() {

        return String.format(
                "%-5s | %-25s | %-15s | %-30s | %-10s",
                getId(),
                name,
                phone,
                email,
                isPremium ? "Yes" : "No"
        );
    }
}