package io.github.miniplaceholders.expansion.player.velocity;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.util.ModInfo.Mod;
import io.github.miniplaceholders.api.Expansion;
import io.github.miniplaceholders.expansion.player.common.PlatformExpansionProvider;
import io.github.miniplaceholders.expansion.player.common.resolver.DisplayNameResolver;
import io.github.miniplaceholders.expansion.player.common.resolver.LocaleResolver;
import io.github.miniplaceholders.expansion.player.common.resolver.NameResolver;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;

import java.util.Optional;
import java.util.stream.Collectors;

public final class VelocityProvider implements PlatformExpansionProvider {

    @Override
    public Expansion.Builder provideBuilder() {
        return Expansion.builder("player")
                .audiencePlaceholder("name", new NameResolver())
                .audiencePlaceholder("displayname", new DisplayNameResolver())
                .audiencePlaceholder(Player.class, "client", (player, _, _) -> {
                    String playerClient = player.getClientBrand();
                    return Tag.preProcessParsed(playerClient != null
                            ? playerClient
                            : "vanilla");
                })
                .audiencePlaceholder(Player.class, "ping", (player, _, _) -> {
                    return Tag.preProcessParsed(Long.toString(player.getPing()));
                })
                .audiencePlaceholder("locale", new LocaleResolver())
                .audiencePlaceholder(Player.class, "current_server", (player, _, _) -> {
                    final String server = player.getCurrentServer().map(sv -> sv.getServerInfo().getName()).orElse("");
                    return Tag.preProcessParsed(server);
                })
                .audiencePlaceholder(Player.class, "mods", (player, _, _) -> {
                    final String mod = player.getModInfo()
                            .map(info -> info.getMods().stream()
                                    .map(Mod::getId)
                                    .collect(Collectors.joining(", ")))
                            .orElse("");
                    return Tag.preProcessParsed(mod);
                })
                .audiencePlaceholder(Player.class, "tab_header",
                        (aud, _, _) -> Tag.selfClosingInserting(Optional
                                .ofNullable(aud.getPlayerListHeader())
                                .orElse(Component.empty())))
                .audiencePlaceholder(Player.class, "tab_footer",
                        (aud, _, _) -> Tag.selfClosingInserting(Optional
                                .ofNullable(aud.getPlayerListFooter())
                                .orElse(Component.empty())));
    }
}
