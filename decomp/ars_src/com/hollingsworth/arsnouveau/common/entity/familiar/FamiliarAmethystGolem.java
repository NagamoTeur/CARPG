package com.hollingsworth.arsnouveau.common.entity.familiar;

import com.hollingsworth.arsnouveau.common.entity.ModEntities;
import com.hollingsworth.arsnouveau.common.potions.ModPotions;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags.Items;
import software.bernie.ars_nouveau.geckolib3.core.PlayState;
import software.bernie.ars_nouveau.geckolib3.core.builder.AnimationBuilder;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;

public class FamiliarAmethystGolem extends FamiliarEntity {
   public static final Map<String, ResourceLocation> Variants = new HashMap<>();

   public FamiliarAmethystGolem(EntityType<? extends PathfinderMob> p_i48575_1_, Level p_i48575_2_) {
      super(p_i48575_1_, p_i48575_2_);
   }

   protected InteractionResult m_6071_(Player player, InteractionHand hand) {
      if (!this.f_19853_.f_46443_ && hand == InteractionHand.MAIN_HAND) {
         if (player.m_21205_().m_204117_(Items.GEMS_AMETHYST)) {
            player.m_7292_(new MobEffectInstance((MobEffect)ModPotions.DEFENCE_EFFECT.get(), 3600));
            player.m_21205_().m_41774_(1);
            return InteractionResult.SUCCESS;
         } else {
            return super.m_6071_(player, hand);
         }
      } else {
         return InteractionResult.SUCCESS;
      }
   }

   @Override
   public PlayState walkPredicate(AnimationEvent<?> animationEvent) {
      if (animationEvent.isMoving()) {
         animationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("run"));
         return PlayState.CONTINUE;
      } else {
         return PlayState.STOP;
      }
   }

   public EntityType<?> m_6095_() {
      return (EntityType<?>)ModEntities.FAMILIAR_AMETHYST_GOLEM.get();
   }

   public ResourceLocation getTexture(FamiliarEntity entity) {
      return Variants.getOrDefault(entity.getColor(), Variants.get("default"));
   }

   static {
      Variants.put("default", new ResourceLocation("ars_nouveau", "textures/entity/amethyst_golem.png"));
   }
}
