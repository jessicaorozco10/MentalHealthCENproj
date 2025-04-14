package com.example.CENProj.model.ViewModel;

import com.example.CENProj.model.Therapist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TherapistViewModel {
    private Therapist therapist;
    private String therapistAvailability;
}
