import java.util.*;
class InvalidRoomTypeException extends Exception {
    public InvalidRoomTypeException(String message) { super(message); }
}
class InsufficientInventoryException extends Exception {
    public InsufficientInventoryException(String message) { super(message); }
}
class Booking {
    private String bookingId;
    private String guestName;
    private String roomType;
    private double price;

    public Booking(String id, String guest, String type, double price) {
        this.bookingId = id;
        this.guestName = guest;
        this.roomType = type;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("[%s] %-10s | %-10s | $%.2f", bookingId, guestName, roomType, price);
    }
}

class BookingSystem {
    private Map<String, Integer> inventory = new HashMap<>();
    private Map<String, Double> roomPrices = new HashMap<>();
    private List<Booking> history = new ArrayList<>();
    private int counter = 1001;

    public BookingSystem() {
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 1);
        roomPrices.put("Standard", 120.0);
        roomPrices.put("Deluxe", 250.0);
    }

    public void processBooking(String guestName, String roomType) 
            throws InvalidRoomTypeException, InsufficientInventoryException {
        
        System.out.println("Processing: " + guestName + " for " + roomType + "...");

        if (!inventory.containsKey(roomType)) {
            throw new InvalidRoomTypeException("Validation Failed: Room type '" + roomType + "' is not offered.");
        }

        int available = inventory.get(roomType);
        if (available <= 0) {
            throw new InsufficientInventoryException("Validation Failed: No " + roomType + " rooms left.");
        }
        inventory.put(roomType, available - 1);

        String id = "BK" + (counter++);
        Booking newBooking = new Booking(id, guestName, roomType, roomPrices.get(roomType));
        history.add(newBooking);
        
        System.out.println("Success: " + id + " confirmed for " + guestName);
    }

    public void displayHistory() {
        System.out.println("\n--- FINAL BOOKING HISTORY REPORT ---");
        if (history.isEmpty()) System.out.println("No successful bookings.");
        else history.forEach(System.out::println);

    }
}

public class UseCase9ErrorHandlingValidation {
    public static void main(String[] args) {
        BookingSystem system = new BookingSystem();

        System.out.println("--- Book My Stay: Use Case 9 (Validation & Error Handling) ---");
        System.out.println("Rules: Deluxe (1 available), Standard (2 available)\n");
        String[][] requests = {
            {"Alice", "Deluxe"},
            {"Bob", "Deluxe"},
            {"Charlie", "Suite"},
            {"Diana", "Standard"},
            {"Eve", "Standard"},
            {"Frank", "Standard"}
        };

        for (String[] req : requests) {
            try {
                system.processBooking(req[0], req[1]);
            } catch (InvalidRoomTypeException | InsufficientInventoryException e) {
                System.err.println("ALERT >> " + e.getMessage());
            } catch (Exception e) {
                System.err.println("SYSTEM ERROR: " + e.getMessage());
            }
            System.out.println("---");
        }
        system.displayHistory();
    }
}
