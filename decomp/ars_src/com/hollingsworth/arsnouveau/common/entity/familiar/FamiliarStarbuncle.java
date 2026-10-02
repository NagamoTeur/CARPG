package com.hollingsworth.arsnouveau.common.entity.familiar;

import com.hollingsworth.arsnouveau.api.scrying.CompoundScryer;
import com.hollingsworth.arsnouveau.api.scrying.TagScryer;
import com.hollingsworth.arsnouveau.common.entity.ModEntities;
import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import com.hollingsworth.arsnouveau.common.ritual.RitualScrying;
import java.util.Arrays;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags.Blocks;
import net.minecraftforge.common.Tags.Items;
import software.bernie.ars_nouveau.geckolib3.core.PlayState;
import software.bernie.ars_nouveau.geckolib3.core.builder.AnimationBuilder;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;

public class FamiliarStarbuncle extends FamiliarEntity {
   public FamiliarStarbuncle(EntityType<? extends PathfinderMob> ent, Level world) {
      super(ent, world);
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      if (!this.f_19853_.f_46443_ && this.f_19853_.m_46467_() % 60L == 0L && this.getOwner() != null) {
         this.getOwner().m_7292_(new MobEffectInstance(MobEffects.f_19596_, 600, 1, false, false, true));
         this.m_7292_(new MobEffectInstance(MobEffects.f_19596_, 600, 1, false, false, true));
      }
   }

   protected InteractionResult m_6071_(Player player, InteractionHand hand) {
      if (!player.f_19853_.f_46443_ && player.equals(this.getOwner())) {
         ItemStack stack = player.m_21120_(hand);
         if (stack.m_204117_(Items.NUGGETS_GOLD)) {
            stack.m_41774_(1);
            RitualScrying.grantScrying((ServerPlayer)player, 3600, new CompoundScryer(new TagScryer(Blocks.ORES_GOLD), new TagScryer(BlockTags.f_13043_)));
            return InteractionResult.SUCCESS;
         }

         if (player.m_21205_().m_204117_(Items.DYES)) {
            DyeColor color = DyeColor.getColor(stack);
            if (color != null
               && !((String)this.f_19804_.m_135370_(COLOR)).equals(color.m_41065_())
               && Arrays.asList(Starbuncle.carbyColors).contains(color.m_41065_())) {
               this.setColor(color);
               return InteractionResult.SUCCESS;
            }

            return InteractionResult.SUCCESS;
         }
      }

      return super.m_6071_(player, hand);
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

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135381_(COLOR, DyeColor.ORANGE.m_41065_());
   }

   public ResourceLocation getTexture(FamiliarEntity entity) {
      String color = this.getColor();
      if (color.isEmpty()) {
         color = DyeColor.ORANGE.m_41065_();
      }

      return new ResourceLocation("ars_nouveau", "textures/entity/starbuncle_" + color.toLowerCase() + ".png");
   }

   public EntityType<?> m_6095_() {
      return (EntityType<?>)ModEntities.ENTITY_FAMILIAR_STARBUNCLE.get();
   }
}
