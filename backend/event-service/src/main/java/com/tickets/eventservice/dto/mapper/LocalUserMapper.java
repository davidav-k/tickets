package com.tickets.eventservice.dto.mapper;

import com.tickets.eventservice.dto.UserResponse;
import com.tickets.eventservice.entity.LocalUser;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LocalUserMapper {

    UserResponse toDto(LocalUser user);

}
