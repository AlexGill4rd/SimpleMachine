package simplemachine.simplemachine.Components;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import simplemachine.simplemachine.SimpleMachine;

import static simplemachine.simplemachine.Materials.Materials.*;
import static simplemachine.simplemachine.Tools.Functies.*;

public class ItemGenerator {

    private final ItemStack itemGeneratorItemstack = createItemstack(itemGeneratorMaterial, "§7§l* §6Item Generator §7§l*", createArraylist("§8§l§m------", "§7Place this item to create a §6ITEM GENERATOR", "§fRight-Click §8- §7To place down", "§8§l§m------"));

    private ItemStack generateItem = createItemstack(Material.BARRIER, "§7none", null);
    private ItemStack fuelItem = defaultMachineFuel;
    private float fuelLevel = 100;
    private float itemsPerHour = 3600;
    private boolean enabled = false;

    private Location location;

    //STATS
    private float statFuelUsed = 0;
    private float statItemsProduced = 0;
    private long statItemGeneratorAge = System.currentTimeMillis();

    private final SimpleMachine plugin = SimpleMachine.getPlugin(SimpleMachine.class);
    private Machine machine;

    public ItemGenerator(){}

    public ItemGenerator(ItemStack generateItem, ItemStack fuelItem, float fuelLevel, float itemsPerHour, boolean enabled, Location location, float statFuelUsed, float statItemsProduced, long statItemGeneratorAge){
        this.generateItem = generateItem;
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

    public ItemStack getGenerateItem() {
        return generateItem;
    }
    public void setGenerateItem(ItemStack generateItem) {
        this.generateItem = generateItem;
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
    public void setStatItemsProduced(float statItemsProduced) {
        this.statItemsProduced = statItemsProduced;
    }

    public long getStatItemGeneratorAge() {
        return statItemGeneratorAge;
    }


    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    public void startItemGenerator() {
        enabled = true;
        if (isReady()){
            Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                @Override
                public void run() {
                    generateItem();
                }
            }, (long) (20 * itemsPerHour), (long) (20 * itemsPerHour));
        }
    }
    void generateItem() {
        GenerateItem generateItem = new GenerateItem(this.generateItem, this);
        generateItem.setMachine(machine);
        generateItem.create();
        generateItem.startRouting();
    }
    public boolean isReady() {
        return generateItem != null &&
                fuelItem != null &&
                fuelLevel != 0 &&
                itemsPerHour != 0 &&
                enabled;
    }
}
