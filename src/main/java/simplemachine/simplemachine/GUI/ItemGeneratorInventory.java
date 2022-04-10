package simplemachine.simplemachine.GUI;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import simplemachine.simplemachine.Components.Machine;
import simplemachine.simplemachine.SimpleMachine;

import static simplemachine.simplemachine.Tools.Functies.*;

public class ItemGeneratorInventory {

    private final Player player;
    private final Machine machine;

    private int updater;
    SimpleMachine plugin = SimpleMachine.getPlugin(SimpleMachine.class);

    public ItemGeneratorInventory(Player player, Machine machine){
        this.player = player;
        this.machine = machine;
    }

    public void openItemGeneratorMenu(){
        Inventory inventory = Bukkit.createInventory(null, 18, "§7§l| §eItem Generator §7§l|");
        updateMachineInventory(inventory);
        player.openInventory(inventory);
    }
    private void updateMachineInventory(Inventory inventory){
        updater = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (player.getOpenInventory().getTopInventory().getTitle().equals("§7§l| §eItem Generator §7§l|")){
                inventory.clear();
                inventory.setItem(1, createItemstack(Material.LAVA_BUCKET, "§7§l► §6Fuel §7Amount: §f" + machine.getItemGenerator().getFuelLevel() + " §7§l◄", createArraylist("§6§l§m§l-----", "§7The amount of fuel left in", "§7the machine at the moment", "§6§l§m§l-----")));
                inventory.setItem(2, createItemstack(Material.FEATHER, "§7§l► §6Machine §7Speed: §f" + machine.getItemGenerator().getItemsPerHour() + " items/h" + " §7§l◄", createArraylist("§6§l§m§l-----", "§7The amount of items that the", "§7machine will generate in 1 hour", "§7Items/s:§f " + (Math.round((machine.getItemGenerator().getItemsPerHour()/60f/60f)*100f)/100f), "§6§l§m§l-----")));
                inventory.setItem(4, editItemMeta(machine.getItemGenerator().getProduct(), "§7§l► §6Current §7Product §7§l◄", createArraylist("§6§l§m§l-----", "§7The current item the machine", "§7produces", "§6§l§m§l-----")));
                inventory.setItem(7, createItemstack(Material.REDSTONE, "§7§l► §6Statistics §7§l◄", createArraylist("§7A list of all the machine statistics",
                        "§6§l§m§l-----",
                        "§7Items generated: §f" + (int) (machine.getItemGenerator().getStatItemsProduced()),
                        "§7Fuel Used: §f" + machine.getItemGenerator().getStatFuelUsed(),
                        "§7Machine Age: §f" + calculateTime((System.currentTimeMillis() - machine.getItemGenerator().getStatItemGeneratorAge()) / 1000),
                        "§7Machine Location:§f " + convertLocationToString(machine.getLocation()),
                        "§6§l§m§l-----")));
                inventory.setItem(13, getBackItemstack());
                if (!machine.getItemGenerator().isEnabled()) inventory.setItem(17, createItemstack(Material.GREEN_SHULKER_BOX, "§7§l► §aStart Machine §7§l◄", createArraylist("§6§l§m§l-----", "§7Press this button to", "§7turn the machine on", "§6§l§m§l-----")));
                else inventory.setItem(17, createItemstack(Material.RED_SHULKER_BOX, "§7§l► §aStop Machine §7§l◄", createArraylist("§6§l§m§l-----", "§7Press this button to", "§7turn the machine off", "§6§l§m§l-----")));
                fillInv(inventory, 15);
            }else Bukkit.getScheduler().cancelTask(updater);
        }, 0, 10);
    }

}
