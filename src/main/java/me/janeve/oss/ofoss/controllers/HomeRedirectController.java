package me.janeve.oss.ofoss.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class HomeRedirectController {

    @GetMapping("")
    public void home(HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Location", "/projects/");
        httpServletResponse.setStatus(307);

    }

}