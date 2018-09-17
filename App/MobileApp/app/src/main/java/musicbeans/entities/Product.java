package musicbeans.entities;

public class Product {
    private int ID;
    private String band;
    private String name;
    private String type;
    private double price;
    private  int stock;
    private byte[] content;

    public Product(int ID, String name, double price) {
        this.ID = ID;
        this.name = name;
        this.price = price;
    }

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Product(String name, String type, double price, int stock, byte[] content) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.stock = stock;
        this.content = content;
    }

    public Product(int ID, String band, String name, String type, double price, byte[] content) {
        this.ID = ID;
        this.band = band;
        this.name = name;
        this.type = type;
        this.price = price;
        this.content = content;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
