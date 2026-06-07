package net.steveson.immersivetrims;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(ImmersiveTrimsMod.MOD_ID)
public class ImmersiveTrimsMod {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "immersive_trims";


    public ImmersiveTrimsMod(IEventBus modEventBus, ModContainer modContainer) {
//        NeoForge.EVENT_BUS.register(this);
    }
}
