// Imports
import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Main {
    public static void main(String[] args) {

        // Takes users input and stores it
        ArrayList<Equipment> equipmentList = new ArrayList<>();
        ArrayList<Member> memberList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        //infinite loop for the options
        while (true) {
            System.out.println("\n--- Equipment Loan System Menu ---");
            System.out.println("1. Add Equipment");
            System.out.println("2. View Equipment");
            System.out.println("3. Register Member");
            System.out.println("4. View Members");
            System.out.println("5. Borrow Equipment");
            System.out.println("6. Return Equipment");
            System.out.println("7. Manage Equipment (Edit/Delete)");
            System.out.println("8. Manage Members (Edit/Delete)");

            System.out.print("\nChoose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            //Adding equipment
            if (choice == 1) {
                System.out.println("Enter equipment name:");
                String name = scanner.nextLine();
                System.out.println("Enter brand:");
                String brand = scanner.nextLine();
                System.out.println("Enter category:");
                String category = scanner.nextLine();
                System.out.println("Enter purchase date (YYYY-MM-DD):");
                String dateInput = scanner.nextLine();
                LocalDate purchaseDate = LocalDate.parse(dateInput);
                Equipment eq = new Equipment(name, brand, category, purchaseDate);
                equipmentList.add(eq);

                System.out.println("Equipment added.");

                //View equipment list
            } else if (choice == 2) {
                System.out.println("Equipment List:");
                if (equipmentList.isEmpty()) {
                    System.out.println("No equipment added yet.");
                } else {
                    for (Equipment eq : equipmentList) {
                        String status = eq.getIsAvailable() ? "Available" : "Unavailable";
                        System.out.println(eq.toString());
                    }
                }


            } else if (choice == 3) {
                System.out.println("\n--- Register New Member ---");
                System.out.print("Enter member name: ");
                String memberName = scanner.nextLine();

                System.out.print("Enter university ID: ");
                String universityID = scanner.nextLine();

                System.out.print("Enter contact information : ");
                String contactInfo = scanner.nextLine();

                Member newMember = new Member(memberName, universityID, contactInfo);
                memberList.add(newMember);
                System.out.println("Member registered successfully!");

            } else if (choice == 4) {
                System.out.println("\n--- Registered Members ---");
                if (memberList.isEmpty()) {
                    System.out.println("No members registered yet.");
                } else {
                    for (int i = 0; i < memberList.size(); i++) {
                        Member member = memberList.get(i);
                        System.out.println((i + 1) + ". " + member.toString());
                    }
                }
            } else if (choice == 5) {
                System.out.println("\n--- Borrow Equipment ---");
                System.out.print("Enter member's University ID: ");
                String memberIdInput = scanner.nextLine();
                Member foundMember = null;
                for (Member m : memberList) {
                    if (m.getUniversityID().equals(memberIdInput)) {
                        foundMember = m;
                        break;

                    }
                }
                if (foundMember == null) {
                    System.out.println("Error: Member with ID '" + memberIdInput + "' not found.");
                    continue;
                }
                System.out.print("Enter equipment name to borrow: ");
                String equipmentNameInput = scanner.nextLine();
                Equipment foundEquipment = null;
                for (Equipment e : equipmentList) {
                    if (e.getName().equalsIgnoreCase(equipmentNameInput)) {
                        foundEquipment = e;
                        break;
                    }
                }
                if (foundEquipment == null) {
                    System.out.println("Error: Equipment '" + equipmentNameInput + "' not found.");
                    continue;
                }
                if (!foundEquipment.getIsAvailable()) {
                    System.out.println("Error: Equipment '" + foundEquipment.getName() + "' is currently unavailable.");
                    continue;
                }
                System.out.print("Enter loan time in days: ");
                int durationDays = scanner.nextInt();
                scanner.nextLine();
                if (durationDays <= 0) {
                    System.out.println("Error: Loan time must be a positive number.");
                    continue;
                }
                LocalDate checkoutDate = LocalDate.now();
                LocalDate dueDate = checkoutDate.plusDays(durationDays);
                foundEquipment.setIsAvailable(false);
                foundEquipment.setCurrentBorrower(foundMember);
                foundEquipment.setCheckoutDate(checkoutDate);
                foundEquipment.setDueDate(dueDate);
                foundMember.addBorrowedEquipment(foundEquipment);
                System.out.println("\n--- Loan Successful! ---");
                System.out.println("Equipment '" + foundEquipment.getName() + "' borrowed by " + foundMember.getName() + ".");
                System.out.println("Checkout Date: " + checkoutDate);
                System.out.println("Due Date: " + dueDate);
            } else if (choice == 6) {
                System.out.println("\n--- Return Equipment ---");
                System.out.print("Enter equipment name to return: ");
                String equipmentToReturnName = scanner.nextLine();
                Equipment foundEquipment = null;
                for (Equipment e : equipmentList) {
                    if (e.getName().equalsIgnoreCase(equipmentToReturnName)) {
                        foundEquipment = e;
                        break;
                    }
                }
                if (foundEquipment == null) {
                    System.out.println("Error: Equipment '" + equipmentToReturnName + "' not found in the system.");
                    continue;
                }
                if (foundEquipment.getIsAvailable()) {
                    System.out.println("Error: Equipment '" + foundEquipment.getName() + "' is not currently on loan.");
                    continue;
                }
                System.out.print("Enter the University ID of the member returning this equipment: ");
                String memberIdReturning = scanner.nextLine();
                Member actualBorrower = foundEquipment.getCurrentBorrower();
                if (actualBorrower == null || !actualBorrower.getUniversityID().equals(memberIdReturning)) {
                    System.out.println("Error: The Member ID does not match the borrower of this equipment or the item hasnt been borrowed.");
                    continue;
                }
                LocalDate returnDate = LocalDate.now();
                LocalDate dueDate = foundEquipment.getDueDate();
                long daysLate = 0;
                if (returnDate.isAfter(dueDate)) {
                    daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
                }
                double fee = daysLate * 1.0;

                foundEquipment.setIsAvailable(true);
                foundEquipment.setReturnDate(returnDate);
                foundEquipment.setCurrentBorrower(null);
                foundEquipment.setCheckoutDate(null);
                foundEquipment.setDueDate(null);
                if (actualBorrower != null) {
                    actualBorrower.removeBorrowedEquipment(foundEquipment);
                }
                System.out.println("\n--- Return Successful! ---");
                System.out.println("Equipment '" + equipmentToReturnName + "' has been returned by " + actualBorrower.getName() + ".");
                System.out.println("Return Date: " + returnDate);
                if (daysLate > 0) {
                    System.out.println("Item is overdue by " + daysLate + " days.");
                    System.out.println("Late fee: £" + fee);
                } else {
                    System.out.println("Returned on time. No fee.");
                }
            } else if (choice == 7) {
                System.out.print("Enter equipment name to manage: ");
                String searchName = scanner.nextLine();
                Equipment foundEquipment = null;
                for (Equipment e : equipmentList) {
                    if (e.getName().equalsIgnoreCase(searchName)) {
                        foundEquipment = e;
                        break;
                    }
                }
                if (foundEquipment == null) {
                    System.out.println("Equipment not found.");
                    continue;
                }
                System.out.println("\n1. Edit Equipment");
                System.out.println("2. Delete Equipment");
                System.out.print("Choose an option: ");
                int action = scanner.nextInt();
                scanner.nextLine();
                if (action == 1) {
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new brand: ");
                    String newBrand = scanner.nextLine();
                    System.out.print("Enter new category: ");
                    String newCategory = scanner.nextLine();
                    foundEquipment.setName(newName);
                    foundEquipment.setBrand(newBrand);
                    foundEquipment.setCategory(newCategory);
                    System.out.println("Equipment updated successfully.");
                } else if (action == 2) {
                    equipmentList.remove(foundEquipment);
                    System.out.println("Equipment deleted successfully.");
                } else {
                    System.out.println("Invalid option.");
                }
            } else if (choice == 8) {
                System.out.print("Enter member University ID: ");
                String searchID = scanner.nextLine();
                Member foundMember = null;
                for (Member m : memberList) {
                    if (m.getUniversityID().equals(searchID)) {
                        foundMember = m;
                        break;
                    }
                }
                if (foundMember == null) {
                    System.out.println("Member not found.");
                    continue;
                }
                System.out.println("\n1. Edit Member");
                System.out.println("2. Delete Member");
                System.out.print("Choose an option: ");
                int action = scanner.nextInt();
                scanner.nextLine();
                if (action == 1) {
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new contact info: ");
                    String newContact = scanner.nextLine();
                    foundMember.setName(newName);
                    foundMember.setContactInformation(newContact);
                    System.out.println("Member updated successfully.");
                }
                else if (action == 2) {
                    if (!foundMember.getBorrowedEquipment().isEmpty()) {
                        System.out.println("Cannot delete member. They still have borrowed equipment.");
                        continue;
                    }
                    memberList.remove(foundMember);
                    System.out.println("Member deleted successfully.");
                } else {
                    System.out.println("Invalid option.");
                }
            }
        }
    }
    }






