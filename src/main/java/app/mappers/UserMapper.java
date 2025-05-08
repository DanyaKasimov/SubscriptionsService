package app.mappers;

import app.dto.request.AddUserDto;
import app.dto.request.EditUserDto;
import app.dto.response.UserDto;
import app.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(AddUserDto dto);

    void updateEntity(EditUserDto dto, @MappingTarget User user);
}