/**
 * 
 */
package com.webrest.hobbyte.app.reaction;

import com.webrest.hobbyte.core.utils.WithCode;

/**
 * @author Emil Wojewódka
 *
 * @since 10 kwi 2018
 */
public interface IPostEntryReaction {

	public enum PostEntryReactions implements WithCode{
		LIKE("like");
		
		private String code;
		
		private PostEntryReactions(String code) {
			this.code = code;
		}

		@Override
		public String getCode() {
			return code;
		}
		
		
	}
	
	PostEntryReactions getReactionType();
	
}
