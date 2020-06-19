package me.janeve.oss.o3ppm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdministrationController extends BaseController{

    @GetMapping("wipe-data")
    public String wipeData(){
        projectRepository.deleteAll();
        libraryRepository.deleteAll();
        libraryVersionRepository.deleteAll();
        return "redirect:/";
    }

}