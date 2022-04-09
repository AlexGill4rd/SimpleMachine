package simplemachine.simplemachine.Components;

import org.bukkit.Location;

public class Conveyor {

    private Location location;
    private Machine machine;

    public Conveyor(Location location){
        this.location = location;
    }

    public Machine getMachine() {
        return machine;
    }
    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
}
