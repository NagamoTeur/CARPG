package shadows.apotheosis.util;

import com.google.common.base.Predicates;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.apotheosis.core.attributeslib.AttributesLib;
import shadows.apotheosis.core.attributeslib.api.IFormattableAttribute;
import shadows.apotheosis.ench.table.ApothEnchantmentMenu;
import shadows.apotheosis.ench.table.EnchantingStatManager;

public class CommonTooltipUtil {
   public static void appendBossData(Level level, LivingEntity entity, Consumer<Component> tooltip) {
      LootRarity rarity = LootRarity.byId(entity.getPersistentData().m_128461_("apoth.rarity"));
      if (rarity != null) {
         tooltip.accept(Component.m_237110_("info.apotheosis.boss", new Object[]{rarity.toComponent()}).m_130940_(ChatFormatting.GRAY));
         if (!FMLEnvironment.production) {
            tooltip.accept(CommonComponents.f_237098_);
            tooltip.accept(Component.m_237115_("info.apotheosis.boss_modifiers").m_130940_(ChatFormatting.GRAY));
            AttributeMap map = entity.m_21204_();
            ForgeRegistries.ATTRIBUTES.getValues().stream().<AttributeInstance>map(map::m_22146_).filter(Predicates.notNull()).forEach(inst -> {
               for (AttributeModifier modif : inst.m_22122_()) {
                  if (modif.m_22214_().startsWith("placebo_random_modifier_")) {
                     tooltip.accept(IFormattableAttribute.toComponent(inst.m_22099_(), modif, AttributesLib.getTooltipFlag()));
                  }
               }
            });
         }
      }
   }

   public static void appendBlockStats(Level world, BlockState state, Consumer<Component> tooltip) {
      float maxEterna = EnchantingStatManager.getMaxEterna(state, world, BlockPos.f_121853_);
      float eterna = EnchantingStatManager.getEterna(state, world, BlockPos.f_121853_);
      float quanta = EnchantingStatManager.getQuanta(state, world, BlockPos.f_121853_);
      float arcana = EnchantingStatManager.getArcana(state, world, BlockPos.f_121853_);
      float rectification = EnchantingStatManager.getQuantaRectification(state, world, BlockPos.f_121853_);
      int clues = EnchantingStatManager.getBonusClues(state, world, BlockPos.f_121853_);
      if (eterna != 0.0F || quanta != 0.0F || arcana != 0.0F || rectification != 0.0F || clues != 0) {
         tooltip.accept(Component.m_237115_("info.apotheosis.ench_stats").m_130940_(ChatFormatting.GOLD));
      }

      if (eterna != 0.0F) {
         if (eterna > 0.0F) {
            tooltip.accept(
               Component.m_237110_("info.apotheosis.eterna.p", new Object[]{String.format("%.2f", eterna), String.format("%.2f", maxEterna)})
                  .m_130940_(ChatFormatting.GREEN)
            );
         } else {
            tooltip.accept(Component.m_237110_("info.apotheosis.eterna", new Object[]{String.format("%.2f", eterna)}).m_130940_(ChatFormatting.GREEN));
         }
      }

      if (quanta != 0.0F) {
         tooltip.accept(
            Component.m_237110_("info.apotheosis.quanta" + (quanta > 0.0F ? ".p" : ""), new Object[]{String.format("%.2f", quanta)})
               .m_130940_(ChatFormatting.RED)
         );
      }

      if (arcana != 0.0F) {
         tooltip.accept(
            Component.m_237110_("info.apotheosis.arcana" + (arcana > 0.0F ? ".p" : ""), new Object[]{String.format("%.2f", arcana)})
               .m_130940_(ChatFormatting.DARK_PURPLE)
         );
      }

      if (rectification != 0.0F) {
         tooltip.accept(
            Component.m_237110_("info.apotheosis.rectification" + (rectification > 0.0F ? ".p" : ""), new Object[]{String.format("%.2f", rectification)})
               .m_130940_(ChatFormatting.YELLOW)
         );
      }

      if (clues != 0) {
         tooltip.accept(
            Component.m_237110_("info.apotheosis.clues" + (clues > 0 ? ".p" : ""), new Object[]{String.format("%d", clues)})
               .m_130940_(ChatFormatting.DARK_AQUA)
         );
      }
   }

   public static void appendTableStats(Level world, BlockPos pos, Consumer<Component> tooltip) {
      ApothEnchantmentMenu.TableStats stats = ApothEnchantmentMenu.gatherStats(world, pos);
      tooltip.accept(
         Component.m_237110_(
               "info.apotheosis.eterna.t",
               new Object[]{String.format("%.2f", stats.eterna()), String.format("%.2f", EnchantingStatManager.getAbsoluteMaxEterna())}
            )
            .m_130940_(ChatFormatting.GREEN)
      );
      tooltip.accept(
         Component.m_237110_("info.apotheosis.quanta.t", new Object[]{String.format("%.2f", Math.min(100.0F, stats.quanta()))}).m_130940_(ChatFormatting.RED)
      );
      tooltip.accept(
         Component.m_237110_("info.apotheosis.arcana.t", new Object[]{String.format("%.2f", Math.min(100.0F, stats.arcana()))})
            .m_130940_(ChatFormatting.DARK_PURPLE)
      );
      tooltip.accept(
         Component.m_237110_("info.apotheosis.rectification.t", new Object[]{String.format("%.2f", Mth.m_14036_(stats.rectification(), -100.0F, 100.0F))})
            .m_130940_(ChatFormatting.YELLOW)
      );
      tooltip.accept(Component.m_237110_("info.apotheosis.clues.t", new Object[]{String.format("%d", stats.clues())}).m_130940_(ChatFormatting.DARK_AQUA));
   }
}
