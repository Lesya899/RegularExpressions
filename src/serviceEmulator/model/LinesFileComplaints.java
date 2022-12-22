package serviceEmulator.model;


import java.time.LocalDateTime;

public class LinesFileComplaints {
    private int id;
    private LocalDateTime dataTime;
    private String fullName;
    private String phoneNumber;
    private String textComplaint;

    public LinesFileComplaints(int id, LocalDateTime dataTime, String fullName, String phoneNumber, String textComplaint) {
        this.id = id;
        this.dataTime = dataTime;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.textComplaint = textComplaint;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDataTime() {
        return dataTime;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getTextComplaint() {
        return textComplaint;
    }

}

