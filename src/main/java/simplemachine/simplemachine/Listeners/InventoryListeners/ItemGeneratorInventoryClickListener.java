package simplemachine.simplemachine.Listeners.InventoryListeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import simplemachine.simplemachine.Components.Machine;
import simplemachine.simplemachine.GUI.ItemGeneratorSpeedModifyInventory;

import static simplemachine.simplemachine.Tools.Functies.*;

public class ItemGeneratorInventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClickItemGenerator(InventoryClickEvent e){

        if (e.getInventory().getTitle().equals("§7§l| §eItem Generator §7§l|")){
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            if (e.getClickedInventory() instanceof PlayerInventory)return;

            Machine machine = new Machine(convertStringToLocation(e.getInventory().getItem(7).getItemMeta().getLore().get(5).split(" ")[2]));

            ItemStack clickedItem = e.getCurrentItem();
            switch (clickedItem.getType()){
                case LAVA_BUCKET:
                    player.sendMessage(getMessage("Current Fuel Level"));
                    break;
                case FEATHER:
                    if (player.hasPermission("SMachine.itemgenerator.change")){
                        ItemGeneratorSpeedModifyInventory itemGeneratorSpeedModifyInventory = new ItemGeneratorSpeedModifyInventory(player, machine);
                        itemGeneratorSpeedModifyInventory.openInventory();
                    }
                    break;
                case ARROW:
                    player.closeInventory();
                    break;
                case GREEN_SHULKER_BOX:
                    machine.getItemGenerator().startItemGenerator();
                    player.sendMessage(getMessage("Item Generator Started"));
                    player.closeInventory();
                    break;
                case RED_SHULKER_BOX:
                    machine.getItemGenerator().startItemGenerator();
                    player.sendMessage(getMessage("Item Generator Stopped"));
                    player.closeInventory();
                    break;
            }

        }

    }

}
