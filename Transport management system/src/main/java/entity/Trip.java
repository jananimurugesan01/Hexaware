package entity;

import java.time.LocalDateTime;

public class Trip {
    private int tripId;
    private int vehicleId;
    private int routeId;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private String status;
    private String tripType;
    private int maxPassengers;

    public Trip() {}

    public Trip(int tripId, int vehicleId, int routeId, LocalDateTime departureDate,
                LocalDateTime arrivalDate, String status, String tripType, int maxPassengers) {
        this.tripId = tripId;
        this.vehicleId = vehicleId;
        this.routeId = routeId;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.status = status;
        this.tripType = tripType;
        this.maxPassengers = maxPassengers;
    }

    public int getTripId() { return tripId; }
    public void setTripId(int tripId) { this.tripId = tripId; }

    public int getVehicleId() { return vehicleId; }
    public void setVehicleId(int vehicleId) { this.vehicleId = vehicleId; }

    public int getRouteId() { return routeId; }
    public void setRouteId(int routeId) { this.routeId = routeId; }

    public LocalDateTime getDepartureDate() { return departureDate; }
    public void setDepartureDate(LocalDateTime departureDate) { this.departureDate = departureDate; }

    public LocalDateTime getArrivalDate() { return arrivalDate; }
    public void setArrivalDate(LocalDateTime arrivalDate) { this.arrivalDate = arrivalDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getTripType() { return tripType; }
    public void setTripType(String tripType) { this.tripType = tripType; }

    public int getMaxPassengers() { return maxPassengers; }
    public void setMaxPassengers(int maxPassengers) { this.maxPassengers = maxPassengers; }
}
