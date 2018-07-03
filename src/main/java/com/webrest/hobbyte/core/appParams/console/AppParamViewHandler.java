package com.webrest.hobbyte.core.appParams.console;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.webrest.hobbyte.core.appParams.AppParam;
import com.webrest.hobbyte.core.appParams.AppParamDao;
import com.webrest.hobbyte.core.console.details.ParamConsole;
import com.webrest.hobbyte.core.console.handler.DBOConsoleHandler;
import com.webrest.hobbyte.core.console.render.ToolbarButton;
import com.webrest.hobbyte.core.criteria.CriteriaFilter;
import com.webrest.hobbyte.core.exception.ConsoleRedirectException;
import com.webrest.hobbyte.core.http.context.ExtranetUserContext;
import com.webrest.hobbyte.core.utils.StringUtils;

@Service("AppParamViewHandler")
@Scope("session")
public class AppParamViewHandler extends DBOConsoleHandler<AppParam> {

	@Autowired
	private AppParamDao dao;

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
		String id = req.getParameter("id");
		if (!StringUtils.isEmpty(id))
			appParam.setId(StringUtils.getAsInt(id));
		dao.save(appParam);
	}

	@Override
	public void onRemove(ExtranetUserContext context, Model model) throws Exception {
		super.onRemove(context, model);
		String id = context.getRequest().getParameter("id");
		if (StringUtils.isEmpty(id)) {
			context.getMessageHandler().addError("Error during delete app param.");
			return;
		}
		dao.delete(StringUtils.getAsInt(id));
		context.getMessageHandler().addSuccess(
				"Application parameter (code: " + context.getRequest().getParameter("key") + ") is removed.");
	}

	@Override
	public void onClone(ExtranetUserContext context, Model model) throws Exception {
		super.onClone(context, model);

		AppParam byId = dao.getById(StringUtils.getAsInt(context.getRequest().getParameter("id"), -1));
		if (byId == null) {
			context.getMessageHandler().addError("Cannot copy element.");
			return;
		}
		AppParam clone = (AppParam) byId.cloneDBO();
		clone.setCode(byId.getCode() + StringUtils.generateRandom(15));
		dao.save(clone);
	}

	// -------------- RENDER SECTION -------------

	@Override
	protected void initButtons() {
		super.initButtons();
		ToolbarButton addNewBtn = new ToolbarButton("add");
		addNewBtn.setCodeAction("add");
		addNewBtn.setLabel("Add new");
		addButton(addNewBtn);
		ToolbarButton saveBtn = new ToolbarButton("save");
		saveBtn.setCodeAction("save");
		saveBtn.setLabel("Save");
		addButton(saveBtn);
	}

	@Override
	protected CriteriaFilter getCriteriaFilter() {
		CriteriaFilter cf = super.getCriteriaFilter();
		cf.addWhere("group", ((ParamConsole) getConsole()).getGroup());
		return cf;
	}

	@SuppressWarnings("unchecked")
	@Override
	public AppParamDao getDao() {
		return dao;
	}

}
