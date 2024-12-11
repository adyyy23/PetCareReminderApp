package petReminder;
import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Connect to the database
        Connection connection = DatabaseConnection.connect();
        if (connection == null) {
            System.out.println("Database connection failed.");
            return;
        }

        // Menu loop for user interaction
        while (true) {
            System.out.println("\n--- Pet Care Reminder App ---");
            System.out.println("1. Add a New Pet");
            System.out.println("2. Add a Reminder");
            System.out.println("3. Add Appointments");
            System.out.println("4. View Pets");
            System.out.println("5. Check Due Reminders");
            System.out.println("6. View Due Reminders and Notifications");
            System.out.println("7. Adoption Records");
            System.out.println("8. Exit");

            System.out.print("Enter your choice: ");
            int choice = readInt(scanner);

            switch (choice) {
                case 1:
                    addPet(connection, scanner);
                    break;
                case 2:
                    addReminder(connection, scanner);
                    break;
                case 3:
                    addAppointment(connection, scanner);
                    break;
                case 4:
                    viewPets(connection);
                    break;
                case 5:
                    checkDueReminders(connection);
                    break;
                case 6:
                    viewDueRemindersWithNotifications(connection);
                    break;
                case 7:
                    viewAdoptionRecords(connection);
                    break;
                case 8:
                    System.out.println("Goodbye!");
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void addPet(Connection connection, Scanner scanner) {
        scanner.nextLine(); // Consume newline left over from previous nextInt()

        // Pet details input
        System.out.print("Enter pet name: ");
        String name = readString(scanner);

        System.out.print("Enter pet weight: ");
        float weight = readFloat(scanner);

        System.out.print("Enter pet age: ");
        int age = readInt(scanner);

        System.out.print("Enter animal category (e.g., dog, cat, etc.): ");
        String category = readString(scanner);

        System.out.print("Enter last checkup date (YYYY-MM-DD): ");
        String lastCheckup = readString(scanner);

        System.out.print("Enter last vaccination date (YYYY-MM-DD): ");
        String lastVaccination = readString(scanner);

        System.out.print("Enter disease name (if any): ");
        String disease = readString(scanner);

        try {
            String query = "INSERT INTO pets (name, weight, age, category, last_checkup, last_vaccination, disease) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setFloat(2, weight);
            stmt.setInt(3, age);
            stmt.setString(4, category);
            stmt.setString(5, lastCheckup);
            stmt.setString(6, lastVaccination);
            stmt.setString(7, disease);
            stmt.executeUpdate();
            System.out.println("Pet added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addReminder(Connection connection, Scanner scanner) {
        System.out.print("Enter pet ID for reminder: ");
        int petId = readInt(scanner);

        System.out.println("Choose a reminder type:");
        System.out.println("1. Feeding");
        System.out.println("2. Walking");
        int reminderChoice = readInt(scanner);

        if (reminderChoice == 1) {
            System.out.print("Enter the amount of food (in grams): ");
            int foodAmount = readInt(scanner);

            System.out.print("Enter time for feeding (HH:MM): ");
            String feedingTime = readString(scanner);

            try {
                String query = "INSERT INTO reminders (petId, task_description, time, completed, food_amount) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setInt(1, petId);
                stmt.setString(2, "Feeding");
                stmt.setString(3, feedingTime);
                stmt.setBoolean(4, false);
                stmt.setInt(5, foodAmount);
                stmt.executeUpdate();
                System.out.println("Feeding reminder added successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (reminderChoice == 2) {
            System.out.print("Enter walking schedule time (HH:MM): ");
            String walkingTime = readString(scanner);

            try {
                String query = "INSERT INTO reminders (petId, task_description, time, completed) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setInt(1, petId);
                stmt.setString(2, "Walking");
                stmt.setString(3, walkingTime);
                stmt.setBoolean(4, false);
                stmt.executeUpdate();
                System.out.println("Walking reminder added successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid reminder type.");
        }
    }

    private static void addAppointment(Connection connection, Scanner scanner) {
        System.out.print("Enter pet ID for appointment: ");
        int petId = readInt(scanner);

        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        String appointmentDate = readString(scanner);

        System.out.print("Enter appointment time (HH:MM): ");
        String appointmentTime = readString(scanner);

        System.out.print("Enter doctor's name: ");
        String doctorName = readString(scanner);

        System.out.println("Would you like to reschedule this appointment? (y/n)");
        String rescheduleChoice = readString(scanner);

        if (rescheduleChoice.equalsIgnoreCase("y")) {
            System.out.print("Enter new appointment date (YYYY-MM-DD): ");
            appointmentDate = readString(scanner);

            System.out.print("Enter new appointment time (HH:MM): ");
            appointmentTime = readString(scanner);
        }

        try {
            String query = "INSERT INTO appointments (petId, appointment_date, appointment_time, doctor_name) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, petId);
            stmt.setString(2, appointmentDate);
            stmt.setString(3, appointmentTime);
            stmt.setString(4, doctorName);
            stmt.executeUpdate();
            System.out.println("Appointment scheduled successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewPets(Connection connection) {
        try {
            String query = "SELECT * FROM pets";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("\n--- Pets ---");
            if (!rs.isBeforeFirst()) {
                System.out.println("No pets added.");
            } else {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Name: " + rs.getString("name"));
                    System.out.println("Weight: " + rs.getFloat("weight"));
                    System.out.println("Age: " + rs.getInt("age"));
                    System.out.println("Category: " + rs.getString("category"));
                    System.out.println("Last Checkup: " + rs.getString("last_checkup"));
                    System.out.println("Last Vaccination: " + rs.getString("last_vaccination"));
                    System.out.println("Disease: " + rs.getString("disease"));
                    System.out.println("-----------------------------");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void checkDueReminders(Connection connection) {
        try {
            String query = "SELECT * FROM reminders WHERE completed = false";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("\n--- Due Reminders ---");
            if (!rs.isBeforeFirst()) {
                System.out.println("No due reminders.");
            } else {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Pet ID: " + rs.getInt("petId"));
                    System.out.println("Task: " + rs.getString("task_description"));
                    System.out.println("Time: " + rs.getString("time"));
                    System.out.println("-----------------------------");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewDueRemindersWithNotifications(Connection connection) {
        try {
            String query = "SELECT * FROM reminders WHERE completed = false";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("\n--- Due Reminders with Notifications ---");
            if (!rs.isBeforeFirst()) {
                System.out.println("No due reminders.");
            } else {
                while (rs.next()) {
                    String status = "Not Due";
                    // Check if the reminder is due (You can add more logic here to check the actual date and time)
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Pet ID: " + rs.getInt("petId"));
                    System.out.println("Task: " + rs.getString("task_description"));
                    System.out.println("Time: " + rs.getString("time"));
                    System.out.println("Status: " + status);
                    System.out.println("-----------------------------");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewAdoptionRecords(Connection connection) {
        try {
            String query = "SELECT * FROM pets WHERE adoption_status = 'Available'";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("\n--- Available Pets for Adoption ---");
            if (!rs.isBeforeFirst()) {
                System.out.println("No pets available for adoption.");
            } else {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Name: " + rs.getString("name"));
                    System.out.println("Weight: " + rs.getFloat("weight"));
                    System.out.println("Age: " + rs.getInt("age"));
                    System.out.println("Category: " + rs.getString("category"));
                    System.out.println("Adoption Status: " + rs.getString("adoption_status"));
                    System.out.println("-----------------------------");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Utility methods to handle input safely
    private static String readString(Scanner scanner) {
        String input = scanner.nextLine();
        while (input.trim().isEmpty()) {
            input = scanner.nextLine();
        }
        return input.trim();
    }

    private static int readInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a valid number: ");
            scanner.nextLine();  // Consume the invalid input
        }
        return scanner.nextInt();
    }

    private static float readFloat(Scanner scanner) {
        while (!scanner.hasNextFloat()) {
            System.out.print("Invalid input. Please enter a valid number: ");
            scanner.nextLine();  // Consume the invalid input
        }
        return scanner.nextFloat();
    }
}
