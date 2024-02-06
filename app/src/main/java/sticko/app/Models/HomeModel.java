package sticko.app.Models;

public class HomeModel {
    private int id;
    private int user_id;
    private String social_media_id;
    private String link;
    private String visible;
    private String prefix;
    private String logo_path;
    private String name;

    public HomeModel(int id, int user_id, String social_media_id, String link, String visible, String prefix, String logo_path, String name) {
        this.id = id;
        this.user_id = user_id;
        this.social_media_id = social_media_id;
        this.link = link;
        this.visible = visible;
        this.prefix = prefix;
        this.logo_path = logo_path;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getSocial_media_id() {
        return social_media_id;
    }

    public void setSocial_media_id(String social_media_id) {
        this.social_media_id = social_media_id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getLogo_path() {
        return logo_path;
    }

    public void setLogo_path(String logo_path) {
        this.logo_path = logo_path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
