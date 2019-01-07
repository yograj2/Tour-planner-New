package com.example.yogra.tourplanner.Util;

public class Place {

    private String tourPlace;
    private String tourDescription;
    private String sightSeeing;
    private String imageData;

    public Place(){}

    public Place(String tourPlace, String tourDescription, String sightSeeing,String imageData) {
        this.tourPlace = tourPlace;
        this.tourDescription = tourDescription;
        this.sightSeeing = sightSeeing;
        this.imageData = imageData;
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
