package com.webrest.hobbyte.core.http.context;

import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.core.http.context.IHttpContext;

public interface IExtranetUserContext extends IHttpContext{

	boolean isUserLogged();
	
	ExtranetUser getUser();
	
	void loginUser(ExtranetUser user);
	
}
