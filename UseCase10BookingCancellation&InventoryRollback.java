import java.util.*;

public class UseCase10BookingCancellation {

    private static Map<String, Integer> inventory = new HashMap<>();
    private static Map<String, String> bookingRoomType = new HashMap<>();
    private static Map<String, String> bookingRoomID = new HashMap<>();
    private static Stack<String> releasedRoomIDs = new Stack<>();

    public static void main(String[] args) {
        inventory.put("Single", 10);
        inventory.put("Double", 5);

        String bookingId = "B001";
        String roomType = "Single";
        String allocatedRoomID = "S101";

        bookingRoomType.put(bookingId, roomType);
        bookingRoomID.put(bookingId, allocatedRoomID);
        inventory.put(roomType, inventory.get(roomType) - 1);

        System.out.println("Before cancellation:");
        printInventory();

        cancelBooking(bookingId);

        System.out.println("After cancellation:");
        printInventory();
    }

    public static void cancelBooking(String bookingId) {
        if (!bookingRoomType.containsKey(bookingId)) {
            System.out.println("Cancellation failed: Booking ID not found.");
            return;
        }
        String roomType = bookingRoomType.get(bookingId);
        String roomID = bookingRoomID.get(bookingId);

        releasedRoomIDs.push(roomID);
        inventory.put(roomType, inventory.get(roomType) + 1);
        bookingRoomType.remove(bookingId);
        bookingRoomID.remove(bookingId);

        System.out.println("Booking " + bookingId + " cancelled successfully.");
        System.out.println("Released room ID " + roomID + " added to rollback stack.");
    }

    public static void printInventory() {
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " rooms available: " + entry.getValue());
        }
        System.out.println();
    }
}