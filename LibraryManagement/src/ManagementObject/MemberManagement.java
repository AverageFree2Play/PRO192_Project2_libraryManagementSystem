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

    /* ================= INITIALIZER ================= */
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

    /* ================= ADD MEMBER ================= */
    public void add() {

        try {

            Member m = inputMember();

            // BR1: unique ID
            if (findMemberByID(m.getId()) != null) {
                System.out.println("Member ID already exists!");
                return;
            }

            memberList.add(m);
            saveToFile();

            System.out.println("Member added successfully.");

        } catch (Exception e) {
            System.out.println("Add failed: " + e.getMessage());
        }
    }

    /* ================= UPDATE MEMBER ================= */
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

            String name = DataInput.getString("New Name (Enter to skip): ");
            if (!name.trim().isEmpty()) m.setName(name);

            String phone = DataInput.getString("New Phone (Enter to skip): ");
            if (!phone.trim().isEmpty()) m.setPhone(phone);

            String email = DataInput.getString("New Email (Enter to skip): ");
            if (!email.trim().isEmpty()) m.setEmail(email);

            String premium = DataInput.getString("Premium (Y/N, Enter skip): ");
            if (!premium.trim().isEmpty()) {
                m.setPremium(premium.equalsIgnoreCase("Y"));
            }

            saveToFile();
            System.out.println("Member updated successfully.");

        } catch (Exception e) {
            System.out.println("Update failed: " + e.getMessage());
        }
    }

    /* ================= DELETE MEMBER ================= */
    public void delete() {

        String id = DataInput.getString("Enter Member ID: ").trim().toUpperCase();

        Member m = findMemberByID(id);

        if (m == null) {
            System.out.println("Member not found!");
            return;
        }

        System.out.println("Member found:");
        System.out.println(m);

        String confirm = DataInput.getString("Confirm delete (Y/N): ");

        if (confirm.equalsIgnoreCase("Y")) {

            memberList.remove(m);
            saveToFile();

            System.out.println("Member deleted successfully.");

        } else {
            System.out.println("Cancelled.");
        }
    }

    /* ================= VIEW ALL ================= */
    public void viewMemberList() {

        if (memberList.isEmpty()) {
            System.out.println("No members found.");
            return;
        }
        System.out.print(con.separator);
        System.out.print(con.separator);
        System.out.println(con.separator);
        System.out.printf("%-5s | %-25s | %-15s | %-30s | %-10s%n",
                "ID", "Name", "Phone", "Email", "Premium");
        System.out.print(con.separator);
        System.out.print(con.separator);
        System.out.println(con.separator);

        for (Member member : memberList) {

        System.out.printf("%-5s | %-25s | %-15s | %-30s | %-10s%n",
                member.getId(),
                member.getName(),
                member.getPhone(),
                member.getEmail(),
                member.isPremium() ? "Yes" : "No");
    }


        System.out.println(con.separator);
    }

    /* ================= SEARCH ================= */
    public void searchMember() {

        String keyword = DataInput.getString("Enter ID or Name: ").toLowerCase();

        boolean found = false;

        for (Member m : memberList) {

            if (m.getId().toLowerCase().contains(keyword)
                    || m.getName().toLowerCase().contains(keyword)) {

                System.out.println(m);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No member found.");
        }
    }

    /* ================= INPUT ================= */
    public Member inputMember() throws Exception {

        String id = DataInput.getString("Member ID: ").trim().toUpperCase();
        String name = DataInput.getString("Name: ");
        String phone = DataInput.getString("Phone: ");
        String email = DataInput.getString("Email: ");
        String premium = DataInput.getString("Premium (Y/N): ");

        boolean isPremium = premium.equalsIgnoreCase("Y");

        return new Member(id, name, phone, email, isPremium);
    }

    /* ================= FIND ================= */
    public Member findMemberByID(String id) {

        for (Member m : memberList) {
            if (m.getId().equalsIgnoreCase(id)) {
                return m;
            }
        }

        return null;
    }

    /* ================= GET ================= */
    public ArrayList<Member> get() {

        memberList.sort(Comparator.comparing(Member::getId));
        return memberList;
    }

    /* ================= SAVE FILE ================= */
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

    /* ================= LOAD FILE ================= */
    public void loadFromFile() {

        memberList.clear();

        try {

            List<String> lines = filemanager.readDataFromFile();

            for (String line : lines) {

                if (line.trim().isEmpty()) continue;

                String[] p = line.split("\\|");

                if (p.length < 5) continue;

                Member m = new Member(
                        p[0],
                        p[1],
                        p[2],
                        p[3],
                        p[4].equalsIgnoreCase("Y")
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