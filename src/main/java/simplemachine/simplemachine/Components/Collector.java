package simplemachine.simplemachine.Components;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Collector {

    private Location location;
    private ArrayList<ItemStack> storage = new ArrayList<>();
    private Machine machine;

    public Collector(){}
    public Collector(Location location){
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    public ArrayList<ItemStack> getStorage() {
        return storage;
    }
    public void setStorage(ArrayList<ItemStack> storage) {
        this.storage = storage;
    }
    public void addStorageItem(ItemStack itemStack) {
        this.storage.add(itemStack);
    }

    public Machine getMachine() {
        return machine;
    }
    public void setMachine(Machine machine) {
        this.machine = machine;
    }
}
