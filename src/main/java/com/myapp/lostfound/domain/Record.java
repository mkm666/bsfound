package com.myapp.lostfound.domain;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "record")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column(length = 64)
    private int lostUserId;

    @Column(length = 64)
    private String title;

    @Column
    private String content;

    //若为1表示是失物招领，若为2表示寻物启事
    @Column(length = 1)
    private int typeCode;

    //若为0表示展示，若为1表示记录已完成，若为2记录过期
    @Column(length = 1)
    private int status;

    @Column
    private String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLostUserId() {
        return lostUserId;
    }

    public void setLostUserId(int lostUserId) {
        this.lostUserId = lostUserId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(int typeCode) {
        this.typeCode = typeCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", lostUserId=" + lostUserId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", typeCode=" + typeCode +
                ", status=" + status +
                ", time='" + time + '\'' +
                '}';
    }
}
