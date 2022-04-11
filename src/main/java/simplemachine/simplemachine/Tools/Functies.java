package simplemachine.simplemachine.Tools;

import org.apache.commons.lang.StringUtils;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import simplemachine.simplemachine.Data.Configs;
import simplemachine.simplemachine.SimpleMachine;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static simplemachine.simplemachine.Data.Configs.customConfigFile2;

public class Functies {

    private static final SimpleMachine plugin = SimpleMachine.getPlugin(SimpleMachine.class);

    public static ItemStack createHead(OfflinePlayer target, String title, ArrayList<String> lore){
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());

        SkullMeta skull_meta = (SkullMeta) skull.getItemMeta();
        skull_meta.setOwningPlayer(Bukkit.getOfflinePlayer(target.getUniqueId()));
        if (title != null) skull_meta.setDisplayName(title);
        if (lore != null) skull_meta.setLore(lore);
        skull.setItemMeta(skull_meta);

        return skull;
    }
    public static String color(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String getServerPrefix(){return color(plugin.getConfig().getString("ServerPrefix"));}

    public static String getMessage(String path){
        if (Configs.getCustomConfig1().contains(path)){
            return color(Configs.getCustomConfig1().getString(path));
        }
        return "§cInvalid Message";
    }
    public static String getItemDisplayname(ItemStack item){
        if (item.hasItemMeta()){
            if (item.getItemMeta().getDisplayName() != null){
                return item.getItemMeta().getDisplayName();
            }
        }
        return StringUtils.capitalize(item.getType().toString().toLowerCase().replace("_", " "));
    }
    public static ItemStack editItemMeta(ItemStack stack, String displayname, ArrayList<String> lore){
        ItemStack newItem = stack.clone();
        ItemMeta meta = newItem.getItemMeta();
        if (displayname != null)meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayname));
        if (lore != null)meta.setLore(lore);
        newItem.setItemMeta(meta);
        return newItem;
    }
    public static ArrayList<EntityType> getAllEntityTypes(){
        ArrayList<EntityType> entityTypes = new ArrayList<>();
        for (EntityType entityType : EntityType.values()){
            if (entityType == null)continue;
            if (entityType.isAlive()){
                if (invalidEntities().contains(entityType))continue;
                if (entityType.getName() == null)continue;
                entityTypes.add(entityType);
            }
        }
        return entityTypes;
    }
    public static ArrayList<EntityType> invalidEntities(){
        ArrayList<EntityType> disabled = new ArrayList<>();
        disabled.add(EntityType.PLAYER);
        disabled.add(EntityType.EVOKER);
        disabled.add(EntityType.VINDICATOR);
        disabled.add(EntityType.ILLUSIONER);
        disabled.add(EntityType.ARMOR_STAND);
        disabled.add(EntityType.ITEM_FRAME);
        disabled.add(EntityType.GHAST);
        return disabled;
    }
    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    public static boolean hasPerm(Player player, String permission){
        if (player.hasPermission(permission)) return true;
        player.sendMessage(getMessage("No Permissions"));
        return false;
    }
    public static ItemStack createItemstack(Material material, String title, ArrayList<String> lore){
        ItemStack stack = new ItemStack(material);
        ItemMeta stack_meta = stack.getItemMeta();
        if (title != null)stack_meta.setDisplayName(title);
        if (lore != null)stack_meta.setLore(lore);
        stack.setItemMeta(stack_meta);
        return stack;
    }
    public static ItemStack createGlass(String title, int color, ArrayList<String> lore){
        ItemStack stack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) color);
        return editItemMeta(stack, title, lore);
    }
    public static ArrayList<String> createArraylist(String... args){
        ArrayList<String> lines = new ArrayList<>();
        Collections.addAll(lines, args);
        return lines;
    }
    public static ArrayList<String> createLore(String... args){
        ArrayList<String> lines = new ArrayList<>();
        for (String arg : args)
            lines.addAll(splitString(arg, 40));
        return lines;
    }
    public static ArrayList<String> splitString(String s, int length){
        ArrayList<String> list = new ArrayList<>();
        StringBuilder sentence = new StringBuilder();
        String[] words = s.split(" ");
        String latestColor = "§7";
        for (String word : words) {
            if (word.contains("§"))
                latestColor = "§" + word.charAt(word.indexOf("§") + 1);
            if (sentence.length() + word.length() + 1 > length) {
                list.add(sentence.toString().trim());
                sentence = new StringBuilder();
                sentence.append(latestColor).append(word).append(" ");
            } else sentence.append(latestColor).append(word).append(" ");
        }
        list.add(sentence.toString().trim());
        return list;
    }
    public static void fillInv(Inventory inventory, int color){
        for (int i = 0; i < inventory.getSize(); i++){
            ItemStack current = inventory.getItem(i);
            if (current == null || current.getType() == Material.AIR){
                inventory.setItem(i, createGlass(" ", color, null));
            }
        }
    }
    public static String convertLocationToString(Location location){
        return location.getWorld().getName() + "," + (int) location.getX() + "," + (int) location.getY() + "," + (int) location.getZ();
    }
    public static Location convertStringToLocation(String stringLoc){
        String[] args = ChatColor.stripColor(color(stringLoc)).split(",");
        if (args.length == 4)
            return new Location(Bukkit.getWorld(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), 0, 0);
        else return null;
    }
    public static boolean compareLocations(Location location1, Location location2){
        return convertLocationToString(location1).equals(convertLocationToString(location2));
    }
    public static void saveData(){
        try {
            Configs.getCustomConfig2().save(customConfigFile2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void fillColomnGlass(Inventory inventory, int colomn, int color){
        for (int i = 0; i < inventory.getSize(); i+=9) inventory.setItem(i + (colomn - 1), createGlass(" ", color, null));
    }
    public static void fillColomnItemstack(Inventory inventory, int colomn, Material material){
        for (int i = 0; i < inventory.getSize(); i+=9) inventory.setItem(i + (colomn - 1), createItemstack(material, " ", null));
    }
    public static void fillRowGlass(Inventory inventory, int row, int color){
        for (int i = 0; i < 9; i++) inventory.setItem((row*9)-9+i, createGlass(" ", color, null));
    }
    public static String removeLastChar(String str) {
        return str.trim().substring(0, str.length() - 1);
    }
    public static ItemStack getBackItemstack(){
        return createItemstack(Material.ARROW, "§7§l- §cClose the menu §7§l-", createArraylist("§7Close the current menu"));
    }
    public static ItemStack getItemstackTemplate(ItemStack itemStack){
        ItemStack clone = itemStack.clone();
        clone.setAmount(1);
        return clone;
    }
    public static boolean compareItemstack(ItemStack itemStack1, ItemStack itemStack2){
        return getItemstackTemplate(itemStack1).equals(getItemstackTemplate(itemStack2));
    }
    public static void setItemDurability(ItemStack itemStack, int durability){
        if (itemStack.getType().getMaxDurability() - durability < 0)itemStack.setDurability((short) 0);
        else itemStack.setDurability((short) (itemStack.getType().getMaxDurability() - durability));
    }
    public static ItemStack createPotion(Color potioncolor, String displayname, ArrayList<String> potionlore){
        ItemStack potion = new ItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta) potion.getItemMeta();
        meta.setColor(potioncolor);
        potion.setItemMeta(meta);
        meta.setDisplayName(displayname);
        meta.setLore(potionlore);
        potion.setItemMeta(meta);
        return potion;
    }
    public static ArrayList<String> getStringArrayOfConfig(String path){
        ArrayList<String> list = new ArrayList<>();
        if (Configs.getCustomConfig2().contains(path))list = (ArrayList<String>) Configs.getCustomConfig2().getStringList(path);
        for (int i = 0; i < list.size(); i++)
            list.set(i, color(list.get(i)));
        return list;
    }
    public static void giveItemToPlayer(Player player, ItemStack itemStack){
        int air = (int) Arrays.stream(player.getInventory().getStorageContents()).filter(Objects::isNull).count();
        if (itemStack == null || itemStack.getType() == Material.AIR)return;
        if (air > 0) player.getInventory().addItem(itemStack);
        else player.getWorld().dropItem(player.getLocation(), itemStack);
    }
    public static boolean isValidItemstack(ItemStack itemStack){
        if (itemStack == null || itemStack.getType() == Material.AIR)return false;
        if (!itemStack.hasItemMeta())return false;
        return itemStack.getItemMeta().hasDisplayName();
    }
    public static String getStringFromArraylist(ArrayList<String> list, String match, int args){
        for (String s : list){
            if (s.contains(match))return s.split(" ")[args];
        }
        return null;
    }
    public static int countItemsInList(ArrayList<ItemStack> itemList){
        int counter = 0;
        for (ItemStack itemStack : itemList){
            if (itemStack == null)continue;
            counter+= itemStack.getAmount();
        }
        return counter;
    }
    public static String calculateTime(long seconds) {
        int day = (int) TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24L);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);

        return day + " d " + hours + " h " + minute + " m " + second + " s";
    }
    public static ItemStack getMostPopularItemstack(ArrayList<ItemStack> itemStacklist){
        HashMap<ItemStack, Integer> population = new HashMap<>();
        ArrayList<ItemStack> validItems = new ArrayList<>();
        for (ItemStack itemStack : itemStacklist){
            if (itemStack != null)validItems.add(itemStack);
        }
        validItems.forEach(itemStack -> population.put(itemStack, population.get(itemStack) + 1));
        int highest = population.values().stream().mapToInt(i -> i).filter(i -> i >= 0).max().orElse(0);
        return population.keySet().stream().filter(itemStack -> population.get(itemStack) == highest).findFirst().orElse(null);
    }
    public static ArrayList<ItemStack> getStackedItemstackList(ArrayList<ItemStack> list){
        Inventory inventory = Bukkit.createInventory(null, 54, "Counter");
        list.stream().filter(Objects::nonNull).forEach(inventory::addItem);
        return Arrays.stream(inventory.getContents()).filter(Objects::nonNull).collect(Collectors.toCollection(ArrayList::new));
    }
}
