package sticko.app.Models;

public class ListOfConnections_model {
    private  int id;
    public String name;
    private String logo_path;
    private String prefix;
    private String placeholder;

    public ListOfConnections_model(int id, String name, String logo_path, String prefix, String placeholder) {
        this.id = id;
        this.name = name;
        this.logo_path = logo_path;
        this.prefix = prefix;
        this.placeholder = placeholder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo_path() {
        return logo_path;
    }

    public void setLogo_path(String logo_path) {
        this.logo_path = logo_path;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }
}
