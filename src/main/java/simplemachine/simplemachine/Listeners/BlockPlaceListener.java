package simplemachine.simplemachine.Listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import simplemachine.simplemachine.Components.Collector;
import simplemachine.simplemachine.Components.ItemGenerator;
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
                Machine machine = Machine.getFromLocation(machineLocation);
                if (compareItemstack(machine.getItemGenerator().getItemGeneratorItemstack(),player.getInventory().getItemInMainHand())){
                    ItemGenerator itemGenerator = new ItemGenerator();
                    itemGenerator.setLocation(machineLocation);
                    itemGenerator.setMachine(machine);
                    machine.setItemGenerator(itemGenerator);
                    machineHashMap.put(machineLocation, machine);
                    player.sendMessage(getMessage("Item Generator Placed"));
                }
            }
        }else if (e.getBlockPlaced().getType() == collectorMaterial){
            if (hasPerm(player, "SMachine.collector.place")){
                Location machineLocation = convertStringToLocation(player.getInventory().getItemInMainHand().getItemMeta().getLore().get(6));
                Machine machine = Machine.getFromLocation(machineLocation);
                if (machine.isValid()){
                    if (compareItemstack(machine.getCollector().getBlockItemstack(), player.getInventory().getItemInMainHand())){
                        Collector collector = new Collector(e.getBlockPlaced().getLocation());
                        collector.setMachine(machine);
                        machine.setCollector(collector);
                        player.sendMessage(getMessage("Collector Placed"));
                    }
                }else player.sendMessage(getMessage("Invalid Machine"));
            }
        }

    }

}
