package com.gabriel.box.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.box.application.service.BoxService;

@RestController
@RequestMapping("/box")
public class BoxController {
	
	@Autowired
	private BoxService boxService;
	
	@GetMapping("/{idClient}/{boxName}")
    public ResponseEntity<?> receiveBoxData(@PathVariable("idClient") long idClient,
    		@PathVariable("boxName") String boxName) {
		if (idClient <= 0) {
			return new ResponseEntity<>("Invalid user identifier", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (boxName == null || boxName.length() == 0) {
			return new ResponseEntity<>("Invalid box identifier", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		try {
			boxService.updateBoxName(boxName);
			boxService.countBoxUse(idClient, boxName);
		} catch (Exception e) {
			e.printStackTrace();
			new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
    }
	
}
