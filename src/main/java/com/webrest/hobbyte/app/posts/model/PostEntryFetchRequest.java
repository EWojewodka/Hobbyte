/**
 * 
 */
package com.webrest.hobbyte.app.posts.model;

/**
 * @author Emil Wojewódka
 *
 * @since 28 cze 2018
 */
public class PostEntryFetchRequest {

	private int size = 10;

	private int offset = 0;

	private int userId;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
