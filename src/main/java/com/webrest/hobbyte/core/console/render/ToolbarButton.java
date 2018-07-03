package com.webrest.hobbyte.core.console.render;

import java.util.HashMap;
import java.util.Map;

public class ToolbarButton {

	private String label;

	private String codeAction;

	private String icon;

	private String code;

	private Map<String, Object> hrefParams = new HashMap<>();

	public ToolbarButton(String code) {
		this.code = code;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getCodeAction() {
		return codeAction;
	}

	public void setCodeAction(String codeAction) {
		this.codeAction = codeAction;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getCode() {
		return code;
	}

	public Map<String, Object> getHrefParams() {
		return hrefParams;
	}

	public void addHrefParam(String paramName, Object paramValue) {
		hrefParams.put(paramName, paramValue);
	}

}
