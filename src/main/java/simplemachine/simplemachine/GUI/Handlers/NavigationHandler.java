package simplemachine.simplemachine.GUI.Handlers;

import org.bukkit.entity.Player;
import simplemachine.simplemachine.Components.Machine;

import java.util.HashMap;

public class NavigationHandler {

    private HashMap<Player, Machine> machineHandler = new HashMap<>();

    public HashMap<Player, Machine> getMachineHandler() {
        return machineHandler;
    }
    public void addMachineHandle(Player player, Machine machine){this.machineHandler.put(player, machine);}
    public void setMachineHandler(HashMap<Player, Machine> machineHandler) {
        this.machineHandler = machineHandler;
    }
}
