package com.skyedu.model.book;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
/**
 * @author huangRunDong
 * @createTime 15:06 2019/9/6
 */
@Entity
@Table(name = "Tk_sendBookJsonRecord_t")
public class SendBookJsonRecordModel implements Serializable {
    private int sendBookJsonRecordId;
    private Date createTime;
    private String result;
    private String jsonCode;
    private int bookId;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    public int getSendBookJsonRecordId() {
        return sendBookJsonRecordId;
    }

    public void setSendBookJsonRecordId(int sendBookJsonRecordId) {
        this.sendBookJsonRecordId = sendBookJsonRecordId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getJsonCode() {
        return jsonCode;
    }

    public void setJsonCode(String jsonCode) {
        this.jsonCode = jsonCode;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
