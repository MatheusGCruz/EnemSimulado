package com.enemSimulado.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class InitializationService implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private BotService bot; 
	
	@Override
	public void onApplicationEvent(final ContextRefreshedEvent event) {
	  bot.sendNewMessage(bot);
	}
}