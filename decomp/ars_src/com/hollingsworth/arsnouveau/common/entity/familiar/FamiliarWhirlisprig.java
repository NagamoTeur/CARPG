package com.hollingsworth.arsnouveau.common.entity.familiar;

import com.hollingsworth.arsnouveau.api.event.SpellCastEvent;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.common.entity.ModEntities;
import com.hollingsworth.arsnouveau.common.entity.Whirlisprig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Finish;
import software.bernie.ars_nouveau.geckolib3.core.PlayState;
import software.bernie.ars_nouveau.geckolib3.core.builder.AnimationBuilder;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;

public class FamiliarWhirlisprig extends FlyingFamiliarEntity implements ISpellCastListener {
   public FamiliarWhirlisprig(EntityType<? extends PathfinderMob> ent, Level world) {
      super(ent, world);
   }

   public InteractionResult m_7111_(Player pPlayer, Vec3 pVec, InteractionHand hand) {
      if (hand == InteractionHand.MAIN_HAND && !pPlayer.m_20193_().f_46443_) {
         ItemStack stack = pPlayer.m_21120_(hand);
         String color = Whirlisprig.getColorFromStack(stack);
         if (color != null && !this.getColor().equals(color)) {
            this.setColor(color);
            stack.m_41774_(1);
            return InteractionResult.SUCCESS;
         } else {
            return super.m_7111_(pPlayer, pVec, hand);
         }
      } else {
         return InteractionResult.PASS;
      }
   }

   @Override
   public void onCast(SpellCastEvent event) {
      if (this.m_6084_()) {
         if (this.getOwner() != null && this.getOwner().equals(event.getEntity())) {
            int discount = 0;

            for (AbstractSpellPart part : event.spell.recipe) {
               if (SpellSchools.ELEMENTAL_EARTH.isPartOfSchool(part)) {
                  discount = (int)((double)discount + (double)part.getCastingCost() * 0.5);
               }
            }

            event.spell.addDiscount(discount);
         }
      }
   }

   public void eatEvent(Finish event) {
      if (this.m_6084_()) {
         if (!event.getEntity().f_19853_.f_46443_ && this.getOwner() != null && this.getOwner().equals(event.getEntity())) {
            FoodProperties food = event.getItem().m_41720_().getFoodProperties(event.getItem(), this.getOwner());
            if (food != null && event.getItem().m_41720_().m_41472_()) {
               float saturationModifier = food.m_38745_();
               int nutrition = food.m_38744_();
               float satAmount = (float)nutrition * saturationModifier * 2.0F;
               if (event.getEntity() instanceof Player) {
                  FoodData stats = ((Player)event.getEntity()).m_36324_();
                  stats.f_38697_ += satAmount * 0.4F;
               }
            }
         }
      }
   }

   @Override
   public PlayState walkPredicate(AnimationEvent<?> event) {
      if (event.isMoving()) {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("fly"));
      } else {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("idle"));
      }

      return PlayState.CONTINUE;
   }

   public EntityType<?> m_6095_() {
      return (EntityType<?>)ModEntities.ENTITY_FAMILIAR_SYLPH.get();
   }

   public ResourceLocation getTexture(FamiliarEntity entity) {
      return new ResourceLocation(
         "ars_nouveau", "textures/entity/whirlisprig_" + (this.getColor().isEmpty() ? "summer" : this.getColor().toLowerCase()) + ".png"
      );
   }
}
