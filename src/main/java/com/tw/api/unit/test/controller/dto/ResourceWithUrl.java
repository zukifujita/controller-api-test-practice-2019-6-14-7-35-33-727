package com.tw.api.unit.test.controller.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

//  import javax.xml.bind.annotation.XmlAnyElement;

public class ResourceWithUrl<T> {
    private T content;
    private String url;

    public ResourceWithUrl(T content, String url) {
        this.content = content;
        this.url = url;
    }

    public ResourceWithUrl(T content) {
        this(content, "");
    }

    @JsonUnwrapped
//    @XmlAnyElement
    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
