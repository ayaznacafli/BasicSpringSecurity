package com.ayaz.repository;

import com.ayaz.domain.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends CrudRepository<Person,String> {
    public Person findByEmailIgnoreCase(@Param("email") String email);
}
