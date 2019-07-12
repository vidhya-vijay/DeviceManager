package com.cmad.DeviceManager.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "device")
public class Device {
	@Id
	@GeneratedValue
	private Integer deviceId;
	private String deviceName;
	
	@OneToMany(mappedBy="device")
	private List<Message> messages;

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer id) {
		deviceId = id;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
	
}
