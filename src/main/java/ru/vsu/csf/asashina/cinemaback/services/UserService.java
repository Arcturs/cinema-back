package ru.vsu.csf.asashina.cinemaback.services;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.csf.asashina.cinemaback.exceptions.ObjectNotExistsException;
import ru.vsu.csf.asashina.cinemaback.exceptions.PasswordDoesNotMatchException;
import ru.vsu.csf.asashina.cinemaback.exceptions.UserAlreadyExistsException;
import ru.vsu.csf.asashina.cinemaback.exceptions.WrongInputLoginException;
import ru.vsu.csf.asashina.cinemaback.mappers.UserMapper;
import ru.vsu.csf.asashina.cinemaback.models.dtos.UserDTO;
import ru.vsu.csf.asashina.cinemaback.models.entities.RoleEntity;
import ru.vsu.csf.asashina.cinemaback.models.entities.UserEntity;
import ru.vsu.csf.asashina.cinemaback.models.request.LoginForm;
import ru.vsu.csf.asashina.cinemaback.models.request.SignUpForm;
import ru.vsu.csf.asashina.cinemaback.repositories.RoleRepository;
import ru.vsu.csf.asashina.cinemaback.repositories.UserRepository;

import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final UserMapper userMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public UserDTO registerNewUser(SignUpForm signUpForm) {
        if (userRepository.findUserEntityByEmail(signUpForm.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with following email already exists");
        }

        if (!signUpForm.getPassword().equals(signUpForm.getRepeatPassword())) {
            throw new PasswordDoesNotMatchException("Passwords do not match");
        }

        String passwordHash = bCryptPasswordEncoder.encode(signUpForm.getPassword());
        Set<RoleEntity> roles = Set.of(roleRepository.findUserRole());
        UserEntity user = userRepository.save(userMapper.fromRequestToEntity(signUpForm, passwordHash, roles));
        return userMapper.fromEntityToDTO(user);
    }

    public UserDTO findUserById(Long id) {
        UserEntity user = findUserEntityById(id);
        return userMapper.fromEntityToDTO(user);
    }

    public UserDTO login(LoginForm loginForm) {
        if (userRepository.findUserEntityByEmail(loginForm.getEmail()).isEmpty()) {
            throw new WrongInputLoginException("Wrong login or password");
        }
        UserEntity user = userRepository.findUserEntityByEmail(loginForm.getEmail()).get();
        if (!bCryptPasswordEncoder.matches(loginForm.getPassword(), user.getPasswordHash())) {
            throw new WrongInputLoginException("Wrong login or password");
        }
        return userMapper.fromEntityToDTO(user);
    }

    public UserDTO findUserByEmail(String email) {
        UserEntity user = userRepository.findUserEntityByEmail(email).orElseThrow(
                () -> new ObjectNotExistsException("User with following email does not exist")
        );
        return userMapper.fromEntityToDTO(user);
    }

    public UserEntity findUserEntityById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ObjectNotExistsException("User with following id does not exist")
        );
    }
}
