import DataObjects.FileManager;

public abstract class BaseManagement {
    // Common methods or properties all managers might use
    protected FileManager fileManager = new FileManager();

    public abstract void init();
    public abstract void add();
    public abstract void update();
    public abstract void displayAll();
}