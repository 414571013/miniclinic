package tw.edu.fju.miniclinic.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import tw.edu.fju.miniclinic.model.Doctor;
import tw.edu.fju.miniclinic.model.DoctorRepository;
import tw.edu.fju.miniclinic.model.PasswordForm;

@Controller
public class PasswordController {

    @Autowired
    private DoctorRepository doctorRepo;

    @GetMapping("/password")
    public String showPasswordForm(HttpSession session, Model model) {
        String doctorName = (String) session.getAttribute("loggedInDoctorName");
        model.addAttribute("loggedInDoctorName", doctorName);
        
        if (!model.containsAttribute("passwordForm")) {
            model.addAttribute("passwordForm", new PasswordForm());
        }
        return "password";
    }

    @PostMapping("/password")
    public String updatePassword(@ModelAttribute("passwordForm") PasswordForm form,
                                 HttpSession session,
                                 Model model) {
        String doctorId = (String) session.getAttribute("loggedInDoctorId");
        String doctorName = (String) session.getAttribute("loggedInDoctorName");
        Doctor doctor = doctorRepo.findById(doctorId).orElse(null);

        if (doctor == null) {
            return "redirect:/login";
        }

        boolean hasError = false;

        if (form.getOldPassword() == null || !BCrypt.checkpw(form.getOldPassword(), doctor.getPasswordHash())) {
            model.addAttribute("errorOldPassword", "舊密碼錯誤");
            hasError = true;
        }
        if (form.getNewPassword() == null || form.getNewPassword().length() < 8) {
            model.addAttribute("errorNewPassword", "密碼至少需要 8 個字元");
            hasError = true;
        }
        if (form.getNewPassword() != null && !form.getNewPassword().equals(form.getConfirmPassword())) {
            model.addAttribute("errorConfirmPassword", "兩次密碼不相符");
            hasError = true;
        }

        if (hasError) {
            model.addAttribute("loggedInDoctorName", doctorName);
            return "password";
        }

        // 更新密碼後導回 Dashboard
        doctor.setPasswordHash(BCrypt.hashpw(form.getNewPassword(), BCrypt.gensalt()));
        doctorRepo.save(doctor);

        return "redirect:/dashboard";
    }
}