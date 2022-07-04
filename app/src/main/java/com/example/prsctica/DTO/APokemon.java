package com.example.prsctica.DTO;

public class APokemon {
    public int id;
    public String name;
    public String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public APokemon(int id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }
}
