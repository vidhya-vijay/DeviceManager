package com.cmad.DeviceManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmad.DeviceManager.domain.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer>{
	
	public Device findByDeviceName(String name);

}
