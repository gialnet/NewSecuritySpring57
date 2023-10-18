package com.vivaldispring.newsecurity.web_controller;

import com.vivaldispring.newsecurity.data.AppUser;
import com.vivaldispring.newsecurity.services.ServicesUsers;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Slf4j
@Controller
public class LoginFormController {

    private final ServicesUsers servicesUsers;
    private AppUser appUser;

    public LoginFormController(ServicesUsers servicesUsers) {
        this.servicesUsers = servicesUsers;
    }

    @GetMapping("/")
    public String root(HttpSession session) {

        log.info("index '{}'", session.getAttribute("id"));
        return "index";
    }

    @GetMapping(value = "/login")
    public String showFormLogin(Model model,
                                HttpSession session) {

        //log.info("error value '{}'", model.getAttribute("error"));
        if (model.getAttribute("error")!=null)
            model.addAttribute("message", " user or password wrong");
        else
            model.addAttribute("message", " write your email");


        //session.setAttribute("id",uuid.toString());
        /*var appUser = AppUser.builder().build();
        model.addAttribute("AppUser",appUser);*/

        //log.info("Login");
        return "login";
    }

    /*  *********************************************************************************************
    @PostMapping("/login") is managed by ShouldAuthenticateAgainstThirdPartySystem class Custom pakage
    */

//    @GetMapping("/access-denied")
//    public String accessDenied() {
//        log.info("error/access-denied");
//        return "error/access-denied";
//    }

    @GetMapping("/register")
    public String showForm(Model model) {

        log.info("Get register");
        appUser = new AppUser();
        log.info("AppUser for new user '{}'", appUser);
        model.addAttribute("AppUser", appUser);

        return "register";
    }

    /**
     * check if appUser.getIdUser() exist in RocksDB
     * exist true alert to UI
     * exist false:
     * create the new user
     *
     * * Exist true send an alert RegisterUserIfNotAlreadyExist()
     *      * 0 Ok
     *      * 1 email empty
     *      * 2 password empty
     *      * 3 fails RocksDB save
     *      * 5 user already exist
     *
     * @param appUser
     * @return
     */
    @PostMapping("/register")
    public String submitForm(@ModelAttribute("AppUser") AppUser appUser,
                             BindingResult result, ModelMap model) throws IOException {

        log.info("Post register");
        log.info("AppUser for register new user '{}'", appUser);

        // check if appUser.getIdUser() exist
        int resultRUNAE =servicesUsers.RegisterUserIfNotAlreadyExist(appUser);
        if (resultRUNAE==0)
            return "register_success";
        else{
            log.info("AppUser for new user '{}'", appUser);
            model.addAttribute("AppUser", appUser);
            model.addAttribute("message", " already exist");
            return "/register";
        }

    }
}
