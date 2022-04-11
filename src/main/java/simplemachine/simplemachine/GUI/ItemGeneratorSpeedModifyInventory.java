package simplemachine.simplemachine.GUI;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import simplemachine.simplemachine.Components.Machine;

import static simplemachine.simplemachine.Tools.Functies.*;

public class ItemGeneratorSpeedModifyInventory {

    private final Player player;
    private final Machine machine;

    public ItemGeneratorSpeedModifyInventory(Player player, Machine machine){
        this.player = player;
        this.machine = machine;
    }
    public void openInventory(){
        Inventory inventory = Bukkit.createInventory(null, 27, "§7§l| §eItem Generator §7- §8Production Speed §7§l|");

        fillColomnItemstack(inventory, 1, Material.ANVIL);
        fillColomnItemstack(inventory, 9, Material.ANVIL);

        inventory.setItem(10, createGlass("§7-§a +1", 5, createArraylist("§7Click here to adjust the current speed")));
        inventory.setItem(11, createGlass("§7-§a +10", 5, createArraylist("§7Click here to adjust the current speed")));
        inventory.setItem(12, createGlass("§7-§a +100", 5, createArraylist("§7Click here to adjust the current speed")));
        inventory.setItem(13, createItemstack(Material.FEATHER, "§8- §eSpeed §7Editor §8-", createArraylist("§6§l§m§l-----", "§7Use the indicators on the side", "§7to adjust the speed for", "§7producing products", "§6§l§m§l-----", "§7Current Speed: §f" + machine.getItemGenerator().getItemsPerHour() + " items/h", "§7Location:§f " + convertLocationToString(machine.getLocation()))));
        inventory.setItem(16, createGlass("§7-§c +1", 14, createArraylist("§7Click here to adjust the current speed")));
        inventory.setItem(15, createGlass("§7-§c +10", 14, createArraylist("§7Click here to adjust the current speed")));
        inventory.setItem(14, createGlass("§7-§c +100", 14, createArraylist("§7Click here to adjust the current speed")));
        fillInv(inventory, 15);

        player.openInventory(inventory);
    }

}
