package com.example.webapplicatie.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;


// Model voor car entity
@Entity
@Table(name = "cars")
public class Car {

    // Definieert tabel kolommen
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "published")
    private boolean published;

    // Defineert relatie tussen car en user
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Car() {
    }

    // Car constructor
    public Car(String title, String description, User user, boolean published) {
        this.title = title;
        this.description = description;
        this.published = published;
        this.user = user;
    }

    // Getters en setters
    public void setId(long id)
    {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean isPublished) {
        this.published = isPublished;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Defineert response
    @Override
    public String toString() {
        return "Car [id=" + id + ", title=" + title + ", desc=" + description + ", published=" + published + ", user=" + user + "]";
    }
}
