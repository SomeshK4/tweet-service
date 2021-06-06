package org.twitter.api.user;


import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.twitter.security.handlers.UserContextHandler;
import org.twitter.service.dto.UserDTO;
import org.twitter.service.user.UserService;

@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserContextHandler loggedInUsersHandler;


    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity signup(@RequestBody @Valid UserDTO userDTO) {
        userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
