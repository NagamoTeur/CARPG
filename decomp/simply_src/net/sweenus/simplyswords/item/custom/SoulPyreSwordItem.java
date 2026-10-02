package net.sweenus.simplyswords.item.custom;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
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
import net.minecraft.world.phys.AABB;
import net.sweenus.simplyswords.config.SimplySwordsConfig;
import net.sweenus.simplyswords.item.UniqueSwordItem;
import net.sweenus.simplyswords.registry.EffectRegistry;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

public class SoulPyreSwordItem extends UniqueSwordItem {
   private int relocationTimer;
   private static int stepMod = 0;
   private final int relocationDuration = (int)SimplySwordsConfig.getFloatValue("soultether_duration");
   private boolean canRelocate;
   private LivingEntity relocateTarget;
   private double relocateX;
   private double relocateY;
   private double relocateZ;

   public SoulPyreSwordItem(Tier toolMaterial, int attackDamage, float attackSpeed, Properties settings) {
      super(toolMaterial, attackDamage, attackSpeed, settings);
   }

   @Override
   public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      if (!attacker.f_19853_.m_5776_()) {
         ServerLevel sworld = (ServerLevel)attacker.f_19853_;
         HelperMethods.playHitSounds(attacker, target);
      }

      return super.m_7579_(stack, target, attacker);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player user, InteractionHand hand) {
      if (!user.f_19853_.m_5776_()) {
         int range = (int)SimplySwordsConfig.getFloatValue("soultether_range");
         int radius = (int)SimplySwordsConfig.getFloatValue("soultether_radius");
         int ignite_duration = (int)SimplySwordsConfig.getFloatValue("soultether_ignite_duration") / 20;
         int resistance_duration = (int)SimplySwordsConfig.getFloatValue("soultether_resistance_duration");
         LivingEntity target = (LivingEntity)HelperMethods.getTargetedEntity(user, range);
         if (target != null && HelperMethods.checkFriendlyFire(target, user)) {
            this.relocateX = user.m_20185_();
            this.relocateY = user.m_20186_();
            this.relocateZ = user.m_20189_();
            this.relocateTarget = target;
            double rememberx = target.m_20185_();
            double remembery = target.m_20186_();
            double rememberz = target.m_20189_();
            target.m_20984_(user.m_20185_(), user.m_20186_(), user.m_20189_(), true);
            user.m_20984_(rememberx, remembery, rememberz, true);
            world.m_6269_(null, user, (SoundEvent)SoundRegistry.ELEMENTAL_SWORD_SCIFI_ATTACK_01.get(), SoundSource.PLAYERS, 0.3F, 1.0F);
            user.m_147207_(new MobEffectInstance(MobEffects.f_19606_, resistance_duration, 0), user);
            user.m_147207_(new MobEffectInstance(MobEffects.f_19607_, resistance_duration, 0), user);
            user.m_147207_(new MobEffectInstance(MobEffects.f_19598_, this.relocationDuration, 3), user);
            target.m_147207_(new MobEffectInstance((MobEffect)EffectRegistry.FREEZE.get(), this.relocationDuration - 10, 0), user);
            this.canRelocate = true;
            this.relocationTimer = this.relocationDuration;
            AABB box = new AABB(
               rememberx + (double)radius,
               remembery + (double)radius,
               rememberz + (double)radius,
               rememberx - (double)radius,
               remembery - (double)radius,
               rememberz - (double)radius
            );

            for (Entity entities : world.m_6249_(user, box, EntitySelector.f_20403_)) {
               if (entities != null && entities instanceof LivingEntity) {
                  LivingEntity le = (LivingEntity)entities;
                  if (HelperMethods.checkFriendlyFire(le, user)) {
                     le.m_20334_((rememberx - le.m_20185_()) / 4.0, (remembery - le.m_20186_()) / 4.0, (rememberz - le.m_20189_()) / 4.0);
                     le.m_147207_(new MobEffectInstance(MobEffects.f_19597_, 40, 3), user);
                     world.m_6269_(null, le, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_FIRE_SHOOT_IMPACT_03.get(), SoundSource.PLAYERS, 0.1F, 3.0F);
                     le.m_20254_(ignite_duration);
                  }
               }
            }

            user.m_36335_().m_41524_(this, this.relocationDuration);
         }
      }

      return super.m_7203_(world, user, hand);
   }

   @Override
   public void m_6883_(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
      if (!world.f_46443_ && entity instanceof Player player && this.canRelocate) {
         this.relocationTimer--;
         if (this.relocationTimer <= 0) {
            if (this.relocateTarget != null) {
               this.relocateTarget.m_20984_(player.m_20185_(), player.m_20186_(), player.m_20189_(), true);
            }

            player.m_20984_(this.relocateX, this.relocateY, this.relocateZ, true);
            world.m_6269_(null, player, (SoundEvent)SoundRegistry.ELEMENTAL_SWORD_SCIFI_ATTACK_03.get(), SoundSource.PLAYERS, 0.3F, 1.0F);
            this.canRelocate = false;
         }

         if (this.relocationTimer == 40) {
            world.m_6269_(null, player, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_RECHARGE.get(), SoundSource.PLAYERS, 0.3F, 0.4F);
         }
      }

      if (stepMod > 0) {
         stepMod--;
      }

      if (stepMod <= 0) {
         stepMod = 7;
      }

      HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.f_123745_, ParticleTypes.f_123745_, ParticleTypes.f_123757_, true);
      HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.f_175834_, ParticleTypes.f_175834_, ParticleTypes.f_123757_, false);
      HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.f_123762_, ParticleTypes.f_123762_, ParticleTypes.f_123757_, false);
      super.m_6883_(stack, world, entity, slot, selected);
   }

   public Component m_7626_(ItemStack stack) {
      return Component.m_237115_(this.m_5671_(stack)).m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD, ChatFormatting.UNDERLINE});
   }

   @Override
   public void m_7373_(ItemStack itemStack, Level world, List<Component> tooltip, TooltipFlag tooltipContext) {
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.soulpyresworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD}));
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.onrightclick").m_130944_(new ChatFormatting[]{ChatFormatting.BOLD, ChatFormatting.GREEN}));
      tooltip.add(Component.m_237115_("item.simplyswords.soulpyresworditem.tooltip2"));
      tooltip.add(Component.m_237115_("item.simplyswords.soulpyresworditem.tooltip3"));
      tooltip.add(Component.m_237115_("item.simplyswords.soulpyresworditem.tooltip4"));
      tooltip.add(Component.m_237115_("item.simplyswords.soulpyresworditem.tooltip5"));
      tooltip.add(Component.m_237115_("item.simplyswords.soulpyresworditem.tooltip6"));
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237110_("item.simplyswords.soulpyresworditem.tooltip7", new Object[]{this.relocationDuration / 20}));
      tooltip.add(Component.m_237115_("item.simplyswords.soulpyresworditem.tooltip8"));
      tooltip.add(Component.m_237115_("item.simplyswords.soulpyresworditem.tooltip9"));
      tooltip.add(Component.m_237113_(""));
      super.m_7373_(itemStack, world, tooltip, tooltipContext);
   }
}
