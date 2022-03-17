package me.dreamerzero.example.velocity;

import java.util.Locale;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.util.ModInfo.Mod;

import org.slf4j.Logger;

import me.dreamerzero.miniplaceholders.api.Expansion;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;

@Plugin(
    name = "Player-Expansion",
    id = "playerexpansion",
    version = "1.0.0",
    authors = {"4drian3d"},
    dependencies = {
        @Dependency(
            id = "miniplaceholders",
            optional = false)
        }
)
public final class VelocityPlugin {
    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {
        Expansion.builder("player")
            .filter(Player.class)
            .audiencePlaceholder("name", (aud,queue, ctx) -> Tag.selfClosingInserting(Component.text(((Player)aud).getUsername())))
            .audiencePlaceholder("client", (aud, queue, ctx) -> {
                Player player = (Player)aud;
                String playerClient = player.getClientBrand();
                return Tag.selfClosingInserting(playerClient != null
                    ? Component.text(playerClient)
                    : Component.empty());
            })
            .audiencePlaceholder("locale", (aud, queue, ctx) -> {
                Player player = (Player)aud;
                Locale locale = player.getEffectiveLocale();
                return Tag.selfClosingInserting(locale != null
                    ? Component.text(locale.getDisplayName())
                    : Component.empty());
            })
            .audiencePlaceholder("current_server", (aud, queue, ctx) -> {
                Player player = (Player)aud;
                String server = player.getCurrentServer().map(sv -> sv.getServerInfo().getName()).orElse("");
                return Tag.selfClosingInserting(Component.text(server));
            })
            .audiencePlaceholder("ping", (aud, queue, ctx) -> Tag.selfClosingInserting(Component.text(((Player)aud).getPing())))
            .audiencePlaceholder("mods", (aud, queue, ctx) -> {
                Player player = (Player)aud;
                String mod = player.getModInfo()
                    .map(info -> info.getMods().stream()
                        .map(Mod::getId)
                        .collect(Collectors.joining(", ")))
                    .orElseGet(() -> "");
                return Tag.selfClosingInserting(Component.text(mod));
            })
            .build()
            .register();
    }
}
