package com.yourssincerelyjapan.init;

import com.yourssincerelyjapan.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserCreationInit implements CommandLineRunner {

    private final UserService userService;

    public UserCreationInit(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.userService.administratorInit();
    }
}
