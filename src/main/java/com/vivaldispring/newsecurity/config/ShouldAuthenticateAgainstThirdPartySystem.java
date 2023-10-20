package com.vivaldispring.newsecurity.config;

import com.vivaldispring.newsecurity.services.ServicesUsers;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;



@Slf4j
@Component
public class ShouldAuthenticateAgainstThirdPartySystem {

    private final ServicesUsers servicesUsers;
    private final HttpSession httpSession;

    public ShouldAuthenticateAgainstThirdPartySystem(ServicesUsers servicesUsers, HttpSession httpSession) {
        this.servicesUsers = servicesUsers;
        this.httpSession = httpSession;
    }

    public boolean AuthenticateAgainstThirdPartySystem(String name, String password){

        boolean user_auth = servicesUsers.UserAuthByUserAndPassword(name, password);
        if (user_auth){
            log.info("From AuthenticateAgainstThirdPartySystem: uuid-> '{}' email'{}'", servicesUsers.getAppUser().getUserUUID(), servicesUsers.getAppUser().getIdUser());
            httpSession.setAttribute("uuid",servicesUsers.getAppUser().getUserUUID());
            httpSession.setAttribute("email",servicesUsers.getAppUser().getIdUser());
        }
        
        return user_auth;

    }

}
