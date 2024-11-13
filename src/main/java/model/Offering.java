package model;

import java.util.Date;

public class Offering {

    private Schedule schedule = new Schedule(null, null, null, null, null);
    private Lesson lesson = new Lesson(null, null, null);
    private Location location = new Location(null, null);
    private String availability;
    private String instructor;
    private Date startDate;
    private Date endDate;
    private String startTime;
    private String endTime;
    private String dayOfWeek;
    private String lessonType;
    private String duration;
    private String lessonPrivacy;
    private String city;
    private String locationType;

    public Offering(Date startDate, Date endDate, String startTime, String endTime, String dayOfWeek, String lessonType, String duration, String lessonPrivacy, String city, String locationType, String availability, String instructor) {
        schedule.setStartDate(startDate);
        schedule.setEndDate(endDate);
        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);
        schedule.setDayOfWeek(dayOfWeek);
        lesson.setLessonType(lessonType);
        lesson.setDuration(duration);
        lesson.setLessonPrivacy(lessonPrivacy);
        location.setCity(city);
        location.setLocationType(locationType);

        this.availability = availability;
        this.instructor = instructor;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public Date getStartDate() {
        return schedule.getStartDate();
    }

    public void setStartDate(Date startDate) {
        schedule.setStartDate(startDate);
    }

    public Date getEndDate() {
        return schedule.getEndDate();
    }

    public void setEndDate(Date endDate) {
        schedule.setEndDate(endDate);
    }

    public String getStartTime() {
        return schedule.getStartTime();
    }

    public void setStartTime(String startTime) {
        schedule.setStartTime(startTime);
    }

    public String getEndTime() {
        return schedule.getEndTime();
    }

    public void setEndTime(String endTime) {
        schedule.setEndTime(endTime);
    }

    public String getDayOfWeek() {
        return schedule.getDayOfWeek();
    }

    public void setDayOfWeek(String dayOfWeek) {
        schedule.setDayOfWeek(dayOfWeek);
    }

    public String getLessonType() {
        return lesson.getLessonType();
    }

    public void setLessonType(String lessonType) {
        lesson.setLessonType(lessonType);
    }

    public String getDuration() {
        return lesson.getDuration();
    }

    public void setDuration(String duration) {
        lesson.setDuration(duration);
    }

    public String getLessonPrivacy() {
        return lesson.getLessonPrivacy();
    }

    public void setLessonPrivacy(String lessonPrivacy) {
        lesson.setLessonPrivacy(lessonPrivacy);
    }

    public String getCity() {
        return location.getCity();
    }

    public void setCity(String city) {
        location.setCity(city);
    }

    public String getLocationType() {
        return location.getLocationType();
    }

    public void setLocationType(String locationType) {
        location.setLocationType(locationType);
    }
}
