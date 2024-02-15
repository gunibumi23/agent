package com.agent.config.db;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Path;


import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.springframework.MariaDB4jSpringService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AgentDbInitailize {
	
	private final String HOST = "localhost";
	private final String CREATE_SHEMA_SQL = "CREATE SCHEMA IF NOT EXISTS  `%s` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;"; 
	private final int	 PORT = 3306;
	private String dbNm;
	
	public AgentDbInitailize(String dbNm) {
		this.dbNm = dbNm;
	}
	
	/**
	 * embeded 용.. mysql 설치하고 그게 번거러워서.. 그냥 내장으로..
	 */
	public void build() {		
		String projPath = Path.of(System.getProperty("user.dir")).getParent().toString();
		String basePath = projPath + File.separator + "db";
		String libPath  = basePath + File.separator + "lib";
		String dataPath = basePath + File.separator + "data";
		try {
			new Socket(HOST,PORT).close();			
		}catch(IOException e) {
			log.error(e.getMessage());
			MariaDB4jSpringService schema = new MariaDB4jSpringService();
			schema.setDefaultBaseDir(basePath); 
			schema.setDefaultLibDir (libPath);
			schema.setDefaultDataDir(dataPath); 
			schema.setDefaultPort(PORT);
			
			schema.start();
			
			String query = String.format(CREATE_SHEMA_SQL, dbNm);
			String user  = dbNm;
			String pwd   = "@" +  dbNm;
			
			DB db = schema.getDB();
			try {
				log.info("Schema : {}, User : {} ",query,user);
				db.run(query, user, pwd);
				db.source("sql/init.sql",user,pwd,dbNm);
			} catch (ManagedProcessException e1) {
				log.error(e1.getMessage());
			}
		}		
	}	
}
