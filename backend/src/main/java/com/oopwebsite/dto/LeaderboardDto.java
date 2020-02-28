package com.oopwebsite.dto;

import com.oopwebsite.entity.User;

public class LeaderboardDto  {
    private String name;
    private long mark;
    private String group;
    private long labsCount;

    public LeaderboardDto(String name, int mark, String group, long labsCount) {
        this.name = name;
        this.mark = mark;
        this.group = group;
        this.labsCount = labsCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMark() {
        return mark;
    }

    public void setMark(long mark) {
        this.mark = mark;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public long getLabsCount() {
        return labsCount;
    }

    public void setLabsCount(int labsCount) {
        this.labsCount = labsCount;
    }
}
