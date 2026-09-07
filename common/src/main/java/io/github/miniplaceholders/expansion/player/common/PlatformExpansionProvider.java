package io.github.miniplaceholders.expansion.player.common;

import io.github.miniplaceholders.api.Expansion;

public interface PlatformExpansionProvider {

    Expansion.Builder provideBuilder();
}
