package tw.edu.fju.miniclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tw.edu.fju.miniclinic.model.AppointmentRepository;
import tw.edu.fju.miniclinic.model.DoctorRepository;
import tw.edu.fju.miniclinic.model.PatientRepository;

@Controller
public class StatsPageController {

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private AppointmentRepository appointmentRepo;

    @GetMapping("/stats")
    public String showStatsPage(Model model) {
        // 將總數放入 Model，讓 HTML 可以用 Thymeleaf 變數讀取
        model.addAttribute("totalDoctors", doctorRepo.count());
        model.addAttribute("totalPatients", patientRepo.count());
        model.addAttribute("totalAppointments", appointmentRepo.count());
        
        // 如果你有在 AppointmentRepository 加上 countByDepartment，就把下面這行解除註解
        // model.addAttribute("deptStats", appointmentRepo.countByDepartment());

        return "stats";  // 對應 src/main/resources/templates/stats.html
    }
}