package fitNess_Monster;
// Workout.java
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Workout {
    private final LocalDate date;
    private final WorkoutType type;
    private final String exercise;
    private final int minutes;
    private final int calories;

    public Workout(LocalDate date, WorkoutType type, String exercise, int minutes, int calories) {
        this.date = date;
        this.type = type;
        this.exercise = exercise;
        this.minutes = minutes;
        this.calories = calories;
    }

    public LocalDate getDate() {
        return date;
    }

    public WorkoutType getType() {
        return type;
    }

    public String getExercise() {
        return exercise;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getCalories() {
        return calories;
    }

    @Override
    public String toString() {
        return date + " | " + type + " | " + exercise + " | " + minutes + " min | " + calories + " cal";
    }

    public String toCsvLine() {
        return escape(date.toString()) + "," +
               escape(type.name()) + "," +
               escape(exercise) + "," +
               minutes + "," +
               calories;
    }

    public static Workout fromCsvLine(String line) {
        String[] parts = splitCsv(line);
        if (parts.length != 5) {
            throw new IllegalArgumentException("Bad CSV line: " + line);
        }

        LocalDate date = LocalDate.parse(unescape(parts[0]));
        WorkoutType type = WorkoutType.valueOf(unescape(parts[1]).trim().toUpperCase());
        String exercise = unescape(parts[2]);
        int minutes = Integer.parseInt(parts[3].trim());
        int calories = Integer.parseInt(parts[4].trim());

        return new Workout(date, type, exercise, minutes, calories);
    }

    private static String escape(String s) {
        if (s == null) return "";
        boolean needsQuotes = s.contains(",") || s.contains("\"") || s.contains("\n") || s.contains("\r");
        String out = s.replace("\"", "\"\"");
        return needsQuotes ? "\"" + out + "\"" : out;
    }

    private static String unescape(String s) {
        s = s.trim();
        if (s.startsWith("\"") && s.endsWith("\"") && s.length() >= 2) {
            String inner = s.substring(1, s.length() - 1);
            return inner.replace("\"\"", "\"");
        }
        return s;
    }

    private static String[] splitCsv(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '\"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '\"') {
                    current.append('\"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                fields.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }

        fields.add(current.toString());
        return fields.toArray(new String[0]);
    }
}
