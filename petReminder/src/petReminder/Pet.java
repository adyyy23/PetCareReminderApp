package petReminder;

import java.util.Date;

public class Pet {
    private int id;
    private String name;
    private String type;
    private float weight;
    private String vaccinationStatus;
    private String lastFeedingTime;
    private int foodAmountInGrams;
    private Date vetAppointment;  // Added for vet appointment

    // Constructor
    public Pet(String name, String type, float weight, String vaccinationStatus, String lastFeedingTime, int foodAmountInGrams, Date vetAppointment) {
        this.name = name;
        this.type = type;
        this.weight = weight;
        this.vaccinationStatus = vaccinationStatus;
        this.lastFeedingTime = lastFeedingTime;
        this.foodAmountInGrams = foodAmountInGrams;
        this.vetAppointment = vetAppointment;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public float getWeight() { return weight; }
    public void setWeight(float weight) { this.weight = weight; }
    public String getVaccinationStatus() { return vaccinationStatus; }
    public void setVaccinationStatus(String vaccinationStatus) { this.vaccinationStatus = vaccinationStatus; }
    public String getLastFeedingTime() { return lastFeedingTime; }
    public void setLastFeedingTime(String lastFeedingTime) { this.lastFeedingTime = lastFeedingTime; }
    public int getFoodAmountInGrams() { return foodAmountInGrams; }
    public void setFoodAmountInGrams(int foodAmountInGrams) { this.foodAmountInGrams = foodAmountInGrams; }
    public Date getVetAppointment() { return vetAppointment; }  // Getter for vet appointment
    public void setVetAppointment(Date vetAppointment) { this.vetAppointment = vetAppointment; }  // Setter for vet appointment
}
