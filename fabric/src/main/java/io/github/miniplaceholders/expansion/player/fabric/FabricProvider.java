package io.github.miniplaceholders.expansion.player.fabric;

import io.github.miniplaceholders.api.Expansion;
import io.github.miniplaceholders.expansion.player.common.PlatformExpansionProvider;
import io.github.miniplaceholders.expansion.player.common.resolver.DisplayNameResolver;
import io.github.miniplaceholders.expansion.player.common.resolver.LocaleResolver;
import io.github.miniplaceholders.expansion.player.common.resolver.NameResolver;
import net.minecraft.server.MinecraftServer;

public class FabricProvider extends PlatformExpansionProvider<MinecraftServer> {
    public FabricProvider(Object platformInstance) {
        super((MinecraftServer) platformInstance);
    }

    @Override
    public Expansion.Builder provideBuilder() {
        return Expansion.builder("player")
                .audiencePlaceholder("name", new NameResolver())
                .audiencePlaceholder("displayname", new DisplayNameResolver())
                .audiencePlaceholder("locale", new LocaleResolver());
    }
}
