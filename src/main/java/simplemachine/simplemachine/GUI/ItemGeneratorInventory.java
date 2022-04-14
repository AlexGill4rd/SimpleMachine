package simplemachine.simplemachine.GUI;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import simplemachine.simplemachine.Components.Machine;
import simplemachine.simplemachine.SimpleMachine;

import static simplemachine.simplemachine.SimpleMachine.navigationHandlerHashMap;
import static simplemachine.simplemachine.Tools.Functies.*;

public class ItemGeneratorInventory {

    private final Player player;
    private final Machine machine;

    private int updater;
    SimpleMachine plugin = SimpleMachine.getPlugin(SimpleMachine.class);

    public ItemGeneratorInventory(Player player){
        this.player = player;
        this.machine = navigationHandlerHashMap.get(player).getMachine();
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
                inventory.setItem(1, createItemstack(Material.LAVA_BUCKET, "§7§l► §6Fuel §7Amount: §f" + machine.getItemGenerator().getFuelLevel() + " §7§l◄", createLore("§6§l§m------", "§7Here you can see how much fuel the machine currently has in its tank.", "§6§l§m------")));
                inventory.setItem(2, createItemstack(Material.FEATHER, "§7§l► §6Machine §7Speed: §f" + machine.getItemGenerator().getItemsPerHour() + " items/h" + " §7§l◄", createLore("§6§l§m------", "§7The number of products the machine will produce/is producing in the unit", "§6Items/h §f" + (Math.round((machine.getItemGenerator().getItemsPerHour()/60f/60f)*100f)/100f), "§6§l§m------")));
                if (machine.getItemGenerator().getProduct().getType() != Material.BARRIER)
                    inventory.setItem(4, editItemMeta(machine.getItemGenerator().getProduct(), "§7§l► §6Current §7Product §7§l◄", createLore("§6§l§m------", "§7Here you can see which product the machine currently has in production.", "§6§l§m------")));
                else{
                    if (player.hasPermission("SMachine.itemgenerator.setproduct") || player.isOp())
                        inventory.setItem(4, editItemMeta(machine.getItemGenerator().getProduct(), "§7§l► §4No product selected §7§l◄", createLore("§6§l§m------", "§7There is currently no product set for the machine to produce.", " ", "§7To set up a product §fLeft-Click", "§6§l§m------")));
                    else
                        inventory.setItem(4, editItemMeta(machine.getItemGenerator().getProduct(), "§7§l► §4No product selected §7§l◄", createLore("§6§l§m------", "§7There is currently no product set for the machine to produce.", "§6§l§m------")));

                }
                inventory.setItem(7, createItemstack(Material.REDSTONE, "§7§l► §6Statistics §7§l◄", createLore("§7A list of all the machine's stats:",
                    "§6§l§m------",
                    "§7Items generated: §f" + (int) machine.getItemGenerator().getStatItemsProduced(),
                    "§7Fuel Used: §f" + machine.getItemGenerator().getStatFuelUsed(),
                    "§7Machine Age: §f" + calculateTime((System.currentTimeMillis() - machine.getItemGenerator().getStatItemGeneratorAge()) / 1000),
                    "§7Machine Location:§f " + convertLocationToString(machine.getLocation()),
                    "§6§l§m------")));
                inventory.setItem(13, getBackItemstack());
                if (!machine.getItemGenerator().isEnabled()) inventory.setItem(17, createItemstack(Material.GREEN_SHULKER_BOX, "§7§l► §aStart Machine §7§l◄", createArraylist("§6§l§m§l-----", "§7Press this button to", "§7turn the machine on", "§6§l§m§l-----")));
                else inventory.setItem(17, createItemstack(Material.RED_SHULKER_BOX, "§7§l► §aStop Machine §7§l◄", createArraylist("§6§l§m§l-----", "§7Press this button to", "§7turn the machine off", "§6§l§m§l-----")));
                fillInv(inventory, 15);
            }else Bukkit.getScheduler().cancelTask(updater);
        }, 0, 20);
    }
    public void openProductConfiguration(){
        Inventory inventory = Bukkit.createInventory(null, 9, "§7§l| §eItem Gen. §7- §8Product Config §7§l|");

        inventory.setItem(4, editItemMeta(machine.getItemGenerator().getProduct(), "§8§l- §6Current Item: §f" + machine.getItemGenerator().getProduct().getType().toString() + " §8§l-", createLore("§7§l§m------", "§7Click on an item in your inventory to set it as the product of the machine.", "", "§fLeft-Click §7In your inventory to set product", "§fRight-Click §7In your inventory to get product", "", "§fShift-Left-Click §7On this item to set no product", "§fRight-Click §7On this item to get the machine's product", "§7§l§m------")));
        inventory.setItem(8, getBackItemstack());

        fillInv(inventory, 15);
        player.openInventory(inventory);
    }
}
