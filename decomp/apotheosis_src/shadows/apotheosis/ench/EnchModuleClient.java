package shadows.apotheosis.ench;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.client.particle.EnchantmentTableParticle.Provider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.EnchantTableRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.event.ScreenEvent.Render.Post;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.ench.library.EnchLibraryScreen;
import shadows.apotheosis.ench.table.ApothEnchantScreen;
import shadows.apotheosis.ench.table.EnchantingStatManager;
import shadows.placebo.util.EnchantmentUtils;

public class EnchModuleClient {
   static BlockHitResult res = BlockHitResult.m_82426_(Vec3.f_82478_, Direction.NORTH, BlockPos.f_121853_);

   @SubscribeEvent
   public void tooltips(ItemTooltipEvent e) {
      Item i = e.getItemStack().m_41720_();
      List<Component> tooltip = e.getToolTip();
      if (i == Items.f_41863_) {
         tooltip.add(Component.m_237115_("info.apotheosis.cobweb").m_130940_(ChatFormatting.GRAY));
      } else if (i == Apoth.Items.PRISMATIC_WEB.get()) {
         tooltip.add(Component.m_237115_("info.apotheosis.prismatic_cobweb").m_130940_(ChatFormatting.GRAY));
      } else if (i instanceof BlockItem) {
         Block block = ((BlockItem)i).m_40614_();
         Level world = Minecraft.m_91087_().f_91073_;
         if (world == null || Minecraft.m_91087_().f_91074_ == null) {
            return;
         }

         BlockPlaceContext ctx = new BlockPlaceContext(world, Minecraft.m_91087_().f_91074_, InteractionHand.MAIN_HAND, e.getItemStack(), res) {
         };
         BlockState state = null;

         try {
            state = block.m_5573_(ctx);
         } catch (Exception var14) {
            EnchModule.LOGGER.debug(var14.getMessage());
            StackTraceElement[] trace = var14.getStackTrace();

            for (StackTraceElement traceElement : trace) {
               EnchModule.LOGGER.debug("\tat " + traceElement);
            }
         }

         if (state == null) {
            state = block.m_49966_();
         }

         float maxEterna = EnchantingStatManager.getMaxEterna(state, world, BlockPos.f_121853_);
         float eterna = EnchantingStatManager.getEterna(state, world, BlockPos.f_121853_);
         float quanta = EnchantingStatManager.getQuanta(state, world, BlockPos.f_121853_);
         float arcana = EnchantingStatManager.getArcana(state, world, BlockPos.f_121853_);
         float rectification = EnchantingStatManager.getQuantaRectification(state, world, BlockPos.f_121853_);
         int clues = EnchantingStatManager.getBonusClues(state, world, BlockPos.f_121853_);
         if (eterna != 0.0F || quanta != 0.0F || arcana != 0.0F || rectification != 0.0F || clues != 0) {
            tooltip.add(Component.m_237115_("info.apotheosis.ench_stats").m_130940_(ChatFormatting.GOLD));
         }

         if (eterna != 0.0F) {
            if (eterna > 0.0F) {
               tooltip.add(
                  Component.m_237110_("info.apotheosis.eterna.p", new Object[]{String.format("%.2f", eterna), String.format("%.2f", maxEterna)})
                     .m_130940_(ChatFormatting.GREEN)
               );
            } else {
               tooltip.add(Component.m_237110_("info.apotheosis.eterna", new Object[]{String.format("%.2f", eterna)}).m_130940_(ChatFormatting.GREEN));
            }
         }

         if (quanta != 0.0F) {
            tooltip.add(
               Component.m_237110_("info.apotheosis.quanta" + (quanta > 0.0F ? ".p" : ""), new Object[]{String.format("%.2f", quanta)})
                  .m_130940_(ChatFormatting.RED)
            );
         }

         if (arcana != 0.0F) {
            tooltip.add(
               Component.m_237110_("info.apotheosis.arcana" + (arcana > 0.0F ? ".p" : ""), new Object[]{String.format("%.2f", arcana)})
                  .m_130940_(ChatFormatting.DARK_PURPLE)
            );
         }

         if (rectification != 0.0F) {
            tooltip.add(
               Component.m_237110_("info.apotheosis.rectification" + (rectification > 0.0F ? ".p" : ""), new Object[]{String.format("%.2f", rectification)})
                  .m_130940_(ChatFormatting.YELLOW)
            );
         }

         if (clues != 0) {
            tooltip.add(
               Component.m_237110_("info.apotheosis.clues" + (clues > 0 ? ".p" : ""), new Object[]{String.format("%d", clues)})
                  .m_130940_(ChatFormatting.DARK_AQUA)
            );
         }
      } else if (i == Items.f_42690_) {
         ItemStack stack = e.getItemStack();
         Map<Enchantment, Integer> enchMap = EnchantmentHelper.m_44831_(stack);
         if (enchMap.size() == 1) {
            Enchantment ench = enchMap.keySet().iterator().next();
            int lvl = enchMap.values().iterator().next();
            if (!ModList.get().isLoaded("enchdesc") && "apotheosis".equals(ForgeRegistries.ENCHANTMENTS.getKey(ench).m_135827_())) {
               tooltip.add(Component.m_237115_(ench.m_44704_() + ".desc").m_130940_(ChatFormatting.DARK_GRAY));
            }

            if (EnchConfig.showEnchantedBookMetadata) {
               EnchantmentInfo info = EnchModule.getEnchInfo(ench);
               Object[] args = new Object[]{
                  boolComp("info.apotheosis.discoverable", info.isDiscoverable()),
                  boolComp("info.apotheosis.lootable", info.isLootable()),
                  boolComp("info.apotheosis.tradeable", info.isTradeable()),
                  boolComp("info.apotheosis.treasure", info.isTreasure())
               };
               if (e.getFlags().m_7050_()) {
                  tooltip.add(Component.m_237110_("%s ┇ %s ┇ %s ┇ %s", new Object[]{args[0], args[1], args[2], args[3]}).m_130940_(ChatFormatting.DARK_GRAY));
                  tooltip.add(
                     Component.m_237110_("info.apotheosis.book_range", new Object[]{info.getMinPower(lvl), info.getMaxPower(lvl)})
                        .m_130940_(ChatFormatting.GREEN)
                  );
               } else {
                  tooltip.add(Component.m_237110_("%s ┇ %s", new Object[]{args[2], args[3]}).m_130940_(ChatFormatting.DARK_GRAY));
               }
            }
         }
      }
   }

   @SubscribeEvent
   public void drawAnvilCostBlob(Post e) {
      if (e.getScreen() instanceof AnvilScreen anv) {
         int level = ((AnvilMenu)anv.m_6262_()).m_39028_();
         if (level <= 0 || !((AnvilMenu)anv.m_6262_()).m_38853_(2).m_6657_()) {
            return;
         }

         List<Component> list = new ArrayList<>();
         list.add(
            Component.m_237113_(I18n.m_118938_("info.apotheosis.anvil_at", new Object[]{level}))
               .m_130944_(new ChatFormatting[]{ChatFormatting.UNDERLINE, ChatFormatting.GREEN})
         );
         int expCost = EnchantmentUtils.getTotalExperienceForLevel(level);
         list.add(
            Component.m_237110_(
               "info.apotheosis.anvil_xp_cost",
               new Object[]{Component.m_237113_(expCost + "").m_130940_(ChatFormatting.GREEN), Component.m_237113_(level + "").m_130940_(ChatFormatting.GREEN)}
            )
         );
         this.drawOnLeft(anv, e.getPoseStack(), list, anv.getGuiTop() + 28);
      }
   }

   private static Component boolComp(String key, boolean flag) {
      return Component.m_237115_(key + (flag ? "" : ".not")).m_130948_(Style.f_131099_.m_178520_(flag ? 1083408 : 11146774));
   }

   public static void init() {
      BlockEntityRenderers.m_173590_(BlockEntityType.f_58928_, EnchantTableRenderer::new);
      MenuScreens.m_96206_((MenuType)Apoth.Menus.ENCHANTING_TABLE.get(), ApothEnchantScreen::new);
      MenuScreens.m_96206_((MenuType)Apoth.Menus.LIBRARY.get(), EnchLibraryScreen::new);
   }

   @SubscribeEvent
   public static void particleFactories(RegisterParticleProvidersEvent e) {
      e.register((ParticleType)Apoth.Particles.ENCHANT_FIRE.get(), Provider::new);
      e.register((ParticleType)Apoth.Particles.ENCHANT_WATER.get(), Provider::new);
      e.register((ParticleType)Apoth.Particles.ENCHANT_SCULK.get(), Provider::new);
      e.register((ParticleType)Apoth.Particles.ENCHANT_END.get(), Provider::new);
   }

   public void drawOnLeft(AnvilScreen screen, PoseStack stack, List<Component> list, int y) {
      if (!list.isEmpty()) {
         Font font = screen.getMinecraft().f_91062_;
         int xPos = screen.getGuiLeft() - 16 - list.stream().<Integer>map(font::m_92852_).max(Integer::compare).get();
         int maxWidth = 9999;
         if (xPos < 0) {
            maxWidth = screen.getGuiLeft() - 6;
            xPos = -8;
         }

         List<FormattedText> split = new ArrayList<>();
         int lambdastupid = maxWidth;
         list.forEach(comp -> split.addAll(font.m_92865_().m_92414_(comp, lambdastupid, comp.m_7383_())));
         screen.renderComponentTooltip(stack, split, xPos, y, font);
      }
   }
}
