package com.anran.services;

import java.util.HashMap;
import java.util.List;

import com.anran.pojo.Node;

public interface NodeServices {

	
	HashMap<String,Node> getNodeInfo(List<String> stationList);
	
}
