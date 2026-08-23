package io.github.unrealpuggy.modernecon.Menu;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class Menu implements InventoryHolder {
    private final Inventory inventory;
    private final Map<Integer, MenuItem> items = new HashMap<>();

    public boolean cancelDefault = true;
    public boolean cancelInventory = true;

    public Menu(int rows, Component title) {
        this.inventory = Bukkit.createInventory(this, 9 * rows, title);
    }

    protected void setItem(int index, MenuItem menuItem) {
        inventory.setItem(index, menuItem.getItem());
        items.put(index, menuItem);
    }

    protected boolean removeItem(int index) {
        if (index < 0 || index > inventory.getSize()) return false;
//        inventory.setItem(index, null);
        return items.remove(index) != null;
    }
    protected boolean removeItem(MenuItem item) {
        return removeItem(getItemIndex(item));
    }


    private int rcToIndex(int row, int column) {
        return Math.max(row - 1, 0) * 9 + Math.max(column - 1, 0);
    }
    protected void setItem(int row, int column, MenuItem menuItem) {
        setItem(rcToIndex(row,column), menuItem);
    }

    @Nullable
    public MenuItem getItem(int index) {
        return items.get(index);
    }

    @Nullable
    public MenuItem getItem(int row, int column) {
        return items.get(rcToIndex(row,column));
    }

    /**
     * @param item The MenuItem to search for
     * @return index of the item, or -1 if not found
     */
    protected int getItemIndex(MenuItem item) {
        return items.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == item)
                .map(Map.Entry::getKey)
                .findFirst().orElse(-1);

    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
