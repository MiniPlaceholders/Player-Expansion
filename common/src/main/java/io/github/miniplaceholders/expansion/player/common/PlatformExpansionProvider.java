package io.github.miniplaceholders.expansion.player.common;

import io.github.miniplaceholders.api.Expansion;

public abstract class PlatformExpansionProvider<T> {
    protected T platformInstance;

    protected PlatformExpansionProvider(T platformInstance) {
        this.platformInstance = platformInstance;
    }

    public abstract Expansion.Builder provideBuilder();
}
