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

public class StormsEdgeSwordItem extends UniqueSwordItem {
   private static int stepMod = 0;
   int radius = 1;
   int ability_timer_max = 13;
   int skillCooldown = (int)SimplySwordsConfig.getFloatValue("stormjolt_cooldown");
   int chargeChance = (int)SimplySwordsConfig.getFloatValue("stormjolt_chance");

   public StormsEdgeSwordItem(Tier toolMaterial, int attackDamage, float attackSpeed, Properties settings) {
      super(toolMaterial, attackDamage, attackSpeed, settings);
   }

   @Override
   public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      HelperMethods.playHitSounds(attacker, target);
      if (!attacker.f_19853_.m_5776_()
         && attacker.m_217043_().m_188503_(100) <= this.chargeChance
         && attacker instanceof Player player
         && player.m_36335_().m_41521_(this, 1.0F) > 0.0F) {
         player.m_36335_().m_41524_(this, 0);
         attacker.f_19853_.m_6269_(null, attacker, (SoundEvent)SoundRegistry.MAGIC_SWORD_BLOCK_01.get(), SoundSource.PLAYERS, 0.7F, 1.0F);
      }

      return super.m_7579_(stack, target, attacker);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player user, InteractionHand hand) {
      ItemStack itemStack = user.m_21120_(hand);
      if (itemStack.m_41773_() >= itemStack.m_41776_() - 1) {
         return InteractionResultHolder.m_19100_(itemStack);
      } else {
         world.m_6269_(null, user, (SoundEvent)SoundRegistry.MAGIC_BOW_CHARGE_SHORT_VERSION.get(), SoundSource.PLAYERS, 0.4F, 1.2F);
         user.m_147207_(new MobEffectInstance(MobEffects.f_19606_, 20, 5), user);
         user.m_6672_(hand);
         return InteractionResultHolder.m_19096_(itemStack);
      }
   }

   public void m_5929_(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
      if (!world.f_46443_ && user.m_6844_(EquipmentSlot.MAINHAND) == stack) {
         AbilityMethods.tickAbilityStormJolt(stack, world, user, remainingUseTicks, this.skillCooldown, this.radius);
      }
   }

   public void m_5551_(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
      if (!world.f_46443_ && user.m_6844_(EquipmentSlot.MAINHAND) == stack) {
         user.m_20334_(0.0, 0.0, 0.0);
         user.f_19864_ = true;
         user.m_147207_(new MobEffectInstance(MobEffects.f_19598_, 80, 1), user);
      }
   }

   public int m_8105_(ItemStack stack) {
      return this.ability_timer_max;
   }

   public UseAnim m_6164_(ItemStack stack) {
      return UseAnim.BLOCK;
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
         Component.m_237115_("item.simplyswords.stormsedgesworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD})
      );
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.stormsedgesworditem.tooltip2"));
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.onrightclick").m_130944_(new ChatFormatting[]{ChatFormatting.BOLD, ChatFormatting.GREEN}));
      tooltip.add(Component.m_237115_("item.simplyswords.stormsedgesworditem.tooltip3"));
      tooltip.add(Component.m_237115_("item.simplyswords.stormsedgesworditem.tooltip4"));
      tooltip.add(Component.m_237115_("item.simplyswords.stormsedgesworditem.tooltip5"));
      super.m_7373_(itemStack, world, tooltip, tooltipContext);
   }
}
