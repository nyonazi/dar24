package com.dar24.app.model.mailchimp_subscribe;

import com.google.gson.annotations.SerializedName;

public class LinksItem{

	@SerializedName("targetSchema")
	private String targetSchema;

	@SerializedName("method")
	private String method;

	@SerializedName("rel")
	private String rel;

	@SerializedName("href")
	private String href;

	@SerializedName("schema")
	private String schema;

	public void setTargetSchema(String targetSchema){
		this.targetSchema = targetSchema;
	}

	public String getTargetSchema(){
		return targetSchema;
	}

	public void setMethod(String method){
		this.method = method;
	}

	public String getMethod(){
		return method;
	}

	public void setRel(String rel){
		this.rel = rel;
	}

	public String getRel(){
		return rel;
	}

	public void setHref(String href){
		this.href = href;
	}

	public String getHref(){
		return href;
	}

	public void setSchema(String schema){
		this.schema = schema;
	}

	public String getSchema(){
		return schema;
	}

	@Override
 	public String toString(){
		return 
			"LinksItem{" + 
			"targetSchema = '" + targetSchema + '\'' + 
			",method = '" + method + '\'' + 
			",rel = '" + rel + '\'' + 
			",href = '" + href + '\'' + 
			",schema = '" + schema + '\'' + 
			"}";
		}
}