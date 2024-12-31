package com.ELOUSTA.ELOUSTA.backend.dto.requestDto;

import lombok.Data;
import java.util.Date;

@Data
public class RequestDto {
    private int userid;
    private int techid;
    private String description;
    private String location;
    private String state;
    private Date startdate;
    private Date enddate;

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setTechid(int techid) {
        this.techid = techid;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public int getUserid() {
        return userid;
    }

    public int getTechid() {
        return techid;
    }

    public Date getStartdate() {
        return startdate;
    }

    public Date getEnddate() {
        return enddate;
    }
}
