package com.altran.toolsbox.usermanagement.service;

public interface EmailService {

	void sendSimpleMessage(String to, String subject, String text);

}