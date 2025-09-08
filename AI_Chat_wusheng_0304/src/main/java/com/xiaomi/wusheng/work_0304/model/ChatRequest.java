package com.xiaomi.wusheng.work_0304.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatRequest {
    private String model;
    private List<Message> messages;
    private double temperature;
    private double top_p;
    private Boolean do_sample;
    private Boolean stream;

    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public List<Message> getMessages() {
        return messages;
    }
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
    public double getTemperature() {
        return temperature;
    }
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
    public double getTop_p() {
        return top_p;
    }
    public void setTop_p(double top_p) {
        this.top_p = top_p;
    }
    public Boolean getDo_sample() {
        return do_sample;
    }
    public void setDo_sample(Boolean do_sample) {
        this.do_sample = do_sample;
    }
    public Boolean getStream() {
        return stream;
    }
    public void setStream(Boolean stream) {
        this.stream = stream;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Message {
        private String role;
        private String content;

        public String getRole() {
            return role;
        }
        public void setRole(String role) {
            this.role = role;
        }
        public String getContent() {
            return content;
        }
        public void setContent(String content) {
            this.content = content;
        }
    }
}
