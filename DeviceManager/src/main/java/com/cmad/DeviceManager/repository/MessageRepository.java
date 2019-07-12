package com.cmad.DeviceManager.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cmad.DeviceManager.domain.Device;
import com.cmad.DeviceManager.domain.Message;


@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
	
	public List<Message> findByDeviceAndSeverityAndLastUpdatedDate(Device device, Integer severity, Date lastUpdatedDate);
	
	public List<Message> findByDevice(Device device);
	
	public List<Message> findBySeverity(Integer severity);
	
	public List<Message> findByDeviceAndSeverity(Device device, Integer severity);
	
	@Query("select distinct severity from message")
	public List<Integer> findDistinctSeverity();
	

}
