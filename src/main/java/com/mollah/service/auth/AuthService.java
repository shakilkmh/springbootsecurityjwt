package com.mollah.service.auth;

import com.mollah.dto.SignupDTO;
import com.mollah.model.User;
import com.mollah.record.HelloResponse;

public interface AuthService {
    Object createUser(SignupDTO signupDTO);

    User getUserByEmail(String email);

}
