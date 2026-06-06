package tw.edu.fju.miniclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatsPageController {

    @GetMapping("/stats")
    public String showStatsPage() {
        return "stats";  // 對應 src/main/resources/templates/stats.html
    }
}