package io.github.miniplaceholders.expansion.player.paper.resolver;

import io.github.miniplaceholders.api.resolver.AudienceTagResolver;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public final class StatisticResolver implements AudienceTagResolver<@NotNull Audience> {
	@Override
	public @NotNull Tag tag(@NotNull Audience audience, @NotNull ArgumentQueue queue, @NotNull Context ctx) {
		if (!queue.hasNext()) {
			return Tag.preProcessParsed("You need to provide a statistic");
		}

		Statistic statistic;
		try {
			statistic = Statistic.valueOf(queue.pop().value().toUpperCase(Locale.ROOT));
		} catch (IllegalArgumentException e) {
			return Tag.preProcessParsed("Unknown statistic");
		}

		final Player player = (Player) audience;

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
	}
}
