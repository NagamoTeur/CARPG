package com.hollingsworth.arsnouveau.common.items.curios;

import com.hollingsworth.arsnouveau.api.item.ArsNouveauCurio;
import com.hollingsworth.arsnouveau.common.lib.PotionEffectTags;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class BeltOfUnstableGifts extends ArsNouveauCurio {
   public void curioTick(SlotContext slotContext, ItemStack stack) {
      ArrayList<MobEffect> effectTable = PotionEffectTags.getEffects(PotionEffectTags.UNSTABLE_GIFTS);
      if (effectTable != null && effectTable.size() != 0) {
         LivingEntity wearer = slotContext.entity();
         if (wearer != null) {
            if (slotContext.entity().m_9236_() instanceof ServerLevel world && world.m_46467_() % 120L == 0L) {
               wearer.m_7292_(new MobEffectInstance(effectTable.get(new Random().nextInt(effectTable.size())), 120, new Random().nextInt(3)));
            }
         }
      }
   }
}
