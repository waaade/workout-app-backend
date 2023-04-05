package com.cognixia.jump.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.UserWorkout;

@Repository
public interface UserWorkoutRepository extends JpaRepository<UserWorkout, Integer> {
    
}
