package simplemachine.simplemachine.Listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import simplemachine.simplemachine.Components.Machine;

import static simplemachine.simplemachine.Materials.Materials.*;
import static simplemachine.simplemachine.SimpleMachine.machineHashMap;
import static simplemachine.simplemachine.Tools.Functies.*;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onMachinePlace(BlockPlaceEvent e){

        Player player = e.getPlayer();
        if (e.getBlockPlaced().getType() == itemGeneratorMaterial){
            if (hasPerm(player, "SMachine.itemgenerator.place")){
                Location machineLocation = e.getBlockPlaced().getLocation();
                Machine machine = new Machine(machineLocation);
                if (compareItemstack(machine.getItemGenerator().getItemGeneratorItemstack(),player.getInventory().getItemInMainHand())){
                    machine.getItemGenerator().setLocation(machineLocation);
                    machineHashMap.put(machineLocation, machine);
                    player.sendMessage(getMessage("Item Generator Placed"));
                }
            }
        }

    }

}
