package com.cmad.DeviceManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmad.DeviceManager.domain.Device;
import com.cmad.DeviceManager.repository.DeviceRepository;
import com.cmad.DeviceManager.Exception.InvalidDeviceException;

@Service
public class DeviceService implements DeviceServiceIf{
	
	@Autowired
	DeviceRepository deviceRepository;
	
	public Device addDevice(Device device) {
		
		if(device==null || device.getDeviceName().equals(""))
			throw new InvalidDeviceException();
		
		if(deviceRepository.findByDeviceName(device.getDeviceName())!=null)
			throw new InvalidDeviceException();
		
		deviceRepository.save(device);
		return device;
	}

}
