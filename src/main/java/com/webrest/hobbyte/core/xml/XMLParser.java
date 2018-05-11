package com.webrest.hobbyte.core.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.webrest.hobbyte.core.console.ConsoleBuilder;
import com.webrest.hobbyte.core.menuTree.MenuTreeBuilder;
import com.webrest.hobbyte.core.utils.Asserts;

/**
 * Class for parsing xml config file.
 * 
 * @see MenuTreeBuilder
 * @see ConsoleBuilder
 * @author wojew
 *
 * @param <T>
 */
public abstract class XMLParser<T> {

	private String xmlPath;

	private Document doc;

	private List<T> resultList = new ArrayList<>();

	public XMLParser(String xmlPath) {
		Asserts.notEmpty(xmlPath, "Cannot init XMLParser for null source path");
		this.xmlPath = xmlPath;
		try {
			initDocument();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Process xml and use abstract method {@link #processSingle(Element)} for
	 * handling what you need from all of node. </br>
	 * <i>Note: This method handle only the highest level of XML children, e.g.
	 * </br>
	 * <b> &lt;?xml version="1.0" encoding="UTF-8"?&gt;</br>
	 *  &lt;menutree&gt;</br>
	 * &lt;folder name="Extranet users" id="users" icon="fa fa-folder"&gt; &lt;-- this</br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;leaf name="Users" uri="/sys/users" id="users_list" icon="fa fa-users"/&gt; &lt;-- this NOT</br>
	 * &lt;/folder&gt;
	 * &lt;folder name="Application" id="application" icon="fa fa-folder"/&gt; &lt;-- this</br>
	 * &lt;/menutree&gt; </b></i>
	 * 
	 * @return
	 */
	public List<T> process() {
		NodeList nodes = doc.getDocumentElement().getChildNodes();
		if (nodes == null || nodes.getLength() == 0)
			return resultList;
		int len = nodes.getLength();
		for (int i = 0; i < len; i++) {
			Node node = nodes.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE)
				continue;
			try {
				processSingle((Element) node);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultList;
	}

	/**
	 * Add object to {@link #resultList}
	 * 
	 * @param t
	 */
	public void addToList(T t) {
		resultList.add(t);
	}

	protected abstract void processSingle(Element element);

	private void initDocument() throws Exception {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		this.doc = dBuilder.parse(this.getClass().getClassLoader().getResourceAsStream(xmlPath));
	}

}
