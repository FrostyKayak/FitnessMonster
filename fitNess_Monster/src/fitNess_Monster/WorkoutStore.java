package fitNess_Monster;

//WorkoutStore.java
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class WorkoutStore {
 private final Path saveFile;

 public WorkoutStore(Path saveFile) {
     this.saveFile = saveFile;
 }

 public List<Workout> load() {
     List<Workout> workouts = new ArrayList<>();

     if (!Files.exists(saveFile)) {
         return workouts;
     }

     try {
         List<String> lines = Files.readAllLines(saveFile, StandardCharsets.UTF_8);
         for (String line : lines) {
             String trimmed = line.trim();
             if (trimmed.isEmpty()) continue;
             workouts.add(Workout.fromCsvLine(trimmed));
         }
     } catch (IOException e) {
         System.out.println("Could not load saved workouts: " + e.getMessage());
     } catch (RuntimeException e) {
         System.out.println("Saved file was not in the expected format: " + e.getMessage());
     }

     return workouts;
 }

 public void save(List<Workout> workouts) {
     List<String> lines = new ArrayList<>();
     for (Workout w : workouts) {
         lines.add(w.toCsvLine());
     }

     try {
         Files.write(saveFile, lines, StandardCharsets.UTF_8);
     } catch (IOException e) {
         System.out.println("Could not save workouts: " + e.getMessage());
     }
 }
}

