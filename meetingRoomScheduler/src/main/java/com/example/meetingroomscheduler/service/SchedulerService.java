package com.example.meetingroomscheduler.service;

import com.example.meetingroomscheduler.model.Booking;
import com.example.meetingroomscheduler.model.MeetingRoom;
import com.example.meetingroomscheduler.repository.MeetingRoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchedulerService {

    private final MeetingRoomRepository repository;

    public SchedulerService(MeetingRoomRepository repository) {
        this.repository = repository;
    }

    // Clear bookings if the current time is after 7pm (IST)
    public void clearBookingsIfNeeded() {
        LocalTime now = LocalTime.now(); // For proper IST handling, ensure your server timezone is set to IST
        if (now.getHour() >= 19) { // After 7:00 PM
            repository.clearAllBookings();
        }
    }

    // Get a list of available meeting rooms for a given time slot
    public List<MeetingRoom> getAvailableRooms(LocalTime start, LocalTime end) {
        clearBookingsIfNeeded(); // Ensure rooms are cleared if past 7:00 PM
        return repository.getMeetingRooms().stream()
                .filter(room -> room.isAvailable(start, end))
                .collect(Collectors.toList());
    }

    // Book a meeting room for the given time slot
    public boolean bookRoom(String roomName, LocalTime start, LocalTime end) {
        clearBookingsIfNeeded(); // Ensure rooms are cleared if past 7:00 PM
        for (MeetingRoom room : repository.getMeetingRooms()) {
            if (room.getName().equals(roomName) && room.isAvailable(start, end)) {
                room.addBooking(new Booking(start, end));
                return true; // Booking successful
            }
        }
        return false; // Booking failed (room not available)
    }
}

/*
What This Service Does:
Handles Business Logic:
The SchedulerService ensures your app handles meeting room availability and bookings dynamically.
Automatic Booking Reset:
The clearBookingsIfNeeded method automatically clears all room bookings after 7:00 PM.
Fetch Available Rooms:
The getAvailableRooms method checks the database (or repository) and returns a list of rooms that are free for a given time slot.
Handle Room Booking:
The bookRoom method books a specific room for the user if it’s available for the requested time slot.
Where It Fits:
The Controller calls these methods whenever a user interacts with the app (e.g., searching for rooms or making a booking).
The Repository provides the list of meeting rooms, while the SchedulerService takes care of business logic like filtering available rooms or resetting bookings.
*/
