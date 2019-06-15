package com.gabriel.box.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gabriel.box.application.repository.entity.Alarm;

@Repository
public interface AlarmRepository extends CrudRepository<Alarm, Long>  {

	@Query(value = "SELECT * FROM Alarm a WHERE a.box_id = :box and a.notify_id is not null and a.notify_id != 0 ", nativeQuery = true)
	Alarm findFistAlarmTriggered(@Param("box") long boxId);

	@Query(value = "SELECT * FROM Alarm a WHERE a.time = :time ", nativeQuery = true)
	List<Alarm> findAlarmsToThrow(@Param("time") int time);
	
	@Query(value = "SELECT max(a.notify_id) FROM Alarm a WHERE a.box_id = :box ", nativeQuery = true)
	Integer getCurrentNotifyId(@Param("box") long boxId);

	@Query(value = "SELECT * FROM Alarm a INNER JOIN Box b ON b.id = a.box_id WHERE b.user_id = :user ORDER BY a.time ASC ", nativeQuery = true)
	List<Alarm> getAlarmsFromUser(@Param("user") long clientId);

	@Query(value = "SELECT * FROM Alarm a WHERE a.box_id = :box ORDER BY a.time ASC ", nativeQuery = true)
	List<Alarm> findAlarmsFromBox(@Param("box") long boxId);
	
}
