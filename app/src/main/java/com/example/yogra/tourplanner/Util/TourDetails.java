package com.example.yogra.tourplanner.Util;

public class TourDetails {

    private String place;
    private String description;
    private String sightseeing;

    public TourDetails() {

    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSightseeing() {
        return sightseeing;
    }

    public void setSightseeing(String sightseeing) {
        this.sightseeing = sightseeing;
    }

    public TourDetails(String place, String description, String sightseeing) {

        this.place = place;
        this.description = description;
        this.sightseeing = sightseeing;
    }
}
