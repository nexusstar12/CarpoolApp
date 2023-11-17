package com.fantasticfour.poolapp.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
public class Pool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int poolId;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "crew_id")
    private Crew crew;

    @ManyToOne
    @JoinColumn(name = "member_1_id")
    private Profile member1;

    @ManyToOne
    @JoinColumn(name = "member_2_id")
    private Profile member2;

    @ManyToOne
    @JoinColumn(name = "member_3_id")
    private Profile member3;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Profile creator;

    private String description;
    private LocalDateTime startTime;
    private String startLocation;
    private String endLocation;

    //Test
    private String startStreet;
    private String startCity;
    private String startState;
    private String startZip;

    private double startLatitude;
    private double startLongitude;

    private String endStreet;
    private String endCity;

    private String endState;
    private String endZip;

    private double endLatitude;
    private double endLongitude;

    private boolean privacy;

    private boolean crewCreated;
    private String startAddress;
    private String endAddress;

    public Profile getMember1() {
        return member1;
    }

    public void setMember1(Profile member1) {
        this.member1 = member1;
    }

    public Profile getMember2() {
        return member2;
    }

    public void setMember2(Profile member2) {
        this.member2 = member2;
    }

    public Profile getMember3() {
        return member3;
    }

    public void setMember3(Profile member3) {
        this.member3 = member3;
    }

    public Profile getCreator() {
        return creator;
    }

    public void setCreator(Profile creator) {
        this.creator = creator;
    }

    //Getters and Setters


    public boolean isCrewCreated() {
        return crewCreated;
    }

    public void setCrewCreated(boolean crewCreated) {
        this.crewCreated = crewCreated;
    }

    public boolean getViewable(){
        return privacy;
    }
    public void setViewable(boolean status){
        privacy = status;
    }
    public int getPoolId() {
        return poolId;
    }

    public void setPoolId(int poolId) {
        this.poolId = poolId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public String getStartStreet() {
        return startStreet;
    }

    public void setStartStreet(String startStreet) {
        this.startStreet = startStreet;
    }

    public String getStartCity() {
        return startCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public String getStartState() {
        return startState;
    }

    public void setStartState(String startState) {
        this.startState = startState;
    }

    public String getStartZip() {
        return startZip;
    }

    public void setStartZip(String startZip) {
        this.startZip = startZip;
    }

    public double getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(double startLatitude) {
        this.startLatitude = startLatitude;
    }

    public double getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(double startLongitude) {
        this.startLongitude = startLongitude;
    }

    public String getEndStreet() {
        return endStreet;
    }

    public void setEndStreet(String endStreet) {
        this.endStreet = endStreet;
    }

    public String getEndCity() {
        return endCity;
    }

    public void setEndCity(String endCity) {
        this.endCity = endCity;
    }

    public String getEndState() {
        return endState;
    }

    public void setEndState(String endState) {
        this.endState = endState;
    }

    public String getEndZip() {
        return endZip;
    }

    public void setEndZip(String endZip) {
        this.endZip = endZip;
    }

    public double getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(double endLatitude) {
        this.endLatitude = endLatitude;
    }

    public double getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(double endLongitude) {
        this.endLongitude = endLongitude;
    }

    public Crew getCrew() {
        return crew;
    }

    public void setCrew(Crew crew) {
        this.crew = crew;
    }

    // 1 is public 0 is private
    public boolean isPrivacy() {
        return privacy;
    }

    public void setPrivacy(boolean privacy) {
        this.privacy = privacy;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    @Override
    public String toString() {
        return "Pool{" +
                "poolId=" + poolId +
                ", crew=" + crew +
                ", member1=" + member1 +
                ", member2=" + member2 +
                ", member3=" + member3 +
                ", creator=" + creator +
                ", description='" + description + '\'' +
                ", startTime=" + startTime +
                ", startLocation='" + startLocation + '\'' +
                ", endLocation='" + endLocation + '\'' +
                ", startStreet='" + startStreet + '\'' +
                ", startCity='" + startCity + '\'' +
                ", startState='" + startState + '\'' +
                ", startZip='" + startZip + '\'' +
                ", startLatitude=" + startLatitude +
                ", startLongitude=" + startLongitude +
                ", endStreet='" + endStreet + '\'' +
                ", endCity='" + endCity + '\'' +
                ", endState='" + endState + '\'' +
                ", endZip='" + endZip + '\'' +
                ", endLatitude=" + endLatitude +
                ", endLongitude=" + endLongitude +
                ", privacy=" + privacy +
                '}';
    }
}
