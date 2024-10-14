package com.lautaro.NbaApp.Controller.Dto;


import java.util.List;

public class PlayerResponse {
    private List<PlayerDto> data;
    private Meta meta;

    public PlayerResponse() {}

    public PlayerResponse (List<PlayerDto> data) {
        this.data = data;
    }

    public void setData(List<PlayerDto> data) {
        this.data = data;
    }

    public List<PlayerDto> getData() {
        return data;
    }

    public Meta getMeta() {
        return meta;
    }

    //Clase anidada
    public static class Meta {
        private Integer next_cursor;
        private Integer per_page;

        public Integer getNextCursor() {
            return next_cursor;
        }

        public Integer getPerPage() {
            return per_page;
        }
    }
}
