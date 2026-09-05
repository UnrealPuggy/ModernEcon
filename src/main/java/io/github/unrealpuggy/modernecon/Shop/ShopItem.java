package io.github.unrealpuggy.modernecon.Shop;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import io.github.unrealpuggy.modernecon.Economy.EconomyUtil;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Basic ShopItem Structure
 * @param item ItemStack to be sold
 * @param price set to -1 to disable buying
 * @param sellPrice set to -1 to disable selling
 */
public record ShopItem(String id, ItemStack item, double price, double sellPrice) implements ConfigurationSerializable {
    public boolean buyable() {
        return price >= 0;
    }
    public boolean sellable() {
        return sellPrice >= 0;
    }

    public ShopItem {
        price = EconomyUtil.roundCurrency(price);
        sellPrice = EconomyUtil.roundCurrency(sellPrice);
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String,Object> map = new HashMap<>();
        map.put("item",item);
        map.put("id",id);
        map.put("price",price);
        map.put("sellPrice",sellPrice);
        return map;
    }

    public static ShopItem deserialize(Map<String,Object> map) {
        String id =(String) map.get("id");
        ItemStack item = (ItemStack) map.get("item");

        return new ShopItem(id,item,(double) map.get("price"),(double) map.get("sellPrice"));
    }
}