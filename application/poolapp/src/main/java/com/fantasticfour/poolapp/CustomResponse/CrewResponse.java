package com.fantasticfour.poolapp.CustomResponse;

import com.fantasticfour.poolapp.domain.User;

import java.util.ArrayList;
import java.util.List;

public class CrewResponse {

    private int crewId;
    private String description;
    List<User> members;

    public CrewResponse() {
        this.members = new ArrayList<>();
    }

    public int getCrewId() {
        return crewId;
    }

    public void setCrewId(int crewId) {
        this.crewId = crewId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }



    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }
    public void setOneMember(User member){
        this.members.add(member);
    }
}
