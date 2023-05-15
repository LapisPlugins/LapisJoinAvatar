package net.lapismc.lapisavatarjoin;

import net.lapismc.lapisavatarjoin.util.AvatarGenerator;
import net.lapismc.lapiscore.LapisCoreConfiguration;
import net.lapismc.lapiscore.LapisCorePlugin;

public final class LapisAvatarJoin extends LapisCorePlugin {

    public AvatarGenerator generator;

    @Override
    public void onEnable() {
        generator = new AvatarGenerator(this);
        registerConfiguration(new LapisCoreConfiguration(this, 1, 1));
        new JoinManager(this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
