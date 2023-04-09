package com.cognixia.jump.model;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserWorkout implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user_id;

    @Column
    private LocalDate workoutDate;


    public UserWorkout(Integer id, User userId, LocalDate workoutDate) {
        this.id = id;
        this.user_id = userId;
        this.workoutDate = workoutDate;
    }


    public UserWorkout() {
    }


    public Integer getWorkoutid() {
        return this.id;
    }

    public void setWorkoutid(Integer Workoutid) {
        this.id = Workoutid;
    }

    public User getUserId() {
        return this.user_id;
    }

    public void setUserId(User userId) {
        this.user_id = userId;
    }

    public LocalDate getWorkoutDate() {
        return this.workoutDate;
    }

    public void setWorkoutDate(LocalDate workoutDate) {
        this.workoutDate = workoutDate;
    }

    @Override
    public String toString() {
        return "{" +
            " Workoutid='" + getWorkoutid() + "'" +
            ", userId='" + getUserId() + "'" +
            ", workoutDate='" + getWorkoutDate() + "'" +
            "}";
    }

    
}
