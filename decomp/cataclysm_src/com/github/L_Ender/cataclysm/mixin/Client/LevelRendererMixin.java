package com.github.L_Ender.cataclysm.mixin.Client;

import com.min01.archaeology.block.BrushableBlock;
import javax.annotation.Nullable;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({LevelRenderer.class})
public abstract class LevelRendererMixin {
   @Shadow
   @Nullable
   private ClientLevel f_109465_;

   @Inject(
      method = {"levelEvent"},
      at = {@At("HEAD")}
   )
   private void handleCustomLevelEvents(int type, BlockPos position, int data, CallbackInfo callback) {
      if (type == 3008) {
         BlockState blockState2 = Block.m_49803_(data);
         if (blockState2.m_60734_() instanceof BrushableBlock brushableBlock) {
            this.f_109465_.m_104677_(position, brushableBlock.getBrushCompletedSound(), SoundSource.PLAYERS, 1.0F, 1.0F, false);
         }

         this.f_109465_.m_142052_(position, blockState2);
      }
   }
}
