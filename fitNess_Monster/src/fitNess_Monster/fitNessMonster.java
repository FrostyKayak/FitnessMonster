package fitNess_Monster;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class fitNessMonster {

    private static final Path WORKOUTS_FILE = Path.of("workouts.csv");
    private static final Path SETTINGS_FILE = Path.of("settings.properties");
    private static final Path PROFILE_FILE = Path.of("profile.properties");

    private final WorkoutStore workoutStore;
    private final SettingsStore settingsStore;
    private final ProfileStore profileStore;

    private final AppSettings settings;
    private final Profile profile;
    private final List<Workout> workouts;

    public fitNessMonster() {
        this.settingsStore = new SettingsStore(SETTINGS_FILE);
        this.settings = settingsStore.load();

        this.profileStore = new ProfileStore(PROFILE_FILE);
        this.profile = profileStore.load();

        this.workoutStore = new WorkoutStore(WORKOUTS_FILE);
        this.workouts = new ArrayList<>(workoutStore.load());
    }

    public static void main(String[] args) {
        new fitNessMonster().run();
    }

    private void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;

            while (running) {
                printMenu();
                int choice = InputHelper.readInt(scanner, "Choose an option: ");

                switch (choice) {
                    case 1 -> addWorkout(scanner);
                    case 2 -> listWorkouts();
                    case 3 -> showTotals();
                    case 4 -> settingsMenu(scanner);
                    case 5 -> profileMenu(scanner);
                    case 6 -> {
                        workoutStore.save(workouts);
                        settingsStore.save(settings);
                        profileStore.save(profile);
                        running = false;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            }
        }

        System.out.println("Goodbye.");
    }

    private void printMenu() {
        System.out.println();
        System.out.println("fitNessMonster");
        System.out.println("1) Add workout");
        System.out.println("2) List workouts");
        System.out.println("3) Show totals");
        System.out.println("4) Settings");
        System.out.println("5) Profile");
        System.out.println("6) Exit");
    }

    private void addWorkout(Scanner scanner) {
        DateTimeFormatter df = settings.getDateFormatter();
        String example = settings.formatExampleDate();

        LocalDate date = InputHelper.readDate(scanner, "Enter date (" + example + "): ", df);
        WorkoutType type = InputHelper.readWorkoutType(scanner);

        System.out.print("Enter exercise name: ");
        String exercise = scanner.nextLine().trim();

        int minutes = InputHelper.readInt(scanner, "Enter minutes: ");
        int calories = InputHelper.readInt(scanner, "Enter calories (" + settings.getEnergyUnit().name() + "): ");

        workouts.add(new Workout(date, type, exercise, minutes, calories));
        workoutStore.save(workouts);
        System.out.println("Workout saved.");
    }

    private void listWorkouts() {
        if (workouts.isEmpty()) {
            System.out.println("No workouts yet.");
            return;
        }

        System.out.println();
        System.out.println("Workout History");
        for (int i = 0; i < workouts.size(); i++) {
            System.out.println((i + 1) + ") " + workouts.get(i));
        }
    }

    private void showTotals() {
        int totalMinutes = 0;
        int totalCalories = 0;

        int cardioMinutes = 0;
        int cardioCalories = 0;

        int weightsMinutes = 0;
        int weightsCalories = 0;

        for (Workout w : workouts) {
            totalMinutes += w.getMinutes();
            totalCalories += w.getCalories();

            if (w.getType() == WorkoutType.CARDIO) {
                cardioMinutes += w.getMinutes();
                cardioCalories += w.getCalories();
            } else if (w.getType() == WorkoutType.WEIGHTS) {
                weightsMinutes += w.getMinutes();
                weightsCalories += w.getCalories();
            }
        }

        System.out.println();
        System.out.println("Totals");
        System.out.println("Total minutes: " + totalMinutes);
        System.out.println("Total energy: " + totalCalories + " " + settings.getEnergyUnit().name());
        System.out.println();
        System.out.println("Cardio minutes: " + cardioMinutes);
        System.out.println("Cardio energy: " + cardioCalories + " " + settings.getEnergyUnit().name());
        System.out.println("Weights minutes: " + weightsMinutes);
        System.out.println("Weights energy: " + weightsCalories + " " + settings.getEnergyUnit().name());
    }

    private void settingsMenu(Scanner scanner) {
        boolean inMenu = true;

        while (inMenu) {
            printSettings();
            System.out.println("1) Change date format");
            System.out.println("2) Change time zone");
            System.out.println("3) Change temperature unit");
            System.out.println("4) Change weight unit");
            System.out.println("5) Change distance unit");
            System.out.println("6) Change energy unit");
            System.out.println("7) Change first day of week");
            System.out.println("8) Toggle 24 hour clock");
            System.out.println("9) Back");

            int choice = InputHelper.readInt(scanner, "Choose an option: ");

            switch (choice) {
                case 1 -> changeDateFormat(scanner);
                case 2 -> changeTimeZone(scanner);
                case 3 -> changeTemperatureUnit(scanner);
                case 4 -> changeWeightUnit(scanner);
                case 5 -> changeDistanceUnit(scanner);
                case 6 -> changeEnergyUnit(scanner);
                case 7 -> changeFirstDayOfWeek(scanner);
                case 8 -> toggleClock(scanner);
                case 9 -> inMenu = false;
                default -> System.out.println("Invalid choice. Try again.");
            }

            settingsStore.save(settings);
        }
    }

    private void printSettings() {
        System.out.println();
        System.out.println("Current settings");
        System.out.println("Date pattern: " + settings.getDatePattern() + " Example: " + settings.formatExampleDate());
        System.out.println("Time zone: " + settings.getTimeZoneId());
        System.out.println("Temperature unit: " + settings.getTemperatureUnit());
        System.out.println("Weight unit: " + settings.getWeightUnit());
        System.out.println("Distance unit: " + settings.getDistanceUnit());
        System.out.println("Energy unit: " + settings.getEnergyUnit());
        System.out.println("First day of week: " + settings.getFirstDayOfWeek());
        System.out.println("24 hour clock: " + (settings.isUse24HourClock() ? "On" : "Off"));
        System.out.println();
    }

    private void changeDateFormat(Scanner scanner) {
        System.out.println("Enter a Java date pattern like yyyy-MM-dd or MM/dd/yyyy");
        System.out.print("New date pattern: ");
        String pattern = scanner.nextLine().trim();

        try {
            settings.setDatePattern(pattern);
            System.out.println("Saved. Example: " + settings.formatExampleDate());
        } catch (IllegalArgumentException e) {
            System.out.println("That pattern was invalid. No change made.");
        }
    }

    private void changeTimeZone(Scanner scanner) {
        System.out.println("Enter a time zone like America/New_York or Europe/London");
        System.out.print("New time zone: ");
        String tz = scanner.nextLine().trim();

        try {
            settings.setTimeZoneId(tz);
            System.out.println("Saved.");
        } catch (Exception e) {
            System.out.println("That time zone was invalid. No change made.");
        }
    }

    private void changeTemperatureUnit(Scanner scanner) {
        System.out.println("1) F");
        System.out.println("2) C");
        int choice = InputHelper.readInt(scanner, "Choose 1 or 2: ");
        if (choice == 1) settings.setTemperatureUnit(TemperatureUnit.F);
        else if (choice == 2) settings.setTemperatureUnit(TemperatureUnit.C);
        else System.out.println("Invalid choice. No change made.");
    }

    private void changeWeightUnit(Scanner scanner) {
        System.out.println("1) LB");
        System.out.println("2) KG");
        int choice = InputHelper.readInt(scanner, "Choose 1 or 2: ");
        if (choice == 1) settings.setWeightUnit(WeightUnit.LB);
        else if (choice == 2) settings.setWeightUnit(WeightUnit.KG);
        else System.out.println("Invalid choice. No change made.");
    }

    private void changeDistanceUnit(Scanner scanner) {
        System.out.println("1) MI");
        System.out.println("2) KM");
        int choice = InputHelper.readInt(scanner, "Choose 1 or 2: ");
        if (choice == 1) settings.setDistanceUnit(DistanceUnit.MI);
        else if (choice == 2) settings.setDistanceUnit(DistanceUnit.KM);
        else System.out.println("Invalid choice. No change made.");
    }

    private void changeEnergyUnit(Scanner scanner) {
        System.out.println("1) KCAL");
        System.out.println("2) KJ");
        int choice = InputHelper.readInt(scanner, "Choose 1 or 2: ");
        if (choice == 1) settings.setEnergyUnit(EnergyUnit.KCAL);
        else if (choice == 2) settings.setEnergyUnit(EnergyUnit.KJ);
        else System.out.println("Invalid choice. No change made.");
    }

    private void changeFirstDayOfWeek(Scanner scanner) {
        System.out.println("1) MONDAY");
        System.out.println("2) SUNDAY");
        int choice = InputHelper.readInt(scanner, "Choose 1 or 2: ");
        if (choice == 1) settings.setFirstDayOfWeek(DayOfWeek.MONDAY);
        else if (choice == 2) settings.setFirstDayOfWeek(DayOfWeek.SUNDAY);
        else System.out.println("Invalid choice. No change made.");
    }

    private void toggleClock(Scanner scanner) {
        boolean newValue = InputHelper.readYesNo(scanner, "Use 24 hour clock? (y or n): ");
        settings.setUse24HourClock(newValue);
    }

    private void profileMenu(Scanner scanner) {
        boolean inMenu = true;

        while (inMenu) {
            printProfile();
            System.out.println("1) Set name");
            System.out.println("2) Set age");
            System.out.println("3) Set height");
            System.out.println("4) Set current weight");
            System.out.println("5) Set goal weight");
            System.out.println("6) Set birthday");
            System.out.println("7) Back");

            int choice = InputHelper.readInt(scanner, "Choose an option: ");

            try {
                switch (choice) {
                    case 1 -> {
                        System.out.print("Name: ");
                        profile.setName(scanner.nextLine());
                    }
                    case 2 -> {
                        int age = InputHelper.readInt(scanner, "Age: ");
                        profile.setAge(age);
                    }
                    case 3 -> {
                        double height = InputHelper.readDouble(scanner, "Height: ");
                        HeightUnit unit = InputHelper.readHeightUnit(scanner);
                        profile.setHeight(height, unit);
                    }
                    case 4 -> {
                        double w = InputHelper.readDouble(scanner, "Current weight: ");
                        profile.setCurrentWeight(w);
                    }
                    case 5 -> {
                        double w = InputHelper.readDouble(scanner, "Goal weight: ");
                        profile.setGoalWeight(w);
                    }
                    case 6 -> {
                        DateTimeFormatter df = settings.getDateFormatter();
                        String example = settings.formatExampleDate();
                        LocalDate bday = InputHelper.readDate(scanner, "Birthday (" + example + "): ", df);
                        profile.setBirthday(bday);
                    }
                    case 7 -> inMenu = false;
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Could not save that value: " + e.getMessage());
            }

            profileStore.save(profile);
        }
    }

    private void printProfile() {
        System.out.println();
        System.out.println("Profile");
        System.out.println("Name: " + (profile.getName().isBlank() ? "(not set)" : profile.getName()));
        System.out.println("Age: " + profile.getAge());
        System.out.println("Height: " + profile.getHeight() + " " + profile.getHeightUnit());
        System.out.println("Current weight: " + profile.getCurrentWeight());
        System.out.println("Goal weight: " + profile.getGoalWeight());
        System.out.println("Birthday: " + (profile.getBirthday() == null ? "(not set)" : profile.getBirthday()));
        System.out.println();
    }
}
