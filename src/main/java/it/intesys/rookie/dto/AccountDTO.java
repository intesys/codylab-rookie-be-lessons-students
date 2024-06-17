package it.intesys.rookie.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

public class AccountDTO {
    private Long id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Instant dateCreated, dateModified;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String alias, name, surname, email;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private StatusDTO status;

    @Override
    public String toString() {
        return "AccountDTO{" +
                "id=" + id +
                ", dateCreated=" + dateCreated +
                ", dateModified=" + dateModified +
                ", alias='" + alias + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getDateModified() {
        return dateModified;
    }

    public void setDateModified(Instant dateModified) {
        this.dateModified = dateModified;
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

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
    }
}
