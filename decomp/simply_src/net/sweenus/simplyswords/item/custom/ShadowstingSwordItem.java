package net.sweenus.simplyswords.item.custom;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
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
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

public class ShadowstingSwordItem extends UniqueSwordItem {
   private static int stepMod = 0;
   int skillCooldown = (int)SimplySwordsConfig.getFloatValue("shadowmist_cooldown");
   int abilityChance = (int)SimplySwordsConfig.getFloatValue("shadowmist_chance");
   int damageArmorMultiplier = (int)(SimplySwordsConfig.getFloatValue("shadowmist_damage_multiplier") * 2.0F);
   int blindDuration = (int)SimplySwordsConfig.getFloatValue("shadowmist_blind_duration");
   int radius = (int)SimplySwordsConfig.getFloatValue("shadowmist_radius");

   public ShadowstingSwordItem(Tier toolMaterial, int attackDamage, float attackSpeed, Properties settings) {
      super(toolMaterial, attackDamage, attackSpeed, settings);
   }

   @Override
   public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      HelperMethods.playHitSounds(attacker, target);
      if (!attacker.f_19853_.m_5776_() && attacker.m_217043_().m_188503_(100) <= this.abilityChance && attacker instanceof Player player) {
         attacker.f_19853_.m_6269_(null, attacker, (SoundEvent)SoundRegistry.MAGIC_SWORD_SPELL_02.get(), SoundSource.PLAYERS, 0.3F, 1.8F);
         int extraDamage = target.m_21230_() * this.damageArmorMultiplier / 2;
         target.m_6469_(DamageSource.f_19319_, (float)extraDamage);
      }

      return super.m_7579_(stack, target, attacker);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player user, InteractionHand hand) {
      ItemStack itemStack = user.m_21120_(hand);
      world.m_6269_(null, user, (SoundEvent)SoundRegistry.ELEMENTAL_SWORD_EARTH_ATTACK_01.get(), SoundSource.PLAYERS, 0.4F, 1.6F);
      user.m_36335_().m_41524_(this.m_7968_().m_41720_(), this.skillCooldown);
      AABB box = new AABB(
         user.m_20185_() + (double)this.radius,
         user.m_20186_() + (double)this.radius,
         user.m_20189_() + (double)this.radius,
         user.m_20185_() - (double)this.radius,
         user.m_20186_() - (double)this.radius,
         user.m_20189_() - (double)this.radius
      );

      for (Entity entities : world.m_6249_(user, box, EntitySelector.f_20403_)) {
         if (entities != null && entities instanceof LivingEntity) {
            LivingEntity le = (LivingEntity)entities;
            if (HelperMethods.checkFriendlyFire(le, user)) {
               le.m_147207_(new MobEffectInstance(MobEffects.f_19610_, this.blindDuration, 3), user);
            }
         }
      }

      double xpos = user.m_20185_() - (double)(this.radius + 1);
      double ypos = user.m_20186_();
      double zpos = user.m_20189_() - (double)(this.radius + 1);

      for (int i = this.radius * 2; i > 0; i--) {
         for (int j = this.radius * 2; j > 0; j--) {
            float choose = (float)(Math.random() * 1.0);
            HelperMethods.spawnParticle(
               world, ParticleTypes.f_123755_, xpos + (double)i + (double)choose, ypos + 0.4, zpos + (double)j + (double)choose, 0.0, 0.1, 0.0
            );
            HelperMethods.spawnParticle(
               world, ParticleTypes.f_123777_, xpos + (double)i + (double)choose, ypos + 0.1, zpos + (double)j + (double)choose, 0.0, 0.0, 0.0
            );
            HelperMethods.spawnParticle(
               world, ParticleTypes.f_123762_, xpos + (double)i + (double)choose, ypos, zpos + (double)j + (double)choose, 0.0, 0.1, 0.0
            );
         }
      }

      BlockState currentStateLow = world.m_8055_(user.m_20183_().m_5484_(user.m_6374_(), 5));
      double targetPositionX = (double)user.m_20183_().m_5484_(user.m_6374_(), 5).m_123341_();
      double targetPositionY = (double)user.m_20183_().m_5484_(user.m_6374_(), 5).m_123342_();
      double targetPositionZ = (double)user.m_20183_().m_5484_(user.m_6374_(), 5).m_123343_();
      BlockState currentStateHigh = world.m_8055_(user.m_20183_().m_6630_(1).m_5484_(user.m_6374_(), 5));
      BlockState state = Blocks.f_50016_.m_49966_();
      if (currentStateLow == state && currentStateHigh == state) {
         user.m_20324_(targetPositionX, targetPositionY, targetPositionZ);
      }

      return super.m_7203_(world, user, hand);
   }

   @Override
   public void m_6883_(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
      if (stepMod > 0) {
         stepMod--;
      }

      if (stepMod <= 0) {
         stepMod = 7;
      }

      HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.f_123757_, ParticleTypes.f_123757_, ParticleTypes.f_123757_, true);
      super.m_6883_(stack, world, entity, slot, selected);
   }

   public Component m_7626_(ItemStack stack) {
      return Component.m_237115_(this.m_5671_(stack)).m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD, ChatFormatting.UNDERLINE});
   }

   @Override
   public void m_7373_(ItemStack itemStack, Level world, List<Component> tooltip, TooltipFlag tooltipContext) {
      tooltip.add(Component.m_237113_(""));
      tooltip.add(
         Component.m_237115_("item.simplyswords.shadowmistsworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD})
      );
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.shadowmistsworditem.tooltip2"));
      tooltip.add(Component.m_237115_("item.simplyswords.shadowmistsworditem.tooltip3"));
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.onrightclick").m_130944_(new ChatFormatting[]{ChatFormatting.BOLD, ChatFormatting.GREEN}));
      tooltip.add(Component.m_237115_("item.simplyswords.shadowmistsworditem.tooltip4"));
      tooltip.add(Component.m_237115_("item.simplyswords.shadowmistsworditem.tooltip5"));
      super.m_7373_(itemStack, world, tooltip, tooltipContext);
   }
}
