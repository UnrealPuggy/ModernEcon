package io.github.unrealpuggy.modernecon.Menu;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

import java.util.stream.Collectors;

public class MenuListener implements Listener {
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getWhoClicked();
        if (!(inventory.getHolder(false) instanceof Menu menu)) {
            return;
        }
        boolean isInInventory = event.getRawSlots().stream().anyMatch(slot -> slot < inventory.getSize());

        event.setCancelled(isInInventory ? menu.cancelDefault : menu.cancelInventory);

//        event.getInventorySlots().stream().jo;
        player.sendMessage(event.getRawSlots().stream().map(Object::toString).collect(Collectors.joining(",")));


//        event.getRawSlots();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        Inventory inventory = event.getInventory();
        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory == null || !(inventory.getHolder(false) instanceof Menu menu)) {
            return;
        }
        boolean menuClicked = clickedInventory == inventory;
        event.setCancelled(menuClicked ? menu.cancelDefault : menu.cancelInventory);
        if (!menuClicked && (event.isShiftClick() || event.getClick() == ClickType.DOUBLE_CLICK) && menu.cancelDefault) {
            event.setCancelled(true);
        }

        Player player = (Player) event.getWhoClicked();
        if (menuClicked) {
            int slot = event.getSlot();
            MenuItem clickedItem = menu.getItem(slot);
            if (clickedItem == null) return;
            clickedItem.onClick(player,event);
            if (event.isRightClick()) clickedItem.onRightClick(player, event);
            if (event.isLeftClick()) clickedItem.onLeftClick(player, event);

        } else {
//            player.sendMessage("not inventopry");
        }


    }

}
