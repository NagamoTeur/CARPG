package com.github.L_Ender.cataclysm.mixin;

import com.github.L_Ender.cataclysm.entity.Pet.Modern_Remnant_Entity;
import com.min01.archaeology.block.BrushableBlock;
import com.min01.archaeology.blockentity.BrushableBlockEntity;
import com.min01.archaeology.init.ArchaeologyItems;
import com.min01.archaeology.init.ArchaeologySounds;
import com.min01.archaeology.item.BrushItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.event.ForgeEventFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({BrushItem.class})
public class BrushItemMixin {
   @Inject(
      method = {"onUseTick"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void onUseTick(Level p_273467_, LivingEntity p_273619_, ItemStack p_273316_, int p_273101_, CallbackInfo ci) {
      ci.cancel();
      if (p_273101_ >= 0 && p_273619_ instanceof Player player) {
         HitResult hitresult = this.calculateHitResult(p_273619_);
         if (hitresult instanceof BlockHitResult blockhitresult) {
            if (hitresult.m_6662_() == Type.BLOCK) {
               int i = BrushItem.class.cast(this).m_8105_(p_273316_) - p_273101_ + 1;
               boolean flag = i % 10 == 5;
               if (flag) {
                  BlockPos blockpos = blockhitresult.m_82425_();
                  BlockState blockstate = p_273467_.m_8055_(blockpos);
                  HumanoidArm humanoidarm = p_273619_.m_7655_() == InteractionHand.MAIN_HAND ? player.m_5737_() : player.m_5737_().m_20828_();
                  this.spawnDustParticles(p_273467_, blockhitresult, blockstate, p_273619_.m_20252_(0.0F), humanoidarm);
                  SoundEvent soundevent;
                  if (blockstate.m_60734_() instanceof BrushableBlock brushableblock) {
                     soundevent = brushableblock.getBrushSound();
                  } else {
                     soundevent = (SoundEvent)ArchaeologySounds.BRUSH_GENERIC.get();
                  }

                  p_273467_.m_5594_(player, blockpos, soundevent, SoundSource.BLOCKS, 1.0F, 1.0F);
                  if (!p_273467_.m_5776_() && p_273467_.m_7702_(blockpos) instanceof BrushableBlockEntity brushableblockentity) {
                     boolean flag1 = brushableblockentity.brush(p_273467_.m_46467_(), player, blockhitresult.m_82434_());
                     if (flag1) {
                        EquipmentSlot equipmentslot = p_273316_.equals(player.m_6844_(EquipmentSlot.OFFHAND)) ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
                        p_273316_.m_41622_(1, p_273619_, p_279044_ -> p_279044_.m_21166_(equipmentslot));
                     }
                  }
               }

               return;
            }
         } else if (hitresult instanceof EntityHitResult entityhitresult && hitresult.m_6662_() == Type.ENTITY) {
            if (entityhitresult.m_82443_() instanceof Modern_Remnant_Entity remnant) {
               int i = BrushItem.class.cast(this).m_8105_(p_273316_) - p_273101_ + 1;
               boolean flag = i % 10 == 5;
               if (flag) {
                  HumanoidArm humanoidarmx = p_273619_.m_7655_() == InteractionHand.MAIN_HAND ? player.m_5737_() : player.m_5737_().m_20828_();
                  this.spawnDustParticlesToRemnant(p_273467_, remnant, humanoidarmx);
                  p_273467_.m_5594_(player, remnant.m_20183_(), (SoundEvent)ArchaeologySounds.BRUSH_GENERIC.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
                  if (!remnant.m_21824_() && p_273316_.m_41720_() == ArchaeologyItems.BRUSH.get()) {
                     if (!player.m_150110_().f_35937_) {
                        p_273316_.m_41622_(1, player, p_219739_ -> {
                           p_219739_.m_21190_(p_273619_.m_7655_());
                           ForgeEventFactory.onPlayerDestroyItem(player, p_273316_, p_273619_.m_7655_());
                        });
                     }

                     remnant.m_146850_(GameEvent.f_157806_);
                     if (!ForgeEventFactory.onAnimalTame(remnant, player) && i >= 60) {
                        remnant.m_21828_(player);
                        remnant.f_19853_.m_7605_(remnant, (byte)7);
                     } else {
                        remnant.f_19853_.m_7605_(remnant, (byte)6);
                     }
                  }
               }
            }

            return;
         }

         p_273619_.m_21253_();
      } else {
         p_273619_.m_21253_();
      }
   }

   @Unique
   private void spawnDustParticlesToRemnant(Level p_278327_, Modern_Remnant_Entity p_278272_, HumanoidArm p_285071_) {
      int i = p_285071_ == HumanoidArm.RIGHT ? 1 : -1;
      int j = p_278327_.m_213780_().m_216339_(7, 12);
      BlockParticleOption blockparticleoption = new BlockParticleOption(ParticleTypes.f_123794_, Blocks.f_49992_.m_49966_());

      for (int k = 0; k < j; k++) {
         p_278327_.m_7106_(
            blockparticleoption,
            p_278272_.m_20185_(),
            p_278272_.m_20188_(),
            p_278272_.m_20189_(),
            (double)i * 3.0 * p_278327_.m_213780_().m_188500_(),
            0.0,
            (double)i * 3.0 * p_278327_.m_213780_().m_188500_()
         );
      }
   }

   @Shadow
   public void spawnDustParticles(Level p_278327_, BlockHitResult p_278272_, BlockState p_278235_, Vec3 p_278337_, HumanoidArm p_285071_) {
   }

   @Shadow
   private HitResult calculateHitResult(LivingEntity p_281264_) {
      throw new IllegalStateException();
   }
}
