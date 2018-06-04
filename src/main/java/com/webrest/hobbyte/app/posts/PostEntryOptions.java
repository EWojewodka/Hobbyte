package com.webrest.hobbyte.app.posts;

import java.util.ArrayList;
import java.util.List;

import com.webrest.hobbyte.core.utils.WithCode;
import com.webrest.hobbyte.core.utils.collection.IPriority;

public enum PostEntryOptions implements WithCode, IPriority {

	/**
	 * priority level: </br>
	 * 2 -> OWNER </br>
	 * 1 -> Everyone
	 * 
	 */
	DELETE("delete", 2),
	/***/
	EDIT("edit", 2),
	/***/
	HIDE("hide", 2),
	/***/
	NOTIFY("notify", 1);

	private String code;

	private int priority;

	private PostEntryOptions(String code, int priority) {
		this.code = code;
		this.priority = priority;
	}

	public static List<PostEntryOptions> getByOwnerStatus(int level) {
		PostEntryOptions[] values = values();
		List<PostEntryOptions> resultList = new ArrayList<>();

		for (PostEntryOptions option : values) {
			if (option.priority <= level)
				resultList.add(option);
		}

		return resultList;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public int getPriority() {
		return priority;
	}

	@Override
	public void setPriority(int priority) {

	}

}