package com.min.mms.user.service;

import com.min.mms.user.model.UserCreateDTO;

import java.util.List;
import java.util.Map;

public interface UserService {

    void memberJoin(UserCreateDTO userCreateDTO);

    void memberDelete(String username);

    List<Map<String, Object>> memberSelect(Map<String, Object> params);

    Map<String, Object> memberDetailSelect(String username);

    void memberUpdate(String username, Map<String, Object> params);

}
