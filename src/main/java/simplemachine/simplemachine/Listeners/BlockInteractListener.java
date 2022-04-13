package simplemachine.simplemachine.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import simplemachine.simplemachine.Components.Machine;
import simplemachine.simplemachine.GUI.CollectorInventory;
import simplemachine.simplemachine.GUI.Handlers.NavigationHandler;
import simplemachine.simplemachine.GUI.ItemGeneratorInventory;

import static simplemachine.simplemachine.Materials.Materials.*;
import static simplemachine.simplemachine.SimpleMachine.navigationHandlerHashMap;
import static simplemachine.simplemachine.Tools.Functies.*;

public class BlockInteractListener implements Listener {

    @EventHandler
    public void onMachineInteract(PlayerInteractEvent e){

        Player player = e.getPlayer();

        if (e.getHand() == EquipmentSlot.OFF_HAND)return;
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if (e.getClickedBlock().getType() == itemGeneratorMaterial){
                Machine machine = Machine.getFromLocation(e.getClickedBlock().getLocation());
                if (!machine.isValid())return;
                if (hasPerm(player, "SMachine.itemgenerator.interact")){
                    e.setCancelled(true);
                    navigationHandlerHashMap.put(player, new NavigationHandler(player, machine));
                    ItemGeneratorInventory itemGeneratorInventory = new ItemGeneratorInventory(player, machine);
                    itemGeneratorInventory.openItemGeneratorMenu();
                }
            }else if (e.getClickedBlock().getType() == collectorMaterial){
                Machine machine = Machine.getMachineFromCollectorLocation(e.getClickedBlock().getLocation());

                if (!machine.isValid())return;
                if (!machine.getCollector().isValid())return;

                if (hasPerm(player, "SMachine.collector.interact")){
                    e.setCancelled(true);
                    navigationHandlerHashMap.put(player, new NavigationHandler(player, machine));
                    CollectorInventory collectorInventory = new CollectorInventory(player);
                    collectorInventory.mainmenu();

                }
            }
        }

    }

}
