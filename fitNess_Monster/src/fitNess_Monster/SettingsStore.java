package fitNess_Monster;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.util.Properties;

public class SettingsStore {

    private final Path file;

    public SettingsStore(Path file) {
        this.file = file;
    }

    public AppSettings load() {
        AppSettings settings = new AppSettings();

        if (!Files.exists(file)) {
            return settings;
        }

        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(file)) {
            props.load(in);

            String datePattern = props.getProperty("datePattern");
            if (datePattern != null && !datePattern.isBlank()) {
                settings.setDatePattern(datePattern.trim());
            }

            String timeZoneId = props.getProperty("timeZoneId");
            if (timeZoneId != null && !timeZoneId.isBlank()) {
                settings.setTimeZoneId(timeZoneId.trim());
            }

            String tempUnit = props.getProperty("temperatureUnit");
            if (tempUnit != null && !tempUnit.isBlank()) {
                settings.setTemperatureUnit(TemperatureUnit.valueOf(tempUnit.trim().toUpperCase()));
            }

            String weightUnit = props.getProperty("weightUnit");
            if (weightUnit != null && !weightUnit.isBlank()) {
                settings.setWeightUnit(WeightUnit.valueOf(weightUnit.trim().toUpperCase()));
            }

            String distanceUnit = props.getProperty("distanceUnit");
            if (distanceUnit != null && !distanceUnit.isBlank()) {
                settings.setDistanceUnit(DistanceUnit.valueOf(distanceUnit.trim().toUpperCase()));
            }

            String energyUnit = props.getProperty("energyUnit");
            if (energyUnit != null && !energyUnit.isBlank()) {
                settings.setEnergyUnit(EnergyUnit.valueOf(energyUnit.trim().toUpperCase()));
            }

            String firstDay = props.getProperty("firstDayOfWeek");
            if (firstDay != null && !firstDay.isBlank()) {
                settings.setFirstDayOfWeek(DayOfWeek.valueOf(firstDay.trim().toUpperCase()));
            }

            String use24 = props.getProperty("use24HourClock");
            if (use24 != null && !use24.isBlank()) {
                settings.setUse24HourClock(Boolean.parseBoolean(use24.trim()));
            }

        } catch (IOException e) {
            System.out.println("Could not load settings: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Settings file had invalid values. Using defaults. " + e.getMessage());
        }

        return settings;
    }

    public void save(AppSettings settings) {
        Properties props = new Properties();

        props.setProperty("datePattern", settings.getDatePattern());
        props.setProperty("timeZoneId", settings.getTimeZoneId());
        props.setProperty("temperatureUnit", settings.getTemperatureUnit().name());
        props.setProperty("weightUnit", settings.getWeightUnit().name());
        props.setProperty("distanceUnit", settings.getDistanceUnit().name());
        props.setProperty("energyUnit", settings.getEnergyUnit().name());
        props.setProperty("firstDayOfWeek", settings.getFirstDayOfWeek().name());
        props.setProperty("use24HourClock", String.valueOf(settings.isUse24HourClock()));

        try (OutputStream out = Files.newOutputStream(file)) {
            props.store(out, "fitNessMonster settings");
        } catch (IOException e) {
            System.out.println("Could not save settings: " + e.getMessage());
        }
    }
}

