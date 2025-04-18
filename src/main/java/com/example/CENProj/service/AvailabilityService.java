package com.example.CENProj.service;

import com.example.CENProj.model.Availability;
import com.example.CENProj.model.enums.DayOfWeek;
import com.example.CENProj.repository.AvailabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailabilityService {
    private final AvailabilityRepository repository;
    public List<Availability> findAll() {return repository.findAll();}

    // Takes in availability and checks which days were put in availability
    public static boolean isAvailable(Availability availability, String monday, String tuesday,
                               String wednesday, String thursday, String friday, String saturday, String sunday) {
        return switch (availability.getDayOfWeek()) {
            case DayOfWeek.MONDAY -> StringUtils.hasText(monday);
            case DayOfWeek.TUESDAY -> StringUtils.hasText(tuesday);
            case DayOfWeek.WEDNESDAY -> StringUtils.hasText(wednesday);
            case DayOfWeek.THURSDAY -> StringUtils.hasText(thursday);
            case DayOfWeek.FRIDAY -> StringUtils.hasText(friday);
            case DayOfWeek.SATURDAY -> StringUtils.hasText(saturday);
            case DayOfWeek.SUNDAY -> StringUtils.hasText(sunday);
            default -> false;
        };
    }
}
