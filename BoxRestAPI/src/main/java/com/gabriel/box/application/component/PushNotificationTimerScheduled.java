package com.gabriel.box.application.component;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gabriel.box.application.repository.AlarmRepository;
import com.gabriel.box.application.repository.entity.Alarm;
import com.gabriel.box.application.service.PushNotificationService;
import com.gabriel.box.application.utils.Utils;

@Component
@EnableScheduling
public class PushNotificationTimerScheduled {

	private static final String TIME_ZONE = "America/Sao_Paulo";
	
	@Autowired
	private PushNotificationService notificationService;
	
	@Autowired
	private AlarmRepository alarmRepository;

	@Scheduled(cron = "0 */1 * * * *", zone = TIME_ZONE)
	public void verificarEnvioNotificacao() {

		System.out.println(LocalDateTime.now() + " Timer funcionando");
		
		List<Alarm> alarmsToThrow = alarmRepository.findAlarmsToThrow(Utils.getCurrentIntTime());
		
		Integer notifyId;
		
		for (Alarm alarm : alarmsToThrow) {
			notifyId = alarmRepository.getCurrentNotifyId(alarm.getBox().getId());
			
			if (notifyId == null) {
				notifyId = 1000;
			} else {
				notifyId++;
			}
			
			alarm.setNotify_id(notifyId);
			
			System.out.println(LocalDateTime.now() + " Notify Id: " + notifyId);
			
			notificationService.sendNoficiation(
					"Tomar remédio", 
					"Tomar remédio: " + alarm.getBox().getName(), 
					alarm.getBox().getUser().getNotificationToken(), 
					"create", 
					notifyId
					);
		}
		
		alarmRepository.saveAll(alarmsToThrow);

	}

}
