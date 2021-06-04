package com.example.LinkShortener;

import javax.persistence.*;

@Entity
@Table(name = "links")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int ID;

    @Column(name = "link")
    private String originalName;

    @Column(name = "short_link")
    private String shortName;

    public String getOriginalName() {
        return originalName;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
