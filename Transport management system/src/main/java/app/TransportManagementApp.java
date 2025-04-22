package app;

import dao.TransportManagementService;
import dao.TransportManagementServiceImpl;
import entity.Vehicle;
import exception.BookingNotFoundException;
import exception.VehicleNotFoundException;

import java.util.Scanner;

public class TransportManagementApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TransportManagementService service = new TransportManagementServiceImpl();

        while (true) {
            System.out.println("\n--- Transport Management System ---");
            System.out.println("1. Add Vehicle");
            System.out.println("2. Update Vehicle");
            System.out.println("3. Delete Vehicle");
            System.out.println("4. Schedule Trip");
            System.out.println("5. Cancel Trip");
            System.out.println("6. Book Trip");
            System.out.println("7. Cancel Booking");
            System.out.println("8. Allocate Driver");
            System.out.println("9. Deallocate Driver");
            System.out.println("10. Get Bookings by Passenger");
            System.out.println("11. Get Bookings by Trip");
            System.out.println("12. Get Available Drivers");
            System.out.println("13. Exit");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(sc.nextLine());

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter Model: ");
                        String model = sc.nextLine();
                        System.out.print("Enter Capacity: ");
                        double capacity = Double.parseDouble(sc.nextLine());
                        System.out.print("Enter Type (Truck/Van/Bus): ");
                        String type = sc.nextLine();
                        System.out.print("Enter Status (Available/On Trip/Maintenance): ");
                        String status = sc.nextLine();
                        Vehicle vehicle = new Vehicle(0, model, capacity, type, status);
                        if (service.addVehicle(vehicle)) {
                            System.out.println("Vehicle added successfully.");
                        } else {
                            System.out.println("Failed to add vehicle.");
                        }
                        break;

                    case 2:
                        System.out.print("Enter Vehicle ID to Update: ");
                        int vId = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter New Model: ");
                        model = sc.nextLine();
                        System.out.print("Enter New Capacity: ");
                        capacity = Double.parseDouble(sc.nextLine());
                        System.out.print("Enter New Type: ");
                        type = sc.nextLine();
                        System.out.print("Enter New Status: ");
                        status = sc.nextLine();
                        Vehicle updatedVehicle = new Vehicle(vId, model, capacity, type, status);
                        if (service.updateVehicle(updatedVehicle)) {
                            System.out.println("Vehicle updated successfully.");
                        } else {
                            System.out.println("Failed to update vehicle.");
                        }
                        break;

                    case 3:
                        System.out.print("Enter Vehicle ID to Delete: ");
                        int deleteId = Integer.parseInt(sc.nextLine());
                        if (service.deleteVehicle(deleteId)) {
                            System.out.println("Vehicle deleted successfully.");
                        } else {
                            System.out.println("Failed to delete vehicle.");
                        }
                        break;

                    case 4:
                        System.out.print("Enter Vehicle ID: ");
                        int vehicleId = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter Route ID: ");
                        int routeId = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter Departure DateTime (yyyy-MM-ddTHH:mm:ss): ");
                        String dep = sc.nextLine();
                        System.out.print("Enter Arrival DateTime (yyyy-MM-ddTHH:mm:ss): ");
                        String arr = sc.nextLine();
                        if (service.scheduleTrip(vehicleId, routeId, dep, arr)) {
                            System.out.println("Trip scheduled successfully.");
                        } else {
                            System.out.println("Failed to schedule trip.");
                        }
                        break;

                    case 5:
                        System.out.print("Enter Trip ID to Cancel: ");
                        int tripId = Integer.parseInt(sc.nextLine());
                        if (service.cancelTrip(tripId)) {
                            System.out.println("Trip cancelled successfully.");
                        } else {
                            System.out.println("Failed to cancel trip.");
                        }
                        break;

                    case 6:
                        System.out.print("Enter Trip ID: ");
                        int tId = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter Passenger ID: ");
                        int pId = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter Booking DateTime (yyyy-MM-ddTHH:mm:ss): ");
                        String bookingDate = sc.nextLine();
                        if (service.bookTrip(tId, pId, bookingDate)) {
                            System.out.println("Trip booked successfully.");
                        } else {
                            System.out.println("Failed to book trip.");
                        }
                        break;

                    case 7:
                        System.out.print("Enter Booking ID to Cancel: ");
                        int bookingId = Integer.parseInt(sc.nextLine());
                        if (service.cancelBooking(bookingId)) {
                            System.out.println("Booking cancelled successfully.");
                        } else {
                            System.out.println("Failed to cancel booking.");
                        }
                        break;

                    case 8:
                        System.out.print("Enter Trip ID: ");
                        int allocateTripId = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter Driver ID: ");
                        int driverId = Integer.parseInt(sc.nextLine());
                        if (service.allocateDriver(allocateTripId, driverId)) {
                            System.out.println("Driver allocated successfully.");
                        } else {
                            System.out.println("Failed to allocate driver.");
                        }
                        break;

                    case 9:
                        System.out.print("Enter Trip ID: ");
                        int deallocateTripId = Integer.parseInt(sc.nextLine());
                        if (service.deallocateDriver(deallocateTripId)) {
                            System.out.println("Driver deallocated successfully.");
                        } else {
                            System.out.println("Failed to deallocate driver.");
                        }
                        break;

                    case 10:
                        System.out.print("Enter Passenger ID: ");
                        int passId = Integer.parseInt(sc.nextLine());
                        service.getBookingsByPassenger(passId)
                               .forEach(System.out::println);
                        break;

                    case 11:
                        System.out.print("Enter Trip ID: ");
                        int tripBId = Integer.parseInt(sc.nextLine());
                        service.getBookingsByTrip(tripBId)
                               .forEach(System.out::println);
                        break;

                    case 12:
                        service.getAvailableDrivers()
                               .forEach(System.out::println);
                        break;

                    case 0:
                        System.out.println("Exiting...");
                        System.exit(0);

                    default:
                        System.out.println("Invalid option.");
                }

            } catch (VehicleNotFoundException | BookingNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected Error: " + e.getMessage());
            }
        }
    }
}
