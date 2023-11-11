package com.fantasticfour.poolapp.CustomResponse;

import com.fantasticfour.poolapp.domain.Profile;
import com.fantasticfour.poolapp.domain.User;

import java.util.ArrayList;
import java.util.List;

public class CrewResponse {

    private int crewId;
    private String description;
    List<Profile> members;

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



    public List<Profile> getMembers() {
        return members;
    }

    public void setMembers(List<Profile> members) {
        this.members = members;
    }
    public void setOneMember(Profile member){
        this.members.add(member);
    }
}
