package example.com.itemarchiver;

/**
 * Created by colehowell on 5/19/16.
 */
public class Item {

    String name, description, serialNumber, dateOfPurchased;

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", dateOfPurchased='" + dateOfPurchased + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDateOfPurchased() {
        return dateOfPurchased;
    }

    public void setDateOfPurchased(String dateOfPurchased) {
        this.dateOfPurchased = dateOfPurchased;
    }

    public Item() {

    }

    public Item(String name, String description, String serialNumber, String dateOfPurchased) {

        this.name = name;
        this.description = description;
        this.serialNumber = serialNumber;
        this.dateOfPurchased = dateOfPurchased;
    }
}
