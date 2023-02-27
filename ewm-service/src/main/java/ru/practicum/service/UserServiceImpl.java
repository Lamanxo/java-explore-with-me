package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.UserDto;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mappers.UserMapper;
import ru.practicum.model.User;
import ru.practicum.repo.UserRepository;
import ru.practicum.service.interfaces.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    @Override
    @Transactional
    public UserDto addUser(UserDto userDto) {
        User user;
        try {
            user = userRepo.save(UserMapper.makeUser(userDto));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("This email already used");
        }
        return UserMapper.makeUserDto(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepo.findById(id).orElseThrow(() ->
                new NotFoundException("User with id: " + id + " not found"));
        userRepo.deleteById(id);
    }

    @Override
    public Collection<UserDto> getUsersPageable(List<Long> ids, Pageable pageable) {
        List<UserDto> dtoUsers = new ArrayList<>();
        for (var user : userRepo.findByIdIsIn(ids, pageable)) {
            dtoUsers.add(UserMapper.makeUserDto(user));
        }
        return dtoUsers;
    }
}
