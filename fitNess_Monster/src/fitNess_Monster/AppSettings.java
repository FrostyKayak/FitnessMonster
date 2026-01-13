package fitNess_Monster;

import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class AppSettings {

    private String datePattern;
    private String timeZoneId;
    private TemperatureUnit temperatureUnit;
    private WeightUnit weightUnit;
    private DistanceUnit distanceUnit;
    private EnergyUnit energyUnit;
    private DayOfWeek firstDayOfWeek;
    private boolean use24HourClock;

    public AppSettings() {
        this.datePattern = "yyyy-MM-dd";
        this.timeZoneId = ZoneId.systemDefault().getId();
        this.temperatureUnit = TemperatureUnit.F;
        this.weightUnit = WeightUnit.LB;
        this.distanceUnit = DistanceUnit.MI;
        this.energyUnit = EnergyUnit.KCAL;
        this.firstDayOfWeek = DayOfWeek.MONDAY;
        this.use24HourClock = false;
    }

    public String getDatePattern() {
        return datePattern;
    }

    public void setDatePattern(String datePattern) {
        validateDatePattern(datePattern);
        this.datePattern = datePattern;
    }

    public ZoneId getZoneId() {
        return ZoneId.of(timeZoneId);
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(String timeZoneId) {
        ZoneId.of(timeZoneId);
        this.timeZoneId = timeZoneId;
    }

    public TemperatureUnit getTemperatureUnit() {
        return temperatureUnit;
    }

    public void setTemperatureUnit(TemperatureUnit temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }

    public WeightUnit getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(WeightUnit weightUnit) {
        this.weightUnit = weightUnit;
    }

    public DistanceUnit getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(DistanceUnit distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

    public EnergyUnit getEnergyUnit() {
        return energyUnit;
    }

    public void setEnergyUnit(EnergyUnit energyUnit) {
        this.energyUnit = energyUnit;
    }

    public DayOfWeek getFirstDayOfWeek() {
        return firstDayOfWeek;
    }

    public void setFirstDayOfWeek(DayOfWeek firstDayOfWeek) {
        this.firstDayOfWeek = firstDayOfWeek;
    }

    public boolean isUse24HourClock() {
        return use24HourClock;
    }

    public void setUse24HourClock(boolean use24HourClock) {
        this.use24HourClock = use24HourClock;
    }

    public DateTimeFormatter getDateFormatter() {
        return DateTimeFormatter.ofPattern(datePattern);
    }

    public String formatExampleDate() {
        return getDateFormatter().format(java.time.LocalDate.of(2026, 1, 13));
    }

    private void validateDatePattern(String pattern) {
        try {
            DateTimeFormatter.ofPattern(pattern);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid date pattern.");
        }
    }
}

