package com.ji.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ji.entity.Member;
import com.ji.util.DataSourceUtil;
import com.ji.util.InstallProperty;

@RestController
public class InstallController {

	//기본 root 계정으로 초기화 됨
	//jpa 사용시 새로 생성된 계정으로 connect
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	InstallProperty installProperty;
	
	@GetMapping("/install/create/db-account")
	public Object initCreateDBMSUSer(@RequestParam("username") String username,
			@RequestParam("password") String password) throws IOException {

		try {
			
			jdbcTemplate.execute("CREATE user '" + username + "'@'%' IDENTIFIED BY '" + password + "';");
			jdbcTemplate.execute("GRANT ALL PRIVILEGES ON *.* TO '" + username + "'@'%';");
			jdbcTemplate.execute("FLUSH PRIVILEGES;");
			
			Properties props = new Properties();
			props.setProperty("spring.datasource.url", installProperty.getUrl());
			props.setProperty("spring.datasource.driver-class-name", installProperty.getDriverClassName());
			props.setProperty("spring.datasource.username", username);
			props.setProperty("spring.datasource.password", password);
			
			File f = new File(installProperty.getDbInfoPath());
			OutputStream out = new FileOutputStream(f);
			props.store(out, "");
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "ok";

	}

	@GetMapping("/save/data")
	public Object saveMemberData() {

		Properties dataSourceProp = DataSourceUtil.getDataSourcePropByConfigFile(installProperty.getDbInfoPath());
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa_setting", dataSourceProp);
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			Member member = new Member();
			member.setName("hello5");
			em.persist(member);
			
			em.flush();
			em.clear();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}
		
		emf.close();

		return "ok";
	}
	
	

}
