
abstract class Room {
    protected String roomType;
    protected double price;
    protected String amenities;

    public Room(String roomType, double price, String amenities) {
        this.roomType = roomType;
        this.price = price;
        this.amenities = amenities;
    }

    public abstract void displayDetails();
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1500.0, "Single Bed, Wi-Fi, AC");
    }

    @Override
    public void displayDetails() {
        System.out.println("Type: " + roomType + " | Price: " + price + " | Amenities: " + amenities);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2500.0, "Double Bed, Wi-Fi, AC, Mini Bar");
    }

    @Override
    public void displayDetails() {
        System.out.println("Type: " + roomType + " | Price: " + price + " | Amenities: " + amenities);
    }
}


class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 5000.0, "King Size Bed, Wi-Fi, AC, Jacuzzi, Balcony");
    }

    @Override
    public void displayDetails() {
        System.out.println("Type: " + roomType + " | Price: " + price + " | Amenities: " + amenities);
    }
}
public class UseCase2RoomInitialization {
    public static void main(String[] args) {
        System.out.println("******************************************");
        System.out.println("   Book My Stay - Room Initialization   ");
        System.out.println("   Version: 2.0                          ");
        System.out.println("******************************************");

        Room single = new SingleRoom();
        Room doubleRm = new DoubleRoom();
        Room suite = new SuiteRoom();

        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        System.out.println("Available Rooms & Details:");
        System.out.println("------------------------------------------");
        
        single.displayDetails();
        System.out.println("Count Available: " + singleAvailable);
        System.out.println("------------------------------------------");

        doubleRm.displayDetails();
        System.out.println("Count Available: " + doubleAvailable);
        System.out.println("------------------------------------------");
        suite.displayDetails();
        System.out.println("Count Available: " + suiteAvailable);
        System.out.println("------------------------------------------");
    }
}
