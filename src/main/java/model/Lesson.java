package model;

public class Lesson {
    private String lessonType;
    private String duration;
    private String lessonPrivacy;

    public Lesson(String lessonType, String duration, String lessonPrivacy) {
        this.lessonType = lessonType;
        this.duration = duration;
        this.lessonPrivacy = lessonPrivacy;
    }

    public String getLessonType() {
        return lessonType;
    }

    public void setLessonType(String lessonType) {
        this.lessonType = lessonType;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLessonPrivacy() {
        return lessonPrivacy;
    }

    public void setLessonPrivacy(String lessonPrivacy) {
        this.lessonPrivacy = lessonPrivacy;
    }
}
