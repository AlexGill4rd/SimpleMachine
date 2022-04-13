package simplemachine.simplemachine.Tools;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

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
    public Integer[] fromInventory(){
        Integer[] matrix = new Integer[this.x * this.y];
        int row = 1;
        int index = 0;
        for (int i = 0; i < 54; i++){
            if (index >= this.x*this.y)break;
            if ((i % 9) == 0 && i != 0)row++;
            if (i >= (9 * row) - (9-this.x))continue;
            matrix[index] = i;
            index++;
        }
        return matrix;
    }
    public ArrayList<ItemStack> getInventoryItems(Inventory inventory){
        ArrayList<ItemStack> items = new ArrayList<>();
        for (int i : fromInventory()){
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
