package io.github.miniplaceholders.expansion.player;

import io.github.miniplaceholders.api.Expansion;
import io.github.miniplaceholders.api.MiniPlaceholders;
import io.github.miniplaceholders.api.provider.ExpansionProvider;
import io.github.miniplaceholders.api.provider.LoadRequirement;
import io.github.miniplaceholders.api.types.Platform;
import io.github.miniplaceholders.expansion.player.common.PlatformExpansionProvider;
import io.github.miniplaceholders.expansion.player.fabric.FabricProvider;
import io.github.miniplaceholders.expansion.player.paper.PaperProvider;
import io.github.miniplaceholders.expansion.player.sponge.SpongeProvider;
import io.github.miniplaceholders.expansion.player.velocity.VelocityProvider;
import org.jspecify.annotations.NullUnmarked;

@NullUnmarked
public class PlayerExpansionProvider implements ExpansionProvider {

    @Override
    public Expansion provideExpansion() {
        final PlatformExpansionProvider platformProvider = switch (MiniPlaceholders.platform()) {
            case VELOCITY -> new VelocityProvider();
            case SPONGE -> new SpongeProvider();
            case PAPER -> new PaperProvider();
            case FABRIC -> new FabricProvider();
            case MINESTOM -> throw new UnsupportedOperationException("Minestom platform is not supported");
        };
        return platformProvider.provideBuilder()
                .author("MiniPlaceholders Contributors")
                .version(Constants.VERSION)
                .build();
    }

    @Override
    public LoadRequirement loadRequirement() {
        return LoadRequirement.platform(
            Platform.FABRIC,
            Platform.PAPER,
            Platform.SPONGE,
            Platform.VELOCITY
        );
    }
}
