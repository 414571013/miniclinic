package tw.edu.fju.miniclinic.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.edu.fju.miniclinic.model.Appointment;
import tw.edu.fju.miniclinic.model.AppointmentRepository;
import tw.edu.fju.miniclinic.model.DoctorRepository;
import tw.edu.fju.miniclinic.model.PatientRepository;

@RestController
public class StatsController {

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private AppointmentRepository appointmentRepo;

    @GetMapping("/api/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        long totalDoctors = doctorRepo.count();
        long totalPatients = patientRepo.count();
        long totalAppointments = appointmentRepo.count();

        // 取出所有掛號，透過 Stream 過濾不同狀態的數量
        List<Appointment> allAppts = appointmentRepo.findAll();
        long bookedCount = allAppts.stream().filter(a -> "BOOKED".equals(a.getStatus())).count();
        long completedCount = allAppts.stream().filter(a -> "COMPLETED".equals(a.getStatus())).count();
        long cancelledCount = allAppts.stream().filter(a -> "CANCELLED".equals(a.getStatus())).count();

        // 組裝巢狀的 byStatus JSON 物件
        Map<String, Object> byStatus = new LinkedHashMap<>();
        byStatus.put("BOOKED", bookedCount);
        byStatus.put("COMPLETED", completedCount);
        byStatus.put("CANCELLED", cancelledCount);

        // 組裝外層的 JSON 物件
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalDoctors", totalDoctors);
        result.put("totalPatients", totalPatients);
        result.put("totalAppointments", totalAppointments);
        result.put("byStatus", byStatus);

        return ResponseEntity.ok(result);
    }
}
