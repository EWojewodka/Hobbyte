package com.webrest.hobbyte.core.utils;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webrest.hobbyte.core.appParams.AppParam;
import com.webrest.hobbyte.core.appParams.AppParamDao;


@Service
public class AppParamUtils{

	private static AppParamDao staticDao;
	
	@Autowired
	private AppParamDao dao;
	
	@PostConstruct
	private void init() {
		staticDao = dao;
	}
	
	public static String getValue(String group, String key) {
		AppParam appParam = staticDao.find(group, key);
		return appParam == null ? "" : appParam.getValue();
	}
	
	
}
