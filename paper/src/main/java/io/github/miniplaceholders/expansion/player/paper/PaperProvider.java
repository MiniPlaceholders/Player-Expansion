package io.github.miniplaceholders.expansion.player.paper;

import io.github.miniplaceholders.api.Expansion;
import io.github.miniplaceholders.expansion.player.common.PlatformExpansionProvider;
import io.github.miniplaceholders.expansion.player.common.resolver.DisplayNameResolver;
import io.github.miniplaceholders.expansion.player.common.resolver.LocaleResolver;
import io.github.miniplaceholders.expansion.player.common.resolver.NameResolver;
import io.github.miniplaceholders.expansion.player.paper.resolver.StatisticResolver;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;
import org.bukkit.entity.Player;

import java.util.Optional;

public final class PaperProvider implements PlatformExpansionProvider {

    @Override
    public Expansion.Builder provideBuilder() {
        return Expansion.builder("player")
                .audiencePlaceholder("name", new NameResolver())
                .audiencePlaceholder("displayname", new DisplayNameResolver())
                .audiencePlaceholder(Player.class, "ping", (player, _, _) -> {
                    return Tag.preProcessParsed(Integer.toString(player.getPing()));
                })
                .audiencePlaceholder("locale", new LocaleResolver())
                .audiencePlaceholder(Player.class, "client", (player, _, _) -> {
                    final String playerClient = player.getClientBrandName();
                    return Tag.preProcessParsed(playerClient != null
                            ? playerClient
                            : "vanilla");
                })
                .audiencePlaceholder(Player.class, "world", (player, _, _) -> {
                    return Tag.preProcessParsed(player.getWorld().getName());
                })
                .audiencePlaceholder(Player.class, "team", (player, _, _) -> {
                    return Tag.selfClosingInserting(player.teamDisplayName());
                })
                .audiencePlaceholder(Player.class, "tab_header", (player, _, _) -> {
                    return Tag.selfClosingInserting(Optional.ofNullable(player.playerListHeader()).orElse(Component.empty()));
                })
                .audiencePlaceholder(Player.class, "tab_footer", (player, _, _) -> {
                    return Tag.selfClosingInserting(Optional.ofNullable(player.playerListFooter()).orElse(Component.empty()));
                })
                .audiencePlaceholder(Player.class, "statistic", new StatisticResolver());
    }
}
