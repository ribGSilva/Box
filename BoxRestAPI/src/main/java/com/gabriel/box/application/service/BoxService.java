package com.gabriel.box.application.service;

import java.util.Calendar;
import java.util.Optional;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabriel.box.application.repository.AlarmRepository;
import com.gabriel.box.application.repository.BoxRespository;
import com.gabriel.box.application.repository.entity.Alarm;
import com.gabriel.box.application.repository.entity.Box;

@Service
public class BoxService {

	@Autowired
	private BoxRespository boxRespository;
	
	@Autowired
	private AlarmRepository alarmRepository;
	
	@Autowired
	private PushNotificationService pushNotificationService;
	
	public void updateBoxName(String name) {
		Optional<Box> box = boxRespository.findById(1L);
		
		box.get().setName(name);
		
		boxRespository.save(box.get());
	}

	public void countBoxUse(long clientId, String boxName) {
		Box box = boxRespository.findUserBoxByName(clientId, boxName);
		
		decreaseBoxPills(box);
		box.setUltimaDose(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime());
		
		Alarm alarm = alarmRepository.findFistAlarmTriggered(box.getId());
		
		if (alarm != null) {
			String userToken = box.getUser().getNotificationToken();
			
			Integer notifyId = alarm.getNotify_id();
			
			pushNotificationService.sendNoficiation("Tomar remédio", "Tomar remédio: " + box.getName(), userToken, "cancel", notifyId);
			
			alarm.setNotify_id(null);
		}
		
		boxRespository.save(box);
		alarmRepository.save(alarm);
		
	}
	
	private void decreaseBoxPills(Box box) {
		Integer pills = box.getPills();
		if (pills == null || pills == 0) {
			box.setPills(0);
		} else {
			box.setPills(pills - 1);
		}
	}
	
}
