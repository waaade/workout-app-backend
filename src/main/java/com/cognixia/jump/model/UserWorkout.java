package com.cognixia.jump.model;

import java.io.Serializable;
import java.sql.Date;

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
    private User userId;

    @Column
    private Date workoutDate;


    public UserWorkout() {
    }


    public Integer getWorkoutid() {
        return this.id;
    }

    public void setWorkoutid(Integer Workoutid) {
        this.id = Workoutid;
    }

    public User getUserId() {
        return this.userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Date getWorkoutDate() {
        return this.workoutDate;
    }

    public void setWorkoutDate(Date workoutDate) {
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
