package it.intesys.codylab.rookie.lessons.dto;

import java.util.List;

public class ChatFilterDto {
    private List<Long> memberIds;

    public List<Long> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(List<Long> memberIds) {
        this.memberIds = memberIds;
    }
}
