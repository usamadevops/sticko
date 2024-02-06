package sticko.app.Models;

public class Top_50_model {
    private String id;
    private String username;
    private String profile_picture_path;
    private String score;

    public Top_50_model(String id, String username, String profile_picture_path, String score) {
        this.id = id;
        this.username = username;
        this.profile_picture_path = profile_picture_path;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfile_picture_path() {
        return profile_picture_path;
    }

    public void setProfile_picture_path(String profile_picture_path) {
        this.profile_picture_path = profile_picture_path;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
