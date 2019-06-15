package com.gabriel.box.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gabriel.box.application.repository.entity.Box;

@Repository
public interface BoxRespository extends CrudRepository<Box, Long> {
	
	@Query(value = "SELECT * FROM Box b WHERE b.user_id = :user", nativeQuery = true)
	List<Box> findUserBoxes(@Param("user") long userId);
	
	@Query(value = "SELECT * FROM Box b WHERE b.user_id = :user and b.name = :boxname", nativeQuery = true)
	Box findUserBoxByName(@Param("user") long userId, @Param("boxname") String boxname);

	@Query(value = "SELECT * FROM Box b WHERE b.user_id = :user and b.id = :box", nativeQuery = true)
	Box loadBoxByClientAndDeviceId(@Param("user") long clientId, @Param("box") long deviceId);
	
}
