package com.fuzedminds.service;

import com.fuzedminds.entity.User;
import com.fuzedminds.payload.LoginDto;
import com.fuzedminds.payload.UserDto;
import com.fuzedminds.repository.UserRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private JWTService jwtService;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, JWTService jwtService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
    }

    UserDto MapToDto(User user) {
        UserDto dto = modelMapper.map(user, UserDto.class);
        return dto;
    }

    User MapToEntity(UserDto dto) {
        User user = modelMapper.map(dto, User.class);
        return user;
    }

    public UserDto findByEmail(String email) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        if (byEmail.isPresent()) {
            return MapToDto(byEmail.get());
        }
        return null;
    }

    public UserDto findByMobile(String mobile) {
        Optional<User> byMobile = userRepository.findByMobile(mobile);
        if (byMobile.isPresent()) {
            return MapToDto(byMobile.get());
        }
        return null;
    }

    public UserDto addUser(UserDto dto) {
        User user = MapToEntity(dto);
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
        user.setRole("ROLE_USER");
        User userSaved = userRepository.save(user);
        return MapToDto(userSaved);
    }
    public UserDto createPropertyOwnerAccount(UserDto dto) {
        User user = MapToEntity(dto);
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
        user.setRole("ROLE_OWNER");
        User userSaved = userRepository.save(user);
        return MapToDto(userSaved);
    }
    public UserDto createBlogManagerAccount(UserDto dto) {
        User user = MapToEntity(dto);
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
        user.setRole("ROLE_BLOGMANAGER");
        User userSaved = userRepository.save(user);
        return MapToDto(userSaved);
    }

    public String verifyLogin(LoginDto loginDto) {
        Optional<User> byEmail = userRepository.findByEmail(loginDto.getEmail());
        if (byEmail.isPresent()) {
            User user = byEmail.get();
            if (BCrypt.checkpw(loginDto.getPassword(), user.getPassword())) {
                String token = jwtService.generateToken(loginDto.getEmail());
                return token;
            } else {
                return null;
            }
        }
        return null;
    }

    public UserDto patchUser(UserDto dto, long id) {
        Optional<User> opUser = userRepository.findById(id);
        User user = opUser.get();
        if (dto.getName() != null) {
            user.setName(dto.getName());
        }
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getMobile() != null) {
            user.setMobile(dto.getMobile());
        }
        if (dto.getAge() != null) {
            user.setAge(dto.getAge());
        }
        if (dto.getGender() != null) {
            user.setGender(dto.getGender());
        }
        if (dto.getPassword() != null) {
            user.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(10)));
        }
        User updateUser = userRepository.save(user);
        return MapToDto(updateUser);
    }

    public String getMobileNumberByEmail(String userEmail) {
        Optional<User> byEmail = userRepository.findByEmail(userEmail);
        if (byEmail.isPresent()) {
            return byEmail.get().getMobile();
        }
        return null;
    }

}
