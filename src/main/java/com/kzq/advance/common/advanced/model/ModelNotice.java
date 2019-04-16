package com.kzq.advance.common.advanced.model;

import java.util.Map;

public class ModelNotice {
    private String touser; //用户的openId
    private String template_id; //模板消息id
    private String url;
    private String topcolor; //标题颜色
    private Map<String, TemplateData> data; //消息具体内容

    public String getTouser() {
        return touser;
    }
    public void setTouser(String touser) {
        this.touser = touser;
    }
    public String getTemplate_id() {
        return template_id;
    }
    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getTopcolor() {
        return topcolor;
    }
    public void setTopcolor(String topcolor) {
        this.topcolor = topcolor;
    }
    public  Map<String, TemplateData> getData() {
        return data;
    }
    public void setData( Map<String, TemplateData> data) {
        this.data = data;
    }

}
