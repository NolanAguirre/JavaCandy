import java.util.*;
public class Bag{
    private ArrayList<Items> contents;
    private Weapon equiped;
    public Bag(){
        contents = new ArrayList<Items>();
        equiped = null;
    }
    public void use(Items item){
        contents.get(contents.indexOf(item)).use();
    }
    public void add(Items item){
        contents.add(item);
    }
    public void remove(Items item){
        contents.remove(contents.indexOf(item));
    }
    public void equip(Weapon weapon){
        equiped = weapon;
    }
    public Weapon getEquiped(){
        return equiped;
    }
    public ArrayList<Items> getContents(){
        return contents;
    }
}
