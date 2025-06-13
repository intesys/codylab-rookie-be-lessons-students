package it.intesys.codylab.rookie.lessons.domain;

import java.time.Instant;
import java.util.Objects;

public class Account {
    private Long id;
    private String alias;
    private String name;
    private String surname;
    private String email;
    private Instant dateCreated;
    private Instant dateModified;

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

    public Instant getDateModified() {
        return dateModified;
    }

    public void setDateModified(Instant dateModified) {
        this.dateModified = dateModified;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static void main(String [] args){
        Account account = new Account();
        account.setId(1L);
        account.setAlias("jdoe");

        Account account2 = new Account();
        account.setId(2L);
        account.setAlias("jdoe");

        System.out.println("Account 1 equals Account 1 : " + account.equals(account2));



    }
}
