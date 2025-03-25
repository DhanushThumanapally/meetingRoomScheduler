package com.example.meetingroomscheduler.repository;

import com.example.meetingroomscheduler.model.MeetingRoom;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MeetingRoomRepository {

    private List<MeetingRoom> meetingRooms = new ArrayList<>();

    @PostConstruct
    public void init() {
        // Initialize with fixed meeting rooms
        meetingRooms.add(new MeetingRoom("Deepak's room"));
        meetingRooms.add(new MeetingRoom("Srinivas's room"));
        meetingRooms.add(new MeetingRoom("Vomann's room"));
        meetingRooms.add(new MeetingRoom("Dhanush's room"));
        meetingRooms.add(new MeetingRoom("Ganesh's room ground floor"));
    }

    // Fetch all meeting rooms
    public List<MeetingRoom> getMeetingRooms() {
        return meetingRooms;
    }

    // Clear all bookings (for daily reset after 7pm IST)
    public void clearAllBookings() {
        for (MeetingRoom room : meetingRooms) {
            room.clearBookings();
        }
    }
}


/*
Explanation of this Code:
Repository Role:
Acts as an intermediary between the service layer (SchedulerService) and the meeting room data.
This is a simple in-memory repository (data stored temporarily in the application).
Meeting Rooms Initialization:
The @PostConstruct annotation runs the init() method after the repository is created, populating it with the fixed list of four meeting rooms:
Deepak's room
Srinivas's room
Vomann's room
Dhanush's room
Daily Booking Reset:
The clearAllBookings() method clears all bookings for every room. This method is triggered when the service detects that the current time is past 7pm.
Fetch Meeting Rooms:
The getMeetingRooms() method returns the current list of meeting rooms. This is used by the SchedulerService to query or filter available rooms.
Where This Fits:
The repository is called by SchedulerService to fetch meeting room data and to reset bookings.
It contains all meeting room data in memory during the application's runtime (no external database is needed for now).
*/

