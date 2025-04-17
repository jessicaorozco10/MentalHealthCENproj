package com.example.CENProj.service;

import com.example.CENProj.model.MoodLog;
import com.example.CENProj.model.enums.MoodType;
import com.example.CENProj.model.User;
import com.example.CENProj.repository.MoodLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MoodTrackService {

    private final MoodLogRepository moodLogRepository;

    /** Fetch all mood entries for display or aggregation */
    public List<MoodLog> getAllMoodLogs() {
        return moodLogRepository.findAll();
    }

    public MoodLog logMood(MoodType mood, User user) {
        return createMoodLog(mood, user);
    }

    /** Create & persist a new mood log */
    public MoodLog createMoodLog(MoodType mood, User user) {
        MoodLog log = MoodLog.builder()
                .mood(mood)
                .createdByUser(user)
                .createdDate(LocalDateTime.now())
                .build();
        return moodLogRepository.save(log);
    }

    /** Count how many times each mood was logged (for pie/bar charts) */
    public Map<String, Long> countsByMood() {
        return getAllMoodLogs().stream()
                .collect(Collectors.groupingBy(
                        e -> e.getMood().name(),
                        Collectors.counting()
                ));
    }

    /** Daily totals of entries (for time‑series charts) */
    public List<DailyCount> dailyCounts() {
        return getAllMoodLogs().stream()
                .collect(Collectors.groupingBy(
                        e -> e.getCreatedDate().toLocalDate(),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .map(e -> new DailyCount(e.getKey(), e.getValue()))
                .sorted((a, b) -> a.date().compareTo(b.date()))
                .toList();
    }

    public record DailyCount(LocalDate date, Long count) {}
}

