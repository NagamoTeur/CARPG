package com.rolfmao.upgradednetherite.content;

import com.rolfmao.upgradedcore.helpers.TooltipHelper;
import com.rolfmao.upgradednetherite.config.UpgradedNetheriteConfig;
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
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class UpgradedNetheriteArmor extends ArmorItem {
   public UpgradedNetheriteArmor(ArmorMaterial materialIn, EquipmentSlot slot, Properties builderIn) {
      super(materialIn, slot, builderIn);
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
      return CorruptUtil.isCorruptArmor(stack) && enchantment == Enchantments.f_44962_ ? false : enchantment.f_44672_.m_7454_(stack.m_41720_());
   }

   public void m_6883_(ItemStack itemStack, Level level, Entity entity, int slot, boolean p_77663_5_) {
      if (CorruptUtil.isCorruptArmor(itemStack) && itemStack.m_41793_() && EnchantmentHelper.getTagEnchantmentLevel(Enchantments.f_44962_, itemStack) > 0) {
         Map<Enchantment, Integer> enchantments = EnchantmentHelper.m_44831_(itemStack);
         if (enchantments.containsKey(Enchantments.f_44962_)) {
            enchantments.remove(Enchantments.f_44962_);
            EnchantmentHelper.m_44865_(enchantments, itemStack);
         }
      }
   }

   public boolean makesPiglinsNeutral(ItemStack itemStack, LivingEntity wearer) {
      return GoldUtil.isWearingGoldArmor((Player)wearer) && UpgradedNetheriteConfig.EnablePiglinNeutral;
   }

   public boolean isEnderMask(ItemStack itemStack, Player player, EnderMan endermanEntity) {
      return EnderUtil.isWearingEnderArmor(player) && UpgradedNetheriteConfig.EnablePreventAnger;
   }

   public void m_6787_(CreativeModeTab tab, NonNullList<ItemStack> list) {
      if (this.m_220152_(tab)) {
         ItemStack itemStack = new ItemStack(this);
         if (GoldUtil.isGoldArmor(itemStack)) {
            list.add(itemStack);
         } else if (FireUtil.isFireArmor(itemStack)) {
            list.add(itemStack);
         } else if (EnderUtil.isEnderArmor(itemStack)) {
            list.add(itemStack);
         } else if (WaterUtil.isWaterArmor(itemStack)) {
            list.add(itemStack);
         } else if (WitherUtil.isWitherArmor(itemStack)) {
            list.add(itemStack);
         } else if (PoisonUtil.isPoisonArmor(itemStack)) {
            list.add(itemStack);
         } else if (PhantomUtil.isPhantomArmor(itemStack)) {
            list.add(itemStack);
         } else if (FeatherUtil.isFeatherArmor(itemStack)) {
            list.add(itemStack);
         } else if (CorruptUtil.isCorruptArmor(itemStack)) {
            list.add(itemStack);
         } else if (EchoUtil.isEchoArmor(itemStack)) {
            list.add(itemStack);
         } else if (tab == CreativeModeTab.f_40754_) {
            list.add(itemStack);
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack itemStack, Level level, List<Component> tooltip, TooltipFlag tooltipFlag) {
      super.m_7373_(itemStack, level, tooltip, tooltipFlag);
      if (!UpgradedNetheriteConfig.DisableTooltips) {
         if (Screen.m_96638_()) {
            if (!GoldUtil.isGoldArmor(itemStack) || !UpgradedNetheriteConfig.EnablePiglinNeutral && !UpgradedNetheriteConfig.EnableLuckBonus) {
               if (!FireUtil.isFireArmor(itemStack) || !UpgradedNetheriteConfig.EnableFireImmune && !UpgradedNetheriteConfig.EnableLavaSpeed) {
                  if (!EnderUtil.isEnderArmor(itemStack) || !UpgradedNetheriteConfig.EnableVoidSave && !UpgradedNetheriteConfig.EnablePreventAnger) {
                     if (!WaterUtil.isWaterArmor(itemStack)
                        || !UpgradedNetheriteConfig.EnableWaterBreath
                           && !UpgradedNetheriteConfig.EnableWaterSpeed
                           && !UpgradedNetheriteConfig.EnableElderGuardianDebuffImmune) {
                        if (WitherUtil.isWitherArmor(itemStack) && UpgradedNetheriteConfig.EnableWitherImmune) {
                           tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                           TooltipHelper.addTWO(tooltip, "upgradednetherite.SetBonus.TT", new Object[]{"§a§l" + WitherUtil.setWitherArmor()});
                           tooltip.add(Component.m_237115_("upgradednetherite.Wither_Bonus.TT"));
                        } else if (!PoisonUtil.isPoisonArmor(itemStack)
                           || !UpgradedNetheriteConfig.EnablePoisonImmune && !UpgradedNetheriteConfig.EnableClimbWall) {
                           if (!PhantomUtil.isPhantomArmor(itemStack) || !UpgradedNetheriteConfig.EnableFallImmune && !UpgradedNetheriteConfig.EnableStepHeight
                              )
                            {
                              if (!FeatherUtil.isFeatherArmor(itemStack)
                                 || !UpgradedNetheriteConfig.EnableWaterLavaWalking
                                    && !UpgradedNetheriteConfig.EnableMultiJump
                                    && !UpgradedNetheriteConfig.EnableReduceFallDamage
                                    && !UpgradedNetheriteConfig.EnableLevitationImmune) {
                                 if (CorruptUtil.isCorruptArmor(itemStack)) {
                                    tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                                    tooltip.add(Component.m_237115_("upgradednetherite.Malus.TT"));
                                    if (UpgradedNetheriteConfig.EnableHealthMalus) {
                                       TooltipHelper.addTWO(
                                          tooltip, "upgradednetherite.Corrupt_Bonus.TT", new Object[]{"§6" + UpgradedNetheriteConfig.HealthMalus + "%"}
                                       );
                                    }

                                    tooltip.add(Component.m_237115_("upgradednetherite.Corrupt_Bonus2.TT"));
                                    tooltip.add(Component.m_237115_("upgradednetherite.Corrupt_Enchant.TT"));
                                 } else if (EchoUtil.isEchoArmor(itemStack)
                                    && (
                                       UpgradedNetheriteConfig.EnableKeepItemsChance
                                          || UpgradedNetheriteConfig.EnableReduceDamageEchoArmor
                                          || UpgradedNetheriteConfig.EnableHealEchoArmor
                                    )) {
                                    tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                                    TooltipHelper.addTWO(tooltip, "upgradednetherite.SetBonus.TT", new Object[]{"§a§l" + EchoUtil.setEchoArmor()});
                                    if (UpgradedNetheriteConfig.EnableKeepItemsChance) {
                                       TooltipHelper.addTWO(
                                          tooltip, "upgradednetherite.Echo_Bonus.TT", new Object[]{"§6" + UpgradedNetheriteConfig.KeepItemsChance + "%"}
                                       );
                                    }

                                    if (UpgradedNetheriteConfig.EnableReduceDamageEchoArmor) {
                                       TooltipHelper.addTWO(
                                          tooltip, "upgradednetherite.Echo_Bonus2.TT", new Object[]{"§6" + UpgradedNetheriteConfig.ReduceDamageEchoArmor + "%"}
                                       );
                                    }

                                    if (UpgradedNetheriteConfig.EnableHealEchoArmor) {
                                       tooltip.add(Component.m_237115_("upgradednetherite.Echo_Bonus3.TT"));
                                    }
                                 } else {
                                    tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                                    tooltip.add(Component.m_237115_("upgradednetherite.Disabled.TT"));
                                 }
                              } else {
                                 tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                                 TooltipHelper.addTWO(tooltip, "upgradednetherite.SetBonus.TT", new Object[]{"§a§l" + FeatherUtil.setFeatherArmor()});
                                 if (UpgradedNetheriteConfig.EnableWaterLavaWalking) {
                                    tooltip.add(Component.m_237115_("upgradednetherite.Feather_Bonus.TT"));
                                 }

                                 if (UpgradedNetheriteConfig.EnableMultiJump) {
                                    tooltip.add(Component.m_237115_("upgradednetherite.Feather_Bonus2.TT"));
                                 }

                                 if (UpgradedNetheriteConfig.EnableReduceFallDamage) {
                                    tooltip.add(Component.m_237115_("upgradednetherite.Feather_Bonus3.TT"));
                                 }

                                 if (UpgradedNetheriteConfig.EnableLevitationImmune) {
                                    tooltip.add(Component.m_237115_("upgradednetherite.Feather_Bonus4.TT"));
                                 }
                              }
                           } else {
                              tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                              TooltipHelper.addTWO(tooltip, "upgradednetherite.SetBonus.TT", new Object[]{"§a§l" + PhantomUtil.setPhantomArmor()});
                              if (UpgradedNetheriteConfig.EnableFallImmune) {
                                 tooltip.add(Component.m_237115_("upgradednetherite.Phantom_Bonus.TT"));
                              }

                              if (UpgradedNetheriteConfig.EnableStepHeight) {
                                 tooltip.add(Component.m_237115_("upgradednetherite.Phantom_Bonus2.TT"));
                              }
                           }
                        } else {
                           tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                           TooltipHelper.addTWO(tooltip, "upgradednetherite.SetBonus.TT", new Object[]{"§a§l" + PoisonUtil.setPoisonArmor()});
                           if (UpgradedNetheriteConfig.EnablePoisonImmune) {
                              tooltip.add(Component.m_237115_("upgradednetherite.Poison_Bonus.TT"));
                           }

                           if (UpgradedNetheriteConfig.EnableClimbWall) {
                              tooltip.add(Component.m_237115_("upgradednetherite.Poison_Bonus2.TT"));
                           }
                        }
                     } else {
                        tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                        TooltipHelper.addTWO(tooltip, "upgradednetherite.SetBonus.TT", new Object[]{"§a§l" + WaterUtil.setWaterArmor()});
                        if (UpgradedNetheriteConfig.EnableWaterBreath) {
                           tooltip.add(Component.m_237115_("upgradednetherite.Water_Bonus.TT"));
                        }

                        if (UpgradedNetheriteConfig.EnableWaterSpeed) {
                           tooltip.add(Component.m_237115_("upgradednetherite.Water_Bonus2.TT"));
                        }

                        if (UpgradedNetheriteConfig.EnableElderGuardianDebuffImmune) {
                           tooltip.add(Component.m_237115_("upgradednetherite.Water_Bonus3.TT"));
                        }
                     }
                  } else {
                     tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                     TooltipHelper.addTWO(tooltip, "upgradednetherite.SetBonus.TT", new Object[]{"§a§l" + EnderUtil.setEnderArmor()});
                     if (UpgradedNetheriteConfig.EnableVoidSave) {
                        tooltip.add(Component.m_237115_("upgradednetherite.Ender_Bonus.TT"));
                     }

                     if (UpgradedNetheriteConfig.EnablePreventAnger) {
                        tooltip.add(Component.m_237115_("upgradednetherite.Ender_Bonus2.TT"));
                     }

                     if (UpgradedNetheriteConfig.EnableBreakEnderArmor && UpgradedNetheriteConfig.EnableVoidSave) {
                        tooltip.add(Component.m_237115_("upgradednetherite.Ender_Bonus3.TT"));
                     }
                  }
               } else {
                  tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                  TooltipHelper.addTWO(tooltip, "upgradednetherite.SetBonus.TT", new Object[]{"§a§l" + FireUtil.setFireArmor()});
                  if (UpgradedNetheriteConfig.EnableFireImmune) {
                     tooltip.add(Component.m_237115_("upgradednetherite.Fire_Bonus.TT"));
                  }

                  if (UpgradedNetheriteConfig.EnableLavaSpeed) {
                     tooltip.add(Component.m_237115_("upgradednetherite.Fire_Bonus2.TT"));
                  }
               }
            } else {
               tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
               TooltipHelper.addTWO(tooltip, "upgradednetherite.SetBonus.TT", new Object[]{"§a§l" + GoldUtil.setGoldArmor()});
               if (UpgradedNetheriteConfig.EnablePiglinNeutral) {
                  tooltip.add(Component.m_237115_("upgradednetherite.Gold_Bonus.TT"));
               }

               if (UpgradedNetheriteConfig.EnableLuckBonus) {
                  TooltipHelper.addTWO(tooltip, "upgradednetherite.Gold_Bonus2.TT", new Object[]{"§6" + UpgradedNetheriteConfig.LuckBonus});
               }
            }
         } else {
            tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
            tooltip.add(Component.m_237115_("upgradednetherite.HoldShift.TT"));
         }
      }
   }
}
