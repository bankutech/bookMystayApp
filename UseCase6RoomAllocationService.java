import java.util.*;
class AllocationService {
    private Map<String, Integer> inventory;
    private Set<String> allocatedRoomIds;
    private int idCounter = 101;

    public AllocationService(Map<String, Integer> inventory) {
        this.inventory = inventory;
        this.allocatedRoomIds = new HashSet<>();
    }
    public void processBooking(ReservationRequest request) {
        String type = request.getRoomType();
        int available = inventory.getOrDefault(type, 0);

        if (available > 0) {
            String roomId = type.substring(0, 1).toUpperCase() + idCounter++;

            if (!allocatedRoomIds.contains(roomId)) {
                allocatedRoomIds.add(roomId);
                inventory.put(type, available - 1);
                
                System.out.println("SUCCESS: " + request.getGuestName() + 
                                   " confirmed in " + type + " (Room ID: " + roomId + ")");
            } else {
                System.out.println("ERROR: Room ID Collision detected for " + roomId);
            }
        } else {
            System.out.println("FAILED: No availability for " + type + " (Guest: " + request.getGuestName() + ")");
        }
    }
}

public class UseCase6RoomAllocationService {
    public static void main(String[] args) {
        System.out.println("******************************************");
        System.out.println("   Book My Stay - Allocation Service    ");
        System.out.println("   Version: 6.0                          ");
        System.out.println("******************************************");
        Map<String, Integer> hotelInventory = new HashMap<>();
        hotelInventory.put("Single Room", 2);
        hotelInventory.put("Suite Room", 1);
        Queue<ReservationRequest> queue = new LinkedList<>();
        queue.offer(new ReservationRequest("Alice", "Suite Room"));
        queue.offer(new ReservationRequest("Bob", "Single Room"));
        queue.offer(new ReservationRequest("Charlie", "Suite Room"));
        queue.offer(new ReservationRequest("Diana", "Single Room"));

        AllocationService allocationService = new AllocationService(hotelInventory);

        System.out.println("Processing Queue...");
        System.out.println("------------------------------------------");
        while (!queue.isEmpty()) {
            ReservationRequest currentRequest = queue.poll();
            allocationService.processBooking(currentRequest);
        }
        System.out.println("------------------------------------------");
        System.out.println("Final Inventory State: " + hotelInventory);
    }
}
