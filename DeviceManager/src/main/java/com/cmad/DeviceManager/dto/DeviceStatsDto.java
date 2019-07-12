package com.cmad.DeviceManager.dto;

import java.util.Map;

public class DeviceStatsDto {

	private String deviceName;
	private Map<String,Integer> messageCount;
	
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public Map<String, Integer> getMessageCount() {
		return messageCount;
	}
	public void setMessageCount(Map<String, Integer> messageCount) {
		this.messageCount = messageCount;
	}
	
}
