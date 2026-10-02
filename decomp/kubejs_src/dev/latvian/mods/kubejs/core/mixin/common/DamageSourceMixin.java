package dev.latvian.mods.kubejs.core.mixin.common;

import dev.latvian.mods.rhino.util.RemapForJS;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@RemapPrefixForJS("kjs$")
@Mixin({DamageSource.class})
public abstract class DamageSourceMixin {
   @Shadow
   @RemapForJS("getType")
   public abstract String m_19385_();

   @Shadow
   @RemapForJS("getImmediate")
   public abstract Entity m_7640_();

   @Shadow
   @RemapForJS("getActual")
   public abstract Entity m_7639_();

   @Nullable
   public Player kjs$getPlayer() {
      return this.m_7639_() instanceof Player p ? p : null;
   }
}
