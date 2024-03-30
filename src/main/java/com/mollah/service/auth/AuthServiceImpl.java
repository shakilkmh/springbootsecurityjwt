package com.mollah.service.auth;

import java.util.regex.Pattern;

import com.mollah.record.HelloResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mollah.dto.SignupDTO;
import com.mollah.dto.UserDTO;
import com.mollah.model.User;
import com.mollah.repository.UserRepository;

import io.micrometer.common.util.StringUtils;

@Service
public class AuthServiceImpl implements AuthService {
	
	private static final Pattern EMAIL = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Autowired
    private UserRepository userRepository;

    @Override
    public Object createUser(SignupDTO signupDTO) {
    	
    	if(StringUtils.isEmpty(signupDTO.getName())) {
    		return "Name is required";
    	}
    	if(StringUtils.isEmpty(signupDTO.getEmail())) {
    		return "Email is required";
    	}
    	if(StringUtils.isEmpty(signupDTO.getPassword())) {
    		return "Password is required";
    	}
    	if(!isEmail(signupDTO.getEmail())) {
    		return "Email is not valid";
    	}
    	Boolean userExist = userRepository.existsByEmail(signupDTO.getEmail());
    	if(userExist) {
    		return "Try again with another email";
    	}
    	
        User user = new User();
        user.setName(signupDTO.getName());
        user.setEmail(signupDTO.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(signupDTO.getPassword()));
        User createdUser = userRepository.save(user);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(createdUser.getId());
        userDTO.setEmail(createdUser.getEmail());
        userDTO.setName(createdUser.getName());
        return userDTO;
    }

    @Override
    public User getUserByEmail(String email) {
        User usr = userRepository.findFirstByEmail(email);
        if(usr == null) {
            throw new RuntimeException("User not found");
        }
        return usr;
    }

    public static boolean isEmail(String s) {
        return EMAIL.matcher(s).matches();
    }
}
