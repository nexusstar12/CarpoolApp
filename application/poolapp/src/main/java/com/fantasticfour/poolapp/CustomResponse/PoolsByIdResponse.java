package com.fantasticfour.poolapp.CustomResponse;

import com.fantasticfour.poolapp.domain.Pool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PoolsByIdResponse {
    private List<PoolResponse> myPools;
    private List<PoolResponse> availablePools;
    private List<PoolResponse> pastPools;

    public PoolsByIdResponse(List<PoolResponse> myPools, List<PoolResponse> availablePools, List<PoolResponse> pastPools) {
        this.myPools = myPools;
        this.availablePools = availablePools;
        this.pastPools = pastPools;
    }

    public PoolsByIdResponse() {
        this.myPools = new ArrayList<>();
        this.availablePools = new ArrayList<>();
        this.pastPools = new ArrayList<>();
    }

    public List<PoolResponse> getMyPools() {
        return myPools;
    }

    public void setMyPools(List<PoolResponse> myPools) {
        this.myPools = myPools;
    }

    public List<PoolResponse> getAvailablePools() {
        return availablePools;
    }

    public void setAvailablePools(List<PoolResponse> availablePools) {
        this.availablePools = availablePools;
    }

    public List<PoolResponse> getPastPools() {
        return pastPools;
    }

    public void setPastPools(List<PoolResponse> pastPools) {
        this.pastPools = pastPools;
    }

    public void addAvailablePoolsIndex(PoolResponse poolResponse) {
        this.availablePools.add(poolResponse) ;
    }

    public void addPastPoolsIndex(PoolResponse poolResponse) {
        this.pastPools.add(poolResponse) ;
    }

    public void addMyPoolsIndex(PoolResponse poolResponse) {
        this.myPools.add(poolResponse) ;
    }

    @Override
    public String toString() {
        return "PoolsByIdResponse{" +
                "myPools=" + myPools +
                ", availablePools=" + availablePools +
                ", pastPools=" + pastPools +
                '}';
    }
}
