package com.myspring.security.mysecuritydemo.domain;

public class Msg {
    private String content;
    private String etraInfo;
    private String title;

    public Msg(String content, String etraInfo, String title) {
        this.content = content;
        this.etraInfo = etraInfo;
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEtraInfo() {
        return etraInfo;
    }

    public void setEtraInfo(String etraInfo) {
        this.etraInfo = etraInfo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
