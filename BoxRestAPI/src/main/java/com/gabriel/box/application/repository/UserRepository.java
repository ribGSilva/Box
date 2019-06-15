package com.gabriel.box.application.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gabriel.box.application.repository.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
