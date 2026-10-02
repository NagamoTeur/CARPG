package com.hollingsworth.arsnouveau.common.items.curios;

import com.hollingsworth.arsnouveau.api.item.ArsNouveauCurio;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.SlotContext;

public class BeltOfLevitation extends ArsNouveauCurio {
   public void curioTick(SlotContext context, ItemStack stack) {
      if (context.entity() instanceof Player player && !player.f_36077_.f_35935_) {
         Level world = player.m_20193_();
         if (!player.m_20096_() && player.m_6144_() && !world.m_5776_()) {
            boolean isTooHigh = true;

            for (int i = 1; i < 6; i++) {
               if (world.m_8055_(player.m_20183_().m_6625_(i)).m_60767_() != Material.f_76296_) {
                  isTooHigh = false;
                  break;
               }
            }

            if (isTooHigh) {
               player.m_7292_(new MobEffectInstance(MobEffects.f_19591_, 5, 2));
            } else {
               player.m_7292_(new MobEffectInstance(MobEffects.f_19620_, 5, 2));
            }

            player.f_19789_ = 0.0F;
         }

         if (world.m_5776_()) {
            Vec3 oldMotion = player.m_20184_();
            double y = oldMotion.m_7098_();
            Vec3 motion = player.m_20184_().m_82490_(1.1);
            if (Math.sqrt(motion.m_82553_()) > 0.6) {
               return;
            }

            player.m_6001_(motion.f_82479_, y, motion.f_82481_);
            player.f_19864_ = true;
         }
      }
   }
}
