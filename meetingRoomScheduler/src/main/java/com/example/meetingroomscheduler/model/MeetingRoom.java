package com.example.meetingroomscheduler.model;

import java.util.ArrayList;
import java.util.List;

public class MeetingRoom {
    private String name; // Name of the meeting room
    private List<Booking> bookings = new ArrayList<>(); // List of bookings for this room

    public MeetingRoom(String name) {
        this.name = name;
    }

    // Getter for the room name
    public String getName() {
        return name;
    }

    // Getter for the room's list of bookings
    public List<Booking> getBookings() {
        return bookings;
    }

    // Check if the room is available for the given time slot
    public boolean isAvailable(java.time.LocalTime start, java.time.LocalTime end) {
        for (Booking booking : bookings) {
            if (booking.overlaps(start, end)) {
                return false; // Room is already booked for this time slot
            }
        }
        return true; // Room is available
    }

    // Add a booking to the room
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    // Clear all bookings for the room
    public void clearBookings() {
        bookings.clear();
    }
}

/*
Explanation:
MeetingRoom Class:
Represents a single meeting room.
Keeps track of its name and a list of Booking objects.
Provides a method to check if the room is available for a specific time slot (isAvailable).
Includes functionality to add a booking and clear all bookings (used during the daily reset).
Booking Class:
Represents a single booking with a start and end time.
Includes methods to get/set start and end times.
Provides a method (overlaps) to check if the booking conflicts with another time slot.
Where These Fit:
The MeetingRoom class is referenced by the repository and service layers to manage room availability and bookings.
The Booking class encapsulates the logic for time slot management and ensures no overlapping bookings.
*/