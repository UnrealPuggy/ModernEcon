package io.github.unrealpuggy.modernecon.Menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class MenuItem implements Cloneable {
    @Nullable
    private ItemStack item;
    private ClickCallback leftClickHandler;
    private ClickCallback rightClickHandler;
    private ClickCallback clickHandler;

    @FunctionalInterface
    public interface ClickCallback {
        void handle(Player player, MenuItem item, InventoryClickEvent event);
    }

    @FunctionalInterface
    public interface SimplerClickCallback {
        void handle(Player player, MenuItem item);
    }


    private MenuItem(@Nullable ItemStack item) {
        this.item = item;
    }

    public boolean removeFrom(Menu menu) {
        return menu.removeItem(this);
    }
    // Clicks

    public MenuItem onLeftClick(ClickCallback callback) {
        this.leftClickHandler = callback;

        return this;
    }

    public MenuItem onLeftClick(SimplerClickCallback callback) {
        this.leftClickHandler = (player, item1, event) -> {
            callback.handle(player, item1);
        };
        return this;
    }

    public MenuItem onClick(ClickCallback callback) {
        this.clickHandler = callback;

        return this;
    }

    public MenuItem onClick(SimplerClickCallback callback) {
        this.clickHandler = (player, item1, event) -> {
            callback.handle(player, item1);
        };
        return this;
    }


    public MenuItem onRightClick(ClickCallback callback) {
        this.rightClickHandler = callback;
        return this;
    }

    public MenuItem onRightClick(SimplerClickCallback callback) {
        this.rightClickHandler = (player, item1, event) -> {
            callback.handle(player, item1);
        };
        return this;
    }


    public void onRightClick(Player player, InventoryClickEvent event) {
        if (this.rightClickHandler != null) {

            this.rightClickHandler.handle(player, this, event);
        }

    }

    public void onLeftClick(Player player, InventoryClickEvent event) {
        if (this.leftClickHandler != null)
            this.leftClickHandler.handle(player, this, event);
    }
   public void onClick(Player player, InventoryClickEvent event) {
        if (this.clickHandler != null)
            this.clickHandler.handle(player, this, event);
    }


    public static MenuItem create(ItemStack item) {
        return new MenuItem(item);
    }

    public static MenuItem create(String itemId) {
        Material foundMaterial = Objects.requireNonNullElse(Material.matchMaterial(itemId), Material.BARRIER);

        return MenuItem.create(ItemStack.of(foundMaterial));
    }


    public @Nullable ItemStack getItem() {
        return item;
    }


    public MenuItem setItem(@Nullable ItemStack item) {
        this.item = item;

        return this;
    }

    public MenuItem clone() throws CloneNotSupportedException {
//        MenuItem menuItem = (MenuItem) super.clone();
        MenuItem newMenuItem = ((MenuItem) super.clone()).setItem((this.item != null) ? this.item.clone() : null);
        newMenuItem.leftClickHandler = leftClickHandler;
        newMenuItem.rightClickHandler = rightClickHandler;

        return newMenuItem;
    }

}
