package com.lautaro.NbaApp.Controller.Dto;

public class TeamDto {
    private String name;
    private String city;
    private String conference;
    private String primaryColor;
    private String secondaryColor;
    private String terciaryColor;
    private String logoUrl;
    private String stadiumName;
    private String headcoach;

    public TeamDto() {}

    public TeamDto(String name, String city, String conference, String primaryColor, String secondaryColor,
                   String terciaryColor, String logoUrl, String stadiumName, String headcoach) {
        this.name = name;
        this.city = city;
        this.conference = conference;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.terciaryColor = terciaryColor;
        this.logoUrl = logoUrl;
        this.stadiumName = stadiumName;
        this.headcoach = headcoach;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getConference() {
        return conference;
    }

    public void setConference(String conference) {
        this.conference = conference;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(String secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public String getTerciaryColor() {
        return terciaryColor;
    }

    public void setTerciaryColor(String terciaryColor) {
        this.terciaryColor = terciaryColor;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    public String getHeadcoach() {
        return headcoach;
    }

    public void setHeadcoach(String headcoach) {
        this.headcoach = headcoach;
    }
}
