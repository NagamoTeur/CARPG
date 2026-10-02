package com.aqutheseal.celestisynth.api.skill;

import com.aqutheseal.celestisynth.common.attack.base.WeaponAttackInstance;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public interface ISkillInstance {
   String getName();

   Component getDescription();

   Component getCriterionDescription();

   ResourceLocation getIcon();

   @Nullable
   WeaponAttackInstance getAttack();

   ISkillTier getTier();

   @Nullable
   AbstractSkillTree getSkillTree();

   int getMinSkillLevel();

   int getMaxSkillLevel();

   boolean isPassive();

   boolean getActivationConditions(Player var1);

   boolean getDeactivationConditions(Player var1);

   void onSkillLearned(Player var1);

   void onSkillUnlearned(Player var1);

   void onSkillLevelChanged(Player var1, boolean var2);

   void onSkillActivated(Player var1);

   void onSkillTick(Player var1);

   void onPassiveTick(Player var1);

   void onSkillDeactivated(Player var1);

   void setSkillLevel(int var1);

   void setTier(ISkillTier var1);

   void setAttack(@Nullable WeaponAttackInstance var1, boolean var2);

   void setDescription(Component var1);

   CompoundTag serializeToNBT();
}
