import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class Reservation {
    private String pnr;
    private String name;
    private String trainNumber;
    private String trainName;
    private String classType;
    private String date;
    private String from;
    private String to;

    public Reservation(String pnr, String name, String trainNumber, String trainName, String classType, String date, String from, String to) {
        this.pnr = pnr;
        this.name = name;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.classType = classType;
        this.date = date;
        this.from = from;
        this.to = to;
    }

    public String getPnr() {
        return pnr;
    }

    public String getDetails() {
        return "PNR: " + pnr + ", Name: " + name + ", Train Number: " + trainNumber + ", Train Name: " + trainName +
               ", Class: " + classType + ", Date: " + date + ", From: " + from + ", To: " + to;
    }
}

class ReservationSystem {
    private List<User> users = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();
    private int nextPnr = 1000; // Starting PNR number

    public ReservationSystem() {
        // Sample user
        users.add(new User("user1", "password1"));
    }

    public boolean login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public void register(String username, String password) {
        // Check if the username already exists
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username already exists. Please choose a different username.");
                return;
            }
        }
        // Add new user
        users.add(new User(username, password));
        System.out.println("Registration successful!");
    }

    public Reservation makeReservation(String name, String trainNumber, String trainName, String classType, String date, String from, String to) {
        String pnr = String.valueOf(nextPnr++);
        Reservation reservation = new Reservation(pnr, name, trainNumber, trainName, classType, date, from, to);
        reservations.add(reservation);
        return reservation;
    }

    public Reservation cancelReservation(String pnr) {
        for (Reservation reservation : reservations) {
            if (reservation.getPnr().equals(pnr)) {
                reservations.remove(reservation);
                return reservation;
            }
        }
        return null;
    }

    public List<Reservation> getAllReservations() {
        return reservations;
    }
}

class ReservationSystemUI {
    private ReservationSystem reservationSystem = new ReservationSystem();

    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Online Reservation System");

        while (true) {
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Username: ");
                    String newUsername = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String newPassword = scanner.nextLine();
                    reservationSystem.register(newUsername, newPassword);
                    break;
                case 2:
                    System.out.print("Enter Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String password = scanner.nextLine();

                    if (!reservationSystem.login(username, password)) {
                        System.out.println("Invalid login. Please try again.");
                        break;
                    }

                    // User login successful
                    while (true) {
                        System.out.println("\n1. Make a Reservation");
                        System.out.println("2. View All Reservations");
                        System.out.println("3. Cancel a Reservation");
                        System.out.println("4. Logout");

                        int userChoice = scanner.nextInt();
                        scanner.nextLine();

                        switch (userChoice) {
                            case 1:
                                System.out.print("Name: ");
                                String name = scanner.nextLine();
                                System.out.print("Train Number: ");
                                String trainNumber = scanner.nextLine();
                                System.out.print("Train Name: ");
                                String trainName = scanner.nextLine();
                                System.out.print("Class Type: ");
                                String classType = scanner.nextLine();
                                System.out.print("Date of Journey (YYYY-MM-DD): ");
                                String date = scanner.nextLine();
                                System.out.print("From: ");
                                String from = scanner.nextLine();
                                System.out.print("To: ");
                                String to = scanner.nextLine();

                                Reservation reservation = reservationSystem.makeReservation(name, trainNumber, trainName, classType, date, from, to);
                                System.out.println("Reservation made with PNR " + reservation.getPnr());
                                break;
                            case 2:
                                System.out.println("All Reservations:");
                                for (Reservation r : reservationSystem.getAllReservations()) {
                                    System.out.println(r.getDetails());
                                }
                                break;
                            case 3:
                                System.out.print("Enter PNR number to cancel: ");
                                String pnr = scanner.nextLine();
                                Reservation canceledReservation = reservationSystem.cancelReservation(pnr);
                                if (canceledReservation != null) {
                                    System.out.println("Reservation canceled: " + canceledReservation.getDetails());
                                } else {
                                    System.out.println("Reservation not found with PNR " + pnr);
                                }
                                break;
                            case 4:
                                System.out.println("Logged out successfully.");
                                break; // Breaks the inner loop to go back to the main menu
                            default:
                                System.out.println("Invalid choice. Please try again.");
                        }
                        if (userChoice == 4) {
                            break; // Exit to main menu after logout
                        }
                    }
                    break;
                case 3:
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        ReservationSystemUI obj = new ReservationSystemUI();
        obj.start();
    }
}
