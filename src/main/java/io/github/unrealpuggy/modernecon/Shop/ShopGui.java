package io.github.unrealpuggy.modernecon.Shop;

import io.github.unrealpuggy.modernecon.Menu.Menu;
import io.github.unrealpuggy.modernecon.Menu.MenuItem;
import io.github.unrealpuggy.modernecon.ModernEcon;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;

public class ShopGui extends Menu {
    int page = 0;

    public ShopGui(int page, Player player) {
        if (page < 0) page = 0;
        super(6, Component.text("Shop | Page " + (page + 1)));
        this.page = page;
//        cancelDefault = false;
//        player.sendMessage("Page #" + page);
        List<ShopItem> shopItems = ModernEcon.shopManager().getPage(page);
//        shopItems.forEach(item ->
//                player.sendMessage(item.id())
//        );
        List<MenuItem> menuItems = shopItems.stream().map(item -> MenuItem.create(item.getDisplayedStack()).onClick((player1, item1) -> {
            if(item.buyable()) Menu.open(new PurchaseGUI(item, this.page), player1);
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
        })).toList();
        for (int i = 0; i < menuItems.size(); i++) {
            this.setItem(i, menuItems.get(i));
        }
        MenuItem nextArrow = MenuItem.create("arrow").setDisplayName(Component.text("Next")).onClick((Player, _) -> {
            ShopGui.openPage(Player, ++this.page);
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
        });
        this.setItem(6, 6, nextArrow);
        MenuItem backArrow = MenuItem.create("arrow").setDisplayName(Component.text("Back")).onClick((Player, _) -> {
            ShopGui.openPage(Player, --this.page);
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
        });
        this.setItem(6, 4, backArrow);


//        if (page == 0) {
//
//            this.setItem(3, 5, MenuItem.create("diamond").onLeftClick((player, item) -> {
////                player.openInventory(new ShopGui(1).getInventory());
//                openPage(player,1);
//            }));
//        } else {
//            cancelDefault = true;
//            cancelInventory = false;
//            this.setItem(3, 5, MenuItem.create("iron_ingot").onClick((player, item, event) -> {
////                player.getWorld().dropItemNaturally(player.getLocation(),item.getItem());
////                ItemStack heldItem = item.getItem();
//                player.sendMessage("asd");
//                if(item.removeFrom(this)) {
//                    event.setCancelled(false);
//                }
////                item.setItem(null);
////                event.setCancelled(false);
////                if (heldItem != null) {
////
//////                    player.dropItem(heldItem);
////                    item.setItem(null);
//////                    event.setCurrentItem(null);
////                    event.setCancelled(false);
////                } else {
////
////                    event.setCancelled(true);
////                }
//            }));
//        }
    }

    public static void openForPlayer(Player player) {
        player.openInventory(new ShopGui(0, player).getInventory());
    }

    public static void openPage(Player player, int page) {
        player.openInventory(new ShopGui(page, player).getInventory());
    }
}
