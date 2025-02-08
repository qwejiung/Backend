package LittlePet.UMC.Hospital.service;

import LittlePet.UMC.Hospital.dto.HospitalResponseDTO;
import LittlePet.UMC.Hospital.repository.HospitalPrefRepository;
import LittlePet.UMC.Hospital.repository.HospitalRepository;
import LittlePet.UMC.User.repository.UserRepository;
import LittlePet.UMC.domain.hospitalEntity.Hospital;
import LittlePet.UMC.domain.hospitalEntity.mapping.HospitalPref;
import LittlePet.UMC.domain.userEntity.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final HospitalPrefRepository hospitalPrefRepository;
    private final UserRepository userRepository;

    public HospitalService(HospitalRepository hospitalRepository, HospitalPrefRepository hospitalPrefRepository, UserRepository userRepository){
        this.hospitalRepository = hospitalRepository;
        this.hospitalPrefRepository = hospitalPrefRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void scrapHospital(Long userId, Long hospitalId){

        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("사용자가 없습니다."));
        Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(()-> new RuntimeException("없는 병원입니다."));

        if (hospitalPrefRepository.existsByUserAndHospital(user, hospital)) {
            throw new RuntimeException("이미 스크랩한 병원입니다.");
        }

        HospitalPref hospitalPref = HospitalPref.builder()
                .user(user)
                .hospital(hospital)
                .build();

        hospital.addHospitalPref(hospitalPref);

        hospitalPrefRepository.save(hospitalPref);
    }

    @Transactional
    public void cancelScrapHospital(Long userId, Long hospitalId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("사용자가 없습니다."));
        Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(() -> new RuntimeException("없는 병원입니다."));

        HospitalPref hospitalPref = hospitalPrefRepository.findByUserAndHospital(user, hospital)
                .orElseThrow(() -> new RuntimeException("스크랩한 병원이 아닙니다."));

        hospitalPrefRepository.delete(hospitalPref);
    }

    public List<HospitalResponseDTO> getScrapedHospitals(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("사용자가 없습니다."));

        return hospitalPrefRepository.findByUser(user).stream()
                .map(hospitalPref -> {
                    Hospital hospital = hospitalPref.getHospital();
                    return new HospitalResponseDTO(hospital.getId(), hospital.getName(), hospital.getAddress());
                })
                .collect(Collectors.toList());
    }

}
