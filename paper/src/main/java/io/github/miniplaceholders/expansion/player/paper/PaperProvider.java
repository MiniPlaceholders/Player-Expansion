package io.github.miniplaceholders.expansion.player.paper;

import io.github.miniplaceholders.api.Expansion;
import io.github.miniplaceholders.expansion.player.common.PlatformExpansionProvider;
import io.github.miniplaceholders.expansion.player.common.resolver.DisplayNameResolver;
import io.github.miniplaceholders.expansion.player.common.resolver.LocaleResolver;
import io.github.miniplaceholders.expansion.player.common.resolver.NameResolver;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Locale;
import java.util.Optional;

public final class PaperProvider extends PlatformExpansionProvider<Server> {

    public PaperProvider(Object platformInstance) {
        super((Server) platformInstance);
    }

    @Override
    public Expansion.Builder provideBuilder() {
        return Expansion.builder("player")
                .audiencePlaceholder("name", new NameResolver())
                .audiencePlaceholder("displayname", new DisplayNameResolver())
                .audiencePlaceholder(Player.class, "ping", (player, queue, ctx) -> {
                    return Tag.preProcessParsed(Integer.toString(player.getPing()));
                })
                .audiencePlaceholder("locale", new LocaleResolver())
                .audiencePlaceholder(Player.class, "client", (player, queue, ctx) -> {
                    final String playerClient = player.getClientBrandName();
                    return Tag.preProcessParsed(playerClient != null
                            ? playerClient
                            : "vanilla");
                })
                .audiencePlaceholder(Player.class, "world", (player, queue, ctx) -> {
                    return Tag.preProcessParsed(player.getWorld().getName());
                })
                .audiencePlaceholder(Player.class, "team", (player, queue, ctx) -> {
                    return Tag.selfClosingInserting(player.teamDisplayName());
                })
                .audiencePlaceholder(Player.class, "tab_header", (player, queue, ctx) -> {
                    return Tag.selfClosingInserting(Optional.ofNullable(player.playerListHeader()).orElse(Component.empty()));
                })
                .audiencePlaceholder(Player.class, "tab_footer", (player, queue, ctx) -> {
                    return Tag.selfClosingInserting(Optional.ofNullable(player.playerListFooter()).orElse(Component.empty()));
                })
                .audiencePlaceholder("statistic", (aud, queue, ctx) -> {
                    if (!queue.hasNext()) {
                        return Tag.preProcessParsed("You need to provide a statistic");
                    }

                    Statistic statistic;
                    try {
                        statistic = Statistic.valueOf(queue.pop().value().toUpperCase(Locale.ROOT));
                    } catch (IllegalArgumentException e) {
                        return Tag.preProcessParsed("Unknown statistic");
                    }

                    final Player player = (Player) aud;

                    switch (statistic.getType()) {
                        case UNTYPED -> {
                            return Tag.preProcessParsed(String.valueOf(player.getStatistic(statistic)));
                        }
                        case ITEM, BLOCK -> {
                            if (!queue.hasNext()) {
                                return Tag.preProcessParsed("You need to provide a material");
                            }

                            Material material;
                            try {
                                material = Material.valueOf(queue.pop().value().toUpperCase(Locale.ROOT));
                            } catch (IllegalArgumentException e) {
                                return Tag.preProcessParsed("Unknown material");
                            }

                            return Tag.preProcessParsed(String.valueOf(player.getStatistic(statistic, material)));
                        }
                        case ENTITY -> {
                            if (!queue.hasNext()) {
                                return Tag.preProcessParsed("You need to provide an entity");
                            }

                            EntityType entity;
                            try {
                                entity = EntityType.valueOf(queue.pop().value().toUpperCase(Locale.ROOT));
                            } catch (IllegalArgumentException e) {
                                return Tag.preProcessParsed("Unknown entity");
                            }

                            return Tag.preProcessParsed(String.valueOf(player.getStatistic(statistic, entity)));
                        }
                        default -> {
                            return Tag.preProcessParsed("Unknown statistic type");
                        }
                    }
                });
    }
}
