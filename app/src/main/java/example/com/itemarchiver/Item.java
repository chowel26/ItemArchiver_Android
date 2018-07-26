package example.com.itemarchiver;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by colehowell on 5/19/16.
 */
public class Item implements Serializable {

    private String name, description, serialNumber, dateOfPurchased, itemImage;
    private long _id;

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", _id=" + _id +
                ", dateOfPurchased='" + dateOfPurchased + '\'' +
                ", itemImage=" + itemImage +
                '}';
    }

    public Item() {
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public Item(String name, String description, String serialNumber, long _id, String dateOfPurchased, String itemImage) {
        this.name = name;
        this.description = description;
        this.serialNumber = serialNumber;
        this._id = _id;
        this.dateOfPurchased = dateOfPurchased;
        this.itemImage = itemImage;
    }

    public Item(String name, String description, String serialNumber, String dateOfPurchased) {

        this.name = name;
        this.description = description;
        this.serialNumber = serialNumber;
        this.dateOfPurchased = dateOfPurchased;
    }

    public String getName() {
        return name != null ? name : "Null";
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

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }
}
