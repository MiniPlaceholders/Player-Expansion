package io.github.miniplaceholders.expansion.player.paper;

import io.github.miniplaceholders.api.Expansion;
import io.github.miniplaceholders.expansion.player.common.DisplayNamePlaceholder;
import io.github.miniplaceholders.expansion.player.common.LocalePlaceholder;
import io.github.miniplaceholders.expansion.player.common.NamePlaceholder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

@SuppressWarnings("unused")
public final class PaperPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getSLF4JLogger().info("Starting Player Expansion for Paper");

        Expansion.builder("player")
                .filter(Player.class)
                .audiencePlaceholder("name", new NamePlaceholder())
                .audiencePlaceholder("displayname", new DisplayNamePlaceholder())
                .audiencePlaceholder("ping", (aud, queue, ctx) -> {
                    final Player player = (Player) aud;
                    return Tag.preProcessParsed(Integer.toString(player.getPing()));
                })
                .audiencePlaceholder("locale", new LocalePlaceholder())
                .audiencePlaceholder("client", (aud, queue, ctx) -> {
                    final Player player = (Player) aud;
                    final String playerClient = player.getClientBrandName();
                    return Tag.preProcessParsed(playerClient != null
                            ? playerClient
                            : "vanilla");
                })
                .audiencePlaceholder("world", (aud, queue, ctx) -> {
                    final Player player = (Player) aud;
                    return Tag.preProcessParsed(player.getWorld().getName());
                })
                .audiencePlaceholder("team", (aud, queue, ctx) -> {
                    final Player player = (Player) aud;
                    return Tag.selfClosingInserting(player.teamDisplayName());
                })
                .audiencePlaceholder("tab_header", (aud, queue, ctx) -> {
                    final Player player = (Player) aud;
                    return Tag.selfClosingInserting(Optional.ofNullable(player.playerListHeader()).orElse(Component.empty()));
                })
                .audiencePlaceholder("tab_footer", (aud, queue, ctx) -> {
                    final Player player = (Player) aud;
                    return Tag.selfClosingInserting(Optional.ofNullable(player.playerListFooter()).orElse(Component.empty()));
                })
                .build()
                .register();
    }
}
