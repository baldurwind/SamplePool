package sy.model;

import java.util.List;
import java.util.Map;

/**
 * easyui使用的tree模型
 * 
 * @author ben.xu
 * 
 */
public class EasyuiTreeNode {

	private String id;
	private String text;
	private Boolean checked = false;
	private Map<String, Object> attributes;
	private List<EasyuiTreeNode> children;
	private String state = "open";// open,closed

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public List<EasyuiTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<EasyuiTreeNode> children) {
		this.children = children;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
