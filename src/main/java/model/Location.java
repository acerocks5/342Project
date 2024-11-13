package model;

public class Location {

    private String city;
    private String locationType;

    public Location(String city, String locationType) {
        this.city = city;
        this.locationType = locationType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }
}
