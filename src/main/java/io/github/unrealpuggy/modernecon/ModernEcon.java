package io.github.unrealpuggy.modernecon;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.unrealpuggy.modernecon.Menu.MenuItem;
import io.github.unrealpuggy.modernecon.Menu.MenuListener;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.action.DialogAction;
import io.papermc.paper.registry.data.dialog.input.DialogInput;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickCallback;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import static java.lang.Integer.parseInt;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class ModernEcon extends JavaPlugin {
    private static ModernEcon instance;

    private <T extends Listener> void registerListener(Supplier<T> supplier) {
        getServer().getPluginManager().registerEvents(supplier.get(), this);
    }

    public static ModernEcon getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        registerListener(MenuListener::new);
        LiteralArgumentBuilder<CommandSourceStack> shopCmd = Commands.literal("shop").executes(ctx -> {
            Entity executer = ctx.getSource().getExecutor();
            if (!(executer instanceof Player player)) {
                if(executer != null) {

                executer.sendPlainMessage("Only players can open shop!");
                }
                return Command.SINGLE_SUCCESS;
            }

            ShopGui.openForPlayer(player);
            return Command.SINGLE_SUCCESS;
        });
        LiteralArgumentBuilder<CommandSourceStack> testDialogue = Commands.literal("testdialogue").executes(ctx -> {
            Player player = ctx.getSource().getPlayerOrThrow();
            Dialog dialog = Dialog.create(builder -> builder.empty().base(DialogBase.builder(Component.text("Title")).inputs(List.of(DialogInput.text("item_price", Component.text("Item Price")).initial("1").build())).build()).type(DialogType.multiAction(List.of(ActionButton.builder(Component.text("Cancel", NamedTextColor.RED)).action(DialogAction.customClick((_, audience) -> {
                        player.closeDialog();
                    }, ClickCallback.Options.builder().uses(1).build())).build(), ActionButton.builder(Component.text("Confirm", NamedTextColor.GREEN)).action(DialogAction.customClick((view, audience) -> {
                        String text = Objects.requireNonNullElse(view.getText("item_price"), "");
                        try {

                            float price = Float.parseFloat(text);
                            player.sendMessage("$" + price);
                        } catch (Exception _) {
                            player.sendMessage(Component.text("Invalid price entered! ", NamedTextColor.RED).append(Component.text("\"%s\"".formatted(text), NamedTextColor.GRAY)));
                        }

                    }, ClickCallback.Options.builder().uses(1).build())).build())

            ).build()));
            player.showDialog(dialog);
            return 1;
        });

        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register(shopCmd.build());
            commands.registrar().register(testDialogue.build());
        });
        getLogger().info("Test");
    }
}