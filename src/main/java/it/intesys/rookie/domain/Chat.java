package it.intesys.rookie.domain;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class Chat {
    private Long id;
    private Instant dateCreated, dateModified;
    private List<Account> members;

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

    public List<Account> getMembers() {
        return members;
    }

    public void setMembers(List<Account> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", dateCreated=" + dateCreated +
                ", dateModified=" + dateModified +
                ", members=" + members +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return Objects.equals(id, chat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
