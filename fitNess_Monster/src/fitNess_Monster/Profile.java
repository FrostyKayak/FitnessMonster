package fitNess_Monster;

//Profile.java
import java.time.LocalDate;

public class Profile {

 private String name;
 private int age;
 private double height;
 private HeightUnit heightUnit;
 private double currentWeight;
 private double goalWeight;
 private LocalDate birthday;

 public Profile() {
     this.name = "";
     this.age = 0;
     this.height = 0.0;
     this.heightUnit = HeightUnit.IN;
     this.currentWeight = 0.0;
     this.goalWeight = 0.0;
     this.birthday = null;
 }

 public String getName() {
     return name;
 }

 public void setName(String name) {
     this.name = name == null ? "" : name.trim();
 }

 public int getAge() {
     return age;
 }

 public void setAge(int age) {
     if (age < 0) throw new IllegalArgumentException("Age must be 0 or more.");
     this.age = age;
 }

 public double getHeight() {
     return height;
 }

 public HeightUnit getHeightUnit() {
     return heightUnit;
 }

 public void setHeight(double height, HeightUnit unit) {
     if (height < 0) throw new IllegalArgumentException("Height must be 0 or more.");
     this.height = height;
     this.heightUnit = unit;
 }

 public double getCurrentWeight() {
     return currentWeight;
 }

 public void setCurrentWeight(double currentWeight) {
     if (currentWeight < 0) throw new IllegalArgumentException("Weight must be 0 or more.");
     this.currentWeight = currentWeight;
 }

 public double getGoalWeight() {
     return goalWeight;
 }

 public void setGoalWeight(double goalWeight) {
     if (goalWeight < 0) throw new IllegalArgumentException("Goal weight must be 0 or more.");
     this.goalWeight = goalWeight;
 }

 public LocalDate getBirthday() {
     return birthday;
 }

 public void setBirthday(LocalDate birthday) {
     this.birthday = birthday;
 }
}

