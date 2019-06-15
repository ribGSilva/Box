package com.gabriel.box.application.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabriel.box.application.repository.AlarmRepository;
import com.gabriel.box.application.repository.BoxRespository;
import com.gabriel.box.application.repository.GeneralConfigurationRepository;
import com.gabriel.box.application.repository.UserRepository;
import com.gabriel.box.application.repository.entity.Alarm;
import com.gabriel.box.application.repository.entity.Box;
import com.gabriel.box.application.repository.entity.GeneralConfiguration;
import com.gabriel.box.application.repository.entity.User;
import com.gabriel.box.application.utils.Utils;
import com.gabriel.box.application.vo.AlarmBoxDetailVO;
import com.gabriel.box.application.vo.AlarmVO;
import com.gabriel.box.application.vo.DeviceDetailVO;
import com.gabriel.box.application.vo.DeviceVO;

@Service
public class ClientService {
	
	@Autowired
	private BoxRespository boxRespository;
	
	@Autowired
	private GeneralConfigurationRepository generalConfigurationRepository;
	
	@Autowired
	private AlarmRepository alarmRepository;
	
	@Autowired
	private UserRepository userRepository;

	public List<DeviceVO> findDevicesFromUser(long clientId) {
		List<Box> boxes = boxRespository.findUserBoxes(clientId);
		
		List<DeviceVO> devices = new ArrayList<>();
		List<Alarm> alarms;
		
		DeviceVO vo;
		for(Box box: boxes) {
			vo = new DeviceVO();
			
			vo.setId(box.getId());
			vo.setBoxName(box.getName());
			vo.setLastPill(Utils.formatDate(box.getUltimaDose()));
			vo.setPills(box.getPills());
			
			alarms = alarmRepository.findAlarmsFromBox(box.getId());
			
			vo.setBuyPils(calculateDateToBuyPills(alarms.size(), box.getPills()));
			vo.setNextPill(calculateNextPill(alarms));
			
			devices.add(vo);
		}
		
		return devices;
	}
	
	private String calculateNextPill(List<Alarm> alarms) {
		if (alarms == null || alarms.size() == 0) {
			return "Não def.";
		}
		
		Integer currentTime = Utils.getCurrentIntTime();
		
		List<Alarm> sortedAlarmList = new ArrayList<>();
		
		sortedAlarmList.addAll(alarms.stream().filter( alarm -> currentTime<alarm.getTime()).collect(Collectors.toList()));
		sortedAlarmList.addAll(alarms.stream().filter( alarm -> currentTime>=alarm.getTime()).collect(Collectors.toList()));
		
		if (sortedAlarmList.isEmpty())
			return "";
		
		return Utils.intTimeToStringTime(sortedAlarmList.get(0).getTime());
	}
	
	private String calculateDateToBuyPills(int alarmNum, int pillsCount) {
		if (alarmNum == 0) {
			return "Nunca";
		}
		
		int daysCount = pillsCount / alarmNum;
		
		Date buyDay = Utils.addDaysToCurrentDate(daysCount);
		
		return Utils.formatOnlyDate(buyDay);
	}
	
	public Map<String, String> findConfigurations() {
		Iterable<GeneralConfiguration> configurations = generalConfigurationRepository.findAll();

		Map<String, String> configurationMap = new LinkedHashMap<>();
		
		if (configurations != null) {
			for (GeneralConfiguration generalConfiguration : configurations) {
				configurationMap.put(generalConfiguration.getName(), generalConfiguration.getValue());
			}
		}
		
		return configurationMap;
		
	}

	public List<AlarmVO> findNextAlarmsFromUser(long clientId) {
		
		List<Alarm> alarmList = alarmRepository.getAlarmsFromUser(clientId);
		
		Integer currentTime = Utils.getCurrentIntTime();
		
		List<Alarm> sortedAlarmList = new ArrayList<>();
		
		sortedAlarmList.addAll(alarmList.stream().filter( alarm -> currentTime<alarm.getTime()).collect(Collectors.toList()));
		sortedAlarmList.addAll(alarmList.stream().filter( alarm -> currentTime>=alarm.getTime()).collect(Collectors.toList()));
		
		List<AlarmVO> alamsVo = new ArrayList<>();
		
		AlarmVO vo;
		for (Alarm alarm : sortedAlarmList) {
			vo = new AlarmVO();
			
			vo.setBoxName(alarm.getBox().getName());
			vo.setAlarmTime(Utils.intTimeToStringTime(alarm.getTime()));
			
			alamsVo.add(vo);
		}
		
		return alamsVo;
	}

	public DeviceDetailVO loadBoxDetails(long clientId, long deviceId) {
		Box box = boxRespository.loadBoxByClientAndDeviceId(clientId, deviceId);
		
		DeviceDetailVO vo = new DeviceDetailVO();
			
		vo.setId(box.getId());
		vo.setBoxName(box.getName());
		vo.setLastPill(Utils.formatDate(box.getUltimaDose()));
		vo.setPills(box.getPills());
		
		List<Alarm> alarms = alarmRepository.findAlarmsFromBox(box.getId());
		
		vo.setBuyPils(calculateDateToBuyPills(alarms.size(), box.getPills()));
		vo.setNextPill(calculateNextPill(alarms));
		
		AlarmBoxDetailVO alarmVO;
		
		for (Alarm alarm : alarms) {
			alarmVO = new AlarmBoxDetailVO();
			
			alarmVO.setId(alarm.getId());
			alarmVO.setTime(alarm.getTime());
			
			vo.getAlarms().add(alarmVO);
		}
		
		return vo;
	}

	public void updateToken(long clientId, String deviceId) {
		Optional<User> user = userRepository.findById(clientId);
		
		user.get().setNotificationToken(deviceId);
		
		userRepository.save(user.get());
	}

}
