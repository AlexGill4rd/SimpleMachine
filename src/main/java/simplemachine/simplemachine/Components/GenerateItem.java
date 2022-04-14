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
import static simplemachine.simplemachine.Tools.Functies.compareLocations;
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
        this.machine = itemGenerator.getMachine();
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
        armorStand.setSmall(true);
        armorStand.setCustomNameVisible(false);
        armorStand.setHelmet(itemstack);
        armorStand.setGravity(false);
        armorStand.setMarker(false);

        this.armorStandItem = armorStand;

        machine.getItemGenerator().addStatItemProduced();
    }
    private Conveyor previousConveyer;
    public void startRouting(){
        if (conveyors.size() > maxLength){
            machine.getItemGenerator().setEnabled(false);
            return;
        }
        previousConveyer = new Conveyor(armorStandItem.getLocation());
        routingID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (armorStandItem.isDead()){
                Bukkit.getScheduler().cancelTask(routingID);
                return;
            }
            Conveyor conveyor = getNearConveyor(previousConveyer.getLocation());
            if (conveyor == null){
                Collector collector = detectCollector(previousConveyer.getLocation());
                if (collector != null){
                    if (collector.getLocation().getY() != previousConveyer.getLocation().getY()) {
                        Location tpLoc = collector.getLocation().clone().add(0,  previousConveyer.getLocation().getY() - collector.getLocation().getY(), 0);
                        moveItem(tpLoc.clone().add(0.5, 0, 0.5), 1);
                    }else moveItem(collector.getLocation().clone().add(0.5, 0, 0.5), 1);
                }
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    if (collector != null)
                        collector.addStorageItem(itemstack);
                    armorStandItem.remove();
                    this.machine.getItemGenerator().removeActiveItem(this);
                }, 20);

                Bukkit.getScheduler().cancelTask(routingID);
            } else {
                moveItem(conveyor.getLocation(), 1);
                conveyors.add(convertLocationToString(conveyor.getLocation()));
                previousConveyer = conveyor;
            }
        }, 0, 20);
    }
    private void moveItem(Location location, double distance){
        for (int i = 0; i < 10 * distance; i++){
            int finalI = i;
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                if (direction == Direction.NORTH)
                    armorStandItem.teleport(getLocationWithRotation(location.clone().add(0, 0, distance - (finalI / 10f))));
                else if (direction == Direction.EAST)
                    armorStandItem.teleport(getLocationWithRotation(location.clone().add((finalI / 10f) - distance, 0, 0)));
                else if (direction == Direction.SOUTH)
                    armorStandItem.teleport(getLocationWithRotation(location.clone().add(0, 0, (finalI / 10f) - distance)));
                else if (direction == Direction.WEST)
                    armorStandItem.teleport(getLocationWithRotation(location.clone().add(distance - (finalI / 10f), 0, 0)));
            }, (long) ((2L * i)));
        }
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
        Cuboid cuboid = new Cuboid(location.clone().add(1, 0, 1), location.clone().add(-1, 1, -1));
        for (Block block : cuboid.getBlocks()){
            if (block.getType() == collectorMaterial) {
                if (compareLocations(block.getLocation(), this.machine.getCollector().getLocation())) {
                    return this.machine.getCollector();
                }
            }
        }
        return null;
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
