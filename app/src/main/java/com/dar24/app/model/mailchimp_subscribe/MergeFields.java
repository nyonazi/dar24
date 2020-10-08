package com.dar24.app.model.mailchimp_subscribe;

import com.google.gson.annotations.SerializedName;

public class MergeFields{

	@SerializedName("LNAME")
	private String lNAME;

	@SerializedName("ADDRESS")
	private String aDDRESS;

	@SerializedName("PHONE")
	private String pHONE;

	@SerializedName("BIRTHDAY")
	private String bIRTHDAY;

	@SerializedName("FNAME")
	private String fNAME;

	public MergeFields(String lNAME, String aDDRESS, String pHONE, String bIRTHDAY, String fNAME) {
		this.lNAME = lNAME;
		this.aDDRESS = aDDRESS;
		this.pHONE = pHONE;
		this.bIRTHDAY = bIRTHDAY;
		this.fNAME = fNAME;
	}

	public void setLNAME(String lNAME){
		this.lNAME = lNAME;
	}

	public String getLNAME(){
		return lNAME;
	}

	public void setADDRESS(String aDDRESS){
		this.aDDRESS = aDDRESS;
	}

	public String getADDRESS(){
		return aDDRESS;
	}

	public void setPHONE(String pHONE){
		this.pHONE = pHONE;
	}

	public String getPHONE(){
		return pHONE;
	}

	public void setBIRTHDAY(String bIRTHDAY){
		this.bIRTHDAY = bIRTHDAY;
	}

	public String getBIRTHDAY(){
		return bIRTHDAY;
	}

	public void setFNAME(String fNAME){
		this.fNAME = fNAME;
	}

	public String getFNAME(){
		return fNAME;
	}

	@Override
 	public String toString(){
		return 
			"MergeFields{" + 
			"lNAME = '" + lNAME + '\'' + 
			",aDDRESS = '" + aDDRESS + '\'' + 
			",pHONE = '" + pHONE + '\'' + 
			",bIRTHDAY = '" + bIRTHDAY + '\'' + 
			",fNAME = '" + fNAME + '\'' + 
			"}";
		}
}