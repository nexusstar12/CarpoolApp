package com.fantasticfour.poolapp.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "crew")
public class Crew {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crew_id", nullable = false)
    private int crewId;

    @Column(name = "description")
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member1_id", referencedColumnName = "profileId")
    private Profile member1;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member2_id", referencedColumnName = "profileId")
    private Profile member2;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member3_id", referencedColumnName = "profileId")
    private Profile member3;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id", referencedColumnName = "profileId")
    private Profile creator;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "crew_id", referencedColumnName = "crew_id")
    private Pool pool;


    //added origin_pool_id missing column
    @Column(name = "origin_pool_id")
    private int originPoolId;

    public int getOriginPoolId() {
        return originPoolId;
    }
    public void setOriginPoolId(int originPoolId) {
        this.originPoolId = originPoolId;
    }

    // Constructors, getters, setters, etc.
    public int getCrewId() {
        return crewId;
    }

    public void setCrewId(int crewId) {
        this.crewId = crewId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public Pool getPool() {
        return pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    @Override
    public String toString() {
        return "Crew{" +
                "crewId=" + crewId +
                ", description='" + description + '\'' +
                ", member1=" + member1 +
                ", member2=" + member2 +
                ", member3=" + member3 +
                ", pool=" + pool +
                '}';
    }
}
