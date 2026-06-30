package ManagementObject;
import java.util.ArrayList;

public interface BaseManagement<T> {
    void add();
    void update();
    void delete();
    ArrayList<T> get(); 
}