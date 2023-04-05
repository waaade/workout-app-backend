package com.cognixia.jump.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class UserWorkout implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OneToMany(mappedBy = "userWorkout", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Integer Workoutid;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Column
    private Date workoutDate;


    public UserWorkout() {
    }


    public Integer getWorkoutid() {
        return this.Workoutid;
    }

    public void setWorkoutid(Integer Workoutid) {
        this.Workoutid = Workoutid;
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
