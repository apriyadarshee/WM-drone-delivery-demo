
package com.walmartlabs.dronedelivery.wmdrone.domain;

import java.time.LocalTime;

public class OrderData {

    public enum Tag {
        PROMOTER, NUETRAL, DETRACTOR;
    }

    // Fields from the input file
    private String id;
    private String location;
    private String timeStampStr;

    // Field required for the output file
    private LocalTime launchTime;

    // fields required for calculation

    // time taken for the drone to reach to a customer location in minutes
    private Integer timeToLocation;
    // converted time format for calculation
    private LocalTime timeStamp;
    // Categories for customer satisfaction (promoter, nuetral, detractor)
    private Tag tag;

    public String getId() {
        return id;
    }

    public LocalTime getLaunchTime() {
        return launchTime;
    }

    public void setLaunchTime(LocalTime launchTime) {
        this.launchTime = launchTime;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Integer getTimeToLocation() {
        return timeToLocation;
    }

    public void setTimeToLocation(Integer timeToLocation) {
        this.timeToLocation = timeToLocation;
    }

    public LocalTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(final LocalTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTimeStampStr() {
        return timeStampStr;
    }

    public void setTimeStampStr(final String timeStampStr) {
        this.timeStampStr = timeStampStr;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(final String location) {
        this.location = location;
    }

    public String toString() {
        return (id + " " + timeToLocation + " " + timeStampStr + " " + tag + " " + launchTime);
    }
}