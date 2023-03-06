package ru.practicum.service.interfaces;

import org.springframework.data.domain.Pageable;
import ru.practicum.dto.UserDto;

import java.util.Collection;
import java.util.List;

public interface UserService {

    UserDto addUser(UserDto userDto);

    void deleteUser(Long id);

    Collection<UserDto> getUsersPageable(List<Long> ids, Pageable pageable);

}
