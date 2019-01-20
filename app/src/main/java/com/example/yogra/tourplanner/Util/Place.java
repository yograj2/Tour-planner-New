package com.example.yogra.tourplanner.Util;

public class Place {

    private String tourPlace;
    private String tourDescription;
    private String sightSeeing;
    private int nightCharge;
    private String imageData;
    private String id;

    public int getNightCharge() {
        return nightCharge;
    }

    public void setNightCharge(int nightCharge) {
        this.nightCharge = nightCharge;
    }

    public Place(){}

    public Place(String tourPlace, String tourDescription, String sightSeeing,String imageData,int nightCharge,String id) {
        this.tourPlace = tourPlace;
        this.tourDescription = tourDescription;
        this.sightSeeing = sightSeeing;
        this.nightCharge = nightCharge;
        this.imageData = imageData;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTourPlace() {
        return tourPlace;
    }

    public void setTourPlace(String tourPlace) {
        this.tourPlace = tourPlace;
    }

    public String getTourDescription() {
        return tourDescription;
    }

    public void setTourDescription(String tourDescription) {
        this.tourDescription = tourDescription;
    }

    public String getSightSeeing() {
        return sightSeeing;
    }

    public void setSightSeeing(String sightSeeing) {
        this.sightSeeing = sightSeeing;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }
}
