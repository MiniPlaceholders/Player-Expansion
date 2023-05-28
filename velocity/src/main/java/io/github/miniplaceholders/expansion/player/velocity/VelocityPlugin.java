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
        name = "MiniPlaceholders-Player-Expansion",
        id = "miniplaceholders-player-expansion",
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
                .audiencePlaceholder("name", (aud, queue, ctx) -> {
                    final Player player = (Player) aud;
                    return Tag.preProcessParsed(player.getUsername());
                })
                .audiencePlaceholder("client", (aud, queue, ctx) -> {
                    Player player = (Player) aud;
                    String playerClient = player.getClientBrand();
                    return Tag.preProcessParsed(playerClient != null
                            ? playerClient
                            : "vanilla");
                })
                .audiencePlaceholder("ping", (aud, queue, ctx) -> {
                    final Player player = (Player) aud;
                    return Tag.preProcessParsed(Long.toString(player.getPing()));
                })
                .audiencePlaceholder("locale", (aud, queue, ctx) -> {
                    final Player player = (Player) aud;
                    final Locale locale = player.getEffectiveLocale();
                    return Tag.preProcessParsed(locale != null
                            ? locale.getDisplayName()
                            : Locale.getDefault().getDisplayName());
                })
                .audiencePlaceholder("current_server", (aud, queue, ctx) -> {
                    final Player player = (Player) aud;
                    final String server = player.getCurrentServer().map(sv -> sv.getServerInfo().getName()).orElse("");
                    return Tag.preProcessParsed(server);
                })
                .audiencePlaceholder("mods", (aud, queue, ctx) -> {
                    final Player player = (Player) aud;
                    final String mod = player.getModInfo()
                            .map(info -> info.getMods().stream()
                                    .map(Mod::getId)
                                    .collect(Collectors.joining(", ")))
                            .orElse("");
                    return Tag.preProcessParsed(mod);
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
