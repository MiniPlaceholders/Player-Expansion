package io.github.miniplaceholders.expansion.player.paper;

import io.github.miniplaceholders.api.Expansion;
import io.github.miniplaceholders.expansion.player.common.DisplayNamePlaceholder;
import io.github.miniplaceholders.expansion.player.common.LocalePlaceholder;
import io.github.miniplaceholders.expansion.player.common.NamePlaceholder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;
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
                })
                .build()
                .register();
    }
}
