package dao;

import entity.*;
import exception.BookingNotFoundException;
import exception.VehicleNotFoundException;
import util.DBConnUtil;
import util.DBPropertyUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransportManagementServiceImpl implements TransportManagementService {

    private Connection getConnection() throws SQLException {
        String connStr = DBPropertyUtil.getConnectionString("db.properties");
        return DBConnUtil.getConnection(connStr);
    }

    @Override
    public boolean addVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO Vehicles (Model, Capacity, Type, Status) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, vehicle.getModel());
            ps.setDouble(2, vehicle.getCapacity());
            ps.setString(3, vehicle.getType());
            ps.setString(4, vehicle.getStatus());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace(); // Log properly in real app
        }
        return false;
    }

    @Override
    public boolean updateVehicle(Vehicle vehicle) {
        String sql = "UPDATE Vehicles SET Model=?, Capacity=?, Type=?, Status=? WHERE VehicleID=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, vehicle.getModel());
            ps.setDouble(2, vehicle.getCapacity());
            ps.setString(3, vehicle.getType());
            ps.setString(4, vehicle.getStatus());
            ps.setInt(5, vehicle.getVehicleId());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteVehicle(int vehicleId) {
        String sql = "DELETE FROM Vehicles WHERE VehicleID=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, vehicleId);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new VehicleNotFoundException("Vehicle with ID " + vehicleId + " not found.");
            }
            return true;

        } catch (SQLException | VehicleNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean scheduleTrip(int vehicleId, int routeId, String departureDate, String arrivalDate) {
        String sql = "INSERT INTO Trips (VehicleID, RouteID, DepartureDate, ArrivalDate, Status, TripType, MaxPassengers) " +
                     "VALUES (?, ?, ?, ?, 'Scheduled', 'Freight', 0)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, vehicleId);
            ps.setInt(2, routeId);
            ps.setTimestamp(3, Timestamp.valueOf(departureDate));
            ps.setTimestamp(4, Timestamp.valueOf(arrivalDate));
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean cancelTrip(int tripId) {
        String sql = "UPDATE Trips SET Status='Cancelled' WHERE TripID=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, tripId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean bookTrip(int tripId, int passengerId, String bookingDate) {
        String sql = "INSERT INTO Bookings (TripID, PassengerID, BookingDate, Status) VALUES (?, ?, ?, 'Confirmed')";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, tripId);
            ps.setInt(2, passengerId);
            ps.setTimestamp(3, Timestamp.valueOf(bookingDate));
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean cancelBooking(int bookingId) {
        String sql = "UPDATE Bookings SET Status='Cancelled' WHERE BookingID=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new BookingNotFoundException("Booking ID " + bookingId + " not found.");
            }
            return true;

        } catch (SQLException | BookingNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean allocateDriver(int tripId, int driverId) {
        String sql = "INSERT INTO TripDriver (TripID, DriverID) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, tripId);
            ps.setInt(2, driverId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deallocateDriver(int tripId) {
        String sql = "DELETE FROM TripDriver WHERE TripID=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, tripId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Booking> getBookingsByPassenger(int passengerId) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM Bookings WHERE PassengerID=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, passengerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Booking b = new Booking(
                    rs.getInt("BookingID"),
                    rs.getInt("TripID"),
                    rs.getInt("PassengerID"),
                    rs.getTimestamp("BookingDate").toLocalDateTime(),
                    rs.getString("Status")
                );
                list.add(b);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Booking> getBookingsByTrip(int tripId) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM Bookings WHERE TripID=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, tripId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Booking b = new Booking(
                    rs.getInt("BookingID"),
                    rs.getInt("TripID"),
                    rs.getInt("PassengerID"),
                    rs.getTimestamp("BookingDate").toLocalDateTime(),
                    rs.getString("Status")
                );
                list.add(b);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Driver> getAvailableDrivers() {
        List<Driver> list = new ArrayList<>();
        String sql = "SELECT * FROM Drivers WHERE Status='Available'";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Driver driver = new Driver(
                    rs.getInt("DriverID"),
                    rs.getString("Name"),
                    rs.getString("LicenseNumber"),
                    rs.getString("Status")
                );
                list.add(driver);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    @Override
    public boolean deleteVehicle(int vehicleId) throws VehicleNotFoundException {
        // if not found in DB:
        throw new VehicleNotFoundException("Vehicle ID not found: " + vehicleId);
    }
    @Override
    public List<Booking> getBookingsByPassenger(int passengerId) throws BookingNotFoundException {
        List<Booking> bookings = new ArrayList<>();
        // (your DB logic here)

        if (bookings.isEmpty()) {
            throw new BookingNotFoundException("No bookings found for passenger ID: " + passengerId);
        }

        return bookings;
    }

}
