package com.lautaro.NbaApp.Controller.Dto;

public class PlayerDto {
    private String name;
    private String lastName;
    private int jersey;
    private String position;
    private int height;
    private int weight;
    private String country;
    private Long teamId; // Id del equipo al que pertenece el jugador

    // Constructor vacío
    public PlayerDto() {}

    // Constructor completo
    public PlayerDto(String name, String lastName, int jersey, String position, int height, int weight, String country, Long teamId) {
        this.name = name;
        this.lastName = lastName;
        this.jersey = jersey;
        this.position = position;
        this.height = height;
        this.weight = weight;
        this.country = country;
        this.teamId = teamId;
    }

    // Getters y setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getJersey() {
        return jersey;
    }

    public void setJersey(int jersey) {
        this.jersey = jersey;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
}
