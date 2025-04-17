package entity.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdoptionEvent {
    private int id;  // optional if using database auto-increment
    private String eventName;
    private LocalDate eventDate;
    private List<String> participants;

    public AdoptionEvent(String eventName, LocalDate eventDate) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.participants = new ArrayList<>();
    }

    public AdoptionEvent(int id, String eventName, LocalDate eventDate) {
        this.id = id;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.participants = new ArrayList<>();
    }

    public void addParticipant(String name) {
        participants.add(name);
    }

    public String getEventName() {
        return eventName;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public int getId() {
        return id;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AdoptionEvent{" +
                "id=" + id +
                ", eventName='" + eventName + '\'' +
                ", eventDate=" + eventDate +
                ", participants=" + participants +
                '}';
    }
}
