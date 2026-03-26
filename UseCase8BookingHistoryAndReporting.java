import java.util.*;

class Booking {
    private String bookingId;
    private String guestName;
    private String roomType;
    private double basePrice;

    public Booking(String bookingId, String guestName, String roomType, double basePrice) {
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.basePrice = basePrice;
    }

    public String getBookingId() { return bookingId; }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public double getBasePrice() { return basePrice; }

    @Override
    public String toString() {
        return String.format("ID: %s | Guest: %-10s | Room: %-10s | Cost: $%.2f", 
                             bookingId, guestName, roomType, basePrice);
    }
}

class BookingHistory {
    private List<Booking> history;
    public BookingHistory() {
        this.history = new ArrayList<>();
    }

    public void recordBooking(Booking booking) {
        history.add(booking);
        System.out.println("[System] Recording confirmed booking: " + booking.getBookingId());
    }

    public List<Booking> getAllBookings() {
        return new ArrayList<>(history);
}

class BookingReportService {
    
    public void generateSummaryReport(List<Booking> records) {
        System.out.println("\n--- ADMINISTRATIVE BOOKING REPORT ---");
        if (records.isEmpty()) {
            System.out.println("No records found in history.");
            return;
        }

        double totalRevenue = 0;
        Map<String, Integer> roomTypeCount = new HashMap<>();

        for (Booking b : records) {
            System.out.println(b);
            totalRevenue += b.getBasePrice();
            roomTypeCount.put(b.getRoomType(), roomTypeCount.getOrDefault(b.getRoomType(), 0) + 1);
        }

        System.out.println("-------------------------------------");
        System.out.println("Total Bookings Processed: " + records.size());
        System.out.printf("Total Revenue Generated: $%.2f\n", totalRevenue);
        System.out.println("Bookings by Room Type: " + roomTypeCount);
        System.out.println("-------------------------------------\n");
    }
}

public class UseCase8BookingHistoryReport {
    public static void main(String[] args) {
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        System.out.println("--- Book My Stay: Use Case 8 - Booking History & Reporting ---");
        history.recordBooking(new Booking("BK001", "Alice", "Deluxe", 250.00));
        history.recordBooking(new Booking("BK002", "Bob", "Standard", 120.00));
        history.recordBooking(new Booking("BK003", "Charlie", "Suite", 500.00));
        history.recordBooking(new Booking("BK004", "Diana", "Deluxe", 250.00));
        System.out.println("\nAdmin Request: Generate Operational Report...");
        List<Booking> currentHistory = history.getAllBookings();
        reportService.generateSummaryReport(currentHistory);
        history.recordBooking(new Booking("BK005", "Edward", "Standard", 120.00));
        
        System.out.println("\nAdmin Request: Generate Updated Report...");
        reportService.generateSummaryReport(history.getAllBookings());
    }
}
