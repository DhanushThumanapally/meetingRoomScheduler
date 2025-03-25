package com.example.meetingroomscheduler.controller;

import com.example.meetingroomscheduler.model.MeetingRoom;
import com.example.meetingroomscheduler.service.SchedulerService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@Controller
public class SchedulerController {

    private final SchedulerService schedulerService;

    public SchedulerController(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    // Home page: Time Slot Selection
    @GetMapping("/")
    public String home() {
        return "index"; // Displays the index.html page for time slot selection
    }

    // Handle time slot submission and show available rooms
    @PostMapping("/selectSlot")
    public String selectSlot(@RequestParam("startTime")
                             @DateTimeFormat(pattern = "HH:mm") LocalTime startTime,
                             @RequestParam("endTime")
                             @DateTimeFormat(pattern = "HH:mm") LocalTime endTime,
                             Model model) {
        // Validate time slot: must be within 6:00 - 18:00 and start before end
        LocalTime sixAM = LocalTime.of(6, 0);
        LocalTime sixPM = LocalTime.of(18, 0);
        if (startTime.isBefore(sixAM) || endTime.isAfter(sixPM) || !startTime.isBefore(endTime)) {
            model.addAttribute("error", "Please select a valid time slot between 6:00 and 18:00.");
            return "index"; // Redirect back to the home page if validation fails
        }

        // Pass time slot details to model
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);

        // Retrieve available rooms based on the provided slot
        List<MeetingRoom> availableRooms = schedulerService.getAvailableRooms(startTime, endTime);
        model.addAttribute("availableRooms", availableRooms);

        return "rooms"; // Displays the rooms.html page with available rooms
    }

    // When a user selects a room, go to the payment page
    @PostMapping("/bookRoom")
    public String bookRoom(@RequestParam("roomName") String roomName,
                           @RequestParam("startTime")
                           @DateTimeFormat(pattern = "HH:mm") LocalTime startTime,
                           @RequestParam("endTime")
                           @DateTimeFormat(pattern = "HH:mm") LocalTime endTime,
                           Model model) {
        model.addAttribute("roomName", roomName);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        return "payment"; // Displays the payment.html page for payment simulation
    }

    // Simulate payment and confirm booking
    @PostMapping("/makePayment")
    public String makePayment(@RequestParam("roomName") String roomName,
                              @RequestParam("startTime")
                              @DateTimeFormat(pattern = "HH:mm") LocalTime startTime,
                              @RequestParam("endTime")
                              @DateTimeFormat(pattern = "HH:mm") LocalTime endTime,
                              Model model) {
        boolean success = schedulerService.bookRoom(roomName, startTime, endTime);
        model.addAttribute("bookingSuccess", success);
        model.addAttribute("roomName", roomName);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        return "confirmation"; // Displays confirmation.html page showing booking result
    }

    // Optional endpoint to manually test booking reset (for debugging)
    @GetMapping("/reset")
    @ResponseBody
    public String reset() {
        schedulerService.clearBookingsIfNeeded();
        return "Bookings reset if after 7pm IST.";
    }
}

/*
What This Does:
Handles Requests:
Maps HTTP requests to specific methods.
Example: @GetMapping("/") maps the root URL (/) to the home() method, which shows the time slot selection page.
Passes Data to HTML Pages:
Uses Spring Model to pass data (like available rooms, booking status) to templates like index.html, rooms.html, etc.
Validates Input:
Ensures the time slot is valid (e.g., between 6:00 and 18:00).
Simulates Payment and Booking:
After selecting a room, the user goes to the payment page and confirms the booking.
Booking Reset Logic:
Automatically clears all bookings if the current time is past 7:00 PM IST.

*/