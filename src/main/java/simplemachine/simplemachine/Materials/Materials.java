package simplemachine.simplemachine.Materials;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static simplemachine.simplemachine.Tools.Functies.*;

public class Materials {

    public static Material collectorMaterial = Material.OBSERVER;
    public static Material conveyorMaterial =  Material.STEP;
    public static Material itemGeneratorMaterial = Material.DROPPER;
    public static ItemStack defaultMachineFuel = createItemstack(Material.DIAMOND_HOE, "§7§l- §4Machine Fuel §7§l-", createArraylist("§7§l------", "§7Use this item to fuel your machines", "", "§7Current fuel level:", "§a§l|||||||||||§7|||||", "§7§l------"));

}
