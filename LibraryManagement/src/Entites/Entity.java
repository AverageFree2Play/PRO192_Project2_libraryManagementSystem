package Entites;

public abstract class Entity {
    private String id;

    public Entity(String id) {
        this.id = id;
    }
    
    public String getId() { return this.id; }
    public void setId(String id) throws Exception{ 
        this.id = id; 
    }
}
