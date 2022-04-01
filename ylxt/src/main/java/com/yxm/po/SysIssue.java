package com.yxm.po;


import java.io.Serializable;
import java.util.Date;

public class SysIssue implements Serializable {
    private Integer id;
    private String content;
    private Date releaseDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "SysIssue{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }
}
