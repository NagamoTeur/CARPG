package com.rolfmao.upgradednetherite.content;

import com.rolfmao.upgradedcore.helpers.TooltipHelper;
import com.rolfmao.upgradednetherite.config.UpgradedNetheriteConfig;
import com.rolfmao.upgradednetherite.init.ModItems;
import com.rolfmao.upgradednetherite.utils.ToolUtil;
import com.rolfmao.upgradednetherite.utils.check.CorruptUtil;
import com.rolfmao.upgradednetherite.utils.check.EchoUtil;
import com.rolfmao.upgradednetherite.utils.check.EnderUtil;
import com.rolfmao.upgradednetherite.utils.check.FeatherUtil;
import com.rolfmao.upgradednetherite.utils.check.FireUtil;
import com.rolfmao.upgradednetherite.utils.check.GoldUtil;
import com.rolfmao.upgradednetherite.utils.check.PhantomUtil;
import com.rolfmao.upgradednetherite.utils.check.PoisonUtil;
import com.rolfmao.upgradednetherite.utils.check.WaterUtil;
import com.rolfmao.upgradednetherite.utils.check.WitherUtil;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class UpgradedNetheriteAxe extends AxeItem {
   public UpgradedNetheriteAxe(Tier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
      super(tier, (float)attackDamageIn, attackSpeedIn, properties);
   }

   public boolean canApplyAtEnchantingTable(ItemStack itemStack, Enchantment enchantment) {
      if (itemStack.m_41720_() == ModItems.CORRUPT_UPGRADED_NETHERITE_AXE.get() && enchantment == Enchantments.f_44962_) {
         return false;
      } else if ((itemStack.m_41720_() == ModItems.GOLD_UPGRADED_NETHERITE_AXE.get() || itemStack.m_41720_() == ModItems.CORRUPT_UPGRADED_NETHERITE_AXE.get())
         && enchantment == Enchantments.f_44982_) {
         return true;
      } else {
         return itemStack.m_41720_() == ModItems.FIRE_UPGRADED_NETHERITE_AXE.get() && enchantment == Enchantments.f_44981_
            ? true
            : enchantment.f_44672_.m_7454_(itemStack.m_41720_());
      }
   }

   public void m_6883_(ItemStack itemStack, Level level, Entity entity, int slot, boolean p_77663_5_) {
      if (itemStack.m_41720_() == ModItems.CORRUPT_UPGRADED_NETHERITE_AXE.get()
         && itemStack.m_41793_()
         && EnchantmentHelper.getTagEnchantmentLevel(Enchantments.f_44962_, itemStack) > 0) {
         Map<Enchantment, Integer> enchantments = EnchantmentHelper.m_44831_(itemStack);
         if (enchantments.containsKey(Enchantments.f_44962_)) {
            enchantments.remove(Enchantments.f_44962_);
            EnchantmentHelper.m_44865_(enchantments, itemStack);
         }
      }
   }

   public void m_6787_(CreativeModeTab tab, NonNullList<ItemStack> list) {
      if (this.m_220152_(tab)) {
         ItemStack itemStack = new ItemStack(this);
         if (GoldUtil.isGoldTool(itemStack)) {
            list.add(itemStack);
         } else if (FireUtil.isFireTool(itemStack)) {
            list.add(itemStack);
         } else if (EnderUtil.isEnderTool(itemStack)) {
            list.add(itemStack);
         } else if (WaterUtil.isWaterTool(itemStack)) {
            list.add(itemStack);
         } else if (WitherUtil.isWitherTool(itemStack)) {
            list.add(itemStack);
         } else if (PoisonUtil.isPoisonTool(itemStack)) {
            list.add(itemStack);
         } else if (PhantomUtil.isPhantomTool(itemStack)) {
            list.add(itemStack);
         } else if (FeatherUtil.isFeatherTool(itemStack)) {
            list.add(itemStack);
         } else if (CorruptUtil.isCorruptTool(itemStack)) {
            list.add(itemStack);
         } else if (EchoUtil.isEchoTool(itemStack)) {
            list.add(itemStack);
         } else if (tab == CreativeModeTab.f_40754_) {
            list.add(itemStack);
         }
      }
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand interactionHand) {
      ItemStack stack = player.m_21205_();
      if (player.m_6047_() && !player.m_36335_().m_41519_(stack.m_41720_()) && ToolUtil.isUsingEffectTool(player)) {
         ToolUtil.toggleDisableEffect(player);
         return new InteractionResultHolder(InteractionResult.SUCCESS, stack);
      } else {
         return super.m_7203_(level, player, interactionHand);
      }
   }

   public InteractionResult m_6225_(UseOnContext context) {
      InteractionResult iResult = super.m_6225_(context);
      if (iResult == InteractionResult.PASS
         && context.m_43723_() != null
         && context.m_43723_().m_6047_()
         && EnderUtil.isEnderToolOrWeapon(context.m_43722_())
         && !context.m_43723_().m_36335_().m_41519_(context.m_43722_().m_41720_())) {
         iResult = ToolUtil.EnderSetTag(context);
      }

      if (iResult == InteractionResult.PASS) {
         iResult = super.m_7203_(context.m_43725_(), context.m_43723_(), context.m_43724_()).m_19089_();
      }

      return iResult;
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack itemStack, Level level, List<Component> tooltip, TooltipFlag tooltipFlag) {
      super.m_7373_(itemStack, level, tooltip, tooltipFlag);
      if (!UpgradedNetheriteConfig.DisableTooltips) {
         if (Screen.m_96638_()) {
            if (!GoldUtil.isGoldTool(itemStack)
               || !UpgradedNetheriteConfig.EnableDamageBonusGoldWeapon
                  && !UpgradedNetheriteConfig.EnableFortuneBonus
                  && !UpgradedNetheriteConfig.EnableLootingBonus) {
               if (!FireUtil.isFireTool(itemStack) || !UpgradedNetheriteConfig.EnableDamageBonusFireWeapon && !UpgradedNetheriteConfig.EnableAutoSmelt) {
                  if (EnderUtil.isEnderTool(itemStack)) {
                     if (UpgradedNetheriteConfig.EnablePreventTeleport) {
                        tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                        tooltip.add(Component.m_237115_("upgradednetherite.OnHit.TT"));
                        tooltip.add(Component.m_237115_("upgradednetherite.Ender_Weapon.TT"));
                     }

                     tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                     tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
                     if (UpgradedNetheriteConfig.EnableDamageBonusEnderWeapon) {
                        TooltipHelper.addTWO(
                           tooltip, "upgradednetherite.Ender_Weapon2.TT", new Object[]{"§6" + UpgradedNetheriteConfig.DamageBonusEnderWeapon + "%"}
                        );
                     }

                     if (UpgradedNetheriteConfig.EnableDoubleLootingBonusEnderWeapon) {
                        tooltip.add(Component.m_237115_("upgradednetherite.Ender_Weapon3.TT"));
                     }

                     if (UpgradedNetheriteConfig.EnableTeleportChest) {
                        if (ToolUtil.getDisableEffect(itemStack)) {
                           TooltipHelper.addTWO(tooltip, "upgradednetherite.Ender_Tool.TT", new Object[]{"§c• "});
                        } else {
                           TooltipHelper.addTWO(tooltip, "upgradednetherite.Ender_Tool.TT", new Object[]{"§7• "});
                        }

                        if (itemStack.m_41783_().m_128471_("UpgradedNetherite_Tagged")) {
                           String world = level.m_46472_().m_135782_().m_135815_();
                           if (!world.equals(itemStack.m_41783_().m_128461_("UpgradedNetherite_Dimension"))) {
                              tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                              tooltip.add(Component.m_237115_("upgradednetherite.Target.TT"));
                              tooltip.add(Component.m_237115_("upgradednetherite.Ender_Dim.TT"));
                              tooltip.add(
                                 Component.m_237113_(
                                    "§7• §c"
                                       + itemStack.m_41783_().m_128461_("UpgradedNetherite_Dimension")
                                       + "§7 : §3"
                                       + itemStack.m_41783_().m_128465_("UpgradedNetherite_Position")[0]
                                       + "§7, §3"
                                       + itemStack.m_41783_().m_128465_("UpgradedNetherite_Position")[1]
                                       + "§7, §3"
                                       + itemStack.m_41783_().m_128465_("UpgradedNetherite_Position")[2]
                                       + "§7."
                                 )
                              );
                           } else {
                              tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                              tooltip.add(Component.m_237115_("upgradednetherite.Target.TT"));
                              tooltip.add(
                                 Component.m_237113_(
                                    "§7• §9"
                                       + itemStack.m_41783_().m_128461_("UpgradedNetherite_Dimension")
                                       + "§7 : §3"
                                       + itemStack.m_41783_().m_128465_("UpgradedNetherite_Position")[0]
                                       + "§7, §3"
                                       + itemStack.m_41783_().m_128465_("UpgradedNetherite_Position")[1]
                                       + "§7, §3"
                                       + itemStack.m_41783_().m_128465_("UpgradedNetherite_Position")[2]
                                       + "§7."
                                 )
                              );
                           }
                        }
                     }
                  } else if (!WaterUtil.isWaterTool(itemStack)
                     || !UpgradedNetheriteConfig.EnableDamageBonusWaterWeapon
                        && !UpgradedNetheriteConfig.EnableDamageBonusWaterEndermanWeapon
                        && !UpgradedNetheriteConfig.EnableMiningSpeedUnderwater) {
                     if (WitherUtil.isWitherTool(itemStack)) {
                        if (UpgradedNetheriteConfig.EnableWitherEffect) {
                           tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                           tooltip.add(Component.m_237115_("upgradednetherite.OnHit.TT"));
                           tooltip.add(Component.m_237115_("upgradednetherite.Wither_Weapon.TT"));
                        }

                        if (UpgradedNetheriteConfig.EnableDamageBonusWitherWeapon) {
                           tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                           tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
                           TooltipHelper.addTWO(
                              tooltip, "upgradednetherite.Wither_Weapon2.TT", new Object[]{"§6" + UpgradedNetheriteConfig.DamageBonusWitherWeapon + "%"}
                           );
                        }
                     } else if (PoisonUtil.isPoisonTool(itemStack)) {
                        if (UpgradedNetheriteConfig.EnablePoisonEffect) {
                           tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                           tooltip.add(Component.m_237115_("upgradednetherite.OnHit.TT"));
                           tooltip.add(Component.m_237115_("upgradednetherite.Poison_Weapon.TT"));
                        }

                        if (UpgradedNetheriteConfig.EnableDamageBonusPoisonWeapon) {
                           tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                           tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
                           TooltipHelper.addTWO(
                              tooltip, "upgradednetherite.Poison_Weapon2.TT", new Object[]{"§6" + UpgradedNetheriteConfig.DamageBonusPoisonWeapon + "%"}
                           );
                        }
                     } else if (!PhantomUtil.isPhantomTool(itemStack)
                        || !UpgradedNetheriteConfig.EnableDamageBonusPhantomWeapon
                           && !UpgradedNetheriteConfig.EnableGlowingEffect
                           && !UpgradedNetheriteConfig.EnableReachEffect) {
                        if (FeatherUtil.isFeatherTool(itemStack) && UpgradedNetheriteConfig.EnableAttractItem) {
                           tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                           tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
                           if (ToolUtil.getDisableEffect(itemStack)) {
                              TooltipHelper.addTWO(tooltip, "upgradednetherite.Feather_Tool.TT", new Object[]{"§c• "});
                           } else {
                              TooltipHelper.addTWO(tooltip, "upgradednetherite.Feather_Tool.TT", new Object[]{"§7• "});
                           }
                        } else if (CorruptUtil.isCorruptTool(itemStack)) {
                           tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                           tooltip.add(Component.m_237115_("upgradednetherite.Malus.TT"));
                           tooltip.add(Component.m_237115_("upgradednetherite.Corrupt_Bonus2.TT"));
                           tooltip.add(Component.m_237115_("upgradednetherite.Corrupt_Enchant.TT"));
                           if (UpgradedNetheriteConfig.EnableDamageBonusCorruptWeapon
                              || UpgradedNetheriteConfig.EnableLootingBonusCorruptWeapon
                              || UpgradedNetheriteConfig.EnableFortuneBonusCorruptTool) {
                              tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                              tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
                           }

                           if (Minecraft.m_91087_().f_91074_ != null && CorruptUtil.intWearingCorrupt(Minecraft.m_91087_().f_91074_, true) > 0) {
                              if (UpgradedNetheriteConfig.EnableDamageBonusCorruptWeapon) {
                                 TooltipHelper.addTWO(
                                    tooltip,
                                    "upgradednetherite.Corrupt_Weapon3.TT",
                                    new Object[]{
                                       "§6"
                                          + CorruptUtil.intWearingCorrupt(Minecraft.m_91087_().f_91074_, true)
                                             * UpgradedNetheriteConfig.DamageBonusCorruptWeapon
                                          + "%"
                                    }
                                 );
                              }

                              if (UpgradedNetheriteConfig.EnableLootingBonusCorruptWeapon) {
                                 TooltipHelper.addTWO(
                                    tooltip,
                                    "upgradednetherite.Corrupt_Weapon4.TT",
                                    new Object[]{
                                       "§6"
                                          + CorruptUtil.intWearingCorrupt(Minecraft.m_91087_().f_91074_, true)
                                             * UpgradedNetheriteConfig.LootingBonusCorruptWeapon
                                    }
                                 );
                              }

                              if (UpgradedNetheriteConfig.EnableFortuneBonusCorruptTool) {
                                 TooltipHelper.addTWO(
                                    tooltip,
                                    "upgradednetherite.Corrupt_Tool2.TT",
                                    new Object[]{
                                       "§6"
                                          + CorruptUtil.intWearingCorrupt(Minecraft.m_91087_().f_91074_, true)
                                             * UpgradedNetheriteConfig.FortuneBonusCorruptTool
                                    }
                                 );
                              }
                           }

                           float EnchantBonus = 0.0F;
                           Map<Enchantment, Integer> enchantments = EnchantmentHelper.m_44831_(itemStack);
                           if (!enchantments.isEmpty() && enchantments.containsKey(Enchantments.f_44982_)) {
                              int EnchantLevel = enchantments.get(Enchantments.f_44982_);
                              EnchantBonus = (float)EnchantLevel;
                           }

                           if (!(EnchantBonus > 0.0F)) {
                              tooltip.add(Component.m_237115_("upgradednetherite.Gold_Bow.TT"));
                           }

                           if (UpgradedNetheriteConfig.EnableDamageBonusCorruptWeapon) {
                              TooltipHelper.addTWO(
                                 tooltip, "upgradednetherite.Corrupt_Weapon2.TT", new Object[]{"§d" + UpgradedNetheriteConfig.DamageBonusCorruptWeapon + "%"}
                              );
                           }

                           if (UpgradedNetheriteConfig.EnableLootingBonusCorruptWeapon) {
                              TooltipHelper.addTWO(
                                 tooltip, "upgradednetherite.Corrupt_Weapon.TT", new Object[]{"§d" + UpgradedNetheriteConfig.LootingBonusCorruptWeapon}
                              );
                           }

                           if (UpgradedNetheriteConfig.EnableFortuneBonusCorruptTool) {
                              TooltipHelper.addTWO(
                                 tooltip, "upgradednetherite.Corrupt_Tool.TT", new Object[]{"§d" + UpgradedNetheriteConfig.FortuneBonusCorruptTool}
                              );
                           }
                        } else if (EchoUtil.isEchoTool(itemStack)
                           && (UpgradedNetheriteConfig.EnableDamageBonusEchoWeapon || UpgradedNetheriteConfig.EnableBonusExpEcho)) {
                           tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                           tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
                           if (UpgradedNetheriteConfig.EnableDamageBonusEchoWeapon) {
                              TooltipHelper.addTWO(
                                 tooltip, "upgradednetherite.Echo_Weapon.TT", new Object[]{"§6" + UpgradedNetheriteConfig.DamageBonusEchoWeapon + "%"}
                              );
                           }

                           if (UpgradedNetheriteConfig.EnableBonusExpEcho) {
                              TooltipHelper.addTWO(tooltip, "upgradednetherite.Echo_Tool2.TT", new Object[0]);
                           }
                        } else {
                           tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                           tooltip.add(Component.m_237115_("upgradednetherite.Disabled.TT"));
                        }
                     } else {
                        tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                        tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
                        if (UpgradedNetheriteConfig.EnableDamageBonusPhantomWeapon) {
                           TooltipHelper.addTWO(
                              tooltip, "upgradednetherite.Phantom_Weapon.TT", new Object[]{"§6" + UpgradedNetheriteConfig.DamageBonusPhantomWeapon + "%"}
                           );
                        }

                        if (UpgradedNetheriteConfig.EnableGlowingEffect) {
                           if (ToolUtil.getDisableEffect(itemStack)) {
                              TooltipHelper.addTWO(tooltip, "upgradednetherite.Phantom_Tool.TT", new Object[]{"§c• "});
                           } else {
                              TooltipHelper.addTWO(tooltip, "upgradednetherite.Phantom_Tool.TT", new Object[]{"§7• "});
                           }
                        }

                        if (UpgradedNetheriteConfig.EnableReachEffect) {
                           tooltip.add(Component.m_237115_("upgradednetherite.Phantom_Tool2.TT"));
                        }
                     }
                  } else {
                     tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                     tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
                     if (UpgradedNetheriteConfig.EnableDamageBonusWaterWeapon) {
                        TooltipHelper.addTWO(
                           tooltip, "upgradednetherite.Water_Weapon.TT", new Object[]{"§6" + UpgradedNetheriteConfig.DamageBonusWaterWeapon + "%"}
                        );
                     }

                     if (UpgradedNetheriteConfig.EnableDamageBonusWaterEndermanWeapon) {
                        TooltipHelper.addTWO(
                           tooltip, "upgradednetherite.Water_Weapon2.TT", new Object[]{"§6" + UpgradedNetheriteConfig.DamageBonusWaterEndermanWeapon + "%"}
                        );
                     }

                     if (UpgradedNetheriteConfig.EnableMiningSpeedUnderwater) {
                        tooltip.add(Component.m_237115_("upgradednetherite.Water_Tool.TT"));
                     }
                  }
               } else {
                  tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                  tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
                  if (UpgradedNetheriteConfig.EnableDamageBonusFireWeapon) {
                     float EnchantBonusx = 0.0F;
                     Map<Enchantment, Integer> enchantmentsx = EnchantmentHelper.m_44831_(itemStack);
                     if (!enchantmentsx.isEmpty() && enchantmentsx.containsKey(Enchantments.f_44981_)) {
                        int EnchantLevel = enchantmentsx.get(Enchantments.f_44981_);
                        EnchantBonusx = (float)EnchantLevel;
                     }

                     if (EnchantBonusx >= 2.0F) {
                        TooltipHelper.addTWO(
                           tooltip,
                           "upgradednetherite.Fire_Weapon.TT",
                           new Object[]{"§6" + (UpgradedNetheriteConfig.DamageBonusFireWeapon + UpgradedNetheriteConfig.DamageBonusFireEnchantWeapon) + "%"}
                        );
                     } else {
                        TooltipHelper.addTWO(
                           tooltip, "upgradednetherite.Fire_Weapon.TT", new Object[]{"§6" + UpgradedNetheriteConfig.DamageBonusFireWeapon + "%"}
                        );
                        tooltip.add(Component.m_237115_("upgradednetherite.Fire_Weapon3.TT"));
                        TooltipHelper.addTWO(
                           tooltip, "upgradednetherite.Fire_Weapon2.TT", new Object[]{"§d" + UpgradedNetheriteConfig.DamageBonusFireEnchantWeapon + "%"}
                        );
                     }
                  }

                  if (UpgradedNetheriteConfig.EnableAutoSmelt) {
                     if (ToolUtil.getDisableEffect(itemStack)) {
                        TooltipHelper.addTWO(tooltip, "upgradednetherite.Fire_Tool.TT", new Object[]{"§c• "});
                     } else {
                        TooltipHelper.addTWO(tooltip, "upgradednetherite.Fire_Tool.TT", new Object[]{"§7• "});
                     }
                  }
               }
            } else {
               tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
               tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
               if (UpgradedNetheriteConfig.EnableDamageBonusGoldWeapon) {
                  TooltipHelper.addTWO(tooltip, "upgradednetherite.Gold_Weapon.TT", new Object[]{"§6" + UpgradedNetheriteConfig.DamageBonusGoldWeapon + "%"});
               }

               float EnchantBonusFortune = 0.0F;
               Map<Enchantment, Integer> enchantmentsxx = EnchantmentHelper.m_44831_(itemStack);
               if (!enchantmentsxx.isEmpty() && enchantmentsxx.containsKey(Enchantments.f_44987_)) {
                  int EnchantLevel = enchantmentsxx.get(Enchantments.f_44987_);
                  EnchantBonusFortune = (float)EnchantLevel;
               }

               float EnchantBonusLooting = 0.0F;
               if (!enchantmentsxx.isEmpty() && enchantmentsxx.containsKey(Enchantments.f_44982_)) {
                  int EnchantLevel = enchantmentsxx.get(Enchantments.f_44982_);
                  EnchantBonusLooting = (float)EnchantLevel;
               }

               if (UpgradedNetheriteConfig.EnableFortuneBonus) {
                  if (EnchantBonusFortune >= 3.0F) {
                     TooltipHelper.addTWO(
                        tooltip,
                        "upgradednetherite.Gold_Tool.TT",
                        new Object[]{"§6" + (UpgradedNetheriteConfig.FortuneBonus + UpgradedNetheriteConfig.FortuneEnchantBonus)}
                     );
                  } else {
                     TooltipHelper.addTWO(tooltip, "upgradednetherite.Gold_Tool.TT", new Object[]{"§6" + UpgradedNetheriteConfig.FortuneBonus});
                  }
               }

               if (UpgradedNetheriteConfig.EnableLootingBonus) {
                  if (EnchantBonusLooting >= 3.0F) {
                     TooltipHelper.addTWO(
                        tooltip,
                        "upgradednetherite.Gold_Weapon2.TT",
                        new Object[]{"§6" + (UpgradedNetheriteConfig.LootingBonus + UpgradedNetheriteConfig.LootingEnchantBonus)}
                     );
                  } else {
                     TooltipHelper.addTWO(tooltip, "upgradednetherite.Gold_Weapon2.TT", new Object[]{"§6" + UpgradedNetheriteConfig.LootingBonus});
                  }
               }

               if (EnchantBonusFortune < 3.0F && UpgradedNetheriteConfig.EnableFortuneBonus) {
                  TooltipHelper.addTWO(tooltip, "upgradednetherite.Gold_Tool2.TT", new Object[]{"§d" + UpgradedNetheriteConfig.FortuneEnchantBonus});
               }

               if (EnchantBonusLooting < 3.0F && UpgradedNetheriteConfig.EnableLootingBonus) {
                  if (!(EnchantBonusLooting > 0.0F)) {
                     tooltip.add(Component.m_237115_("upgradednetherite.Gold_Bow.TT"));
                  }

                  TooltipHelper.addTWO(tooltip, "upgradednetherite.Gold_Weapon3.TT", new Object[]{"§d" + UpgradedNetheriteConfig.LootingEnchantBonus});
               }
            }
         } else {
            tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
            tooltip.add(Component.m_237115_("upgradednetherite.HoldShift.TT"));
            if (EnderUtil.isEnderTool(itemStack)) {
               tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
               if (itemStack.m_41783_() != null
                  && itemStack.m_41783_().m_128441_("UpgradedNetherite_Tagged")
                  && itemStack.m_41783_().m_128471_("UpgradedNetherite_Tagged")) {
                  tooltip.add(Component.m_237115_("upgradednetherite.Ender_ToolTar.TT"));
               } else {
                  tooltip.add(Component.m_237115_("upgradednetherite.Ender_ToolReq.TT"));
               }
            }
         }
      }
   }
}
