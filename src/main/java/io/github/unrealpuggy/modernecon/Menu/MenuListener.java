package io.github.unrealpuggy.modernecon.Menu;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

public class MenuListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        Inventory clickedInventory = event.getClickedInventory();

        if(clickedInventory == null || !(inventory.getHolder(false) instanceof Menu menu)) {
            return;
        }
        boolean menuClicked = clickedInventory == inventory;
        event.setCancelled(menuClicked ? menu.cancelDefault : menu.cancelInventory);
        if(!menuClicked && event.isShiftClick() && menu.cancelDefault) {
            event.setCancelled(true);
        }

        Player player = (Player) event.getWhoClicked();
        if(menuClicked) {
            int slot = event.getSlot();
            MenuItem clickedItem = menu.getItem(slot);
            if(clickedItem == null) return;
            if(event.isRightClick()) clickedItem.onRightClick(player,event);
            if(event.isLeftClick()) clickedItem.onLeftClick(player,event);

        } else {
//            player.sendMessage("not inventopry");
        }


    }

}
