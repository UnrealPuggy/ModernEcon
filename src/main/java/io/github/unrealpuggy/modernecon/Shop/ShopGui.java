package io.github.unrealpuggy.modernecon.Shop;

import io.github.unrealpuggy.modernecon.Menu.Menu;
import io.github.unrealpuggy.modernecon.Menu.MenuItem;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class ShopGui extends Menu {
    int page = 0;

    public ShopGui(int page) {
        super(6, Component.text("Shop"));
        cancelDefault = false;
        if (page == 0) {

            this.setItem(3, 5, MenuItem.create("diamond").onLeftClick((player, item) -> {
//                player.openInventory(new ShopGui(1).getInventory());
                openPage(player,1);
            }));
        } else {
            cancelDefault = true;
            cancelInventory = false;
            this.setItem(3, 5, MenuItem.create("iron_ingot").onLeftClick((player, item, event) -> {
//                player.getWorld().dropItemNaturally(player.getLocation(),item.getItem());
//                ItemStack heldItem = item.getItem();
                if(item.removeFrom(this)) {
                    event.setCancelled(false);
                }
//                item.setItem(null);
//                event.setCancelled(false);
//                if (heldItem != null) {
//
////                    player.dropItem(heldItem);
//                    item.setItem(null);
////                    event.setCurrentItem(null);
//                    event.setCancelled(false);
//                } else {
//
//                    event.setCancelled(true);
//                }
            }));
        }
    }

    public static void openForPlayer(Player player) {
        player.openInventory(new ShopGui(0).getInventory());
    }
    public static void openPage(Player player,int page) {
        player.openInventory(new ShopGui(page).getInventory());
    }
}
