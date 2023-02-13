package model;

public class Episode {
    private int duration;
    private String name;
    private String url;
    private String summary;

    public Episode(int duration, String name, String url, String summary) {
        this.duration = duration;
        this.name = name;
        this.url = url;
        this.summary = summary;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int calculateEndTime(int startTime) {
        return startTime + this.duration;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
