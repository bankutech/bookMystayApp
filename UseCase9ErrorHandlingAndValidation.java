import java.util.*;
class InvalidRoomTypeException extends Exception {
    public InvalidRoomTypeException(String message) {
        super(message);
    }
}

class InsufficientInventoryException extends Exception {
    public InsufficientInventoryException(String message) {
        super(message);
    }
}

class InventoryManager {
    private Map<String, Integer> roomInventory;

    public InventoryManager() {
        roomInventory = new HashMap<>();
        roomInventory.put("Standard", 2);
        roomInventory.put("Deluxe", 1);
    }
    public void validateAndReserve(String roomType) throws InvalidRoomTypeException, InsufficientInventoryException {

        if (!roomInventory.containsKey(roomType)) {
            throw new InvalidRoomTypeException("Error: Room type '" + roomType + "' does not exist.");
        }

        int available = roomInventory.get(roomType);
        if (available <= 0) {
            throw new InsufficientInventoryException("Error: No " + roomType + " rooms left in inventory.");
        }

        roomInventory.put(roomType, available - 1);
        System.out.println("[Success] Room reserved: " + roomType + " (Remaining: " + (available - 1) + ")");
    }
}

public class UseCase9ErrorHandlingValidation {
    public static void main(String[] args) {
        InventoryManager inventory = new InventoryManager();
        
        System.out.println("--- Book My Stay: Use Case 9 - Error Handling & Validation ---");
        System.out.println("Initial Inventory: Standard (2), Deluxe (1)\n");
        String[][] requests = {
            {"Alice", "Deluxe"},
            {"Bob", "Deluxe"},
            {"Charlie", "Penthouse"},
            {"Diana", "Standard"}
        };

        for (String[] request : requests) {
            String name = request[0];
            String type = request[1];

            System.out.println("Processing request for " + name + " (" + type + ")...");
            
            try {
                inventory.validateAndReserve(type);
                System.out.println("Status: Booking Confirmed for " + name);
            } 
            catch (InvalidRoomTypeException | InsufficientInventoryException e) {
                System.err.println("Validation Failed >> " + e.getMessage());
            } 
            catch (Exception e) {
                System.err.println("An unexpected error occurred: " + e.getMessage());
            }
            System.out.println("------------------------------------------------");
        }
        
        System.out.println("\nSystem remains stable. Validation prevented invalid states.");
    }
}
