package com.cmad.DeviceManager.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmad.DeviceManager.Exception.DeviceNotFoundException;
import com.cmad.DeviceManager.domain.Device;
import com.cmad.DeviceManager.domain.Message;
import com.cmad.DeviceManager.dto.DeviceStatsDto;
import com.cmad.DeviceManager.dto.MessageStatsDto;
import com.cmad.DeviceManager.repository.DeviceRepository;
import com.cmad.DeviceManager.repository.MessageRepository;

@Service
public class MessageService implements MessageServiceIf{
	
	@Autowired
	MessageRepository messageRepository;
	
	@Autowired
	DeviceRepository deviceRepository;
	
    public List<Message> getMessages() {
		List<Message> messages = new ArrayList<Message>();
		messages = messageRepository.findAll();
		return messages;
	}

	public Message addMessage(String deviceName, Message message) {
		
		Device device = deviceRepository.findByDeviceName(deviceName);
		if(device==null)
			throw new DeviceNotFoundException();
		
		message.setDevice(device);
        return messageRepository.save(message);
   }

	@Override
	public List<Message> getFilteredMessages(String deviceName, Integer severity) {
		List<Message> messages = new ArrayList<Message>();
		Device device = null;
		
		if(deviceName!=null) {
			device = deviceRepository.findByDeviceName(deviceName);
		}
		
		if(device!=null && severity!=null)
			messages = messageRepository.findByDeviceAndSeverity(device, severity);
		else if(device!=null)
			messages = messageRepository.findByDevice(device);
		else if(severity!=null)
			messages = messageRepository.findBySeverity(severity);
		
		return messages;
	}
	
	@Override
	public List<DeviceStatsDto> getDeviceStats(String deviceName){
		List<DeviceStatsDto> deviceStats = new ArrayList<DeviceStatsDto>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		List<Device> deviceList = new ArrayList<Device>();
		if(deviceName!=null && !deviceName.isEmpty()) {
			Device device = deviceRepository.findByDeviceName(deviceName);
			if(device==null)
				throw new DeviceNotFoundException();
			deviceList.add(device);
		}else
			deviceList = deviceRepository.findAll();
		
		for(Device device: deviceList) {
			List<Message> messageList = device.getMessages();
			Map<String,Integer> dateMap = new TreeMap<String, Integer>();
			for(Message message:messageList) {
				Date d = message.getLastUpdatedDate();
				String dStr = dateFormat.format(d);
				if(dateMap.containsKey(dStr))
					dateMap.put(dStr, dateMap.get(dStr)+1);
				else
					dateMap.put(dStr, 1);
			}
			DeviceStatsDto deviceStatsDto = new DeviceStatsDto();
			deviceStatsDto.setDeviceName(device.getDeviceName());
			deviceStatsDto.setMessageCount(dateMap);
			
			deviceStats.add(deviceStatsDto);
		}
		
		return deviceStats;
	}
	
	@Override
	public List<MessageStatsDto> getMessageStats(String deviceName, Integer severity){
		List<MessageStatsDto> messageStats = new ArrayList<MessageStatsDto>();
		
		if(deviceName!=null && !deviceName.isEmpty()) {
			Device device = deviceRepository.findByDeviceName(deviceName);
			if(device==null)
				throw new DeviceNotFoundException();
			
			if(severity!=null) {
				List<Message> messages = messageRepository.findByDeviceAndSeverity(device, severity);
			    MessageStatsDto messageDto = new MessageStatsDto();
		 	    messageDto.setSeverity(severity);
		 	    messageDto.setMessageCount(messages.size());
		 	    
		 	    messageStats.add(messageDto);
		        	
			}else {
				List<Integer> severityList = messageRepository.findDistinctSeverity();
				for(Integer sev: severityList) {
					List<Message> messages = messageRepository.findByDeviceAndSeverity(device, sev);
					MessageStatsDto messageDto = new MessageStatsDto();
			 	    messageDto.setSeverity(sev);
			 	    messageDto.setMessageCount(messages.size());
			 	    
			 	    messageStats.add(messageDto);
				}
				
			}
		}else {
			List<Message> messages = null;
			List<Integer> severityList = null;
			
			if(severity!=null) {
				severityList = new ArrayList<Integer>();
				severityList.add(severity);
			}else
				severityList = messageRepository.findDistinctSeverity();
			
			
			for(Integer sev: severityList) {
			    	messages = messageRepository.findBySeverity(sev);
				    MessageStatsDto messageDto = new MessageStatsDto();
		 	        messageDto.setSeverity(sev);
		 	        messageDto.setMessageCount(messages.size());
		 	        messageStats.add(messageDto);
			}
		}
		
		return messageStats;
	}

	
}
