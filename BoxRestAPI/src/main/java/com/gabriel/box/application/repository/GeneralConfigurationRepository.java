package com.gabriel.box.application.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gabriel.box.application.repository.entity.GeneralConfiguration;

@Repository
public interface GeneralConfigurationRepository extends CrudRepository<GeneralConfiguration, Long> {
	
}
