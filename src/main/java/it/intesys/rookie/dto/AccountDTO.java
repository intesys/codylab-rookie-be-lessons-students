package it.intesys.rookie.dto;

import java.time.Instant;

public class AccountDTO {
    private Long id;
    private Instant dataCreated;
    private Instant dataModified;
    private String alias;
    private String name;
    private String surname;
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataCreated() {
        return dataCreated;
    }

    public void setDataCreated(Instant dataCreated) {
        this.dataCreated = dataCreated;
    }

    public Instant getDataModified() {
        return dataModified;
    }

    public void setDataModified(Instant dataModified) {
        this.dataModified = dataModified;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
