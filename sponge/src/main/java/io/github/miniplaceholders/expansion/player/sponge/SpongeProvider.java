package io.github.miniplaceholders.expansion.player.sponge;

import io.github.miniplaceholders.api.Expansion;
import io.github.miniplaceholders.expansion.player.common.PlatformExpansionProvider;
import io.github.miniplaceholders.expansion.player.common.resolver.DisplayNameResolver;
import io.github.miniplaceholders.expansion.player.common.resolver.LocaleResolver;
import io.github.miniplaceholders.expansion.player.common.resolver.NameResolver;
import net.kyori.adventure.text.minimessage.tag.Tag;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;

public final class SpongeProvider implements PlatformExpansionProvider {
    @Override
    public Expansion.Builder provideBuilder() {
        return Expansion.builder("player")
                .audiencePlaceholder("name", new NameResolver())
                .audiencePlaceholder("displayname", new DisplayNameResolver())
                .audiencePlaceholder("locale", new LocaleResolver())
                .audiencePlaceholder(ServerPlayer.class, "world", (player, _, _) -> {
                    return Tag.preProcessParsed(player.world().key().formatted());
                });
    }
}
