package com.example.meetingroomscheduler.model;

import java.time.LocalTime;

public class Booking {
    private LocalTime startTime; // Start time of the booking
    private LocalTime endTime;   // End time of the booking

    public Booking(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getter for start time
    public LocalTime getStartTime() {
        return startTime;
    }

    // Setter for start time
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    // Getter for end time
    public LocalTime getEndTime() {
        return endTime;
    }

    // Setter for end time
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    // Check if this booking overlaps with another time slot
    public boolean overlaps(LocalTime start, LocalTime end) {
        // No overlap if the new end is before the current start or new start is after the current end
        return !(end.compareTo(this.startTime) <= 0 || start.compareTo(this.endTime) >= 0);
    }
}
