package io.github.unrealpuggy.modernecon.Shop;

import io.github.unrealpuggy.modernecon.ModernEcon;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public class ShopManager {
    private final Map<String, ShopItem> loadedItems = new HashMap<>();

    private List<ShopItem> itemsList = new ArrayList<>();

    public ShopItem getItem(String id) {
        return loadedItems.get(id);
    }

    /**
     * @return ConfigurationSection relevant to the items stored.
     */
    public ConfigurationSection shop_items() {
        ConfigurationSection section = ModernEcon.config().getConfigurationSection("shop_items");
        if(section == null) {
                    ModernEcon.getInstance().getLogger().info("null Section");

                    return ModernEcon.config().createSection("shop_items");
        }
        return section;
//        return Optional.ofNullable()
//                .orElseGet(() -> {
//
//                });
    }

    public void loadItems() {
        loadedItems.clear();
        itemsList.clear();
        ConfigurationSection section = shop_items();
        for (String id : section.getKeys(false)) {

            ShopItem item = section.getSerializable(id, ShopItem.class);

            if (item == null) {
                continue;
            }
            loadedItems.put(id, item);
            itemsList.add(item);
        }
    }

    public List<ShopItem> getPage(int page) {
        return itemsList.stream()
                .skip(page * 45L)
                .limit(45)
                .toList();
    }


}
