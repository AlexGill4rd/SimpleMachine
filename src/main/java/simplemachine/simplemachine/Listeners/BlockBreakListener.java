package simplemachine.simplemachine.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import simplemachine.simplemachine.Components.Machine;

import static simplemachine.simplemachine.Materials.Materials.*;
import static simplemachine.simplemachine.Tools.Functies.getMessage;
import static simplemachine.simplemachine.Tools.Functies.hasPerm;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onMachineBreak(BlockBreakEvent e){

        Player player = e.getPlayer();
        if (e.getBlock().getType() == itemGeneratorMaterial){
            if (hasPerm(player, "SMachine.itemgenerator.break")){
                Machine machine = Machine.getFromLocation(e.getBlock().getLocation());
                if (machine.isValid()){
                    machine.remove();
                    player.sendMessage(getMessage("Item Generator Removed"));
                }
            }
        }else if (e.getBlock().getType() == collectorMaterial){
            if (hasPerm(player, "SMachine.collector.break")){
                Machine machine = Machine.getMachineFromCollectorLocation(e.getBlock().getLocation());
                if (machine.isValid()){
                    machine.getCollector().remove();
                    player.sendMessage(getMessage("Collector Removed"));
                }
            }
        }

    }

}
