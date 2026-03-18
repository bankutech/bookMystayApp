import java.io.*;
import java.util.*;
class BookingRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    private String guestName;
    private String roomType;

    public BookingRecord(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Guest: " + guestName + " | Room: " + roomType;
    }
}
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;
    Map<String, Integer> inventory;
    List<BookingRecord> history;

    public SystemState(Map<String, Integer> inventory, List<BookingRecord> history) {
        this.inventory = inventory;
        this.history = history;
    }
}
class PersistenceService {
    private static final String FILE_NAME = "hotel_system_state.ser";

    public void saveState(Map<String, Integer> inventory, List<BookingRecord> history) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            SystemState state = new SystemState(inventory, history);
            oos.writeObject(state);
            System.out.println("[Persistence] System state successfully saved to " + FILE_NAME);
        } catch (IOException e) {
            System.err.println("[Error] Failed to save system state: " + e.getMessage());
        }
    }

    public SystemState loadState() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("[Persistence] No previous state found. Starting fresh.");
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            SystemState state = (SystemState) ois.readObject();
            System.out.println("[Persistence] System state restored from " + FILE_NAME);
            return state;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[Error] Persistence file corrupted or incompatible. Starting fresh.");
            return null;
        }
    }
}

public class UseCase12DataPersistenceRecovery {
    private Map<String, Integer> inventory = new HashMap<>();
    private List<BookingRecord> history = new ArrayList<>();
    private PersistenceService persistence = new PersistenceService();

    public void initialize() {
        SystemState restored = persistence.loadState();
        if (restored != null) {
            this.inventory = restored.inventory;
            this.history = restored.history;
        } else {
            inventory.put("Deluxe", 5);
            inventory.put("Standard", 10);
        }
    }

    public void makeBooking(String guest, String type) {
        int count = inventory.getOrDefault(type, 0);
        if (count > 0) {
            inventory.put(type, count - 1);
            history.add(new BookingRecord(guest, type));
            System.out.println("Success: " + guest + " booked a " + type + " room.");
        } else {
            System.out.println("Failed: No " + type + " rooms available.");
        }
    }

    public void showStatus() {
        System.out.println("\n--- Current System Status ---");
        System.out.println("Inventory: " + inventory);
        System.out.println("Total Bookings in History: " + history.size());
        if (!history.isEmpty()) {
            System.out.println("Last Guest: " + history.get(history.size() - 1));
        }
        System.out.println("-----------------------------\n");
    }

    public static void main(String[] args) {
        UseCase12DataPersistenceRecovery app = new UseCase12DataPersistenceRecovery();
        
        System.out.println("--- Book My Stay: Use Case 12 - Data Persistence & Recovery ---");
        app.initialize();
        app.showStatus();
        System.out.println("Simulating a new booking...");
        app.makeBooking("Guest_" + (app.history.size() + 1), "Deluxe");

        app.showStatus();
        app.persistence.saveState(app.inventory, app.history);
        
        System.out.println("\nApplication shutting down. Run the program again to see data recovery in action!");
    }
}
