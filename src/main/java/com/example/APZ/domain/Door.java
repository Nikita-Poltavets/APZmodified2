package com.example.APZ.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Door {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Please fill the textbox")
    @Length(max = 2048, message = "Name too long (more than 2kB)")
    private String doorname;
    @Length(max = 2048, message = "Description too long (more than 2kB)")
    private String password; // userName

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    private String filename;

    public Door(){

    }

    public Door(String doorname, String password, User user) {
        this.author = user;
        this.doorname = doorname;
        this.password = password;
    }

    public String getAuthorName(){
        return author != null ? author.getUsername() : "<none>";
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDoorname() {
        return doorname;
    }

    public void setDoorname(String doorname) { this.doorname = doorname;}

    public String getPassword() { return password; }

    public void setPassword(String description) {
        this.password = description;
    }

    public String getFilename() {
        return  filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
