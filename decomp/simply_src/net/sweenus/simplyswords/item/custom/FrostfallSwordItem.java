package net.sweenus.simplyswords.item.custom;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.sweenus.simplyswords.config.SimplySwordsConfig;
import net.sweenus.simplyswords.item.UniqueSwordItem;
import net.sweenus.simplyswords.registry.EffectRegistry;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

public class FrostfallSwordItem extends UniqueSwordItem {
   private static int stepMod = 0;
   private final int abilityCooldown = (int)SimplySwordsConfig.getFloatValue("frostfury_cooldown");
   int radius = (int)SimplySwordsConfig.getFloatValue("frostfury_radius");
   int shatterDamage = (int)SimplySwordsConfig.getFloatValue("frostfury_damage");
   int proc_chance = (int)SimplySwordsConfig.getFloatValue("frostfury_chance");
   int shatter_timer_max = (int)SimplySwordsConfig.getFloatValue("frostfury_duration");
   int shatter_timer;
   int player_shatter_timer;
   double lastX;
   double lastY;
   double lastZ;

   public FrostfallSwordItem(Tier toolMaterial, int attackDamage, float attackSpeed, Properties settings) {
      super(toolMaterial, attackDamage, attackSpeed, settings);
   }

   @Override
   public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      if (!attacker.f_19853_.m_5776_()) {
         ServerLevel world = (ServerLevel)attacker.f_19853_;
         HelperMethods.playHitSounds(attacker, target);
         if (attacker.m_217043_().m_188503_(100) <= this.proc_chance) {
            AABB box = new AABB(
               target.m_20185_() + (double)this.radius,
               target.m_20186_() + (double)this.radius,
               target.m_20189_() + (double)this.radius,
               target.m_20185_() - (double)this.radius,
               target.m_20186_() - (double)this.radius,
               target.m_20189_() - (double)this.radius
            );

            for (Entity entities : world.m_6249_(attacker, box, EntitySelector.f_20403_)) {
               if (entities != null && entities instanceof LivingEntity) {
                  LivingEntity le = (LivingEntity)entities;
                  if (HelperMethods.checkFriendlyFire(le, (Player)attacker)) {
                     le.m_147207_(new MobEffectInstance((MobEffect)EffectRegistry.FREEZE.get(), this.shatter_timer_max + 10, 0), attacker);
                     le.m_147207_(new MobEffectInstance(MobEffects.f_19606_, this.shatter_timer_max - 10, 4), attacker);
                     world.m_6269_(null, le, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_ICE_SHOOT_IMPACT_01.get(), SoundSource.PLAYERS, 0.1F, 3.0F);
                     BlockPos pos = new BlockPos(le.m_20185_(), le.m_20186_(), le.m_20189_());
                     BlockPos pos2 = new BlockPos(le.m_20185_(), le.m_20186_() + 1.0, le.m_20189_());
                     BlockState state = Blocks.f_50126_.m_49966_();
                     if (world.m_8055_(pos) == Blocks.f_50016_.m_49966_()) {
                        world.m_46597_(pos, state);
                     }

                     if (world.m_8055_(pos2) == Blocks.f_50016_.m_49966_()) {
                        world.m_46597_(pos2, state);
                     }
                  }
               }
            }

            this.shatter_timer = this.shatter_timer_max;
         }
      }

      return super.m_7579_(stack, target, attacker);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player user, InteractionHand hand) {
      if (!user.f_19853_.m_5776_()) {
         this.lastX = user.m_20185_();
         this.lastY = user.m_20186_();
         this.lastZ = user.m_20189_();
         world.m_6269_(null, user, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_ICE_SHOOT_IMPACT_03.get(), SoundSource.PLAYERS, 0.6F, 2.0F);
         double xpos = user.m_20185_() - 2.0;
         double ypos = user.m_20186_();
         double zpos = user.m_20189_() - 2.0;
         user.m_20334_(0.0, 0.0, 0.0);
         user.f_19864_ = true;
         user.m_20324_(this.lastX, this.lastY, this.lastZ);

         for (int i = 3; i > 0; i--) {
            for (int j = 3; j > 0; j--) {
               BlockPos poscheck = new BlockPos(xpos + (double)i, ypos, zpos + (double)j);
               BlockPos poscheck2 = new BlockPos(xpos + (double)i, ypos + 1.0, zpos + (double)j);
               BlockPos poscheck3 = new BlockPos(xpos + (double)i, ypos + 2.0, zpos + (double)j);
               BlockPos poscheck4 = new BlockPos(xpos + (double)i, ypos - 1.0, zpos + (double)j);
               BlockState currentState = world.m_8055_(poscheck);
               BlockState currentState2 = world.m_8055_(poscheck2);
               BlockState currentState3 = world.m_8055_(poscheck3);
               BlockState currentState4 = world.m_8055_(poscheck4);
               BlockState state = Blocks.f_50126_.m_49966_();
               if (i + j != 4) {
                  if (currentState == Blocks.f_50016_.m_49966_()
                     || currentState == Blocks.f_50125_.m_49966_()
                     || currentState == Blocks.f_50034_.m_49966_()
                     || currentState == Blocks.f_50360_.m_49966_()
                     || currentState == Blocks.f_50035_.m_49966_()) {
                     world.m_46597_(poscheck, state);
                  }

                  if (currentState2 == Blocks.f_50016_.m_49966_()
                     || currentState2 == Blocks.f_50125_.m_49966_()
                     || currentState2 == Blocks.f_50034_.m_49966_()
                     || currentState2 == Blocks.f_50360_.m_49966_()
                     || currentState2 == Blocks.f_50035_.m_49966_()) {
                     world.m_46597_(poscheck2, state);
                  }
               }

               if (currentState3 == Blocks.f_50016_.m_49966_()
                  || currentState3 == Blocks.f_50125_.m_49966_()
                  || currentState3 == Blocks.f_50034_.m_49966_()
                  || currentState3 == Blocks.f_50360_.m_49966_()
                  || currentState3 == Blocks.f_50035_.m_49966_()) {
                  world.m_46597_(poscheck3, state);
               }

               if (currentState4 == Blocks.f_50016_.m_49966_()
                  || currentState4 == Blocks.f_50125_.m_49966_()
                  || currentState4 == Blocks.f_50034_.m_49966_()
                  || currentState4 == Blocks.f_50360_.m_49966_()
                  || currentState4 == Blocks.f_50035_.m_49966_()) {
                  world.m_46597_(poscheck4, state);
               }
            }
         }

         user.m_20324_(this.lastX, this.lastY, this.lastZ);
         user.m_147207_(new MobEffectInstance(MobEffects.f_19597_, this.shatter_timer_max, 4), user);
         user.m_147207_(new MobEffectInstance(MobEffects.f_19599_, this.shatter_timer_max, 4), user);
         user.m_147207_(new MobEffectInstance(MobEffects.f_19606_, this.shatter_timer_max, 4), user);
         user.m_147207_(new MobEffectInstance(MobEffects.f_19605_, this.shatter_timer_max, 2), user);
         this.player_shatter_timer = this.shatter_timer_max;
         user.m_36335_().m_41524_(this, this.abilityCooldown);
      }

      return super.m_7203_(world, user, hand);
   }

   @Override
   public void m_6883_(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
      if (!world.f_46443_ && entity instanceof Player player) {
         if (this.shatter_timer > 0) {
            this.shatter_timer--;
         }

         if (this.player_shatter_timer > 0) {
            this.player_shatter_timer--;
         }

         if (this.shatter_timer == 1) {
            AABB box = new AABB(
               player.m_20185_() + (double)this.radius + 10.0,
               player.m_20186_() + (double)this.radius + 10.0,
               player.m_20189_() + (double)this.radius + 10.0,
               player.m_20185_() - (double)this.radius - 10.0,
               player.m_20186_() - (double)this.radius - 10.0,
               player.m_20189_() - (double)this.radius - 10.0
            );

            for (Entity entities : world.m_6249_(player, box, EntitySelector.f_20403_)) {
               if (entities != null && entities instanceof LivingEntity) {
                  LivingEntity le = (LivingEntity)entities;
                  if (le.m_21023_((MobEffect)EffectRegistry.FREEZE.get())) {
                     world.m_6269_(null, le, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_ICE_SHOOT_IMPACT_02.get(), SoundSource.PLAYERS, 0.2F, 3.0F);
                     le.m_6469_(DamageSource.f_146701_, (float)this.shatterDamage);
                  }

                  double xpos = le.m_20185_() - 2.0;
                  double ypos = le.m_20186_();
                  double zpos = le.m_20189_() - 2.0;

                  for (int i = 3; i > 0; i--) {
                     for (int j = 3; j > 0; j--) {
                        BlockPos poscheck = new BlockPos(xpos + (double)i, ypos, zpos + (double)j);
                        BlockPos poscheck2 = new BlockPos(xpos + (double)i, ypos + 1.0, zpos + (double)j);
                        BlockPos poscheck3 = new BlockPos(xpos + (double)i, ypos + 2.0, zpos + (double)j);
                        BlockPos poscheck4 = new BlockPos(xpos + (double)i, ypos - 1.0, zpos + (double)j);
                        BlockState currentState = world.m_8055_(poscheck);
                        BlockState currentState2 = world.m_8055_(poscheck2);
                        BlockState currentState3 = world.m_8055_(poscheck3);
                        BlockState currentState4 = world.m_8055_(poscheck4);
                        BlockState state = Blocks.f_50016_.m_49966_();
                        if (currentState == Blocks.f_50126_.m_49966_() || currentState == Blocks.f_49990_.m_49966_()) {
                           world.m_46597_(poscheck, state);
                        }

                        if (currentState2 == Blocks.f_50126_.m_49966_() || currentState2 == Blocks.f_49990_.m_49966_()) {
                           world.m_46597_(poscheck2, state);
                        }

                        if (currentState3 == Blocks.f_50126_.m_49966_() || currentState3 == Blocks.f_49990_.m_49966_()) {
                           world.m_46597_(poscheck3, state);
                        }

                        if (currentState4 == Blocks.f_50126_.m_49966_() || currentState4 == Blocks.f_49990_.m_49966_()) {
                           world.m_46597_(poscheck4, state);
                        }
                     }
                  }
               }
            }
         }

         if (this.player_shatter_timer == 1) {
            world.m_6269_(null, player, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_ICE_SHOOT_IMPACT_02.get(), SoundSource.PLAYERS, 0.6F, 3.0F);
            double xpos = this.lastX - 2.0;
            double ypos = this.lastY;
            double zpos = this.lastZ - 2.0;

            for (int i = 3; i > 0; i--) {
               for (int j = 3; j > 0; j--) {
                  BlockPos poscheckx = new BlockPos(xpos + (double)i, ypos, zpos + (double)j);
                  BlockPos poscheck2x = new BlockPos(xpos + (double)i, ypos + 1.0, zpos + (double)j);
                  BlockPos poscheck3x = new BlockPos(xpos + (double)i, ypos + 2.0, zpos + (double)j);
                  BlockPos poscheck4x = new BlockPos(xpos + (double)i, ypos - 1.0, zpos + (double)j);
                  BlockState currentStatex = world.m_8055_(poscheckx);
                  BlockState currentState2x = world.m_8055_(poscheck2x);
                  BlockState currentState3x = world.m_8055_(poscheck3x);
                  BlockState currentState4x = world.m_8055_(poscheck4x);
                  BlockState statex = Blocks.f_50016_.m_49966_();
                  if (currentStatex == Blocks.f_50126_.m_49966_() || currentStatex == Blocks.f_49990_.m_49966_()) {
                     world.m_46597_(poscheckx, statex);
                  }

                  if (currentState2x == Blocks.f_50126_.m_49966_() || currentState2x == Blocks.f_49990_.m_49966_()) {
                     world.m_46597_(poscheck2x, statex);
                  }

                  if (currentState3x == Blocks.f_50126_.m_49966_() || currentState3x == Blocks.f_49990_.m_49966_()) {
                     world.m_46597_(poscheck3x, statex);
                  }

                  if (currentState4x == Blocks.f_50126_.m_49966_() || currentState4x == Blocks.f_49990_.m_49966_()) {
                     world.m_46597_(poscheck4x, statex);
                  }
               }
            }
         }
      }

      if (stepMod > 0) {
         stepMod--;
      }

      if (stepMod <= 0) {
         stepMod = 7;
      }

      HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.f_175821_, ParticleTypes.f_175821_, ParticleTypes.f_123790_, true);
      super.m_6883_(stack, world, entity, slot, selected);
   }

   public Component m_7626_(ItemStack stack) {
      return Component.m_237115_(this.m_5671_(stack)).m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD, ChatFormatting.UNDERLINE});
   }

   @Override
   public void m_7373_(ItemStack itemStack, Level world, List<Component> tooltip, TooltipFlag tooltipContext) {
      tooltip.add(Component.m_237113_(""));
      tooltip.add(
         Component.m_237115_("item.simplyswords.frostfallsworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD})
      );
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.frostfallsworditem.tooltip2"));
      tooltip.add(Component.m_237115_("item.simplyswords.frostfallsworditem.tooltip3"));
      tooltip.add(Component.m_237110_("item.simplyswords.frostfallsworditem.tooltip4", new Object[]{this.shatter_timer_max / 20, this.shatterDamage}));
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.onrightclick").m_130944_(new ChatFormatting[]{ChatFormatting.BOLD, ChatFormatting.GREEN}));
      tooltip.add(Component.m_237110_("item.simplyswords.frostfallsworditem.tooltip5", new Object[]{this.shatter_timer_max / 20}));
      tooltip.add(Component.m_237115_("item.simplyswords.frostfallsworditem.tooltip6"));
      tooltip.add(Component.m_237113_(""));
      super.m_7373_(itemStack, world, tooltip, tooltipContext);
   }
}
