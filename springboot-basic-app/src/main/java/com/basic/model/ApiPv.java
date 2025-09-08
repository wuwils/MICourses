package com.basic.model;

import lombok.Data;

import java.util.Date;

@Data
public class ApiPv {
    public long id;
    public String uri;
    public long pv;
    public Date ctime;
    public Date utime;

    @Override
    public String toString() {
        return "ApiPv{" +
                "id=" + id +
                ", uri='" + uri + '\'' +
                ", pv=" + pv +
                ", ctime=" + ctime +
                ", utime=" + utime +
                '}';
    }
}
