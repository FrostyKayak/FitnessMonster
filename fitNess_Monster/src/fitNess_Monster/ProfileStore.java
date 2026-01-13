package fitNess_Monster;

//ProfileStore.java
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Properties;

public class ProfileStore {

 private final Path file;

 public ProfileStore(Path file) {
     this.file = file;
 }

 public Profile load() {
     Profile profile = new Profile();

     if (!Files.exists(file)) {
         return profile;
     }

     Properties props = new Properties();
     try (InputStream in = Files.newInputStream(file)) {
         props.load(in);

         profile.setName(props.getProperty("name", "").trim());

         String ageStr = props.getProperty("age");
         if (ageStr != null && !ageStr.isBlank()) {
             profile.setAge(Integer.parseInt(ageStr.trim()));
         }

         String heightStr = props.getProperty("height");
         String heightUnitStr = props.getProperty("heightUnit");
         if (heightStr != null && !heightStr.isBlank()) {
             double height = Double.parseDouble(heightStr.trim());
             HeightUnit unit = HeightUnit.IN;
             if (heightUnitStr != null && !heightUnitStr.isBlank()) {
                 unit = HeightUnit.valueOf(heightUnitStr.trim().toUpperCase());
             }
             profile.setHeight(height, unit);
         }

         String currentWeightStr = props.getProperty("currentWeight");
         if (currentWeightStr != null && !currentWeightStr.isBlank()) {
             profile.setCurrentWeight(Double.parseDouble(currentWeightStr.trim()));
         }

         String goalWeightStr = props.getProperty("goalWeight");
         if (goalWeightStr != null && !goalWeightStr.isBlank()) {
             profile.setGoalWeight(Double.parseDouble(goalWeightStr.trim()));
         }

         String birthdayStr = props.getProperty("birthday");
         if (birthdayStr != null && !birthdayStr.isBlank()) {
             profile.setBirthday(LocalDate.parse(birthdayStr.trim()));
         }

     } catch (IOException e) {
         System.out.println("Could not load profile: " + e.getMessage());
     } catch (RuntimeException e) {
         System.out.println("Profile file had invalid values. Using defaults. " + e.getMessage());
     }

     return profile;
 }

 public void save(Profile profile) {
     Properties props = new Properties();

     props.setProperty("name", profile.getName());
     props.setProperty("age", String.valueOf(profile.getAge()));
     props.setProperty("height", String.valueOf(profile.getHeight()));
     props.setProperty("heightUnit", profile.getHeightUnit().name());
     props.setProperty("currentWeight", String.valueOf(profile.getCurrentWeight()));
     props.setProperty("goalWeight", String.valueOf(profile.getGoalWeight()));
     if (profile.getBirthday() != null) {
         props.setProperty("birthday", profile.getBirthday().toString());
     }

     try (OutputStream out = Files.newOutputStream(file)) {
         props.store(out, "fitNessMonster profile");
     } catch (IOException e) {
         System.out.println("Could not save profile: " + e.getMessage());
     }
 }
}

