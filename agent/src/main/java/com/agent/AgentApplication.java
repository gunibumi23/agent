package com.agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.agent.config.db.AgentDbInitailize;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class AgentApplication {
	
	public static void main(String[] args) {
		
		AgentDbInitailize agentIni = new AgentDbInitailize("agent");
		agentIni.build();		
		SpringApplication.run(AgentApplication.class, args);		
	}
}
