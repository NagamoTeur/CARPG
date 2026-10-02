package com.aqutheseal.celestisynth.common.attack.breezebreaker;

import com.aqutheseal.celestisynth.common.attack.base.WeaponAttackInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public abstract class BreezebreakerAttack extends WeaponAttackInstance {
   public BreezebreakerAttack(Player player, ItemStack stack, int heldDuration) {
      super(player, stack, heldDuration);
   }

   public void addComboPoint() {
      if (this.getTagExtras().m_128451_("cs.bbCombo") < 15) {
         this.getPlayer().m_216990_(SoundEvents.f_11871_);
         this.getTagExtras().m_128405_("cs.bbCombo", this.getTagExtras().m_128451_("cs.bbCombo") + 1);
      } else {
         this.getPlayer().m_216990_(SoundEvents.f_11736_);
         this.getTagExtras().m_128379_("cs.bbBuffState", !this.getTagExtras().m_128471_("cs.bbBuffState"));
         this.getTagExtras().m_128405_("cs.bbCombo", 0);
      }
   }

   @Override
   public void startUsing() {
      this.addComboPoint();
   }

   public int buffStateModified(int originalValue) {
      return this.getTagExtras().m_128471_("cs.bbBuffState") ? originalValue / 2 : originalValue;
   }
}
