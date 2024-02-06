package sticko.app.Models;

public class Following_model {
    private int id;
    private String username;
    private String email;
    private String name;
    private String telephone;
    private String bio;
    private String dob;
    private String address;
    private String score;
    private String role;
    private String provider;
    private int provider_id;
    private String profile_picture_path;

    public Following_model(int id, String username, String email, String name, String telephone, String bio, String dob, String address, String score, String role, String provider, int provider_id, String profile_picture_path) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = name;
        this.telephone = telephone;
        this.bio = bio;
        this.dob = dob;
        this.address = address;
        this.score = score;
        this.role = role;
        this.provider = provider;
        this.provider_id = provider_id;
        this.profile_picture_path = profile_picture_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public int getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(int provider_id) {
        this.provider_id = provider_id;
    }



    public String getProfile_picture_path() {
        return profile_picture_path;
    }

    public void setProfile_picture_path(String profile_picture_path) {
        this.profile_picture_path = profile_picture_path;
    }
}
