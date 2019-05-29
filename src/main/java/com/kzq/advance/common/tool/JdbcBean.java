package com.kzq.advance.common.tool;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("spring.datasource")
public class JdbcBean {
    private String ip;
    private String port;
    private String db;
    private String username;
    private String password;
    private String url;

    public void setUrl(String url) {
        this.url = url;
//        String dataBaseUrl = "jdbc:mysql://127.0.0.1:3306/jxc?characterEncoding=utf8&useUnicode=true&useSSL=false&serverTimezone=Hongkong";
        String ip = url.substring(url.indexOf("//") + 2, url.lastIndexOf(":"));
        String dataBase = url.substring(url.lastIndexOf("/")+1,url.indexOf("?"));
        String port = url.substring(url.lastIndexOf(":")+1,url.lastIndexOf("/"));
        setDb(dataBase);
        setPort(port);
        setIp(ip);
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "JdbcBean{" +
                "ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", db='" + db + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
