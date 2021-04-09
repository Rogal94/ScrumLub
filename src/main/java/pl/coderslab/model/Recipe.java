package pl.coderslab.model;

public class Recipe {
    private int id;
    private String name;
    private String ingredients;
    private String description;
    private String created;
    private String updated;
    private String preparationTime;
    private String preparation;
    private int adminId;

    @Override
    public String toString() {
        return "Recipe [id=" + id + ". name=" + name + ", ingredients=" + ingredients + ", description="
                + description + ", created=" + created + ", updated=" + updated + ", preparationTime="
                + preparationTime + ", preparation=" + preparation + ", adminId=" + adminId + "]";
    }

    public Recipe() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() { return updated; }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public Recipe(String name, String ingredients, String description,String created,String updated,
                  String preparationTime, String preparation, int adminId) {
        this.name = name;
        this.ingredients = ingredients;
        this.description = description;
        this.created = created;
        this.updated = updated;
        this.preparationTime = preparationTime;
        this.preparation = preparation;
        this.adminId = adminId;
    }

    public void setName(String name) { this.name = name; }

    public void setIngredients(String ingredients) { this.ingredients = ingredients; }

    public void setDescription(String description) { this.description = description; }

    public void setPreparationTime(String preparationTime) { this.preparationTime = preparationTime; }

    public void setPreparation(String preparation) { this.preparation = preparation; }

    public String getName() { return name; }

    public String getIngredients() { return ingredients; }

    public String getDescription() { return description; }

    public String getPreparationTime() { return preparationTime; }

    public String getPreparation() { return preparation; }
}
