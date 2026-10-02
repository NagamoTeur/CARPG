package com.hollingsworth.arsnouveau.common.entity.familiar;

import com.hollingsworth.arsnouveau.api.event.SpellModifierEvent;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.common.entity.EntityDrygmy;
import com.hollingsworth.arsnouveau.common.entity.ModEntities;
import java.util.Arrays;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags.Items;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import software.bernie.ars_nouveau.geckolib3.core.PlayState;
import software.bernie.ars_nouveau.geckolib3.core.builder.AnimationBuilder;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;

public class FamiliarDrygmy extends FamiliarEntity implements ISpellCastListener {
   public FamiliarDrygmy(EntityType<? extends PathfinderMob> ent, Level world) {
      super(ent, world);
   }

   protected InteractionResult m_6071_(Player player, InteractionHand hand) {
      if (!this.f_19853_.f_46443_ && hand == InteractionHand.MAIN_HAND) {
         ItemStack stack = player.m_21120_(hand);
         if (player.m_21205_().m_204117_(Items.DYES)) {
            DyeColor color = DyeColor.getColor(stack);
            if (color != null
               && !((String)this.f_19804_.m_135370_(COLOR)).equals(color.m_41065_())
               && Arrays.asList(EntityDrygmy.COLORS).contains(color.m_41065_())) {
               this.setColor(color);
               return InteractionResult.SUCCESS;
            } else {
               return InteractionResult.SUCCESS;
            }
         } else {
            return super.m_6071_(player, hand);
         }
      } else {
         return InteractionResult.SUCCESS;
      }
   }

   @Override
   public void onModifier(SpellModifierEvent event) {
      if (this.m_6084_() && this.getOwner() != null && this.getOwner().equals(event.caster) && SpellSchools.ELEMENTAL_EARTH.isPartOfSchool(event.spellPart)) {
         event.builder.addDamageModifier(2.0);
      }
   }

   public void onLootingEvent(LootingLevelEvent event) {
      if (event.getDamageSource() != null
         && this.m_6084_()
         && this.getOwner() != null
         && event.getDamageSource().m_7639_() != null
         && this.getOwner().equals(event.getDamageSource().m_7639_())
         && (double)this.f_19853_.f_46441_.m_188501_() > 0.4) {
         event.setLootingLevel(event.getLootingLevel() + 1 + this.f_19796_.m_188503_(3));
      }
   }

   @Override
   public PlayState walkPredicate(AnimationEvent<?> event) {
      if (event.isMoving()) {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("run"));
         return PlayState.CONTINUE;
      } else {
         return PlayState.STOP;
      }
   }

   public EntityType<?> m_6095_() {
      return (EntityType<?>)ModEntities.ENTITY_FAMILIAR_DRYGMY.get();
   }

   public ResourceLocation getTexture(FamiliarEntity entity) {
      String color = this.getColor().toLowerCase();
      if (color.isEmpty()) {
         color = "brown";
      }

      return new ResourceLocation("ars_nouveau", "textures/entity/drygmy_" + color + ".png");
   }
}
