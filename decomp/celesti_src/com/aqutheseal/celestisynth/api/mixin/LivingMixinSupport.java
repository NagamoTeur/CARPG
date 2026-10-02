package com.aqutheseal.celestisynth.api.mixin;

import javax.annotation.Nullable;
import net.minecraft.world.entity.player.Player;

public interface LivingMixinSupport {
   @Nullable
   Player getPhantomTagger();

   void setPhantomTagger(@Nullable Player var1);

   @Nullable
   Player getQuasarImbued();

   void setQuasarImbued(@Nullable Player var1);
}
