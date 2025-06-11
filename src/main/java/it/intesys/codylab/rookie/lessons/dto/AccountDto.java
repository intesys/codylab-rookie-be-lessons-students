package it.intesys.codylab.rookie.lessons.dto;

import java.time.Instant;

public class AccountDto {
    private long id;
    private String alias;
    private String name;
    private String surname;
    private String email;
    private Instant dateCreated;
    private Instant dataModified;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Instant getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getDataModified() {
        return dataModified;
    }
    public void setDataModified(Instant dataModified) {
        this.dataModified = dataModified;
    }
}
