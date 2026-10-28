import java.time.LocalDate;
public class Equipment {

    //enacpsulation of attributes

    private String name;
    private String brand;
    private String category;
    private LocalDate purchaseDate;
    private boolean isAvailable;
    private Member currentBorrower;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public Equipment(String name, String brand, String category, LocalDate purchaseDate) {
        this.name = name;
        this.brand = brand;
        this.isAvailable = true;
        this.currentBorrower = null;
        this.checkoutDate = null;
        this.dueDate = null;
        this.returnDate = null;
    }
    public String getName() {
        return name;
    }
    public String getCategory() {
        return category;
    }
    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }
    public LocalDate getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
    public String getBrand() {
        return brand;
    }
        public void setName(String name) {
            this.name = name;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public void setCategory(String category) {
            this.category = category;
    }
    public boolean getIsAvailable() {
        return isAvailable;
    }
    public void setIsAvailable(boolean available) {
        this.isAvailable = available;
    }
    public Member getCurrentBorrower() {
        return currentBorrower;
    }
    public void setCurrentBorrower(Member currentBorrower) {
        this.currentBorrower = currentBorrower;
    }
    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }
    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }
    public LocalDate getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    public String toString() {
        String status = isAvailable ? "Available" : "Unavailable";
        String borrowerInfo = "";
        if (!isAvailable && currentBorrower != null) {
            borrowerInfo = " (Borrowed by: " + currentBorrower.getName() +
                    ", Due: " + dueDate + ")";
        }
        return "Name: " + name + ", Brand: " + brand + " - Status: " + status + borrowerInfo;
    }
}