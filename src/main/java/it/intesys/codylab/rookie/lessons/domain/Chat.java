package it.intesys.codylab.rookie.lessons.domain;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class Chat {
    private Long id;
    private Instant dateCreated;
    private Instant dateModified;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chat)) return false;
        Chat account = (Chat) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
