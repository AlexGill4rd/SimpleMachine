package simplemachine.simplemachine.Tools;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Matrix {

    private int x;
    private int y;

    public Matrix(int x, int y){
        this.x = x;
        this.y = y;
    }
    /**
     *Een methode om de slot integers te krijgen van een matrix in een inventaris
     */
    public ArrayList<Integer> fromInventory(Inventory inventory){
        //Integer is row Arraylist is slots
        ArrayList<Integer>[] rows = new ArrayList[inventory.getSize() / 9];
        ArrayList<Integer> slots = new ArrayList<>();
        int rowCount = 0;
        for (int i = 0; i < inventory.getSize(); i++){
            slots.add(i);
            if (slots.size() >= 9){
                rows[rowCount] = slots;
            }
            if ((i % 9) == 0 && i != 0) rowCount++;
        }
        int minIndex = 0;
        int maxIndex = 0;
        //Loop over all rows
        for (ArrayList<Integer> row : rows){
            if (row.contains(this.x))minIndex = row.indexOf(this.x);
            if (row.contains(this.y))maxIndex = row.indexOf(this.y);
        }
        //End slots validating
        ArrayList<Integer> validSlots = new ArrayList<>();
        for (ArrayList<Integer> row : rows){
            for (Integer i : row){
                if (row.indexOf(i) >= minIndex && row.indexOf(i) <= maxIndex)validSlots.add(i);
            }
        }
        Set<Integer> hashSet = new LinkedHashSet(validSlots);
        return new ArrayList(hashSet);
    }
    public ArrayList<ItemStack> getInventoryItems(Inventory inventory){
        ArrayList<ItemStack> items = new ArrayList<>();
        for (Integer i : fromInventory(inventory)){
            if (inventory.getItem(i) != null)items.add(inventory.getItem(i));
        }
        return items;
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
