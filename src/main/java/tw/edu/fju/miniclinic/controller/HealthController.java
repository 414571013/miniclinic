package tw.edu.fju.miniclinic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/api/health")
    public Map<String, String> health() {
        return Map.of(
            "status", "ok",
            "service", "miniclinic",
            "student_id", " 414571013 ",
            "student_name", " 李錦星 ",
            "project", "MiniClinic",
            "version", "0.1.0",
            "chapter", "Ch09-A"
        );
    }
    @GetMapping("/api/about")
    public Map<String, String> about() {
        return Map.of(
            "status", "ok",
            "service", "miniclinic",
            "student_id", " 414571013 ",
            "student_name", " 李錦星 ",
            "project", "MiniClinic",
            "version", "0.1.0",
            "chapter", "Ch09-A"
        );
    }
}
