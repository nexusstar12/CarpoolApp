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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member1_id", referencedColumnName = "user_id")
    private User member1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member2_id", referencedColumnName = "user_id")
    private User member2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member3_id", referencedColumnName = "user_id")
    private User member3;

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

    public User getMember1() {
        return member1;
    }

    public void setMember1(User member1) {
        this.member1 = member1;
    }

    public User getMember2() {
        return member2;
    }

    public void setMember2(User member2) {
        this.member2 = member2;
    }

    public User getMember3() {
        return member3;
    }

    public void setMember3(User member3) {
        this.member3 = member3;
    }
}
