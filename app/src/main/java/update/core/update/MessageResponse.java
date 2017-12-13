package update.core.update;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by subhashhardaha on 03/12/17.
 */

public class MessageResponse {
    @SerializedName("imei")
    private String imei;
    @SerializedName("messages")
    private List<Message> messages;

    public MessageResponse(String imei, List<Message> messages) {
        this.imei = imei;
        this.messages = messages;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
