package it.intesys.rookie.domain;

import java.time.Instant;

public class Account {
    private Long id;
    private String alias;
    private String name;
    private String surname;
    private String email;
    private Instant dateCreated;
    private Instant dataModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", alias='" + alias + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", dateCreated=" + dateCreated +
                ", dataModified=" + dataModified +
                '}';
    }
}
