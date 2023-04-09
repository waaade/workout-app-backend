package com.cognixia.jump.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;

@Entity
public class WorkoutExercise implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "workout_id", referencedColumnName = "id")
    private UserWorkout workout_id;

    @ManyToOne
    @JoinColumn(name = "exercise_id", referencedColumnName = "id")
    private Exercise exercise_id;

    @Column
    @Min(0)
    private Integer reps;

    @Column
    private Integer weight;


    public WorkoutExercise() {
    }



    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserWorkout getWorkoutId() {
        return this.workout_id;
    }

    public void setWorkoutId(UserWorkout workoutId) {
        this.workout_id = workoutId;
    }

    public Exercise getExerciseId() {
        return this.exercise_id;
    }

    public void setExerciseId(Exercise exerciseId) {
        this.exercise_id = exerciseId;
    }

    public Integer getReps() {
        return this.reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public Integer getWeight() {
        return this.weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", workoutId='" + getWorkoutId() + "'" +
            ", exerciseId='" + getExerciseId() + "'" +
            ", reps='" + getReps() + "'" +
            ", weight='" + getWeight() + "'" +
            "}";
    }
   
    
}
