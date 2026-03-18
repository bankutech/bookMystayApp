import java.util.*;
import java.util.concurrent.*;
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}
class ConcurrentInventoryManager {
    private final Map<String, Integer> inventory = new HashMap<>();
    private final List<String> confirmedBookings = Collections.synchronizedList(new ArrayList<>());

    public ConcurrentInventoryManager() {
        inventory.put("Deluxe", 2);
        inventory.put("Standard", 5);
    }

    public synchronized boolean processBooking(BookingRequest request) {
        String type = request.roomType;
        int available = inventory.getOrDefault(type, 0);

        if (available > 0) {
            try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

            inventory.put(type, available - 1);
            confirmedBookings.add(request.guestName + " confirmed for " + type);
            System.out.println("[SUCCESS] " + request.guestName + " reserved a " + type + " room.");
            return true;
        } else {
            System.out.println("[FAILED] " + request.guestName + ": No " + type + " rooms left.");
            return false;
        }
    }

    public void displayFinalState() {
        System.out.println("\n--- Final System State ---");
        System.out.println("Remaining Inventory: " + inventory);
        System.out.println("Total Confirmed Bookings: " + confirmedBookings.size());
    }
}

public class UseCase11ConcurrentBookingSimulation {
    public static void main(String[] args) {
        System.out.println("--- Book My Stay: Use Case 11 - Concurrent Booking Simulation ---");
        System.out.println("Scenario: 10 guests competing for 2 Deluxe rooms simultaneously.\n");

        ConcurrentInventoryManager manager = new ConcurrentInventoryManager();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        String[] guests = {"Alice", "Bob", "Charlie", "Diana", "Edward", "Frank", "Gina", "Hank", "Ian", "Jack"};

        for (String guest : guests) {
            BookingRequest request = new BookingRequest(guest, "Deluxe");
            executor.execute(() -> {
                manager.processBooking(request);
            });
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
        manager.displayFinalState();
    }
}
