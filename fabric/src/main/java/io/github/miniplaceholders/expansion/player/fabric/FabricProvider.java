package io.github.miniplaceholders.expansion.player.fabric;

import io.github.miniplaceholders.api.Expansion;
import io.github.miniplaceholders.expansion.player.common.PlatformExpansionProvider;
import io.github.miniplaceholders.expansion.player.common.resolver.DisplayNameResolver;
import io.github.miniplaceholders.expansion.player.common.resolver.LocaleResolver;
import io.github.miniplaceholders.expansion.player.common.resolver.NameResolver;

public final class FabricProvider implements PlatformExpansionProvider {
    @Override
    public Expansion.Builder provideBuilder() {
        return Expansion.builder("player")
                .audiencePlaceholder("name", new NameResolver())
                .audiencePlaceholder("displayname", new DisplayNameResolver())
                .audiencePlaceholder("locale", new LocaleResolver());
    }
}
