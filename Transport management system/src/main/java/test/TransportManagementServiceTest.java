package test;

import dao.TransportManagementService;
import dao.TransportManagementServiceImpl;
import entity.Booking;
import entity.Driver;
import entity.Vehicle;
import exception.BookingNotFoundException;
import exception.VehicleNotFoundException;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TransportManagementServiceTest {

    private static TransportManagementService service;

    @BeforeAll
    public static void setup() {
        service = new TransportManagementServiceImpl();
    }

    @Test
    @Order(1)
    public void testAddVehicle() {
        Vehicle vehicle = new Vehicle(0, "Tata Ace", 1000.0, "Truck", "Available");
        boolean result = service.addVehicle(vehicle);
        assertTrue(result, "Vehicle should be added successfully.");
    }

    @Test
    @Order(2)
    public void testUpdateVehicle() {
        Vehicle vehicle = new Vehicle(1, "Tata Ace XL", 1200.0, "Truck", "Maintenance");
        boolean result = service.updateVehicle(vehicle);
        assertTrue(result, "Vehicle should be updated successfully.");
    }

    @Test
    @Order(3)
    public void testVehicleNotFoundException() {
        assertThrows(VehicleNotFoundException.class, () -> {
            boolean result = service.deleteVehicle(9999); // Invalid ID
            assertFalse(result);
        });
    }

    @Test
    @Order(4)
    public void testScheduleTrip() {
        boolean result = service.scheduleTrip(1, 1, "2025-05-01 08:00:00", "2025-05-01 20:00:00");
        assertTrue(result, "Trip should be scheduled successfully.");
    }

    @Test
    @Order(5)
    public void testBookTrip() {
        boolean result = service.bookTrip(1, 1, "2025-04-16 10:30:00");
        assertTrue(result, "Trip booking should be successful.");
    }

    @Test
    @Order(6)
    public void testBookingNotFoundException() {
        assertThrows(BookingNotFoundException.class, () -> {
            service.cancelBooking(9999); // Invalid booking ID
        });
    }

    @Test
    @Order(7)
    public void testGetBookingsByPassenger() {
        List<Booking> bookings = service.getBookingsByPassenger(1);
        assertNotNull(bookings, "Booking list should not be null.");
    }

    @Test
    @Order(8)
    public void testGetAvailableDrivers() {
        List<Driver> drivers = service.getAvailableDrivers();
        assertNotNull(drivers, "Available drivers list should not be null.");
    }

    @Test
    @Order(9)
    public void testDriverAllocation() {
        boolean allocated = service.allocateDriver(1, 1); // tripId and driverId
        assertTrue(allocated, "Driver should be allocated successfully.");
    }

    @Test
    @Order(10)
    public void testDriverDeallocation() {
        boolean deallocated = service.deallocateDriver(1);
        assertTrue(deallocated, "Driver should be deallocated successfully.");
    }
}
