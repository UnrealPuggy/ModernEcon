package io.github.unrealpuggy.modernecon.Shop;

import io.github.unrealpuggy.modernecon.Economy.EconomyUtil;
import io.github.unrealpuggy.modernecon.Menu.Menu;
import io.github.unrealpuggy.modernecon.Menu.MenuItem;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PurchaseGUI extends Menu {
    private int itemCount = 1;
    private final MenuItem purchasingItem;
    private final MenuItem purchaseButton;
    private final ShopItem shopItem;

    private MenuItem generateChangeCountItem(int count) {
        boolean isPos = count > 0;
        MenuItem item = count > 0 ? MenuItem.create("lime_stained_glass_pane") : MenuItem.create("red_stained_glass_pane");
        item.setDisplayName(Component.text((isPos ? "+" : "-") + Math.abs(count), isPos ? NamedTextColor.GREEN : NamedTextColor.RED));
        item.setCount(Math.abs(count));
        item.onClick((player, _) -> {
            itemCount += count;
            itemCount = Math.clamp(itemCount, 1, 64);
//            purchasingItem.setCount(itemCount);
            updateGUI();
//            this.setItem(3, 5, purchasingItem);
//            player.updateInventory();
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);

        });

        return item;
    }

    private void updateGUI() {
        purchasingItem.setCount(itemCount);
        this.setItem(3, 5, purchasingItem);
        purchaseButton.setLore(List.of(MiniMessage.miniMessage().deserialize("<i:false><gray>Total: <green>$<white><price>", Placeholder.unparsed("price", EconomyUtil.formatCents(shopItem.price() * itemCount)))));
        this.setItem(4, 5, purchaseButton);

    }

    public PurchaseGUI(ShopItem shopItem, int prevPage) {

        super(5, Component.text("Purchasing"));
        purchasingItem = MenuItem.create(shopItem.getDisplayedStack());
        this.shopItem = shopItem;

        this.setItem(3, 5, purchasingItem);

        this.setItem(3, 3, generateChangeCountItem(-1));
        this.setItem(3, 2, generateChangeCountItem(-16));
        this.setItem(3, 1, generateChangeCountItem(-32));
        this.setItem(3, 7, generateChangeCountItem(+1));
        this.setItem(3, 8, generateChangeCountItem(+16));
        this.setItem(3, 9, generateChangeCountItem(+32));


        purchaseButton = MenuItem.create("lime_dye").setDisplayName(Component.text("Confirm", NamedTextColor.GREEN)).onLeftClick(((player, item) -> {
            // Logic
            ItemStack purchase = shopItem.item();
            purchase.setAmount(itemCount);
            player.give(purchase);
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);

        }));
        updateGUI();
//        this.setItem(4, 5, purchaseButton);

        MenuItem backButton = MenuItem.create("red_dye").setDisplayName(Component.text("Back", NamedTextColor.RED)).onClick((player, item) -> {

            ShopGui.openPage(player, prevPage);
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
        });
        this.setItem(5, 0, backButton);

    }
}
