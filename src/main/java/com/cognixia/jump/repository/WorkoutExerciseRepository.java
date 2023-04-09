package com.cognixia.jump.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.WorkoutExercise;

@Repository
public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Integer> {

    // @Query("SELECT e.exerciseType, we.reps, we.weight, uw.workoutDate FROM WorkoutExercise we JOIN we.Exercise e JOIN we.UserWorkout uw WHERE uw.userId =?1")
    // public List<WorkoutExercise> allWorkoutExercisesByUser(Long id);
    // @Query(nativeQuery = true, value = "SELECT e.exercise_type, we.reps, we.weight, uw.workout_date, we.id FROM workout_exercise we INNER JOIN exercise e ON we.exercise_id = e.id INNER JOIN user_workout uw ON uw.id = we.workout_id WHERE uw.user_id = ?1")
    @Query(nativeQuery = true, value = "SELECT * FROM workout_exercise we INNER JOIN exercise e ON we.exercise_id = e.id INNER JOIN user_workout uw ON uw.id = we.workout_id WHERE uw.user_id = ?1")
    public List<WorkoutExercise> allWorkoutExercisesByUser(Integer id);
    
}
