package com.min.mms.user.service;

import com.min.mms.user.model.UserDTO;

public interface UserService {

    void memberJoin(UserDTO userDTO);

    void memberDelete(String username);

}
