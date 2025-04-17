package com.example.CENProj.repository;

import com.example.CENProj.model.MoodLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoodLogRepository extends JpaRepository<MoodLog, Integer> {

}

