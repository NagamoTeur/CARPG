package com.hollingsworth.arsnouveau.common.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.HolderSet.Named;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;

public class PotionEffectTags {
   private static final HashMap<TagKey<MobEffect>, ArrayList<MobEffect>> potionEffects = new HashMap<>();
   public static final TagKey<MobEffect> UNSTABLE_GIFTS = TagKey.m_203882_(Registry.f_122900_, new ResourceLocation("ars_nouveau", "unstable_gifts"));
   public static final TagKey<MobEffect> DISPEL_DENY = TagKey.m_203882_(Registry.f_122900_, new ResourceLocation("ars_nouveau", "deny_dispel"));
   public static final TagKey<MobEffect> DISPEL_ALLOW = TagKey.m_203882_(Registry.f_122900_, new ResourceLocation("ars_nouveau", "allow_dispel"));

   public static ArrayList<MobEffect> getEffects(TagKey<MobEffect> tag) {
      return potionEffects.computeIfAbsent(tag, _key -> {
         Optional<Named<MobEffect>> effects = Registry.f_122823_.m_203431_(tag);
         if (effects.isEmpty()) {
            return null;
         } else {
            ArrayList<MobEffect> effectList = new ArrayList<>();

            for (Holder<MobEffect> mobEffectHolder : effects.get()) {
               effectList.add((MobEffect)mobEffectHolder.get());
            }

            return effectList;
         }
      });
   }
}
