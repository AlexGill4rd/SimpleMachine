package simplemachine.simplemachine.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import simplemachine.simplemachine.Components.Collector;
import simplemachine.simplemachine.Components.Machine;

import java.util.Set;

import static simplemachine.simplemachine.Materials.Materials.defaultMachineFuel;
import static simplemachine.simplemachine.Tools.Functies.*;

public class MachineCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("machine")) {

            if (sender instanceof Player) {

                Player player = (Player) sender;

                if (args.length == 0) {
                    if (hasPerm(player, "SMachine.command.machine.help")) {
                        player.sendMessage("§6/Machine get §7{§efuel§6/§emachine§6/§ecollector§7} §8- §7Get sertain machine components");
                    }
                }else if (args.length == 2){
                    if (args[0].equalsIgnoreCase("get")){
                        if (args[1].equalsIgnoreCase("fuel")){
                            if (hasPerm(player, "SMachine.command.machine.get.fuel")){
                                player.getInventory().addItem(defaultMachineFuel);
                                player.sendMessage(getMessage("Fuel Received"));
                            }
                        }else if (args[1].equalsIgnoreCase("machine")){
                            if (hasPerm(player, "SMachine.command.machine.get.machine")){
                                Machine machine = new Machine();
                                player.getInventory().addItem(machine.getItemGenerator().getItemGeneratorItemstack());
                                player.sendMessage(getMessage("Machine Received"));
                            }
                        }else if (args[1].equalsIgnoreCase("collector")){
                            if (hasPerm(player, "SMachine.command.machine.get.collector")){
                                Machine machine = Machine.getFromLocation(player.getTargetBlock(null, 5).getLocation());
                                if (machine.isValid()){
                                    player.getInventory().addItem(machine.getCollector().getBlockItemstack());
                                    player.sendMessage(getMessage("Collector Received"));
                                }else player.sendMessage(getMessage("Target Block Not Machine"));
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
