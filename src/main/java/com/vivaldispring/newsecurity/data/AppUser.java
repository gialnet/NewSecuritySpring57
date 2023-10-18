package com.vivaldispring.newsecurity.data;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AppUser {

    private String IdUser; // email address
    @Builder.Default
    private String UserUUID = UUID.randomUUID().toString(); // it is assigned when is create a new user
    private String Password;
    @Builder.Default
    private Date SignDate = new Date();
    @Builder.Default
    private String Status = "active";
}
