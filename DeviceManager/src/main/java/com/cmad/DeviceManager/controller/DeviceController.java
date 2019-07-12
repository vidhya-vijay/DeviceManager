package com.cmad.DeviceManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cmad.DeviceManager.Exception.InvalidDeviceException;
import com.cmad.DeviceManager.domain.Device;
import com.cmad.DeviceManager.service.DeviceServiceIf;

@RestController
@CrossOrigin
public class DeviceController {
	
	@Autowired
	DeviceServiceIf deviceService;
	
	@PostMapping("/device/add")
	public ResponseEntity<Device> postMessage(@RequestBody Device device){
		try {
			deviceService.addDevice(device);
			return new ResponseEntity<Device>(device, HttpStatus.CREATED);
		} catch (InvalidDeviceException ime) {
			return new ResponseEntity<Device>(device, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<Device>(device, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
