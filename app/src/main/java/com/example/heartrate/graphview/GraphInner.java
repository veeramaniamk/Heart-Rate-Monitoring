package com.example.heartrate.graphview;

public class GraphInner {
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getOriginal_date() {
        return original_date;
    }

    public void setOriginal_date(String original_date) {
        this.original_date = original_date;
    }

    public String getDate_only() {
        return date_only;
    }

    public void setDate_only(String date_only) {
        this.date_only = date_only;
    }

    public String getTime_only() {
        return time_only;
    }

    public void setTime_only(String time_only) {
        this.time_only = time_only;
    }

    private String pid;
    private String level;
    private String original_date;
    private String date_only;
    private String time_only;

    public String getPulse() {
        return pulse;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    private String pulse;

}
