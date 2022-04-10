package simplemachine.simplemachine.Components;

import org.bukkit.Location;

import java.util.ArrayList;

import static simplemachine.simplemachine.SimpleMachine.machineHashMap;

public class Machine {

    private ItemGenerator itemGenerator = new ItemGenerator();
    private Collector collector = new Collector();
    private Location location;
    private boolean valid = false;

    public Machine() {}
    public Machine(Location location){
        this.location = location;
        checkValid();
    }


    public boolean isValid() {
        return this.valid;
    }
    public void setValid(boolean valid) {
        this.valid = valid;
    }
    public void checkValid(){
        valid = machineHashMap.containsKey(location);
        if (valid){
            this.itemGenerator = machineHashMap.get(location).getItemGenerator();
            this.collector = machineHashMap.get(location).getCollector();
            this.itemGenerator.setMachine(this);
            this.collector.setMachine(this);
        }
    }

    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    public ItemGenerator getItemGenerator() {
        return itemGenerator;
    }
    public void setItemGenerator(ItemGenerator itemGenerator) {
        this.itemGenerator = itemGenerator;
    }

    public Collector getCollector() {
        return collector;
    }
    public void setCollector(Collector collector) {
        this.collector = collector;
    }
}
