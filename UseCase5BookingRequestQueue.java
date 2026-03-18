import java.util.LinkedList;
import java.util.Queue;


class ReservationRequest {
    private String guestName;
    private String roomType;

    public ReservationRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }

    @Override
    public String toString() {
        return "Request [Guest: " + guestName + ", Room: " + roomType + "]";
    }
}


class BookingQueue {
    private Queue<ReservationRequest> requestQueue;

    public BookingQueue() {
        this.requestQueue = new LinkedList<>();
    }

    public void addRequest(ReservationRequest request) {
        requestQueue.offer(request);
        System.out.println("Enqueued: " + request);
    }


    public void showQueue() {
        System.out.println("\nCurrent Booking Request Queue (FIFO Order):");
        System.out.println("------------------------------------------");
        if (requestQueue.isEmpty()) {
            System.out.println("No pending requests.");
        } else {
            for (ReservationRequest req : requestQueue) {
                System.out.println(">> " + req);
            }
        }
        System.out.println("------------------------------------------");
    }
    public Queue<ReservationRequest> getInternalQueue() {
        return requestQueue;
    }
}

public class UseCase5BookingRequestQueue {
    public static void main(String[] args) {
        System.out.println("******************************************");
        System.out.println("   Book My Stay - Booking Intake System  ");
        System.out.println("   Version: 5.0                          ");
        System.out.println("******************************************");
        BookingQueue bookingOffice = new BookingQueue();
        bookingOffice.addRequest(new ReservationRequest("Alice", "Suite Room"));
        bookingOffice.addRequest(new ReservationRequest("Bob", "Single Room"));
        bookingOffice.addRequest(new ReservationRequest("Charlie", "Suite Room"));
        bookingOffice.addRequest(new ReservationRequest("Diana", "Double Room"));
        bookingOffice.showQueue();
        System.out.println("Status: Requests collected and ordered. Ready for allocation.");
    }
}
