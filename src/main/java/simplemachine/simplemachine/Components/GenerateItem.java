package simplemachine.simplemachine.Components;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import simplemachine.simplemachine.Components.Generator.Direction;
import simplemachine.simplemachine.SimpleMachine;
import simplemachine.simplemachine.Tools.Cuboid;

import java.util.ArrayList;

import static simplemachine.simplemachine.Materials.Materials.*;
import static simplemachine.simplemachine.Tools.Functies.convertLocationToString;

public class GenerateItem {

    private ItemStack itemstack;
    private ArmorStand armorStandItem;
    private Location location;
    private ArrayList<String> conveyors = new ArrayList<>();
    private final int maxLength = 100;
    private Direction direction;

    private int routingID;

    private final SimpleMachine plugin = SimpleMachine.getPlugin(SimpleMachine.class);
    private Machine machine;

    public GenerateItem(ItemStack itemStack, ItemGenerator itemGenerator){
        this.itemstack = itemStack;
        this.location = itemGenerator.getLocation().clone().add(0.5, 0, 0.5);
    }

    public Machine getMachine() {
        return machine;
    }
    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public ItemStack getItemstack() {
        return itemstack;
    }
    public void setItemstack(ItemStack itemstack) {
        this.itemstack = itemstack;
    }

    public ArmorStand getArmorStandItem() {
        return armorStandItem;
    }
    public void setArmorStandItem(ArmorStand armorStandItem) {
        this.armorStandItem = armorStandItem;
    }

    public final Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public int getRoutingID() {
        return routingID;
    }
    public void setRoutingID(int routingID) {
        this.routingID = routingID;
    }

    public void create(){
        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        armorStand.setVisible(false);
        armorStand.setCustomNameVisible(false);
        armorStand.setHelmet(itemstack);
        armorStand.setGravity(false);
        armorStand.setMarker(false);
        armorStand.setSmall(true);
        this.armorStandItem = armorStand;

        machine.getItemGenerator().addStatItemsProduced(1);
    }
    private Conveyor getNearConveyor(Location location){
        if (location.clone().add(1, 0, 0).getBlock().getType() == conveyorMaterial && !conveyors.contains(convertLocationToString(location.clone().add(1, 0 , 0)))) {
            direction = Direction.EAST;
            return new Conveyor(location.clone().add(1, 0, 0));
        }else if (location.clone().add(-1, 0, 0).getBlock().getType() == conveyorMaterial && !conveyors.contains(convertLocationToString(location.clone().add(-1, 0 , 0)))) {
            direction = Direction.WEST;
            return new Conveyor(location.clone().add(-1, 0, 0));
        }else if (location.clone().add(0, 0, 1).getBlock().getType() == conveyorMaterial && !conveyors.contains(convertLocationToString(location.clone().add(0, 0 , 1)))) {
            direction = Direction.SOUTH;
            return new Conveyor(location.clone().add(0, 0, 1));
        }else if (location.clone().add(0, 0, -1).getBlock().getType() == conveyorMaterial && !conveyors.contains(convertLocationToString(location.clone().add(0, 0 , -1)))) {
            direction = Direction.NORTH;
            return new Conveyor(location.clone().add(0, 0, -1));
        }else return null;
    }
    public void determineRoute(Location location){
        Location routingLocation = location.clone();
        int conveyorCount = 0;
        while (routingLocation != null || conveyorCount < maxLength){
            Conveyor conveyor = getNearConveyor(location);
            if (conveyor == null)routingLocation = null;
            else conveyors.add(convertLocationToString(conveyor.getLocation()));
            conveyorCount += 1;
        }
    }
    public Collector detectCollector(Location location){
        Cuboid cuboid = new Cuboid(location.clone().add(1, 0, 1), location.clone().add(-1, 0, -1));
        for (Block block : cuboid.getBlocks()){
            if (block.getType() == collectorMaterial) {
                if (machine.getCollector().getLocation() == block.getLocation())
                    return new Collector(block.getLocation());
            }
        }
        return null;
    }
    public void startRouting(){
        if (conveyors.size() > maxLength){
            machine.getItemGenerator().setEnabled(false);
            return;
        }
        routingID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (armorStandItem.isDead()){
                Bukkit.getScheduler().cancelTask(routingID);
                return;
            }
            Conveyor conveyor = getNearConveyor(armorStandItem.getLocation());
            if (conveyor == null){
                Collector collector = detectCollector(armorStandItem.getLocation());
                if (collector != null)
                    collector.addStorageItem(itemstack);
                armorStandItem.remove();
                Bukkit.getScheduler().cancelTask(routingID);
            } else {
                armorStandItem.teleport(getLocationWithRotation(conveyor.getLocation()));
                conveyors.add(convertLocationToString(conveyor.getLocation()));
            }
        }, 20, 20);
    }
    public void remove(){
        armorStandItem.remove();
    }
    private Location getLocationWithRotation(Location locRot) {
        float yaw = 0;
        if (direction == Direction.NORTH) yaw = 180f;
        else if (direction == Direction.EAST) yaw = -90f;
        else if (direction == Direction.SOUTH) yaw = 0f;
        else if (direction == Direction.WEST) yaw = 90f;
        locRot.setYaw(yaw);
        locRot.setPitch(yaw);
        return locRot;
    }
}
