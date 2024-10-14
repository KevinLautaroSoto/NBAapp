package com.lautaro.NbaApp.Controller.Dto;

public class TeamResponse {
    private TeamDto[] data; //Teams Array

    public TeamResponse() {}

    public TeamResponse(TeamDto[] data) {
        this.data = data;
    }

    public void setData(TeamDto[] data) {
        this.data = data;
    }

    public TeamDto[] getData() {
        return data;
    }
}
