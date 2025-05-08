package app.services;

import app.dto.request.AddUserDto;
import app.dto.request.EditUserDto;
import app.dto.response.UserDto;
import app.model.User;

public interface UserService {

    UserDto addUser(AddUserDto dto);

    UserDto editUser(Long id, EditUserDto dto);

    UserDto getUser(Long id);

    void deleteUser(Long id);

    User findById(Long id);
}
