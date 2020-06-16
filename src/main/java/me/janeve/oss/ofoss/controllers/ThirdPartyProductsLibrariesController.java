package me.janeve.oss.ofoss.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/3pp_libraries")
public class ThirdPartyProductsLibrariesController {

    @GetMapping("/new")
    public String newLibraryForm() {
        return "3pp_libraries/new";
    }

}