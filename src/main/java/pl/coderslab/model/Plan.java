package pl.coderslab.model;

import java.time.LocalDateTime;

public class Plan {

    private int id;
    private String name;
    private String description;
    private String created;
    private int adminId;

    @Override
    public String toString() {
        return "Book [id=" + id + ", name=" + name + ", description=" + description + ", created=" + created + ", adminId=" + adminId + "]";
    }

    public Plan() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public Plan(String name, String description, String created, int adminId) {
        this.name = name;
        this.description = description;
        this.created = created;
        this.adminId = adminId;
    }

    public void setName(String name) { this.name = name; }

    public void setDescription(String description) { this.description = description; }

    public String getName() { return name; }

    public String getDescription() { return description; }
}
