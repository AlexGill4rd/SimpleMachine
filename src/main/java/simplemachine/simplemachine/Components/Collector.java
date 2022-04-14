package simplemachine.simplemachine.Components;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

import static simplemachine.simplemachine.SimpleMachine.machineHashMap;
import static simplemachine.simplemachine.Tools.Functies.*;

public class Collector {

    private Location location = null;
    private ArrayList<ItemStack> storage = new ArrayList<>();
    private Machine machine = null;
    private ItemStack blockItemstack;
    public final int maxCollectorSize = 27;

    private int statItemsCollected = 0;

    public Collector(){}
    public Collector(Machine machine){
        this.machine = machine;
    }
    public Collector(Location location){
        this.location = location;
    }

    public boolean isValid(){
        if (this.location != null){
            for (Machine machine : machineHashMap.values()){
                if (machine.getCollector().getLocation().equals(this.getLocation()))return true;
            }
        }
        return false;
    }

    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    public ArrayList<ItemStack> getStorage() {
        return getStackedItemstackList(this.storage);
    }
    public void setStorage(ArrayList<ItemStack> storage) {
        this.storage = storage;
    }
    public void addStorageItem(ItemStack itemStack) {
        this.storage.add(itemStack);
        addStatItemsCollected();
    }
    public void removeStorageItem(ItemStack itemStack) {
        this.storage.remove(itemStack);
    }
    public Machine getMachine() {
        return machine;
    }
    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public ItemStack getBlockItemstack() {
        blockItemstack = createItemstack(Material.OBSERVER, "§7§l* §6Item §eCollector §7§l*", createLore("§7§l§m------", "§7Place this item at the end of your conveyor belt to collect the items.", "", "§6Machine Data: §f" + convertLocationToString(machine.getLocation()), "§7§l§m------"));

        return blockItemstack;
    }
    public void setBlockItemstack(ItemStack blockItemstack) {
        this.blockItemstack = blockItemstack;
    }

    public int getStatItemsCollected() {
        return statItemsCollected;
    }
    public void addStatItemsCollected() {
        this.statItemsCollected++;
    }
    public void setStatItemsCollected(int statItemsCollected) {
        this.statItemsCollected = statItemsCollected;
    }

    public void remove(){
        this.getLocation().getBlock().setType(Material.AIR);
        this.getMachine().setCollector(new Collector(this.getMachine()));
    }

    public static Collector getFromLocation(Location location){
        for (Machine machine : machineHashMap.values()){
            if (compareLocations(machine.getCollector().getLocation(), location))return machine.getCollector();
        }
        return new Collector(location);
    }
}
