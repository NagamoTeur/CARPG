package dev.latvian.mods.kubejs.core.mixin.common;

import dev.latvian.mods.kubejs.core.ClientLevelKJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import java.util.List;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.entity.LevelEntityGetter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({ClientLevel.class})
public abstract class ClientLevelMixin implements ClientLevelKJS {
   @Shadow
   @Final
   @HideFromJS
   List<AbstractClientPlayer> f_104566_;

   @Shadow
   @HideFromJS
   public abstract List<AbstractClientPlayer> m_6907_();

   @Shadow
   @HideFromJS
   protected abstract LevelEntityGetter<Entity> m_142646_();
}
