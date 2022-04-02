package com.yxm.vo;

import java.io.Serializable;

public class JsonMsg implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5863100019332084445L;

	private Boolean state;//状态
	private int code;
	private String msg;
	private Object data;

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
