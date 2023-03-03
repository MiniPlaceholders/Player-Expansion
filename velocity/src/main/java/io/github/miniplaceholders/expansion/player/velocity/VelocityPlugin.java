package io.github.miniplaceholders.expansion.player.velocity;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.util.ModInfo.Mod;
import io.github.miniplaceholders.api.Expansion;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;

import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Plugin(
        name = "Player-Expansion",
        id = "player-expansion",
        version = Constants.VERSION,
        authors = {"4drian3d"},
        dependencies = {
                @Dependency(id = "miniplaceholders")
        }
)
public final class VelocityPlugin {
    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {
        Expansion.builder("player")
                .filter(Player.class)
                .audiencePlaceholder("name", (aud, queue, ctx) -> Tag.selfClosingInserting(Component.text(((Player) aud).getUsername())))
                .audiencePlaceholder("client", (aud, queue, ctx) -> {
                    Player player = (Player) aud;
                    String playerClient = player.getClientBrand();
                    return Tag.selfClosingInserting(playerClient != null
                            ? Component.text(playerClient)
                            : Component.empty());
                })
                .audiencePlaceholder("ping", (aud, queue, ctx) -> Tag.selfClosingInserting(Component.text(((Player) aud).getPing())))
                .audiencePlaceholder("locale", (aud, queue, ctx) -> {
                    Player player = (Player) aud;
                    Locale locale = player.getEffectiveLocale();
                    return Tag.selfClosingInserting(locale != null
                            ? Component.text(locale.getDisplayName())
                            : Component.empty());
                })
                .audiencePlaceholder("current_server", (aud, queue, ctx) -> {
                    Player player = (Player) aud;
                    String server = player.getCurrentServer().map(sv -> sv.getServerInfo().getName()).orElse("");
                    return Tag.selfClosingInserting(Component.text(server));
                })
                .audiencePlaceholder("mods", (aud, queue, ctx) -> {
                    Player player = (Player) aud;
                    String mod = player.getModInfo()
                            .map(info -> info.getMods().stream()
                                    .map(Mod::getId)
                                    .collect(Collectors.joining(", ")))
                            .orElse("");
                    return Tag.selfClosingInserting(Component.text(mod));
                })
                .audiencePlaceholder("tab_header", (aud, queue, ctx) -> Tag.selfClosingInserting(Optional
                        .ofNullable(((Player) aud).getPlayerListHeader())
                        .orElse(Component.empty())))
                .audiencePlaceholder("tab_footer", (aud, queue, ctx) -> Tag.selfClosingInserting(Optional
                        .ofNullable(((Player) aud).getPlayerListFooter())
                        .orElse(Component.empty())))
                .build()
                .register();
    }
}
