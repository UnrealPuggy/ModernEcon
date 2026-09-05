package io.github.unrealpuggy.modernecon.Shop;

import io.github.unrealpuggy.modernecon.ModernEcon;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopManager {
    private final Map<String, ShopItem> loadedItems = new HashMap<>();

    private List<ShopItem> itemsList = new ArrayList<>();
    public ShopItem getItem(String id) {
        return loadedItems.get(id);
    }

    public void loadItems() {
        loadedItems.clear();
        itemsList.clear();
//        loadedItems = new HashMap<>();
        ConfigurationSection section = ModernEcon.config().getConfigurationSection("shop_items");
        if (section == null) {
            section = ModernEcon.config().createSection("shop_items");
        }
        for (String id : section.getKeys(false)) {
            loadedItems.put(id, section.getObject(id, ShopItem.class));
        }
        itemsList = new ArrayList<>(loadedItems.values());
    }


}
