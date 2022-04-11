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
        }else if (e.getBlockPlaced().getType() == collectorMaterial){
            if (hasPerm(player, "SMachine.collector.place")){
                Location machineLocation = convertStringToLocation(player.getInventory().getItemInMainHand().getItemMeta().getLore().get(6));
                Machine machine = new Machine(machineLocation);
                System.out.println(machineLocation);
                if (machine.isValid()){
                    if (compareItemstack(machine.getCollector().getBlockItemstack(), player.getInventory().getItemInMainHand())){
                        machine.getCollector().setLocation(e.getBlockPlaced().getLocation());
                        player.sendMessage(getMessage("Item Generator Placed"));
                    }
                }else player.sendMessage(getMessage("Invalid Machine"));
            }
        }

    }

}
