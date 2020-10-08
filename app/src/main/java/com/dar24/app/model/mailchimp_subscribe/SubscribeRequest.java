package com.dar24.app.model.mailchimp_subscribe;

import com.google.gson.annotations.SerializedName;

/**
 * @author Simon Mawole
 * @version 0.1.0
 * @email simonmawole2011@gmail.com
 * @since 0.1.0
 */
public class SubscribeRequest {

    @SerializedName("email_address")
    String emailAddress;
    @SerializedName("status")
    String status;
    @SerializedName("merge_fields")
    MergeFields mergeFields;

    SubscribeRequest(){
        //Empty
    }

    public SubscribeRequest(String emailAddress, String status, MergeFields mergeFields) {
        this.emailAddress = emailAddress;
        this.status = status;
        this.mergeFields = mergeFields;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MergeFields getMergeFields() {
        return mergeFields;
    }

    public void setMergeFields(MergeFields mergeFields) {
        this.mergeFields = mergeFields;
    }
}
