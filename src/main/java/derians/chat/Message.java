package derians.chat;

import java.util.Date;

/**
 * Created by Ivan Chaykin
 * Date: 27.12.2018
 * Time: 15:19
 */
public class Message {

    private String type;
    private String text;
    private String sender;
    private Date date;

    public Message() {
        this.date = new Date();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
