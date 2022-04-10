package simplemachine.simplemachine.Components;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import simplemachine.simplemachine.SimpleMachine;

import java.util.ArrayList;

import static simplemachine.simplemachine.Materials.Materials.*;
import static simplemachine.simplemachine.Tools.Functies.*;

public class ItemGenerator {

    private final ItemStack itemGeneratorItemstack = createItemstack(itemGeneratorMaterial, "§7§l* §6Item Generator §7§l*", createArraylist("§8§l§m------", "§7Place this item to create a §6ITEM GENERATOR", "§fRight-Click §8- §7To place down", "§8§l§m------"));

    private ItemStack product = createItemstack(Material.BARRIER, "§7none", null);
    private ItemStack fuelItem = defaultMachineFuel;
    private float fuelLevel = 100;
    private float itemsPerHour = 3600;
    private boolean enabled = false;

    private ArrayList<GenerateItem> itemsActive = new ArrayList<>();
    private Location location;

    //STATS
    private float statFuelUsed = 0;
    private float statItemsProduced = 0;
    private long statItemGeneratorAge = System.currentTimeMillis();

    private final SimpleMachine plugin = SimpleMachine.getPlugin(SimpleMachine.class);
    private Machine machine;

    public ItemGenerator(){}

    public ItemGenerator(ItemStack generateItem, ItemStack fuelItem, float fuelLevel, float itemsPerHour, boolean enabled, Location location, float statFuelUsed, float statItemsProduced, long statItemGeneratorAge){
        this.product = generateItem;
        this.fuelItem = fuelItem;
        this.fuelLevel = fuelLevel;
        this.itemsPerHour = itemsPerHour;
        this.enabled = enabled;
        this.location = location;
        this.statFuelUsed = statFuelUsed;
        this.statItemsProduced = statItemsProduced;
        this.statItemGeneratorAge = statItemGeneratorAge;
    }
    public Machine getMachine() {
        return machine;
    }
    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public ItemStack getItemGeneratorItemstack() {
        return itemGeneratorItemstack;
    }

    public ItemStack getProduct() {
        return product;
    }
    public void setProduct(ItemStack product) {
        this.product = product;
    }

    public ItemStack getFuelItem() {
        return fuelItem;
    }
    public void setFuelItem(ItemStack fuelItem) {
        this.fuelItem = fuelItem;
    }

    public float getFuelLevel() {
        return fuelLevel;
    }
    public void setFuelLevel(float fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public float getItemsPerHour() {
        return itemsPerHour;
    }
    public void setItemsPerHour(float itemsPerHour) {
        this.itemsPerHour = itemsPerHour;
    }

    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public float getStatFuelUsed() {
        return statFuelUsed;
    }
    public void setStatFuelUsed(float statFuelUsed) {
        this.statFuelUsed = statFuelUsed;
    }

    public float getStatItemsProduced() {
        return statItemsProduced;
    }
    public void setStatItemsProduced(long statItemsProduced) {
        this.statItemsProduced = statItemsProduced;
    }
    public void addStatItemsProduced(long amount) {
        this.statItemsProduced += amount;
    }

    public long getStatItemGeneratorAge() {
        return statItemGeneratorAge;
    }

    public ArrayList<GenerateItem> getItemsActive() {
        return itemsActive;
    }
    public void setItemsActive(ArrayList<GenerateItem> itemsActive) {
        this.itemsActive = itemsActive;
    }

    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    private int producing;
    public void start() {
        enabled = true;
        if (isReady()){
            producing = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                if (!enabled) {
                    Bukkit.getScheduler().cancelTask(producing);
                }else generateItem();
            }, 0, (long) (itemsPerHour / 60 / 60) * 20);
        }
    }
    public void stop(){
        enabled = false;
    }
    void generateItem() {
        GenerateItem generateItem = new GenerateItem(this.product, this);
        generateItem.setMachine(machine);
        generateItem.create();
        generateItem.startRouting();
        itemsActive.add(generateItem);
    }
    public boolean isReady() {
        return product != null &&
                fuelItem != null &&
                fuelLevel != 0 &&
                itemsPerHour != 0 &&
                enabled;
    }
}
