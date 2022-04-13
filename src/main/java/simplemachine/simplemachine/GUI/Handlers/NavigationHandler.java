package simplemachine.simplemachine.GUI.Handlers;

import org.bukkit.entity.Player;
import simplemachine.simplemachine.Components.Machine;

public class NavigationHandler {

    private Machine machine;
    private Player player;

    public NavigationHandler(Player player, Machine machine){
        this.machine = machine;
        this.player = player;
    }

    public Machine getMachine() {
        return machine;
    }
    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
}
