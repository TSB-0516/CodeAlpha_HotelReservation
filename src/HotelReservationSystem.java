package src;

import java.util.ArrayList;
import java.util.Scanner;

public class HotelReservationSystem {
    private final ArrayList<Room> rooms = new ArrayList<>();
    private final ArrayList<Reservation> reservations = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public HotelReservationSystem() {
        rooms.add(new Room(101, "Single", 1000));
        rooms.add(new Room(102, "Double", 2000));
        rooms.add(new Room(103, "Suite", 5000));
    }

    public void start() {
        while (true) {
            System.out.println("\n1. View Available Rooms");
            System.out.println("2. Make a Reservation");
            System.out.println("3. View Booking Details");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> viewAvailableRooms();
                case 2 -> makeReservation();
                case 3 -> viewBookingDetails();
                case 4 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void viewAvailableRooms() {
        System.out.println("\nAvailable Rooms:");
        rooms.stream()
                .filter(room -> !room.isBooked())
                .forEach(System.out::println);
    }

    private void makeReservation() {
        System.out.print("Enter Room Number to Reserve: ");
        int roomNumber = scanner.nextInt();

        Room room = findRoomByNumber(roomNumber);
        if (room == null || room.isBooked()) {
            System.out.println("Room not available. Try another.");
            return;
        }

        scanner.nextLine(); // Clear the buffer
        System.out.print("Enter Your Name: ");
        String name = scanner.nextLine();

        room.setBooked(true);
        reservations.add(new Reservation(name, room));
        System.out.println("Reservation confirmed for " + name + " in Room " + roomNumber + ".");
    }

    private void viewBookingDetails() {
        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        System.out.println("\nBooking Details:");
        reservations.forEach(System.out::println);
    }

    private Room findRoomByNumber(int roomNumber) {
        return rooms.stream()
                .filter(room -> room.getRoomNumber() == roomNumber)
                .findFirst()
                .orElse(null);
    }
}

class Room {
    private final int roomNumber;
    private final String category;
    private final double pricePerNight;
    private boolean booked;

    public Room(int roomNumber, String category, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.pricePerNight = pricePerNight;
        this.booked = false;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + category + ") - Rs. " + pricePerNight + " per night";
    }
}

class Reservation {
    private final String customerName;
    private final Room room;

    public Reservation(String customerName, Room room) {
        this.customerName = customerName;
        this.room = room;
    }

    @Override
    public String toString() {
        return "Customer: " + customerName + ", Room: " + room.getRoomNumber() + " (" + room.getCategory() + ")";
    }
}
