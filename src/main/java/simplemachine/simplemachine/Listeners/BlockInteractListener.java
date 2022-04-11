package simplemachine.simplemachine.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import simplemachine.simplemachine.Components.Collector;
import simplemachine.simplemachine.Components.Machine;
import simplemachine.simplemachine.GUI.CollectorInventory;
import simplemachine.simplemachine.GUI.ItemGeneratorInventory;

import static simplemachine.simplemachine.Materials.Materials.*;
import static simplemachine.simplemachine.Tools.Functies.*;

public class BlockInteractListener implements Listener {

    @EventHandler
    public void onMachineInteract(PlayerInteractEvent e){

        Player player = e.getPlayer();
        if (e.getHand() == EquipmentSlot.OFF_HAND)return;
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if (e.getClickedBlock().getType() == itemGeneratorMaterial){
                Machine machine = new Machine(e.getClickedBlock().getLocation());
                if (!machine.isValid())return;
                if (hasPerm(player, "SMachine.itemgenerator.interact")){
                    e.setCancelled(true);
                    ItemGeneratorInventory itemGeneratorInventory = new ItemGeneratorInventory(player, machine);
                    itemGeneratorInventory.openItemGeneratorMenu();
                }
            }else if (e.getClickedBlock().getType() == collectorMaterial){
                Machine machine = new Collector(e.getClickedBlock().getLocation()).getMachine();
                player.sendMessage("1");
                if (!machine.isValid())return;
                player.sendMessage("2");
                if (!machine.getCollector().isValid())return;
                player.sendMessage("3");
                if (hasPerm(player, "SMachine.collector.interact")){
                    player.sendMessage("4");
                    e.setCancelled(true);
                    CollectorInventory collectorInventory = new CollectorInventory(player, machine);
                    collectorInventory.mainmenu();
                }
            }
        }

    }

}
