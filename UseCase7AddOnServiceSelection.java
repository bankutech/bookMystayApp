import java.util.*;

class Service {
    private String serviceName;
    private double price;

    public Service(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return serviceName + " ($" + price + ")";
    }
}


class AddOnServiceManager {
    private Map<String, List<Service>> reservationServices;

    public AddOnServiceManager() {
        this.reservationServices = new HashMap<>();
    }
    public void addServiceToReservation(String reservationId, Service service) {

        reservationServices.computeIfAbsent(reservationId, k -> new ArrayList<>()).add(service);
        System.out.println("Added '" + service.getServiceName() + "' to Reservation: " + reservationId);
    }

    public double calculateTotalServiceCost(String reservationId) {
        List<Service> services = reservationServices.get(reservationId);
        if (services == null || services.isEmpty()) {
            return 0.0;
        }
        return services.stream().mapToDouble(Service::getPrice).sum();
    }
    public void displayReservationServices(String reservationId) {
        List<Service> services = reservationServices.get(reservationId);
        System.out.println("\n--- Service Summary for " + reservationId + " ---");
        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
        } else {
            services.forEach(s -> System.out.println("- " + s));
            System.out.println("Total Additional Cost: $" + calculateTotalServiceCost(reservationId));
        }
    }
}

public class UseCase7AddOnServiceSelection {
    public static void main(String[] args) {
        System.out.println("--- Book My Stay: Add-On Service Selection ---");

        AddOnServiceManager serviceManager = new AddOnServiceManager();

        Service breakfast = new Service("Buffet Breakfast", 25.0);
        Service spa = new Service("Spa Treatment", 80.0);
        Service airportPickUp = new Service("Airport Pick-up", 45.0);
        Service lateCheckout = new Service("Late Check-out", 30.0);

        String resId1 = "RES1001";
        serviceManager.addServiceToReservation(resId1, breakfast);
        serviceManager.addServiceToReservation(resId1, spa);

        String resId2 = "RES1002";
        serviceManager.addServiceToReservation(resId2, airportPickUp);
        serviceManager.addServiceToReservation(resId2, breakfast);
        serviceManager.addServiceToReservation(resId2, lateCheckout);

        serviceManager.displayReservationServices(resId1);
        serviceManager.displayReservationServices(resId2);
        serviceManager.displayReservationServices("RES1003");
    }
}
