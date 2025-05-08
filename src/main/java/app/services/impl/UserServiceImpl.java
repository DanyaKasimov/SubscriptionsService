package app.services.impl;

import app.dto.request.AddUserDto;
import app.dto.request.EditUserDto;
import app.dto.response.UserDto;
import app.exceptions.InvalidDataException;
import app.exceptions.NoDataFoundException;
import app.mappers.UserMapper;
import app.model.User;
import app.repositories.SubscriptionRepository;
import app.repositories.UserRepository;
import app.repositories.UserSubscriptionRepository;
import app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final UserSubscriptionRepository userSubscriptionRepository;

    private final SubscriptionRepository subscriptionRepository;

    @Override
    @Transactional
    public UserDto addUser(final AddUserDto dto) {
        if (userRepository.existsUsersByEmail(dto.getEmail())) {
            throw new InvalidDataException("Пользователь с почтой - {} уже зарегистрирован", dto.getEmail());
        }

        User user = userMapper.toEntity(dto);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserDto editUser(final Long id, final EditUserDto dto) {
        User user = findById(id);
        userMapper.updateEntity(dto, user);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUser(final Long id) {
        User user = findById(id);
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public void deleteUser(final Long userId) {
        User user = findById(userId);

        List<Long> userSubscriptionIds = userSubscriptionRepository.findSubscriptionIdsByUserId(userId);

        userSubscriptionRepository.deleteAllByUserId(userId);

        userSubscriptionIds.stream()
                .filter(subId -> !userSubscriptionRepository.existsBySubscriptionId(subId))
                .forEach(subscriptionRepository::deleteById);

        userRepository.delete(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoDataFoundException("Пользователь с ID - [{}] не найден.", id));
    }
}
