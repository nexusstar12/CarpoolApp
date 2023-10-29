package com.fantasticfour.poolapp.CustomResponse;

import java.util.List;

public class CrewListResponse {
    private List<CrewResponse> crews;

    public CrewListResponse() {
    }

    public CrewListResponse(List<CrewResponse> crews) {
        this.crews = crews;
    }

    public List<CrewResponse> getCrews() {
        return crews;
    }

    public void setCrews(List<CrewResponse> crews) {
        this.crews = crews;
    }
}
