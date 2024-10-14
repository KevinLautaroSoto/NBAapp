package com.lautaro.NbaApp.Models;

import jakarta.persistence.*;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String city;
    private String conference;
    private String division;
    private String abbreviation;
    private String full_name;

    public Team() {} //Default constructor

    public Team(String name, String city, String conference, String division, String abbreviation, String full_name) {
        this.name = name;
        this.city = city;
        this.conference = conference;
        this.division = division;
        this.abbreviation = abbreviation;
        this.full_name = full_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", conference='" + conference + '\'' +
                ", division='" + division + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                ", full_name='" + full_name + '\'' +
                '}';
    }
}
