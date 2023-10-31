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
    //TODO need to change user_id to profile_id and then change in database crew table
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
    @JoinColumn(name = "member4_id", referencedColumnName = "profileId")
    private Profile member4;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "crew_id", referencedColumnName = "crew_id")
    private Pool pool;

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

    public Profile getMember4() {
        return member4;
    }

    public void setMember4(Profile member4) {
        this.member4 = member4;
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
