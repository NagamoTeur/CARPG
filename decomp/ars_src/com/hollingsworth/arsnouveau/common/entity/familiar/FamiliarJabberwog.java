package com.hollingsworth.arsnouveau.common.entity.familiar;

import com.hollingsworth.arsnouveau.api.event.SpellModifierEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import software.bernie.ars_nouveau.geckolib3.core.PlayState;
import software.bernie.ars_nouveau.geckolib3.core.builder.AnimationBuilder;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;

public class FamiliarJabberwog extends FlyingFamiliarEntity {
   public FamiliarJabberwog(EntityType<? extends PathfinderMob> ent, Level world) {
      super(ent, world);
   }

   public void spellResolveEvent(SpellModifierEvent event) {
      if (this.m_6084_() && this.getOwner() != null && this.getOwner().equals(event.caster)) {
         event.builder.addDamageModifier(3.0);
      }
   }

   @Override
   public PlayState walkPredicate(AnimationEvent<?> event) {
      if (event.isMoving()) {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("hop"));
         return PlayState.CONTINUE;
      } else {
         return PlayState.STOP;
      }
   }

   public EntityType<?> m_6095_() {
      return null;
   }
}
