import java.time.LocalDate;
import java.util.ArrayList;
public class Member {

    //encapsulation of attributes
    private String name;
    private String universityID;
    private String contactInformation;
    private ArrayList<Equipment> borrowedEquipment;

    //constructor for the members borrowing
    public Member(String name, String universityID, String contactInformation) {
        this.name = name;
        this.universityID = universityID;
        this.contactInformation = contactInformation;
        this.borrowedEquipment = new ArrayList<>();
    }
    public String getName() {
        return name;
    }
    public String getUniversityID() {
        return universityID;
    }
    public String getContactInformation() {
        return contactInformation;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }
    public ArrayList<Equipment> getBorrowedEquipment() {
        return borrowedEquipment;
    }
    public void addBorrowedEquipment(Equipment equipment) {
        this.borrowedEquipment.add(equipment);
    }
    public void removeBorrowedEquipment(Equipment equipment) {
        this.borrowedEquipment.remove(equipment);
    }
    public String toString() {
        return "Name: " + name +
                ", ID: " + universityID +
                ", Contact: " + contactInformation;
    }
    }


