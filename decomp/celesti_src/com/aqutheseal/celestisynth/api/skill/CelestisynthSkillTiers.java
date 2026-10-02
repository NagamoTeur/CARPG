package com.aqutheseal.celestisynth.api.skill;

import com.aqutheseal.celestisynth.Celestisynth;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public enum CelestisynthSkillTiers implements ISkillTier {
   BASIC("basic", Component.m_237115_("celestisynth.skill.tier.basic"), Celestisynth.prefix("skills/tiers/basic.png"), 0),
   ADVANCED("advanced", Component.m_237115_("celestisynth.skill.tier.advanced"), Celestisynth.prefix("skills/tiers/advanced.png"), 1),
   EXPERT("expert", Component.m_237115_("celestisynth.skill.tier.expert"), Celestisynth.prefix("skills/tiers/expert.png"), 2);

   private final String tierName;
   private final Component tierDescription;
   private final ResourceLocation tierIcon;
   private final int weight;

   private CelestisynthSkillTiers(String tierName, Component tierDescription, ResourceLocation tierIcon, int weight) {
      this.tierName = tierName;
      this.tierDescription = tierDescription;
      this.tierIcon = tierIcon;
      this.weight = weight;
   }

   @Override
   public String getTierName() {
      return this.tierName;
   }

   @Override
   public Component getTierDescription() {
      return this.tierDescription;
   }

   @Override
   public ResourceLocation getTierIcon() {
      return this.tierIcon;
   }

   @Override
   public int getWeight() {
      return this.weight;
   }
}
