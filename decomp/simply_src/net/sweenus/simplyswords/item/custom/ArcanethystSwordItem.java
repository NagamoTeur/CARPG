package net.sweenus.simplyswords.item.custom;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.sweenus.simplyswords.config.SimplySwordsConfig;
import net.sweenus.simplyswords.item.UniqueSwordItem;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.AbilityMethods;
import net.sweenus.simplyswords.util.HelperMethods;

public class ArcanethystSwordItem extends UniqueSwordItem {
   private static int stepMod = 0;
   int radius = (int)SimplySwordsConfig.getFloatValue("arcaneassault_radius");
   int arcaneDamage = (int)SimplySwordsConfig.getFloatValue("arcaneassault_damage");
   int arcane_timer_max = (int)SimplySwordsConfig.getFloatValue("arcaneassault_duration");
   int skillCooldown = (int)SimplySwordsConfig.getFloatValue("arcaneassault_cooldown");
   int chargeChance = (int)SimplySwordsConfig.getFloatValue("arcaneassault_chance");

   public ArcanethystSwordItem(Tier toolMaterial, int attackDamage, float attackSpeed, Properties settings) {
      super(toolMaterial, attackDamage, attackSpeed, settings);
   }

   @Override
   public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      if (!attacker.f_19853_.m_5776_()) {
         HelperMethods.playHitSounds(attacker, target);
         if (attacker.m_217043_().m_188503_(100) <= this.chargeChance) {
            target.m_147207_(new MobEffectInstance(MobEffects.f_19620_, 60, 1), attacker);
            attacker.f_19853_.m_6269_(null, attacker, (SoundEvent)SoundRegistry.MAGIC_BOW_SHOOT_IMPACT_01.get(), SoundSource.PLAYERS, 0.5F, 1.2F);
         }
      }

      return super.m_7579_(stack, target, attacker);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player user, InteractionHand hand) {
      if (!user.f_19853_.m_5776_()) {
         ItemStack itemStack = user.m_21120_(hand);
         if (itemStack.m_41773_() >= itemStack.m_41776_() - 1) {
            return InteractionResultHolder.m_19100_(itemStack);
         } else {
            world.m_6269_(null, user, (SoundEvent)SoundRegistry.MAGIC_BOW_SHOOT_IMPACT_02.get(), SoundSource.PLAYERS, 0.4F, 1.2F);
            user.m_6672_(hand);
            return InteractionResultHolder.m_19096_(itemStack);
         }
      } else {
         return super.m_7203_(world, user, hand);
      }
   }

   public void m_5929_(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
      if (user.m_6844_(EquipmentSlot.MAINHAND) == stack && user instanceof Player player) {
         AbilityMethods.tickAbilityArcaneAssault(
            stack, world, user, remainingUseTicks, this.arcane_timer_max, this.arcaneDamage, this.skillCooldown, this.radius
         );
      }
   }

   public int m_8105_(ItemStack stack) {
      return this.arcane_timer_max;
   }

   public UseAnim m_6164_(ItemStack stack) {
      return UseAnim.CROSSBOW;
   }

   public void m_5551_(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
      if (!world.f_46443_ && user instanceof Player player) {
         player.m_36335_().m_41524_(stack.m_41720_(), this.skillCooldown);
      }
   }

   @Override
   public void m_6883_(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
      if (stepMod > 0) {
         stepMod--;
      }

      if (stepMod <= 0) {
         stepMod = 7;
      }

      HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.f_123799_, ParticleTypes.f_123799_, ParticleTypes.f_123789_, true);
      super.m_6883_(stack, world, entity, slot, selected);
   }

   public Component m_7626_(ItemStack stack) {
      return Component.m_237115_(this.m_5671_(stack)).m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD, ChatFormatting.UNDERLINE});
   }

   @Override
   public void m_7373_(ItemStack itemStack, Level world, List<Component> tooltip, TooltipFlag tooltipContext) {
      tooltip.add(Component.m_237113_(""));
      tooltip.add(
         Component.m_237115_("item.simplyswords.arcanethystsworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD})
      );
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.arcanethystsworditem.tooltip2"));
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.onrightclick").m_130944_(new ChatFormatting[]{ChatFormatting.BOLD, ChatFormatting.GREEN}));
      tooltip.add(Component.m_237115_("item.simplyswords.arcanethystsworditem.tooltip3"));
      tooltip.add(Component.m_237115_("item.simplyswords.arcanethystsworditem.tooltip4"));
      tooltip.add(Component.m_237115_("item.simplyswords.arcanethystsworditem.tooltip5"));
      tooltip.add(Component.m_237115_("item.simplyswords.arcanethystsworditem.tooltip6"));
      tooltip.add(Component.m_237113_(""));
      super.m_7373_(itemStack, world, tooltip, tooltipContext);
   }
}
