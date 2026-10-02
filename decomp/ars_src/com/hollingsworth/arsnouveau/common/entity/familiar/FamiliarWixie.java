package com.hollingsworth.arsnouveau.common.entity.familiar;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.potion.PotionData;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.block.tile.IAnimationListener;
import com.hollingsworth.arsnouveau.common.entity.EntityWixie;
import com.hollingsworth.arsnouveau.common.entity.ModEntities;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketAnimEntity;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags.Items;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.event.entity.living.MobEffectEvent.Added;
import software.bernie.ars_nouveau.geckolib3.core.PlayState;
import software.bernie.ars_nouveau.geckolib3.core.builder.AnimationBuilder;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;

public class FamiliarWixie extends FlyingFamiliarEntity implements IAnimationListener {
   public int debuffCooldown;

   public FamiliarWixie(EntityType<? extends PathfinderMob> ent, Level world) {
      super(ent, world);
   }

   protected InteractionResult m_6071_(Player player, InteractionHand hand) {
      if (!this.f_19853_.f_46443_ && hand == InteractionHand.MAIN_HAND) {
         ItemStack stack = player.m_21120_(hand);
         if (player.m_21205_().m_204117_(Items.DYES)) {
            DyeColor color = DyeColor.getColor(stack);
            if (color != null
               && !((String)this.f_19804_.m_135370_(COLOR)).equals(color.m_41065_())
               && Arrays.asList(EntityWixie.COLORS).contains(color.m_41065_())) {
               this.setColor(color);
               return InteractionResult.SUCCESS;
            } else {
               return InteractionResult.SUCCESS;
            }
         } else {
            for (BrewingRecipe r : ArsNouveauAPI.getInstance().getAllPotionRecipes()) {
               ItemStack water = PotionUtils.m_43549_(new ItemStack(net.minecraft.world.item.Items.f_42589_), Potions.f_43599_);
               ItemStack awkard = PotionUtils.m_43549_(new ItemStack(net.minecraft.world.item.Items.f_42589_), Potions.f_43602_);
               if (r.isIngredient(stack) && (r.getInput().test(awkard) || r.getInput().test(water))) {
                  PotionData data = new PotionData(r.getOutput().m_41777_());
                  if (!data.isEmpty()) {
                     data.applyEffects(player, player, player);
                     PortUtil.sendMessage(
                        player, Component.m_237110_("ars_nouveau.wixie_familiar.applied", new Object[]{data.asPotionStack().m_41786_().getString()})
                     );
                     Networking.sendToNearby(this.f_19853_, this, new PacketAnimEntity(this.m_19879_(), EntityWixie.Animations.CAST.ordinal()));
                     ParticleUtil.spawnPoof((ServerLevel)this.f_19853_, player.m_20183_().m_7494_());
                     stack.m_41774_(1);
                     return InteractionResult.SUCCESS;
                  }
               }
            }

            return super.m_6071_(player, hand);
         }
      } else {
         return InteractionResult.SUCCESS;
      }
   }

   public void potionEvent(Added event) {
      if (this.m_6084_()) {
         Entity target = event.getEntity();
         Entity applier = event.getEffectSource();
         boolean isBeneficialOwner = target.equals(this.getOwner()) && event.getEffectInstance().m_19544_().m_19486_();
         boolean isApplierOwner = applier != null && applier.equals(this.getOwner());
         if (isBeneficialOwner || isApplierOwner) {
            MobEffectInstance var10000 = event.getEffectInstance();
            var10000.f_19503_ = (int)((double)var10000.f_19503_ + (double)event.getEffectInstance().f_19503_ * 0.2);
         }
      }
   }

   @Override
   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(2, new FamiliarWixie.DebuffTargetGoal(this));
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      if (!this.f_19853_.f_46443_ && this.debuffCooldown > 0) {
         this.debuffCooldown--;
      }
   }

   @Override
   public PlayState walkPredicate(AnimationEvent<?> event) {
      return PlayState.CONTINUE;
   }

   public EntityType<?> m_6095_() {
      return (EntityType<?>)ModEntities.ENTITY_FAMILIAR_WIXIE.get();
   }

   @Override
   public void startAnimation(int arg) {
      if (this.controller != null) {
         if (arg == EntityWixie.Animations.CAST.ordinal()) {
            this.controller.markNeedsReload();
            this.controller.setAnimation(new AnimationBuilder().addAnimation("cast"));
         }
      }
   }

   public ResourceLocation getTexture(FamiliarEntity entity) {
      String color = this.getColor().toLowerCase();
      if (color.isEmpty()) {
         color = "blue";
      }

      return new ResourceLocation("ars_nouveau", "textures/entity/wixie_" + color + ".png");
   }

   public static class DebuffTargetGoal extends Goal {
      FamiliarWixie wixie;
      public static ArrayList<MobEffect> effectTable = new ArrayList<>(
         Arrays.asList(MobEffects.f_19597_, MobEffects.f_19613_, MobEffects.f_19620_, MobEffects.f_19614_)
      );

      public DebuffTargetGoal(FamiliarWixie wixie) {
         this.wixie = wixie;
      }

      public void m_8037_() {
         super.m_8037_();
         if (this.wixie.m_5448_() != null) {
            MobEffect effect = effectTable.get(new Random().nextInt(effectTable.size()));
            if (effect == MobEffects.f_19614_ && this.wixie.m_5448_().m_21222_()) {
               effect = MobEffects.f_19605_;
            }

            Networking.sendToNearby(this.wixie.f_19853_, this.wixie, new PacketAnimEntity(this.wixie.m_19879_(), EntityWixie.Animations.CAST.ordinal()));
            this.wixie.m_5448_().m_7292_(new MobEffectInstance(effect, 140, new Random().nextInt(2)));
            this.wixie.debuffCooldown = 150;
         }
      }

      public boolean m_8036_() {
         return this.wixie.debuffCooldown <= 0 && this.wixie.m_5448_() != null;
      }
   }
}
