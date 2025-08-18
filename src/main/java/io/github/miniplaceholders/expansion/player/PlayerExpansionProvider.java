package io.github.miniplaceholders.expansion.player;

import io.github.miniplaceholders.api.Expansion;
import io.github.miniplaceholders.api.MiniPlaceholders;
import io.github.miniplaceholders.api.provider.ExpansionProvider;
import io.github.miniplaceholders.api.provider.LoadRequirement;
import io.github.miniplaceholders.api.provider.PlatformData;
import io.github.miniplaceholders.expansion.player.common.PlatformExpansionProvider;
import io.github.miniplaceholders.expansion.player.fabric.FabricProvider;
import io.github.miniplaceholders.expansion.player.paper.PaperProvider;
import io.github.miniplaceholders.expansion.player.sponge.SpongeProvider;
import io.github.miniplaceholders.expansion.player.velocity.VelocityProvider;
import team.unnamed.inject.Inject;

public class PlayerExpansionProvider implements ExpansionProvider {
    @Inject
    private PlatformData platformData;

    @Override
    public Expansion provideExpansion() {
        final Object serverInstance = platformData.serverInstance();
        final PlatformExpansionProvider<?> platformProvider = switch (MiniPlaceholders.platform()) {
            case VELOCITY -> new VelocityProvider(serverInstance);
            case SPONGE -> new SpongeProvider(serverInstance);
            case PAPER -> new PaperProvider(serverInstance);
            case FABRIC -> new FabricProvider(serverInstance);
        };
        return platformProvider.provideBuilder()
                .author("MiniPlaceholders Contributors")
                .version(Constants.VERSION)
                .build();
    }

    @Override
    public LoadRequirement loadRequirement() {
        return LoadRequirement.none();
    }
}
