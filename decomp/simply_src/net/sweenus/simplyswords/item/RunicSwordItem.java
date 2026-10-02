package net.sweenus.simplyswords.item;

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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.sweenus.simplyswords.config.SimplySwordsConfig;
import net.sweenus.simplyswords.registry.EffectRegistry;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;
import net.sweenus.simplyswords.util.RunicMethods;

public class RunicSwordItem extends SwordItem {
   public static int maxUseTime;

   public RunicSwordItem(Tier toolMaterial, int attackDamage, float attackSpeed, Properties settings) {
      super(toolMaterial, attackDamage, attackSpeed, settings.m_41486_());
   }

   public boolean m_142305_(ItemStack stack, ItemStack otherStack, Slot slot, ClickAction clickType, Player player, SlotAccess cursorStackReference) {
      if (stack.m_41784_().m_128461_("runic_power").isEmpty()) {
         String runicPowerSelection = HelperMethods.chooseRunicPower();
         stack.m_41784_().m_128359_("runic_power", runicPowerSelection);
      }

      return false;
   }

   public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      if (!attacker.f_19853_.m_5776_()) {
         ServerLevel world = (ServerLevel)attacker.f_19853_;
         HelperMethods.playHitSounds(attacker, target);
         if (stack.m_41784_().m_128461_("runic_power").equals("freeze")) {
            RunicMethods.postHitRunicFreeze(stack, target, attacker);
         }

         if (stack.m_41784_().m_128461_("runic_power").equals("wildfire")) {
            RunicMethods.postHitRunicWildfire(stack, target, attacker);
         }

         if (stack.m_41784_().m_128461_("runic_power").equals("slow")) {
            RunicMethods.postHitRunicSlow(stack, target, attacker);
         }

         if (stack.m_41784_().m_128461_("runic_power").equals("greater_slow")) {
            RunicMethods.postHitRunicGreaterSlow(stack, target, attacker);
         }

         if (stack.m_41784_().m_128461_("runic_power").equals("swiftness")) {
            RunicMethods.postHitRunicSwiftness(stack, target, attacker);
         }

         if (stack.m_41784_().m_128461_("runic_power").equals("greater_swiftness")) {
            RunicMethods.postHitRunicGreaterSwiftness(stack, target, attacker);
         }

         if (stack.m_41784_().m_128461_("runic_power").equals("float")) {
            RunicMethods.postHitRunicFloat(stack, target, attacker);
         }

         if (stack.m_41784_().m_128461_("runic_power").equals("greater_float")) {
            RunicMethods.postHitRunicGreaterFloat(stack, target, attacker);
         }

         if (stack.m_41784_().m_128461_("runic_power").equals("zephyr")) {
            RunicMethods.postHitRunicZephyr(stack, target, attacker);
         }

         if (stack.m_41784_().m_128461_("runic_power").equals("greater_zephyr")) {
            RunicMethods.postHitRunicGreaterZephyr(stack, target, attacker);
         }

         if (stack.m_41784_().m_128461_("runic_power").equals("shielding")) {
            RunicMethods.postHitRunicShielding(stack, target, attacker);
         }

         if (stack.m_41784_().m_128461_("runic_power").equals("greater_shielding")) {
            RunicMethods.postHitRunicGreaterShielding(stack, target, attacker);
         }

         if (stack.m_41784_().m_128461_("runic_power").equals("stoneskin")) {
            RunicMethods.postHitRunicStoneskin(stack, target, attacker);
         }

         if (stack.m_41784_().m_128461_("runic_power").equals("greater_stoneskin")) {
            RunicMethods.postHitRunicGreaterStoneskin(stack, target, attacker);
         }

         if (stack.m_41784_().m_128461_("runic_power").equals("trailblaze")) {
            RunicMethods.postHitRunicTrailblaze(stack, target, attacker);
         }

         if (stack.m_41784_().m_128461_("runic_power").equals("greater_trailblaze")) {
            RunicMethods.postHitRunicGreaterTrailblaze(stack, target, attacker);
         }

         if (stack.m_41784_().m_128461_("runic_power").equals("weaken")) {
            RunicMethods.postHitRunicWeaken(stack, target, attacker);
         }

         if (stack.m_41784_().m_128461_("runic_power").equals("greater_weaken")) {
            RunicMethods.postHitRunicGreaterWeaken(stack, target, attacker);
         }

         if (stack.m_41784_().m_128461_("runic_power").equals("imbued")) {
            RunicMethods.postHitRunicImbued(stack, target, attacker);
         }

         if (stack.m_41784_().m_128461_("runic_power").equals("greater_imbued")) {
            RunicMethods.postHitRunicGreaterImbued(stack, target, attacker);
         }

         if (stack.m_41784_().m_128461_("runic_power").equals("pincushion")) {
            RunicMethods.postHitRunicPinCushion(stack, target, attacker);
         }

         if (stack.m_41784_().m_128461_("runic_power").equals("greater_pincushion")) {
            RunicMethods.postHitRunicGreaterPinCushion(stack, target, attacker);
         }
      }

      return super.m_7579_(stack, target, attacker);
   }

   public void m_5551_(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
      if (!world.f_46443_ && stack.m_41784_().m_128461_("runic_power").contains("momentum")) {
         RunicMethods.stoppedUsingRunicMomentum(stack, world, user, remainingUseTicks);
      }
   }

   public void m_5929_(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
      if (!world.f_46443_) {
         if (stack.m_41784_().m_128461_("runic_power").equals("momentum")) {
            RunicMethods.usageTickRunicMomentum(stack, world, user, remainingUseTicks);
         }

         if (stack.m_41784_().m_128461_("runic_power").equals("greater_momentum")) {
            RunicMethods.usageTickRunicGreaterMomentum(stack, world, user, remainingUseTicks);
         }
      }
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player user, InteractionHand hand) {
      ItemStack itemStack = user.m_21120_(hand);
      if (itemStack.m_41784_().m_128461_("runic_power").contains("momentum")) {
         if (itemStack.m_41773_() >= itemStack.m_41776_() - 1) {
            return InteractionResultHolder.m_19100_(itemStack);
         } else {
            user.m_6672_(hand);
            world.m_6269_(null, user, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_WIND_SHOOT_FLYBY_02.get(), SoundSource.PLAYERS, 0.3F, 1.2F);
            return InteractionResultHolder.m_19096_(itemStack);
         }
      } else if (itemStack.m_41784_().m_128461_("runic_power").equals("ward")) {
         if (itemStack.m_41773_() >= itemStack.m_41776_() - 1) {
            return InteractionResultHolder.m_19100_(itemStack);
         } else {
            user.m_6672_(hand);
            user.m_147207_(new MobEffectInstance((MobEffect)EffectRegistry.WARD.get(), 120, 0), user);
            user.m_36335_().m_41524_(itemStack.m_41720_(), 120);
            user.m_21153_(user.m_21223_() / 2.0F);
            world.m_6269_(null, user, (SoundEvent)SoundRegistry.MAGIC_SWORD_SPELL_02.get(), SoundSource.PLAYERS, 0.3F, 1.2F);
            return InteractionResultHolder.m_19096_(itemStack);
         }
      } else if (itemStack.m_41784_().m_128461_("runic_power").equals("immolation")) {
         if (itemStack.m_41773_() >= itemStack.m_41776_() - 1) {
            return InteractionResultHolder.m_19100_(itemStack);
         } else {
            user.m_6672_(hand);
            user.m_147207_(new MobEffectInstance((MobEffect)EffectRegistry.IMMOLATION.get(), 36000, 0), user);
            user.m_36335_().m_41524_(itemStack.m_41720_(), 40);
            world.m_6269_(null, user, (SoundEvent)SoundRegistry.MAGIC_SWORD_SPELL_02.get(), SoundSource.PLAYERS, 0.3F, 0.6F);
            return InteractionResultHolder.m_19096_(itemStack);
         }
      } else {
         return InteractionResultHolder.m_19100_(itemStack);
      }
   }

   public int m_8105_(ItemStack stack) {
      if (stack.m_41784_().m_128461_("runic_power").contains("momentum")) {
         maxUseTime = 15;
      }

      if (stack.m_41784_().m_128461_("runic_power").equals("ward")) {
         maxUseTime = 1;
      }

      if (stack.m_41784_().m_128461_("runic_power").equals("immolation")) {
         maxUseTime = 1;
      }

      return maxUseTime;
   }

   public UseAnim m_6164_(ItemStack stack) {
      return UseAnim.BLOCK;
   }

   public void m_6883_(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
      if (entity.f_19797_ % 4 == 0
         && SimplySwordsConfig.getBooleanValue("enable_passive_particles")
         && entity instanceof Player player
         && (player.m_6844_(EquipmentSlot.MAINHAND) == stack || player.m_6844_(EquipmentSlot.OFFHAND) == stack)) {
         float randomx = (float)(Math.random() * 6.0);
         float randomz = (float)(Math.random() * 6.0);
         world.m_7106_(
            ParticleTypes.f_123809_,
            player.m_20185_() + player.m_204034_(this).m_7096_(),
            player.m_20186_() + player.m_204034_(this).m_7098_() + 1.3,
            player.m_20189_() + player.m_204034_(this).m_7094_(),
            (double)(-3.0F + randomx),
            0.0,
            (double)(-3.0F + randomz)
         );
      }

      if (!world.f_46443_ && entity instanceof Player player) {
         if (stack.m_41784_().m_128461_("runic_power").equals("unstable")
            && (player.m_6844_(EquipmentSlot.MAINHAND) == stack || player.m_6844_(EquipmentSlot.OFFHAND) == stack)) {
            RunicMethods.inventoryTickRunicUnstable(stack, world, player, slot, selected);
         }

         if (stack.m_41784_().m_128461_("runic_power").equals("active_defence")
            && (player.m_6844_(EquipmentSlot.MAINHAND) == stack || player.m_6844_(EquipmentSlot.OFFHAND) == stack)) {
            RunicMethods.inventoryTickRunicActiveDefence(stack, world, player, slot, selected);
         }

         if (stack.m_41784_().m_128461_("runic_power").equals("frost_ward")
            && (player.m_6844_(EquipmentSlot.MAINHAND) == stack || player.m_6844_(EquipmentSlot.OFFHAND) == stack)) {
            RunicMethods.inventoryTickRunicFrostWard(stack, world, player, slot, selected);
         }

         super.m_6883_(stack, world, entity, slot, selected);
      }
   }

   public void m_7836_(ItemStack stack, Level world, Player player) {
      if (!world.f_46443_) {
         String runicPowerSelection = HelperMethods.chooseRunicPower();
         stack.m_41784_().m_128359_("runic_power", runicPowerSelection);
      }
   }

   public void m_7373_(ItemStack itemStack, Level world, List<Component> tooltip, TooltipFlag tooltipContext) {
      tooltip.add(Component.m_237113_(""));
      if (itemStack.m_41784_().m_128461_("runic_power").contains("greater")) {
         tooltip.add(
            Component.m_237115_("item.simplyswords.greater_runic_power").m_130944_(new ChatFormatting[]{ChatFormatting.DARK_AQUA, ChatFormatting.BOLD})
         );
      }

      if (itemStack.m_41784_().m_128461_("runic_power").isEmpty()) {
         tooltip.add(
            Component.m_237115_("item.simplyswords.unidentifiedsworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.AQUA, ChatFormatting.BOLD})
         );
         tooltip.add(Component.m_237115_("item.simplyswords.unidentifiedsworditem.tooltip2"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").equals("freeze")) {
         tooltip.add(
            Component.m_237115_("item.simplyswords.freezesworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.AQUA, ChatFormatting.BOLD})
         );
         tooltip.add(Component.m_237115_("item.simplyswords.freezesworditem.tooltip2"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").equals("wildfire")) {
         tooltip.add(
            Component.m_237115_("item.simplyswords.wildfiresworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.AQUA, ChatFormatting.BOLD})
         );
         tooltip.add(Component.m_237115_("item.simplyswords.wildfiresworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.wildfiresworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("slow")) {
         tooltip.add(
            Component.m_237115_("item.simplyswords.slownesssworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.AQUA, ChatFormatting.BOLD})
         );
         tooltip.add(Component.m_237115_("item.simplyswords.slownesssworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.slownesssworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("swiftness")) {
         tooltip.add(Component.m_237115_("item.simplyswords.speedsworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.AQUA, ChatFormatting.BOLD}));
         tooltip.add(Component.m_237115_("item.simplyswords.speedsworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.speedsworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("float")) {
         tooltip.add(
            Component.m_237115_("item.simplyswords.levitationsworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.AQUA, ChatFormatting.BOLD})
         );
         tooltip.add(Component.m_237115_("item.simplyswords.levitationsworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.levitationsworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("zephyr")) {
         tooltip.add(
            Component.m_237115_("item.simplyswords.zephyrsworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.AQUA, ChatFormatting.BOLD})
         );
         tooltip.add(Component.m_237115_("item.simplyswords.zephyrsworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.zephyrsworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("shielding")) {
         tooltip.add(
            Component.m_237115_("item.simplyswords.shieldingsworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.AQUA, ChatFormatting.BOLD})
         );
         tooltip.add(Component.m_237115_("item.simplyswords.shieldingsworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.shieldingsworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("stoneskin")) {
         tooltip.add(
            Component.m_237115_("item.simplyswords.stoneskinsworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.AQUA, ChatFormatting.BOLD})
         );
         tooltip.add(Component.m_237115_("item.simplyswords.stoneskinsworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.stoneskinsworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").equals("frost_ward")) {
         tooltip.add(
            Component.m_237115_("item.simplyswords.frostwardsworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.AQUA, ChatFormatting.BOLD})
         );
         tooltip.add(Component.m_237115_("item.simplyswords.frostwardsworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.frostwardsworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("trailblaze")) {
         tooltip.add(
            Component.m_237115_("item.simplyswords.trailblazesworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.AQUA, ChatFormatting.BOLD})
         );
         tooltip.add(Component.m_237115_("item.simplyswords.trailblazesworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.trailblazesworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").equals("active_defence")) {
         tooltip.add(
            Component.m_237115_("item.simplyswords.activedefencesworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.AQUA, ChatFormatting.BOLD})
         );
         tooltip.add(Component.m_237115_("item.simplyswords.activedefencesworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.activedefencesworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("weaken")) {
         tooltip.add(
            Component.m_237115_("item.simplyswords.weakensworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.AQUA, ChatFormatting.BOLD})
         );
         tooltip.add(Component.m_237115_("item.simplyswords.weakensworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.weakensworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").equals("unstable")) {
         tooltip.add(
            Component.m_237115_("item.simplyswords.unstablesworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.AQUA, ChatFormatting.BOLD})
         );
         tooltip.add(Component.m_237115_("item.simplyswords.unstablesworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.unstablesworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("momentum")) {
         tooltip.add(
            Component.m_237115_("item.simplyswords.momentumsworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.AQUA, ChatFormatting.BOLD})
         );
         tooltip.add(Component.m_237115_("item.simplyswords.momentumsworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.momentumsworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("imbued")) {
         tooltip.add(
            Component.m_237115_("item.simplyswords.imbuedsworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.AQUA, ChatFormatting.BOLD})
         );
         tooltip.add(Component.m_237115_("item.simplyswords.imbuedsworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.imbuedsworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("pincushion")) {
         tooltip.add(
            Component.m_237115_("item.simplyswords.pincushionsworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.AQUA, ChatFormatting.BOLD})
         );
         tooltip.add(Component.m_237115_("item.simplyswords.pincushionsworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.pincushionsworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").equals("ward")) {
         tooltip.add(Component.m_237115_("item.simplyswords.wardsworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.AQUA, ChatFormatting.BOLD}));
         tooltip.add(Component.m_237113_(""));
         tooltip.add(Component.m_237115_("item.simplyswords.onrightclick").m_130944_(new ChatFormatting[]{ChatFormatting.BOLD, ChatFormatting.GREEN}));
         tooltip.add(Component.m_237115_("item.simplyswords.wardsworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.wardsworditem.tooltip3"));
         tooltip.add(Component.m_237115_("item.simplyswords.wardsworditem.tooltip4"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").equals("immolation")) {
         tooltip.add(
            Component.m_237115_("item.simplyswords.immolationsworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.AQUA, ChatFormatting.BOLD})
         );
         tooltip.add(Component.m_237113_(""));
         tooltip.add(Component.m_237115_("item.simplyswords.onrightclick").m_130944_(new ChatFormatting[]{ChatFormatting.BOLD, ChatFormatting.GREEN}));
         tooltip.add(Component.m_237115_("item.simplyswords.immolationsworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.immolationsworditem.tooltip3"));
         tooltip.add(Component.m_237115_("item.simplyswords.immolationsworditem.tooltip4"));
      }
   }
}
