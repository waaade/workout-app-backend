package com.cognixia.jump.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;

public class WorkoutExercise implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Integer ExerciseId;

    @ManyToOne
    @JoinColumn(name = "work")
    private UserWorkout workoutId;

    @Column
    @Min(0)
    private Integer reps;

    @Column
    private Integer weight;


    public WorkoutExercise() {
    }


    public Integer getExerciseId() {
        return this.ExerciseId;
    }

    public void setExerciseId(Integer ExerciseId) {
        this.ExerciseId = ExerciseId;
    }

    public UserWorkout getWorkoutId() {
        return this.workoutId;
    }

    public void setWorkoutId(UserWorkout workoutId) {
        this.workoutId = workoutId;
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
            " ExerciseId='" + getExerciseId() + "'" +
            ", workoutId='" + getWorkoutId() + "'" +
            ", reps='" + getReps() + "'" +
            ", weight='" + getWeight() + "'" +
            "}";
    }
    
}
