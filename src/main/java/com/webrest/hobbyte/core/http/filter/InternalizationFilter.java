package com.webrest.hobbyte.core.http.filter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.webrest.hobbyte.core.utils.HttpUtils;
import com.webrest.hobbyte.core.utils.StringUtils;

@Service
public class InternalizationFilter extends BasicFilter{

	@Override
	public String getPath() {
		return "/**";
	}

	@Override
	public void filterBefore(HttpServletRequest request, HttpServletResponse response) {
		String lang = request.getParameter("lang");
		if(StringUtils.isEmpty(lang))
			return;
		Cookie cookie = new Cookie("lang", lang);
		HttpUtils.setMaxAgeCookie(cookie);
		response.addCookie(cookie);
	}

}
