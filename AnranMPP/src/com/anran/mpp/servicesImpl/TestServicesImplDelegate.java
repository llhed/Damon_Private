package com.anran.mpp.servicesImpl;

import org.springframework.jdbc.core.JdbcTemplate;
import com.anran.mpp.services.TestService;

@javax.jws.WebService(targetNamespace = "http://servicesImpl.mpp.anran.com/", serviceName = "TestServicesImplService", portName = "TestServicesImplPort")
public class TestServicesImplDelegate {

	com.anran.mpp.servicesImpl.TestServicesImpl testServicesImpl = new com.anran.mpp.servicesImpl.TestServicesImpl();

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		testServicesImpl.setJdbcTemplate(jdbcTemplate);
	}

	public int test() {
		return testServicesImpl.test();
	}

}