package com.example.yogra.tourplanner.Util;

public class Place {

    private String tourPlace;
    private String tourDescription;
    private String sightSeeing;


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

    public Place(String tourPlace, String tourDescription, String sightSeeing) {
        this.tourPlace = tourPlace;
        this.tourDescription = tourDescription;
        this.sightSeeing = sightSeeing;
    }
    public Place(){

    }
}
