package ManagementObject;

import Entites.Member;
import Utilities.Constants;
import Utilities.DataInput;
import DataObjects.FileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MemberManagement {

    private ArrayList<Member> memberList = new ArrayList<>();
    private Constants con = new Constants();
    private FileManager filemanager = new FileManager("members.txt");

    public void memberMenu() {
        loadFromFile();
        int choice = 0;

        System.out.println("You have entered Member Management session!\n");

        do {
            try {
                System.out.println(con.separator);
                System.out.println("MEMBER MANAGEMENT");
                System.out.println(con.separator);
                System.out.println("1. Add member");
                System.out.println("2. Update member");
                System.out.println("3. Delete member");
                System.out.println("4. View all members");
                System.out.println("5. Search member");
                System.out.println("6. Back");
                System.out.println(con.separator);

                choice = DataInput.getIntegerNumber("Choose an option (1-6): ");

                switch (choice) {
                    case 1:
                        add(); 
                        break;
                    case 2:
                        update();
                        break;
                    case 3:
                        delete();
                        break;
                    case 4:
                        viewMemberList();
                        break;
                    case 5:
                        searchMember(); 
                        break;
                    case 6:
                        System.out.println("Returning to main menu...");
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        } while (choice != 6);
    }
// new update
    private String autoID() {
        if (memberList.isEmpty()) {
            return "M001";
        }
        
        int maxNumber = 0;
        for (Member m : memberList) {
            try {
                String numberStr = m.getId().substring(1);
                int number = Integer.parseInt(numberStr);
                
                if (number > maxNumber) {
                    maxNumber = number;
                }
            } catch (Exception e) {
              
            }
        }
        
        int nextNumber = maxNumber + 1;
        return "M" + String.format("%03d", nextNumber);
    }

    public void add() {
        try {
            Member m = inputMember();
            memberList.add(m);
            saveToFile();
            System.out.println("Member added successfully.");
        } catch (Exception e) {
            System.out.println("Add failed: " + e.getMessage());
        }
    }

    public void update() {
        String id = DataInput.getString("Enter Member ID: ").trim().toUpperCase();
        Member m = findMemberByID(id);

        if (m == null) {
            System.out.println("Member not found!");
            return;
        }

        System.out.println("Current Information:");
        System.out.println(m);

        try {
            String name = DataInput.getString("New Name (Enter to skip): ").trim();
            if (!name.isEmpty()) {
                if (name.contains("|")) throw new Exception("Name cannot contain '|' character!");
                m.setName(name);
            }

            String phone = DataInput.getString("New Phone (Enter to skip): ").trim();
            if (!phone.isEmpty()) {
                if (!phone.matches("\\d{10,11}")) {
                    throw new Exception("Invalid phone format! Must be 10-11 digits.");
                }
                m.setPhone(phone);
            }

            String email = DataInput.getString("New Email (Enter to skip): ").trim();
            if (!email.isEmpty()) {
                if (email.contains("|")) throw new Exception("Email cannot contain '|' character!");
                if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                    throw new Exception("Invalid email format!");
                }
                m.setEmail(email);
            }

            String premium = DataInput.getString("Premium (Y/N, Enter skip): ").trim();
            if (!premium.isEmpty()) {
                if (!premium.equalsIgnoreCase("Y") && !premium.equalsIgnoreCase("N")) {
                    throw new Exception("Premium must be Y or N!");
                }
                
                m.setPremium(premium.equalsIgnoreCase("Y"));
            }

            saveToFile();
            System.out.println("Member updated successfully.");

        } catch (Exception e) {
            System.out.println("Update failed: " + e.getMessage());
        }
    }

  
    public void delete() {
        String id = DataInput.getString("Enter Member ID: ").trim().toUpperCase();
        Member m = findMemberByID(id);

        if (m == null) {
            System.out.println("Member not found!");
            return;
        }

        System.out.println("Member found:");
        System.out.println(m);

        String confirm = DataInput.getString("Confirm delete (Y/N): ").trim();

        if (confirm.equalsIgnoreCase("Y")) {
            memberList.remove(m);
            saveToFile();
            System.out.println("Member deleted successfully.");
        } else {
            System.out.println("Cancelled.");
        }
    }

    public void viewMemberList() {
        if (memberList.isEmpty()) {
            System.out.println("No members found.");
            return;
        }
        printTableHeader();

        for (Member member : memberList) {
            System.out.printf("| %-10s | %-25s | %-15s | %-30s | %-15s%n",
                    member.getId(),
                    member.getName(),
                    member.getPhone(),
                    member.getEmail(),
                    member.isPremium() ? "VIP" : "None");
        }
        System.out.println(con.separator);
    }
    
    
//=====================new update====================================
   /* ================= MULTI-KEYWORD SEARCH MEMBER ================= */
    public void searchMember() {
        if (memberList.isEmpty()) {
            System.out.println("No members available to search.");
            return;
        }

        System.out.println("\n" + con.separator);
        System.out.println("------------ SEARCH MEMBER -------------");
        System.out.println(con.separator);
        
        String inp = DataInput.getString("Enter keywords (e.g., 'Le VIP'): ").trim().toLowerCase();
        if (inp.isEmpty()) {
            System.out.println("Invalid input! Keyword cannot be empty.");
            return;
        }

            String[] keywords = inp.split("\\s+");

            ArrayList<Member> match = new ArrayList<>();

        for (Member m : memberList) {
            String premiumStatus = m.isPremium() ? "VIP" : "None";
            String memberDataRecord = (m.getId() + " " + 
                                       m.getName() + " " + 
                                       m.getPhone() + " " + 
                                       m.getEmail() + " " + 
                                       premiumStatus).toLowerCase();

            boolean matchesAll = true;
            for (String kw : keywords) {
                if (!memberDataRecord.contains(kw)) {
                    matchesAll = false; 
                    break;
                }
            }

            if (matchesAll) {
                match.add(m);
            }
        }

        int length = match.size();

        if (length == 0) {
            System.out.println("No matching profile found for: " + inp);
            return;
        }

        if (length == 1) {
            System.out.println("\nMatching Profile Found:");
            System.out.println(match.get(0));
        } else {
            System.out.println("\n--- ALL MEMBERS FOUND (" + length + " results) ---");
            printTableHeader();

            for (Member m : match) {
                System.out.printf("%-10s | %-25s | %-15s | %-30s | %-15s%n",
                        m.getId(),
                        m.getName(),
                        m.getPhone(),
                        m.getEmail(),
                        m.isPremium() ? "VIP" : "None");
            }
            System.out.println(con.separator);
        }
    
    }

    private void printTableHeader() {
        System.out.print(con.separator);
        System.out.print(con.separator);
        System.out.println(con.separator);
        System.out.printf("%-10s | %-25s | %-15s | %-30s | %-15s%n",
                "ID", "Name", "Phone", "Email", "Premium");
        System.out.print(con.separator);
        System.out.print(con.separator);
        System.out.println(con.separator);
    }

    public Member inputMember() throws Exception {
        String id = autoID();
        System.out.println("Generated Member ID: " + id);

        String name = DataInput.getString("Name: ").trim();
        if (name.isEmpty()) {
            throw new Exception("Name cannot be empty!");
        }
        if (name.contains("|")) {  //fixed
            throw new Exception("Name cannot contain the pipe character '|'!");
        }

        String phone = DataInput.getString("Phone: ").trim();
        if (!phone.matches("\\d{10,11}")) {
            throw new Exception("Invalid phone format! Must be 10-11 digits.");
        }

        String email = DataInput.getString("Email: ").trim();
        if (email.contains("|")) { //fixed
            throw new Exception("Email cannot contain the pipe character '|'!");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new Exception("Invalid email format!");
        }

        String premium = DataInput.getString("Premium (Y/N): ").trim();
        if (!premium.equalsIgnoreCase("Y") && !premium.equalsIgnoreCase("N")) {
            throw new Exception("Premium confirmation must be 'Y' or 'N'!");
        }

        boolean isPremium = premium.equalsIgnoreCase("Y");

        return new Member(id, name, phone, email, isPremium);
    }

    public Member findMemberByID(String id) {
        for (Member m : memberList) {
            if (m.getId().equalsIgnoreCase(id.trim())) {
                return m;
            }
        }
        return null;
    }

    public ArrayList<Member> get() {
        memberList.sort(Comparator.comparing(Member::getId));
        return memberList;
    }

    public void saveToFile() {
        StringBuilder sb = new StringBuilder();

        for (Member m : memberList) {
            sb.append(m.getId()).append("|")
                    .append(m.getName()).append("|")
                    .append(m.getPhone()).append("|")
                    .append(m.getEmail()).append("|")
                    .append(m.isPremium() ? "Y" : "N")
                    .append(System.lineSeparator());
        }

        try {
            filemanager.saveDataToFile(sb.toString());
        } catch (IOException e) {
            System.out.println("Save error: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        memberList.clear();

        try {
            List<String> lines = filemanager.readDataFromFile();

            for (String line : lines) {
                if (line.trim().isEmpty()) continue;

                String[] p = line.split("\\|");
                if (p.length < 5) continue;

                Member m = new Member(
                        p[0].trim(),
                        p[1].trim(),
                        p[2].trim(),
                        p[3].trim(),
                        p[4].trim().equalsIgnoreCase("Y")
                );

                memberList.add(m);
            }

        } catch (IOException e) {
            System.out.println("No member data found.");
        } catch (Exception e) {
            System.out.println("Data corrupted: " + e.getMessage());
        }
    }
}
