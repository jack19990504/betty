package com.activity.util;

import java.net.HttpURLConnection;
import java.util.Collection;

public class WebResponse {

	public WebResponse() {
		// TODO Auto-generated constructor stub
	}
	private Object data;
	private Integer statusCode;
	private Error error = new Error();
	// 2XX
	private static Integer HTTP_OK = HttpURLConnection.HTTP_OK; // 200
	private static Integer HTTP_NO_CONTENT = HttpURLConnection.HTTP_NO_CONTENT; // 204
	private static Integer HTTP_ACCEPTED = HttpURLConnection.HTTP_ACCEPTED; // 202
	// 4XX
	private static Integer HTTP_BAD_REQUEST = HttpURLConnection.HTTP_BAD_REQUEST; // 400
	private static Integer HTTP_UNAUTHORIZED = HttpURLConnection.HTTP_UNAUTHORIZED; // 401
	private static Integer HTTP_FORBIDDEN = HttpURLConnection.HTTP_FORBIDDEN; // 403
	private static Integer HTTP_NOT_FOUND = HttpURLConnection.HTTP_NOT_FOUND; // 404
	private static Integer HTTP_CONFLICT = HttpURLConnection.HTTP_CONFLICT; // 409
	private static Integer HTTP_UNPROCESSABLE_ENTITY = 422; // 422
	private static Integer HTTP_GATEWAY_TIMEOUT = HttpURLConnection.HTTP_GATEWAY_TIMEOUT; // 504
	
	public void OK() {
		this.statusCode = HTTP_OK;
	}
	
	public void BAD_REQUEST() {
		this.statusCode = HTTP_BAD_REQUEST;
	}
	
	public void UNAUTHORIZED() {
		this.statusCode = HTTP_UNAUTHORIZED;
	}
	public void NOT_FOUND() {
		this.statusCode = HTTP_NOT_FOUND;
		errorMessageGenerate("NOT_FOUND");
	}
	public void UNPROCESSABLE_ENTITY() {
		this.statusCode = HTTP_UNPROCESSABLE_ENTITY;
		errorMessageGenerate("UNPROCESSABLE_ENTITY");
	}
	
	public void NO_CONTENT() {
		this.statusCode = HTTP_NO_CONTENT;
		errorMessageGenerate("NO_CONTENT");
	}
	
	public void GATEWAY_TIMEOUT(){
		this.statusCode = HTTP_GATEWAY_TIMEOUT;		
		errorMessageGenerate("GATEWAY_TIMEOUT");
	}
	private void errorMessageGenerate(String httpErrorName){
		if(!(this.error.getMessage()==null) ){
			this.error.setMessage(httpErrorName+" - "+ this.error.getMessage());
		}else{
			this.error.setMessage(httpErrorName+" - FORBIDDEN");
		}
	}
	public Integer getStatusCode() {
		return statusCode;
	}
	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}
	
	public Object getData() {
		if(data == null && statusCode != 200){
			data = error;
		}		
		return data;
	}


	public void setData(Object data) {
		if (data instanceof Collection) {
			Collection<?> dataList = (Collection<?>) data;
			System.out.println("Collection="+dataList.size());
			if (dataList.size() == 0){
				NO_CONTENT();
			}
		} else if (data instanceof Result) {
			if (((Result) data).getTotal() == 0){
				NO_CONTENT();
			}else{
				OK();
			}
		} else if(data == null){
			NO_CONTENT();
		}
		this.data = data;
	}

}
