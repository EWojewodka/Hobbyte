/**
 * 
 */
package com.webrest.hobbyte.core.console.details;

import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Element;

import com.webrest.hobbyte.core.console.Console;
import com.webrest.hobbyte.core.utils.EnumUtils;
import com.webrest.hobbyte.core.utils.StringUtils;
import com.webrest.hobbyte.core.utils.WithCode;

/**
 * @author Emil Wojewódka
 *
 * @since 5 kwi 2018
 */
public class DetailsConsole extends Console {

	private List<DetailField> listOfFields;

	public DetailsConsole(Element element) throws Exception {
		super(element);
	}

	@Override
	public ConsoleType getType() {
		return ConsoleType.DETAILS;
	}

	@Override
	public void init() throws Exception {
		super.init();
		initFields(getAttribute("fields"));
	}

	private void initFields(String fieldDefinition) {
		listOfFields = new LinkedList<>();
		if (StringUtils.isEmpty(fieldDefinition))
			return;

		String[] fields = fieldDefinition.split(",");
		if (StringUtils.isEmpty(fields))
			return;

		for (String fieldDef : fields) {
			DetailField df = new DetailField();
			int indexOfType = fieldDef.indexOf("|");

			String type = fieldDef.substring(indexOfType + 1, fieldDef.length());
			df.type = DetailFieldType.getByCode(type);
			int indexOfLabel = fieldDef.indexOf("#");
			String label = null;
			if (indexOfLabel == -1)
				label = fieldDef.substring(0, indexOfType);
			else
				label = fieldDef.substring(indexOfLabel + 1, indexOfType);
			df.label = label;
			df.propertyName = fieldDef.substring(0, indexOfLabel == -1 ? indexOfType : indexOfLabel);
			listOfFields.add(df);
		}
	}

	public List<DetailField> getListOfFields() {
		return listOfFields;
	}

}

class DetailField {

	String propertyName;

	String label;

	DetailFieldType type;

	public String getPropertyName() {
		return propertyName;
	}

	public String getLabel() {
		return label;
	}

	public DetailFieldType getType() {
		return type;
	}

}

enum DetailFieldType implements WithCode {

	DISABLED("disabled"),
	/**/
	DATETIME("datetime"),
	/**/
	INPUT("input"),
	/**/
	PROPERTY_STRING("propertyString"),
	/**/
	NUMBER("number");

	private String code;

	private DetailFieldType(String code) {
		this.code = code;
	}

	static DetailFieldType getByCode(String code) {
		return EnumUtils.findByCode(DetailFieldType.class, code);
	}

	@Override
	public String getCode() {
		return code;
	}

}