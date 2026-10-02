package immersive_armors.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({Minecraft.class})
public interface MixinMinecraftClient {
   @Accessor("paused")
   boolean getPaused();

   @Accessor("pausedTickDelta")
   float getPausedTickDelta();
}
