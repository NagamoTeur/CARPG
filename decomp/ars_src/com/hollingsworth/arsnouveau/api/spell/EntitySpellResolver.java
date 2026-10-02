package com.hollingsworth.arsnouveau.api.spell;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class EntitySpellResolver extends SpellResolver {
   public EntitySpellResolver(SpellContext context) {
      super(context);
   }

   public void onCastOnEntity(LivingEntity target) {
      super.onCastOnEntity(ItemStack.f_41583_, target, InteractionHand.MAIN_HAND);
   }

   @Override
   boolean enoughMana(LivingEntity entity) {
      return true;
   }
}
