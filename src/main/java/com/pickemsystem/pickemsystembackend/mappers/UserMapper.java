package com.pickemsystem.pickemsystembackend.mappers;

import com.pickemsystem.pickemsystembackend.dto.requests.UserCreateDTO;
import com.pickemsystem.pickemsystembackend.dto.responses.UserDTO;
import com.pickemsystem.pickemsystembackend.entities.User;

public class UserMapper {

    public static UserDTO mapToDTO(User user){
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setImageUrl(user.getImageUrl());

        return userDTO;
    }

    public static User mapToEntity(UserCreateDTO userCreateDTO){
        User user = new User();

        user.setEmail(userCreateDTO.getEmail());
        user.setUsername(userCreateDTO.getUsername());
        user.setPassword(userCreateDTO.getPassword() );
        user.setImageUrl("");

        return user;
    }
}
