package Entites;

public abstract class Entity {
    protected String id; // Every book and member needs a unique ID

    public String getId() { return id; }
    public void setId(String id) throws Exception{ 
        this.id = id; 
    }
}
