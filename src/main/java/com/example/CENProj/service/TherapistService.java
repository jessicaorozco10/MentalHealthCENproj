package com.example.CENProj.service;

import com.example.CENProj.model.Therapist;
import com.example.CENProj.repository.TherapistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TherapistService {
    private final TherapistRepository therapistRepository;

    public List<Therapist> findAll() {
        return therapistRepository.findAll();
    }

    public List<Therapist> findByCriteria() {
        return therapistRepository.findAll();
    }
}
