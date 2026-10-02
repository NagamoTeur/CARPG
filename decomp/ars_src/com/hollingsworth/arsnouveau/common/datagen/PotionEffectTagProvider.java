package com.hollingsworth.arsnouveau.common.datagen;

import com.hollingsworth.arsnouveau.common.lib.PotionEffectTags;
import com.hollingsworth.arsnouveau.common.potions.ModPotions;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class PotionEffectTagProvider extends TagsProvider<MobEffect> {
   public PotionEffectTagProvider(DataGenerator pGenerator, String modId, @Nullable ExistingFileHelper existingFileHelper) {
      super(pGenerator, Registry.f_122823_, modId, existingFileHelper);
   }

   protected void m_6577_() {
      this.m_206424_(PotionEffectTags.UNSTABLE_GIFTS)
         .m_126584_(
            new MobEffect[]{
               MobEffects.f_19591_,
               MobEffects.f_19611_,
               MobEffects.f_19592_,
               MobEffects.f_19617_,
               MobEffects.f_19600_,
               MobEffects.f_19607_,
               MobEffects.f_19598_,
               MobEffects.f_19596_,
               MobEffects.f_19605_,
               MobEffects.f_19606_
            }
         );
      this.m_206424_(PotionEffectTags.DISPEL_DENY).m_126582_((MobEffect)ModPotions.SUMMONING_SICKNESS_EFFECT.get());
   }
}
