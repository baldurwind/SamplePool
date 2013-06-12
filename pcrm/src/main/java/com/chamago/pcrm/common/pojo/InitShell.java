package com.chamago.pcrm.common.pojo;

import com.chamago.pcrm.common.utils.C;


public class InitShell extends BasePojo {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String name;

    private int parentid;

    private String script;

    
    public InitShell(){
    	
    }
    
    public String replaceScript(String appkey,String sellernick){
    	return script.replace(C.CMD_REPLACE_VARIABLE_APPKEY, appkey).replace(C.CMD_REPLACE_VARIABLE_SELLERNICK, sellernick);
    }
    public String replaceScript(String appkey,String sellernick,String acookieday){
    	return replaceScript(appkey,sellernick).replace(C.CMD_REPLACE_VARIABLE_ACOOKIE_DAY, acookieday);
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script == null ? null : script.trim();
    }
}