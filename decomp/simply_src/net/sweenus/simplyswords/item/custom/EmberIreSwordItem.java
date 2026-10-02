package net.sweenus.simplyswords.item.custom;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.sweenus.simplyswords.config.SimplySwordsConfig;
import net.sweenus.simplyswords.item.UniqueSwordItem;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

public class EmberIreSwordItem extends UniqueSwordItem {
   private static int stepMod = 0;
   private static SimpleParticleType particleWalk = ParticleTypes.f_123801_;
   private static SimpleParticleType particleSprint = ParticleTypes.f_123801_;
   private static SimpleParticleType particlePassive = ParticleTypes.f_123762_;

   public EmberIreSwordItem(Tier toolMaterial, int attackDamage, float attackSpeed, Properties settings) {
      super(toolMaterial, attackDamage, attackSpeed, settings);
   }

   @Override
   public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      if (!attacker.f_19853_.m_5776_()) {
         ServerLevel world = (ServerLevel)attacker.f_19853_;
         int fhitchance = (int)SimplySwordsConfig.getFloatValue("ember_ire_chance");
         int fduration = (int)SimplySwordsConfig.getFloatValue("ember_ire_duration");
         HelperMethods.playHitSounds(attacker, target);
         if (attacker.m_217043_().m_188503_(100) <= fhitchance) {
            attacker.m_20254_(fduration / 20);
            attacker.m_147207_(new MobEffectInstance(MobEffects.f_19600_, fduration, 0), attacker);
            attacker.m_147207_(new MobEffectInstance(MobEffects.f_19596_, fduration, 1), attacker);
            attacker.m_147207_(new MobEffectInstance(MobEffects.f_19598_, fduration, 0), attacker);
            world.m_6269_(null, attacker, (SoundEvent)SoundRegistry.MAGIC_SWORD_SPELL_01.get(), SoundSource.PLAYERS, 0.5F, 2.0F);
            particlePassive = ParticleTypes.f_123756_;
            particleWalk = ParticleTypes.f_123777_;
            particleSprint = ParticleTypes.f_123777_;
         }
      }

      return super.m_7579_(stack, target, attacker);
   }

   public Component m_7626_(ItemStack stack) {
      return Component.m_237115_(this.m_5671_(stack)).m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD, ChatFormatting.UNDERLINE});
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player user, InteractionHand hand) {
      if (!user.f_19853_.m_5776_() && user.m_21023_(MobEffects.f_19600_) && user.m_21023_(MobEffects.f_19598_) && user.m_21023_(MobEffects.f_19596_)) {
         ServerLevel sWorld = (ServerLevel)user.f_19853_;
         BlockPos position = user.m_20183_();
         Vec3 rotation = user.m_20252_(1.0F);
         Vec3 newPos = user.m_20182_().m_82549_(rotation);
         LargeFireball fireball = new LargeFireball(EntityType.f_20463_, world);
         fireball.m_20248_(newPos.m_7096_(), user.m_20186_() + 1.5, newPos.m_7094_());
         fireball.m_5602_(user);
         user.m_147207_(new MobEffectInstance(MobEffects.f_19606_, 20, 4), user);
         user.m_147207_(new MobEffectInstance(MobEffects.f_19607_, 60, 2), user);
         sWorld.m_7967_(fireball);
         fireball.m_20256_(rotation);
         user.m_21195_(MobEffects.f_19600_);
         user.m_21195_(MobEffects.f_19596_);
         user.m_21195_(MobEffects.f_19598_);
         world.m_5594_(null, position, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_FIRE_SHOOT_IMPACT_03.get(), SoundSource.PLAYERS, 0.3F, 2.0F);
         user.m_20095_();
      }

      return super.m_7203_(world, user, hand);
   }

   @Override
   public void m_6883_(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
      if (entity instanceof Player player && !player.m_21023_(MobEffects.f_19600_) && !player.m_6060_()) {
         particlePassive = ParticleTypes.f_123762_;
         particleWalk = ParticleTypes.f_123801_;
         particleSprint = ParticleTypes.f_123801_;
      }

      if (stepMod > 0) {
         stepMod--;
      }

      if (stepMod <= 0) {
         stepMod = 7;
      }

      HelperMethods.createFootfalls(entity, stack, world, stepMod, particleWalk, particleSprint, particlePassive, true);
      super.m_6883_(stack, world, entity, slot, selected);
   }

   @Override
   public void m_7373_(ItemStack itemStack, Level world, List<Component> tooltip, TooltipFlag tooltipContext) {
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.emberiresworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD}));
      tooltip.add(Component.m_237115_("item.simplyswords.emberiresworditem.tooltip2"));
      tooltip.add(Component.m_237115_("item.simplyswords.emberiresworditem.tooltip3"));
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.onrightclick").m_130944_(new ChatFormatting[]{ChatFormatting.BOLD, ChatFormatting.GREEN}));
      tooltip.add(Component.m_237115_("item.simplyswords.emberiresworditem.tooltip4"));
      tooltip.add(Component.m_237115_("item.simplyswords.emberiresworditem.tooltip5"));
      super.m_7373_(itemStack, world, tooltip, tooltipContext);
   }
}
