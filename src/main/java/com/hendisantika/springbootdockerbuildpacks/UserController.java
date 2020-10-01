package com.hendisantika.springbootdockerbuildpacks;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-docker-buildpacks
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 02/10/20
 * Time: 05.43
 */
@RestController
public class UserController {

    @GetMapping("/users")
    public List<User> fetchUsers() {
        return Arrays.asList(User.builder()
                        .firstName("Uzumaki")
                        .lastName("Naruto")
                        .email("uzumaki_naruto@konohagakure.co.jp")
                        .mobile("+6281321411511")
                        .build(),
                User.builder().
                        firstName("Uchiha")
                        .lastName("Sasuke")
                        .email("uchiha_sasuke@konohagakure.co.jp")
                        .mobile("+6281321411552")
                        .build(),
                User.builder()
                        .firstName("Kakashi")
                        .lastName("Hatake")
                        .email("hatake_kakashi@konohagakure.co.jp")
                        .mobile("+6281321444555")
                        .build());
    }
}
