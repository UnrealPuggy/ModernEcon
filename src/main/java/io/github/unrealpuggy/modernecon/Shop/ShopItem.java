package io.github.unrealpuggy.modernecon.Shop;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;
import io.github.unrealpuggy.modernecon.Economy.EconomyUtil;
import org.jetbrains.annotations.NotNull;


import java.util.*;

/**
 * Basic ShopItem Structure
 */
@SerializableAs("ShopItem")
public final class ShopItem implements ConfigurationSerializable {
    private final String id;
    private final ItemStack item;
    private final long price;
    private final long sellPrice;

    /**
     * @param item      ItemStack to be sold
     * @param price     set to -1 to disable buying
     * @param sellPrice set to -1 to disable selling
     */
    public ShopItem(String id, ItemStack item, long price, long sellPrice) {
        this.id = id;
        this.item = item;
        this.price = price;
        this.sellPrice = sellPrice;
    }


    public boolean buyable() {
        return price >= 0;
    }

    public boolean sellable() {
        return sellPrice >= 0;
    }
//
//    public ShopItem {
//

    /// /        price = EconomyUtil.roundCurrency(price);
    /// /        sellPrice = EconomyUtil.roundCurrency(sellPrice);
//    }
    private ItemStack displayedStack = null;
    public ItemStack getDisplayedStack() {
        if (displayedStack != null) {
            return displayedStack;
        }
        ItemStack stack = item.clone();
        List<Component> lore = Objects.requireNonNullElse(stack.lore(), new ArrayList<>());
        MiniMessage mm = MiniMessage.miniMessage();

        if (buyable()) {
            lore.add(mm.deserialize("<i:false><green>Buy: $<white><price>", Placeholder.unparsed("price", EconomyUtil.centsToString(price))));
        }
        if (sellable()) {

            lore.add(mm.deserialize("<i:false><red>Sell: $<white><price>", Placeholder.unparsed("price", EconomyUtil.centsToString(sellPrice))));
        }

        stack.lore(lore);

        return displayedStack = stack;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("item", item);
        map.put("id", id);
        map.put("price", price);
        map.put("sellPrice", sellPrice);
        return map;
    }

    public ShopItem(Map<String, Object> map) {
        id = (String) map.get("id");
        item = (ItemStack) map.get("item");

        // It doesn't like when we just cast to long :(
        this.price = map.get("price") instanceof Number n ? n.longValue() : -1L;
        this.sellPrice = map.get("sellPrice") instanceof Number n ? n.longValue() : -1L;
    }

    public static ShopItem deserialize(Map<String,Object> map) {
        return new ShopItem(map);
    }

    public String id() {
        return id;
    }

    public ItemStack item() {
        return item;
    }

    public long price() {
        return price;
    }

    public long sellPrice() {
        return sellPrice;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ShopItem) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.item, that.item) &&
                this.price == that.price &&
                this.sellPrice == that.sellPrice;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, item, price, sellPrice);
    }

    @Override
    public String toString() {
        return "ShopItem[" +
                "id=" + id + ", " +
                "item=" + item + ", " +
                "price=" + price + ", " +
                "sellPrice=" + sellPrice + ']';
    }

}