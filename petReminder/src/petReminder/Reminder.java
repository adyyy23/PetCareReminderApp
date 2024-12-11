package petReminder;

import java.sql.Time;
import java.util.Date;

public class Reminder {
    private int id;
    private int petId;
    private String taskDescription;
    private Time recommendedTime;
    private Time feedingTime;
    private int gramsForFood;
    private Date vetAppointment;
    private boolean completed;

    // Constructor
    public Reminder(int petId, String taskDescription, Time recommendedTime, Time feedingTime, int gramsForFood, Date vetAppointment) {
        this.petId = petId;
        this.taskDescription = taskDescription;
        this.recommendedTime = recommendedTime;
        this.feedingTime = feedingTime;
        this.gramsForFood = gramsForFood;
        this.vetAppointment = vetAppointment;
        this.completed = false;
    }

    // Getters and Setters
    public int getId() { return id; }
    public int getPetId() { return petId; }
    public String getTaskDescription() { return taskDescription; }
    public Time getRecommendedTime() { return recommendedTime; }
    public Time getFeedingTime() { return feedingTime; }
    public int getGramsForFood() { return gramsForFood; }
    public Date getVetAppointment() { return vetAppointment; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}
