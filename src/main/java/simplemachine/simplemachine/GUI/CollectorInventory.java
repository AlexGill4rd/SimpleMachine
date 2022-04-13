package simplemachine.simplemachine.GUI;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import simplemachine.simplemachine.Components.Machine;
import simplemachine.simplemachine.SimpleMachine;

import static simplemachine.simplemachine.SimpleMachine.navigationHandlerHashMap;
import static simplemachine.simplemachine.Tools.Functies.*;

public class CollectorInventory {

    private final Player player;
    private final Machine machine;

    SimpleMachine plugin = SimpleMachine.getPlugin(SimpleMachine.class);

    public CollectorInventory(Player player){
        this.player = player;
        this.machine = navigationHandlerHashMap.get(player).getMachine();
    }

    public void mainmenu(){
        Inventory inventory = Bukkit.createInventory(null, 9, "§7§l| §7§lCollector §7- §8Main menu §7§l|");

        inventory.setItem(1, createItemstack(Material.PAPER,
                "§7§l- §6Collector Information §7§l-",
                createLore("§7§l§m------",
                        "§8Items Collected: §f" + machine.getCollector().getStatItemsCollected(),
                        "§8Current item count: §f" + countItemsInList(machine.getCollector().getStorage()),
                        "§8Most items from: §f",
                        "§7§l§m------")));

        inventory.setItem(4, createItemstack(Material.CHEST, "§7§l- §6Inventory §7§l-", createLore("§7§l§m------", "§8Open the inventory where all the items produced by the machine are collected.", "§7§l§m------")));
        inventory.setItem(7, getBackItemstack());

        fillInv(inventory, 15);

        player.openInventory(inventory);
    }
    public void inventory(){
        Inventory inventory = Bukkit.createInventory(null, 36, "§7§l| §7§lCollector §7- §8Inventory §7§l|");

        for (ItemStack itemStack : machine.getCollector().getStorage())
            inventory.addItem(itemStack);

        inventory.setItem(35, getBackItemstack());

        player.openInventory(inventory);
    }

}
