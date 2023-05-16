package net.lapismc.lapisjoinavatar;

import net.lapismc.lapiscore.LapisCoreConfiguration;
import net.lapismc.lapiscore.LapisCorePlugin;
import net.lapismc.lapiscore.utils.LapisCoreFileWatcher;
import net.lapismc.lapisjoinavatar.util.AvatarGenerator;

public final class LapisJoinAvatar extends LapisCorePlugin {

    public AvatarGenerator generator;

    @Override
    public void onEnable() {
        generator = new AvatarGenerator(this);
        registerConfiguration(new LapisCoreConfiguration(this, 1, 1));
        fileWatcher = new LapisCoreFileWatcher(this);
        tasks.addShutdownTask(() -> fileWatcher.stop());
        new JoinManager(this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
