package app.controllers;

import app.api.UserApi;
import app.dto.request.AddUserDto;
import app.dto.response.UserDto;
import app.dto.request.EditUserDto;
import app.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public UserDto addUser(final AddUserDto dto) {
        log.info("Поступил запрос на добавление пользователя: {}", dto);

        return userService.addUser(dto);
    }

    @Override
    public UserDto getUser(final Long id) {
        log.info("Поступил запрос на получение данных пользователя с id: {}", id);

        return userService.getUser(id);
    }

    @Override
    public UserDto editUser(final Long id, final EditUserDto dto) {
        log.info("Поступил запрос на обновление данных пользователя. ID: {}, Body: {}", id, dto);

        return userService.editUser(id, dto);
    }

    @Override
    public void deleteUser(final Long id) {
        log.info("Поступил запрос на удаление пользователя. ID: {}", id);

        userService.deleteUser(id);
    }
}
