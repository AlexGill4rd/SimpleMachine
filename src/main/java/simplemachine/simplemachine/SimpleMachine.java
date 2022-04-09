package simplemachine.simplemachine;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import simplemachine.simplemachine.Commands.MachineCommand;
import simplemachine.simplemachine.Components.Collector;
import simplemachine.simplemachine.Components.Conveyor;
import simplemachine.simplemachine.Components.ItemGenerator;
import simplemachine.simplemachine.Components.Machine;
import simplemachine.simplemachine.Data.Configs;
import simplemachine.simplemachine.Listeners.BlockInteractListener;
import simplemachine.simplemachine.Listeners.BlockPlaceListener;

import java.util.ArrayList;
import java.util.HashMap;

import static simplemachine.simplemachine.Data.Configs.*;
import static simplemachine.simplemachine.Tools.Functies.*;

public final class SimpleMachine extends JavaPlugin {

    //Location is from the ItemGenerators location
    public static HashMap<Location, Machine> machineHashMap = new HashMap<>();

    @Override
    public void onEnable() {
        createCustomConfig1();
        createCustomConfig2();

        getServer().getPluginManager().registerEvents(new BlockInteractListener(), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);

        this.getCommand("machine").setExecutor(new MachineCommand());

        loadMachines();
    }

    @Override
    public void onDisable() {
        saveMachines();
    }
    private void saveMachines(){
        for (Machine machine : machineHashMap.values()){
            //SAVE ITEMGENERATOR
            String machineLocString = convertLocationToString(machine.getLocation());
            Configs.getCustomConfig2().set("Machines." + machineLocString + ".ItemGenerator.Items.Generate", machine.getItemGenerator().getGenerateItem());
            Configs.getCustomConfig2().set("Machines." + machineLocString + ".ItemGenerator.Items.Fuel", machine.getItemGenerator().getFuelItem());
            Configs.getCustomConfig2().set("Machines." + machineLocString + ".ItemGenerator.Settings.Fuellevel", machine.getItemGenerator().getFuelLevel());
            Configs.getCustomConfig2().set("Machines." + machineLocString + ".ItemGenerator.Settings.ProductionSpeed", machine.getItemGenerator().getProductionSpeed());
            Configs.getCustomConfig2().set("Machines." + machineLocString + ".ItemGenerator.Settings.Enabled", machine.getItemGenerator().isEnabled());
            Configs.getCustomConfig2().set("Machines." + machineLocString + ".ItemGenerator.Settings.Location", convertLocationToString(machine.getItemGenerator().getLocation()));
            Configs.getCustomConfig2().set("Machines." + machineLocString + ".ItemGenerator.Statistics.FuelUsed", machine.getItemGenerator().getStatFuelUsed());
            Configs.getCustomConfig2().set("Machines." + machineLocString + ".ItemGenerator.Statistics.ItemsProduced", machine.getItemGenerator().getStatItemsProduced());
            Configs.getCustomConfig2().set("Machines." + machineLocString + ".ItemGenerator.Statistics.Age", machine.getItemGenerator().getStatItemGeneratorAge());

            //SAVE COLLECTORS
            if (machine.getCollector().getLocation() != null)
                Configs.getCustomConfig2().set("Machines." + machineLocString + ".Collector.Location", convertLocationToString(machine.getCollector().getLocation()));
            Configs.getCustomConfig2().set("Machines." + machineLocString + ".Collector.Storage", machine.getCollector().getStorage());

            //SAVE CONVEYORS
            ArrayList<String> locations = new ArrayList<>();
            for (Conveyor conveyor : machine.getConveyor())
                locations.add(convertLocationToString(conveyor.getLocation()));
            Configs.getCustomConfig2().set("Machines." + machineLocString + ".Conveyors.Locations", locations);
            saveData();
        }
    }
    private void loadMachines(){
        if (!Configs.getCustomConfig2().contains("Machines"))return;
        for (String machineLocation : Configs.getCustomConfig2().getConfigurationSection("Machines").getKeys(false)){
            Machine machine = new Machine();

            ItemStack generateItem = Configs.getCustomConfig2().getItemStack("Machines." + machineLocation + ".ItemGenerator.Items.Generate");
            ItemStack fuelItem = Configs.getCustomConfig2().getItemStack("Machines." + machineLocation + ".ItemGenerator.Items.Fuel");
            float fuelLevel = Configs.getCustomConfig2().getLong("Machines." + machineLocation + ".ItemGenerator.Settings.Fuellevel");
            float productionSpeed = Configs.getCustomConfig2().getLong("Machines." + machineLocation + ".ItemGenerator.Settings.ProductionSpeed");
            boolean enabled = Configs.getCustomConfig2().getBoolean("Machines." + machineLocation + ".ItemGenerator.Settings.Enabled");
            Location location = convertStringToLocation(Configs.getCustomConfig2().getString("Machines." + machineLocation + ".ItemGenerator.Settings.Location"));
            float statFuelUsed = Configs.getCustomConfig2().getLong("Machines." + machineLocation + ".ItemGenerator.Statistics.FuelUsed");
            float statItemsProduced = Configs.getCustomConfig2().getLong("Machines." + machineLocation + ".ItemGenerator.Statistics.ItemsProduced");
            long statItemGeneratorAge = Configs.getCustomConfig2().getLong("Machines." + machineLocation + ".ItemGenerator.Statistics.Age");
            ItemGenerator itemGenerator = new ItemGenerator(generateItem, fuelItem, fuelLevel, productionSpeed, enabled, location, statFuelUsed, statItemsProduced, statItemGeneratorAge);
            machine.setItemGenerator(itemGenerator);

            Collector collector = new Collector();
            if (Configs.getCustomConfig2().getString("Machines." + machineLocation + ".Collector.Location") != null)
                collector.setLocation(convertStringToLocation(Configs.getCustomConfig2().getString("Machines." + machineLocation + ".Collector.Location")));
            ArrayList<ItemStack> storageItems = (ArrayList<ItemStack>) Configs.getCustomConfig2().get("Machines." + machineLocation + ".Collector.Storage");
            collector.setStorage(storageItems);
            machine.setCollector(collector);

            ArrayList<Conveyor> conveyors = new ArrayList<>();
            for (String locString : Configs.getCustomConfig2().getStringList("Machines." + machineLocation + ".Conveyors.Locations")){
                conveyors.add(new Conveyor(convertStringToLocation(locString)));
            }
            machine.setConveyor(conveyors);

            machineHashMap.put(convertStringToLocation(machineLocation), machine);
        }
    }
}
