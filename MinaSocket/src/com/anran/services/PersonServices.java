package com.anran.services;

import java.util.HashMap;
import java.util.List;

import com.anran.pojo.Person;

public interface PersonServices {

	/**
	 * �����Ա��ƣ���Ա����б�
	 * @param personNumList
	 * @return
	 */
	HashMap<String,Person> getPersons(List<String> personNumList);
	
	String getStationPersonLimit(String ipAdress);
}
