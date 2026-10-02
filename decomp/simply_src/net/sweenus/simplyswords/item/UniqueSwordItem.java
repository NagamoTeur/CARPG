package net.sweenus.simplyswords.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.sweenus.simplyswords.config.SimplySwordsConfig;
import net.sweenus.simplyswords.registry.ItemsRegistry;
import net.sweenus.simplyswords.util.HelperMethods;
import net.sweenus.simplyswords.util.RunicMethods;

public class UniqueSwordItem extends SwordItem {
   public static int maxUseTime;

   public UniqueSwordItem(Tier toolMaterial, int attackDamage, float attackSpeed, Properties settings) {
      super(toolMaterial, attackDamage, attackSpeed, settings.m_41486_());
   }

   public void m_6883_(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
      if (stack.m_41784_().m_128461_("runic_power").isEmpty() && stack.m_41784_().m_128461_("nether_power").isEmpty()) {
         float socketChance = (float)(Math.random() * 100.0);
         float socketChance2 = (float)(Math.random() * 100.0);
         if (socketChance > 49.0F) {
            stack.m_41784_().m_128359_("runic_power", "socket_empty");
         } else if (socketChance < 50.0F) {
            stack.m_41784_().m_128359_("runic_power", "no_socket");
         }

         if (socketChance2 > 49.0F) {
            stack.m_41784_().m_128359_("nether_power", "socket_empty");
         } else if (socketChance2 < 50.0F) {
            stack.m_41784_().m_128359_("nether_power", "no_socket");
         }
      }

      super.m_6883_(stack, world, entity, slot, selected);
   }

   public boolean m_142305_(ItemStack stack, ItemStack otherStack, Slot slot, ClickAction clickType, Player player, SlotAccess cursorStackReference) {
      if (stack.m_41784_().m_128461_("runic_power").equals("socket_empty")
         || stack.m_41784_().m_128461_("nether_power").equals("socket_empty") && SimplySwordsConfig.getBooleanValue("enable_unique_gem_sockets")) {
         if (otherStack.m_150930_((Item)ItemsRegistry.RUNEFUSED_GEM.get())) {
            String runicPowerSelection = otherStack.m_41784_().m_128461_("runic_power");
            stack.m_41784_().m_128359_("runic_power", runicPowerSelection);
            player.f_19853_.m_6269_(null, player, SoundEvents.f_11671_, SoundSource.BLOCKS, 1.0F, 1.0F);
            otherStack.m_41774_(1);
         } else if (otherStack.m_150930_((Item)ItemsRegistry.NETHERFUSED_GEM.get())) {
            String netherPowerSelection = otherStack.m_41784_().m_128461_("nether_power");
            stack.m_41784_().m_128359_("nether_power", netherPowerSelection);
            player.f_19853_.m_6269_(null, player, SoundEvents.f_11671_, SoundSource.BLOCKS, 1.0F, 1.0F);
            otherStack.m_41774_(1);
         }
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

         if (stack.m_41784_().m_128461_("nether_power").equals("echo")) {
            RunicMethods.postHitNetherEcho(stack, target, attacker);
         }

         if (stack.m_41784_().m_128461_("nether_power").equals("berserk")) {
            RunicMethods.postHitNetherBerserk(stack, target, attacker);
         }

         if (stack.m_41784_().m_128461_("nether_power").equals("radiance")) {
            RunicMethods.postHitNetherRadiance(stack, target, attacker);
         }

         if (stack.m_41784_().m_128461_("nether_power").equals("onslaught")) {
            RunicMethods.postHitNetherOnslaught(stack, target, attacker);
         }

         if (stack.m_41784_().m_128461_("nether_power").equals("nullification")) {
            RunicMethods.postHitNetherNullification(stack, target, attacker);
         }
      }

      return super.m_7579_(stack, target, attacker);
   }

   public void m_7373_(ItemStack itemStack, Level world, List<Component> tooltip, TooltipFlag tooltipContext) {
      tooltip.add(Component.m_237113_(""));
      if (itemStack.m_41784_().m_128461_("runic_power").contains("greater")) {
         tooltip.add(Component.m_237115_("item.simplyswords.greater_runic_power").m_130940_(ChatFormatting.DARK_AQUA));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("socket_empty")) {
         tooltip.add(Component.m_237115_("item.simplyswords.empty_runic_slot").m_130940_(ChatFormatting.GRAY));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").equals("freeze")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.freeze").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237115_("item.simplyswords.freezesworditem.tooltip2"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").equals("wildfire")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.wildfire").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237115_("item.simplyswords.wildfiresworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.wildfiresworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("slow")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.slow").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237115_("item.simplyswords.slownesssworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.slownesssworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("swiftness")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.swiftness").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237115_("item.simplyswords.speedsworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.speedsworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("float")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.float").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237115_("item.simplyswords.levitationsworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.levitationsworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("zephyr")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.zephyr").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237115_("item.simplyswords.zephyrsworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.zephyrsworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("shielding")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.shielding").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237115_("item.simplyswords.shieldingsworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.shieldingsworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("stoneskin")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.stoneskin").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237115_("item.simplyswords.stoneskinsworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.stoneskinsworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").equals("frost_ward")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.frost_ward").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237115_("item.simplyswords.frostwardsworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.frostwardsworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("trailblaze")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.trailblaze").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237115_("item.simplyswords.trailblazesworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.trailblazesworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").equals("active_defence")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.active_defence").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237115_("item.simplyswords.activedefencesworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.activedefencesworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("weaken")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.weaken").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237115_("item.simplyswords.weakensworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.weakensworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").equals("unstable")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.unstable").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237115_("item.simplyswords.unstablesworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.unstablesworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("momentum")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.momentum").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237115_("item.simplyswords.momentumsworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.momentumsworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("imbued")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.imbued").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237115_("item.simplyswords.imbuedsworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.imbuedsworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("pincushion")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.pincushion").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237115_("item.simplyswords.pincushionsworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.pincushionsworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").equals("ward")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.ward").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237113_(""));
         tooltip.add(Component.m_237115_("item.simplyswords.onrightclick").m_130944_(new ChatFormatting[]{ChatFormatting.BOLD, ChatFormatting.GREEN}));
         tooltip.add(Component.m_237115_("item.simplyswords.wardsworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.wardsworditem.tooltip3"));
         tooltip.add(Component.m_237115_("item.simplyswords.wardsworditem.tooltip4"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").equals("immolation")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.immolation").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237113_(""));
         tooltip.add(Component.m_237115_("item.simplyswords.onrightclick").m_130944_(new ChatFormatting[]{ChatFormatting.BOLD, ChatFormatting.GREEN}));
         tooltip.add(Component.m_237115_("item.simplyswords.immolationsworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.immolationsworditem.tooltip3"));
         tooltip.add(Component.m_237115_("item.simplyswords.immolationsworditem.tooltip4"));
      }

      tooltip.add(Component.m_237113_(""));
      if (itemStack.m_41784_().m_128461_("nether_power").contains("socket_empty")) {
         tooltip.add(Component.m_237115_("item.simplyswords.empty_nether_slot").m_130940_(ChatFormatting.GRAY));
      }

      if (itemStack.m_41784_().m_128461_("nether_power").equals("echo")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.echo").m_130940_(ChatFormatting.RED));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.echo.description"));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.echo.description2"));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.echo.description3"));
      }

      if (itemStack.m_41784_().m_128461_("nether_power").equals("berserk")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.berserk").m_130940_(ChatFormatting.RED));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.berserk.description"));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.berserk.description2"));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.berserk.description3"));
      }

      if (itemStack.m_41784_().m_128461_("nether_power").equals("radiance")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.radiance").m_130940_(ChatFormatting.RED));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.radiance.description"));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.radiance.description2"));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.radiance.description3"));
      }

      if (itemStack.m_41784_().m_128461_("nether_power").equals("onslaught")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.onslaught").m_130940_(ChatFormatting.RED));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.onslaught.description"));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.onslaught.description2"));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.onslaught.description3"));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.onslaught.description4"));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.onslaught.description5"));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.onslaught.description6"));
      }

      if (itemStack.m_41784_().m_128461_("nether_power").equals("nullification")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.nullification").m_130940_(ChatFormatting.RED));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.nullification.description"));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.nullification.description2"));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.nullification.description3"));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.nullification.description4"));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.nullification.description5"));
      }
   }
}
