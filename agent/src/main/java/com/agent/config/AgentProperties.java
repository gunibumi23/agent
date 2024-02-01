package com.agent.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariConfig;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@ConfigurationProperties(prefix = "agent", ignoreInvalidFields = true)
@Component
@Data
public class AgentProperties {	
	private HikariConfig hikaricp;

	private String host;
	private int    port;	

	public void setHikaricp(HikariConfig hikaricp) {
		if(hikaricp != null) {
			String url = hikaricp.getJdbcUrl();
			String[] hostPort = url.substring(url.indexOf("//")+2,url.lastIndexOf("/")).split(":");
			this.setHost(hostPort[0]);
			this.setPort(Integer.valueOf(hostPort[1]));
		}
		this.hikaricp = hikaricp;
	}	
}
