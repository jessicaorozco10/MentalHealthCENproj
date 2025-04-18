package com.example.CENProj.service;

import com.example.CENProj.model.Availability;
import com.example.CENProj.model.Therapist;
import com.example.CENProj.model.User;
import com.example.CENProj.model.ViewModel.TherapistViewModel;
import com.example.CENProj.model.enums.UserType;
import com.example.CENProj.repository.TherapistRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
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

    // creates a therapist view model to help handle therapist availability & check boxes
    public TherapistViewModel createTherapistViewModel(Therapist therapist) {
        TherapistViewModel therapistViewModel = new TherapistViewModel();
        therapistViewModel.setTherapist(therapist);
        therapist.getAvailability().forEach(availability -> {
            switch (availability.getDayOfWeek()){
                case MONDAY:
                    therapistViewModel.setMonday(true);
                    break;
                case TUESDAY:
                    therapistViewModel.setTuesday(true);
                    break;
                case WEDNESDAY:
                    therapistViewModel.setWednesday(true);
                    break;
                case THURSDAY:
                    therapistViewModel.setThursday(true);
                    break;
                case FRIDAY:
                    therapistViewModel.setFriday(true);
                    break;
                case SATURDAY:
                    therapistViewModel.setSaturday(true);
                    break;
                case SUNDAY:
                    therapistViewModel.setSunday(true);
                    break;
                default:
                    break;
            }
        });
        return therapistViewModel;
    }

    // updates therapist and saves it to database
    public Therapist updateTherapist(User user, String specialization, String licenseInfo, int experience, List<Availability> availabilities) {
        LinkedList<Availability> availabilityMutable = new LinkedList<>(availabilities);
        if(user.getUserType() != UserType.THERAPIST) return null;

        Therapist therapist = therapistRepository.findByUserId(user.getId());
        if(therapist == null) {
            therapist = new Therapist();
        }
        therapist.setSpecialization(specialization);
        therapist.setLicenseInfo(licenseInfo);
        therapist.setExperience(experience);
        therapist.setAvailability(availabilityMutable);
        if(therapist.getUser() == null) therapist.setUser(user);
        return therapistRepository.save(therapist);

    }
}
