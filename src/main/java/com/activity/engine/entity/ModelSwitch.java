package com.activity.engine.entity;

import com.activity.engine.control.EngineFunc;

/** 
* @author 作者 eGroupAI Team
* @date 2018年8月12日 下午10:47:59 
* @version 
* @description:
*/
/** 
* @author 作者 eGroupAI Team
* @date 2018年8月12日 下午10:47:59 
* @version 
* @description:
*/
public class ModelSwitch extends EngineFunc{	
	private String newModelPath;
	private String nowModelPath;
	private String switchFilePath;
	private String modelSwitchLogPath;
	private String enginePath;
	
	public String getNewModelPath() {
		return newModelPath;
	}
	public void setNewModelPath(String newModelPath) {
		this.newModelPath = newModelPath;
	}	
	public String getNowModelPath() {
		return nowModelPath;
	}
	public void setNowModelPath(String nowModelPath) {
		this.nowModelPath = nowModelPath;
	}
	public String getSwitchFilePath() {
		return switchFilePath;
	}
	public void setSwitchFilePath(String switchFilePath) {
		this.switchFilePath = switchFilePath;
	}	
	public String getModelSwitchLogPath() {
		return modelSwitchLogPath;
	}
	public void setModelSwitchLogPath(String modelSwitchLogPath) {
		this.modelSwitchLogPath = modelSwitchLogPath;
	}
	public String getEnginePath() {
		return enginePath;
	}
	public void setEnginePath(String enginePath) {
		this.enginePath = enginePath;
	}
	
	
}