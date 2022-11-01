package com.birthdayemail.userdetails.mapper;

import com.birthdayemail.userdetails.entity.PersonEntity;
import com.birthdayemail.userdetails.model.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    PersonEntity modelToEntity(Person person);
    Person entityToModel(PersonEntity personEntity);
}
