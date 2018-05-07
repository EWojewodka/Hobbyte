package com.webrest.hobbyte.core.console.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.webrest.hobbyte.core.appParams.AppParam;
import com.webrest.hobbyte.core.appParams.AppParamDao;
import com.webrest.hobbyte.core.console.IConsole;
import com.webrest.hobbyte.core.console.details.ParamConsole;
import com.webrest.hobbyte.core.console.render.AppParamRenderer;
import com.webrest.hobbyte.core.exception.ConsoleRedirectException;
import com.webrest.hobbyte.core.http.context.ExtranetUserContext;
import com.webrest.hobbyte.core.utils.StringUtils;
import com.webrest.hobbyte.core.utils.spring.DependencyResolver;

public class AppParamViewHandler extends ConsoleHandler {

	public AppParamViewHandler(DependencyResolver resolver, IConsole console) {
		super(resolver, console);
		setRenderer(new AppParamRenderer(resolver, console));
	}

	@Override
	public void onAdd(ExtranetUserContext context, Model model) throws Exception {
		super.onAdd(context, model);
		AppParam appParam = new AppParam();
		appParam.setGroup(((ParamConsole) getConsole()).getGroup());
		context.put("app-param", appParam);
		throw new ConsoleRedirectException(context, "app-param-new");
	}

	@Override
	public void onSave(ExtranetUserContext context, Model model) throws Exception {
		super.onSave(context, model);
		HttpServletRequest req = context.getRequest();
		String group = req.getParameter("group");
		String key = req.getParameter("key");
		if (StringUtils.isEmpty(group) || StringUtils.isEmpty(key)) {
			context.getMessageHandler().addError("Cannot save app param. Group or key is empty.");
			return;
		}
		AppParam appParam = new AppParam();
		appParam.setGroup(group);
		appParam.setCode(key);
		appParam.setValue(req.getParameter("value"));
		if (!StringUtils.isEmpty(req.getParameter("id")))
			appParam.setId(StringUtils.getAsInt(req.getParameter("id")));
		getDependency(AppParamDao.class).save(appParam);
	}

	@Override
	public void onRemove(ExtranetUserContext context, Model model) throws Exception {
		super.onRemove(context, model);
		String id = context.getRequest().getParameter("id");
		if (StringUtils.isEmpty(id)) {
			context.getMessageHandler().addError("Error during delete app param.");
			return;
		}
		getDependency(AppParamDao.class).delete(StringUtils.getAsInt(id));
		context.getMessageHandler().addSuccess(
				"Application parameter (code: " + context.getRequest().getParameter("key") + ") is removed.");
	}

	@Override
	public void onClone(ExtranetUserContext context, Model model) throws Exception {
		super.onClone(context, model);

		AppParamDao dao = getDependency(AppParamDao.class);
		AppParam byId = dao.getById(StringUtils.getAsInt(context.getRequest().getParameter("id"), -1));
		if (byId == null) {
			context.getMessageHandler().addError("Cannot copy element.");
			return;
		}
		AppParam clone = (AppParam) byId.cloneDBO();
		clone.setCode(byId.getCode() + StringUtils.generateRandom(15));
		System.out.println(clone);
		dao.save(clone);
	}

	@Override
	public Class<?>[] getDependencies() {
		List<Class<?>> dependencies = Arrays.asList(super.getDependencies());
		dependencies = new ArrayList<>(dependencies);
		dependencies.add(AppParamDao.class);
		return dependencies.toArray(new Class<?>[dependencies.size()]);
	}

}
