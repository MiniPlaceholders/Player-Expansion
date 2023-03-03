package io.github.miniplaceholders.expansion.player.paper;

import io.github.miniplaceholders.api.Expansion;
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
                .audiencePlaceholder("name", (aud, queue, ctx) -> Tag.selfClosingInserting(((Player) aud).name()))
                .audiencePlaceholder("displayname", (aud, queue, ctx) -> Tag.selfClosingInserting(((Player) aud).displayName()))
                .audiencePlaceholder("ping", (aud, queue, ctx) -> Tag.selfClosingInserting(Component.text(((Player) aud).getPing())))
                .audiencePlaceholder("locale", (aud, queue, ctx) -> Tag.selfClosingInserting(Component.text(((Player) aud).locale().getDisplayName())))
                .audiencePlaceholder("client", (aud, queue, ctx) -> {
                    String playerClient = ((Player) aud).getClientBrandName();
                    return Tag.selfClosingInserting(playerClient != null
                            ? Component.text(playerClient)
                            : Component.empty());
                })
                .audiencePlaceholder("world", (aud, queue, ctx) -> Tag.selfClosingInserting(Component.text(((Player) aud).getWorld().getName())))
                .audiencePlaceholder("team", (aud, queue, ctx) -> Tag.selfClosingInserting(((Player) aud).teamDisplayName()))
                .audiencePlaceholder("tab_header", (aud, queue, ctx) -> Tag.selfClosingInserting(Optional.ofNullable(((Player) aud).playerListHeader()).orElse(Component.empty())))
                .audiencePlaceholder("tab_footer", (aud, queue, ctx) -> Tag.selfClosingInserting(Optional.ofNullable(((Player) aud).playerListFooter()).orElse(Component.empty())))
                .build()
                .register();
    }
}
