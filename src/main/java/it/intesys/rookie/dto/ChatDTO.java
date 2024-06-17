package it.intesys.rookie.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;

public class ChatDTO {
    private Long id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Instant dateCreated, dateModified;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Long> memberIds;


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

    public List<Long> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(List<Long> memberIds) {
        this.memberIds = memberIds;
    }

    @Override
    public String toString() {
        return "ChatDTO{" +
                "id=" + id +
                ", dateCreated=" + dateCreated +
                ", dateModified=" + dateModified +
                ", memberIds=" + memberIds +
                '}';
    }
}
