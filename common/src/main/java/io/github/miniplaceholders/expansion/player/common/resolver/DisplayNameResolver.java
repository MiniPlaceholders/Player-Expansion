package io.github.miniplaceholders.expansion.player.common.resolver;

import io.github.miniplaceholders.api.resolver.AudienceTagResolver;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import org.jetbrains.annotations.NotNull;

public final class DisplayNameResolver implements AudienceTagResolver<@NotNull Audience> {
    @Override
    public Tag tag(
            final @NotNull Audience audience,
            final @NotNull ArgumentQueue queue,
            final @NotNull Context ctx
    ) {
        return Tag.inserting(audience.getOrDefault(Identity.DISPLAY_NAME, Component.empty()));
    }
}
