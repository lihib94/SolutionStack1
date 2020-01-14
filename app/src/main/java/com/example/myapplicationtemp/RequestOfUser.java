package com.example.myapplicationtemp;

public class RequestOfUser {

    String userId;
    String RequestText;
    long creationTime;

    public RequestOfUser(String uID,String RequestTxt,long cTime) {

        this.userId = uID;
        this.RequestText = RequestTxt;
        this.creationTime=cTime;

    }
    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "RequestOfUser{" +
                "userId='" + userId + '\'' +
                ", RequestText='" + RequestText + '\'' +
                ", creationTime=" + creationTime +
                '}';
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRequestText() {
        return RequestText;
    }

    public void setRequestText(String requestText) {
        RequestText = requestText;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }
}