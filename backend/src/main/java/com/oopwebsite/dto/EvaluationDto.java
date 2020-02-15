package com.oopwebsite.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oopwebsite.entity.User;

public class EvaluationDto {
    private String labId;
    private String comment;
    @JsonIgnore
    private User owner;
    private int mark;

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
}
