package com.gabriel.box.application.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.box.application.service.ClientService;
import com.gabriel.box.application.service.PushNotificationService;
import com.gabriel.box.application.vo.AlarmVO;
import com.gabriel.box.application.vo.DeviceDetailVO;
import com.gabriel.box.application.vo.DeviceVO;

@RestController
@RequestMapping("/client")
public class ClientController {

	@Autowired
	private ClientService clientService;
	
	@Autowired
	private PushNotificationService push;
	
	@GetMapping(value = "/{userId}/mydevices", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDevicesFromUser(@PathVariable("userId") long clientId) {
		if (clientId <= 0) {
			return new ResponseEntity<>("Invalid user identifier", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		try {
			List<DeviceVO> findDevicesFromUser = clientService.findDevicesFromUser(clientId);
			
			return new ResponseEntity<>(findDevicesFromUser, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	@GetMapping(value = "/{userId}/nextalarms", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getNextAlarmsFromUser(@PathVariable("userId") long clientId) {
		if (clientId <= 0) {
			return new ResponseEntity<>("Invalid user identifier", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		try {
			List<AlarmVO> alarmsFromUser = clientService.findNextAlarmsFromUser(clientId);
			
			return new ResponseEntity<>(alarmsFromUser, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	
	@GetMapping(value = "/{userId}/mydevice/{deviceId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDeviceDetailFromUser(@PathVariable("userId") long clientId,
    		@PathVariable("deviceId") long deviceId) {
		if (clientId <= 0) {
			return new ResponseEntity<>("Invalid user identifier", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (deviceId <= 0) {
			return new ResponseEntity<>("Invalid device identifier", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		try {
			DeviceDetailVO detail = clientService.loadBoxDetails(clientId, deviceId);
			
	        return new ResponseEntity<>(detail, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	@GetMapping(value = "/updateToken/{userId}/{token}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateToken(@PathVariable("userId") long clientId,
    		@PathVariable("token") String deviceId) {
		if (clientId <= 0) {
			return new ResponseEntity<>("Invalid user identifier", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		try {
			clientService.updateToken(clientId, deviceId);
			
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	@GetMapping(value = "/defaultWifiConfiguration", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDefaultWifiConfiguration() {
		try {
			Map<String, String> findConfigurations = clientService.findConfigurations();

	        return new ResponseEntity<>(findConfigurations, HttpStatus.OK);
	        
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
    }
	
	@GetMapping(value = "/testNot/{key}/{action}/{notifyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDefaultWifiConfiguration(@PathVariable("key") String key,
    		@PathVariable("action") String action, @PathVariable("notifyId") int notifyId) {
		try {
			push.sendNoficiation("Teste server", "Teste server", key, action, notifyId);

			return new ResponseEntity<>(HttpStatus.OK);
	        
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
    }
	
}
