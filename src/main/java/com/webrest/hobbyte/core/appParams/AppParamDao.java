package com.webrest.hobbyte.core.appParams;

import java.util.List;

import org.springframework.stereotype.Service;

import com.webrest.hobbyte.core.criteria.CriteriaFilter;
import com.webrest.hobbyte.core.dao.GenericDao;

@Service
public class AppParamDao extends GenericDao<AppParam>{

	public List<AppParam> findByGroup(String group) {
		return findAllBy("group", group);
	}
	
	public AppParam find(String group, String key) {
		CriteriaFilter cf = new CriteriaFilter("group", group);
		cf.addWhere("code", key);
		List<AppParam> find = find(cf);
		return find.isEmpty() ? null : find.get(0);
	}
	
}
