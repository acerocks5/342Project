package model;

public class Booking {
    private String id;
    private Schedule schedule = new Schedule(null, null, null, null, null);
    private Lesson lesson = new Lesson(null, null, null);
    private Location location = new Location(null, null);
    private String availability;
    private String instructor;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private String dayOfWeek;
    private String lessonType;
    private String duration;
    private String lessonPrivacy;
    private String city;
    private String locationType;
    private String client;
    private String user;

    public Booking(String id, String startDate, String endDate, String startTime, String endTime, String dayOfWeek, String lessonType, String duration, String lessonPrivacy, String city, String locationType, String availability, String instructor, String client, String user) {
        this.id = id;
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
        this.client = client;
        this.user = user;
    }
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
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

    public String getStartDate() {
        return schedule.getStartDate();
    }

    public void setStartDate(String startDate) {
        schedule.setStartDate(startDate);
    }

    public String getEndDate() {
        return schedule.getEndDate();
    }

    public void setEndDate(String endDate) {
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

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
