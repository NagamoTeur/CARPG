package com.aqutheseal.celestisynth.api.skill;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public interface ISkillTier {
   String getTierName();

   Component getTierDescription();

   ResourceLocation getTierIcon();

   int getWeight();
}
