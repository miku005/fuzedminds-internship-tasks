package com.fuzedminds.controller;

import com.fuzedminds.payload.JwtToken;
import com.fuzedminds.payload.LoginDto;
import com.fuzedminds.payload.UserDto;
import com.fuzedminds.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDto dto, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UserDto byEmail = userService.findByEmail(dto.getEmail());
        if (byEmail != null) {
            return new ResponseEntity<>("Email Already Present!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UserDto byMobile = userService.findByMobile(dto.getMobile());
        if (byMobile != null) {
            return new ResponseEntity<>("Mobile Number Already Present!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UserDto savedUser = userService.addUser(dto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
    @PostMapping("/property/sign-up")
    public ResponseEntity<?> createPropertyOwnerAccount(@Valid @RequestBody UserDto dto, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UserDto byEmail = userService.findByEmail(dto.getEmail());
        if (byEmail != null) {
            return new ResponseEntity<>("Email Already Present!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UserDto byMobile = userService.findByMobile(dto.getMobile());
        if (byMobile != null) {
            return new ResponseEntity<>("Mobile Number Already Present!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UserDto savedUser = userService.createPropertyOwnerAccount(dto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
    @PostMapping("/blog/sign-up")
    public ResponseEntity<?> createBlogManagerAccount(@Valid @RequestBody UserDto dto, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UserDto byEmail = userService.findByEmail(dto.getEmail());
        if (byEmail != null) {
            return new ResponseEntity<>("Email Already Present!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UserDto byMobile = userService.findByMobile(dto.getMobile());
        if (byMobile != null) {
            return new ResponseEntity<>("Mobile Number Already Present!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UserDto savedUser = userService.createBlogManagerAccount(dto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto,BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String token = userService.verifyLogin(loginDto);
        JwtToken jwtToken = new JwtToken();
        jwtToken.setToken(token);
        jwtToken.setType("JWT");
        if (token != null) {
            return new ResponseEntity<>(jwtToken , HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid Email/Password", HttpStatus.UNAUTHORIZED);
    }

    @PatchMapping
    public ResponseEntity<UserDto> patchUser( @RequestBody UserDto dto, @RequestParam long id ) {
        UserDto userDto = userService.patchUser(dto, id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/UserEmail/{userEmail}")
    public ResponseEntity<Map<String, String>> getMobileByEmail(@PathVariable String userEmail) {

        String mobile = userService.getMobileNumberByEmail(userEmail);

        Map<String, String> response = new HashMap<>();
        if (mobile != null) {
            response.put("mobile",mobile);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("mobile", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

}
