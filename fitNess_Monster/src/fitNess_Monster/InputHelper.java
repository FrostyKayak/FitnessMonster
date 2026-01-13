package fitNess_Monster;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputHelper {

    public static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    public static boolean readYesNo(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes")) return true;
            if (input.equals("n") || input.equals("no")) return false;
            System.out.println("Please type y or n.");
        }
    }

    public static LocalDate readDate(Scanner scanner, String prompt, DateTimeFormatter formatter) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Date did not match the expected format.");
            }
        }
    }

    public static WorkoutType readWorkoutType(Scanner scanner) {
        while (true) {
            System.out.println("Workout type");
            System.out.println("1) Cardio");
            System.out.println("2) Weights");
            int choice = readInt(scanner, "Choose 1 or 2: ");

            if (choice == 1) return WorkoutType.CARDIO;
            if (choice == 2) return WorkoutType.WEIGHTS;

            System.out.println("Invalid choice. Try again.");
        }
    }
    public static double readDouble(java.util.Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public static HeightUnit readHeightUnit(java.util.Scanner scanner) {
        while (true) {
            System.out.println("Height unit");
            System.out.println("1) Inches");
            System.out.println("2) Centimeters");
            int choice = readInt(scanner, "Choose 1 or 2: ");
            if (choice == 1) return HeightUnit.IN;
            if (choice == 2) return HeightUnit.CM;
            System.out.println("Invalid choice. Try again.");
        }
    }
}
