package simplemachine.simplemachine.Components;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

import static simplemachine.simplemachine.SimpleMachine.machineHashMap;
import static simplemachine.simplemachine.Tools.Functies.*;

public class Collector {

    private Location location;
    private ArrayList<ItemStack> storage = new ArrayList<>();
    private Machine machine;
    private ItemStack blockItemstack = createItemstack(Material.OBSERVER, "§7§l* §6Item §eCollector §7§l*", createLore("§7§l§m------", "§7Place this item at the end of your conveyor belt to collect the items.", "", "§6Machine Data: §4INVALID COLLECTOR", "§7§l§m------"));

    private boolean valid = true;
    private int statItemsCollected = 0;

    public Collector(){}
    public Collector(Location location){
        this.location = location;
        for (Machine machine : machineHashMap.values()){
            if (compareLocations(machine.getCollector().getLocation(), location)){
                this.machine = machine;
                valid = compareLocations(machine.getCollector().getLocation(), location);
            }
        }
    }

    public boolean isValid() {
        return valid;
    }
    public void setValid(boolean valid) {
        this.valid = valid;
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
        addStatItemsCollected(1);
    }

    public Machine getMachine() {
        return machine;
    }
    public void setMachine(Machine machine) {
        this.machine = machine;
        blockItemstack = createItemstack(Material.OBSERVER, "§7§l* §6Item §eCollector §7§l*", createLore("§7§l§m------", "§7Place this item at the end of your conveyor belt to collect the items.", "", "§6Machine Data: §f" + convertLocationToString(machine.getLocation()), "§7§l§m------"));
    }

    public ItemStack getBlockItemstack() {
        return blockItemstack;
    }
    public void setBlockItemstack(ItemStack blockItemstack) {
        this.blockItemstack = blockItemstack;
    }

    public int getStatItemsCollected() {
        return statItemsCollected;
    }
    public void addStatItemsCollected(int amount) {
        this.statItemsCollected += amount;
    }
    public void setStatItemsCollected(int statItemsCollected) {
        this.statItemsCollected = statItemsCollected;
    }
}
