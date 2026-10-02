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
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.ForgeEventFactory;

public class UpgradedNetheriteBow extends BowItem {
   public UpgradedNetheriteBow(Properties properties) {
      super(properties);
   }

   public boolean m_6832_(ItemStack toRepair, ItemStack repair) {
      return Items.f_42418_.equals(repair.m_41720_());
   }

   public boolean canApplyAtEnchantingTable(ItemStack itemStack, Enchantment enchantment) {
      if (itemStack.m_41720_() == ModItems.CORRUPT_UPGRADED_NETHERITE_BOW.get() && enchantment == Enchantments.f_44962_) {
         return false;
      } else {
         return (itemStack.m_41720_() == ModItems.GOLD_UPGRADED_NETHERITE_BOW.get() || itemStack.m_41720_() == ModItems.CORRUPT_UPGRADED_NETHERITE_BOW.get())
               && enchantment == Enchantments.f_44982_
            ? true
            : enchantment.f_44672_.m_7454_(itemStack.m_41720_());
      }
   }

   public void m_6883_(ItemStack itemStack, Level level, Entity entity, int slot, boolean p_77663_5_) {
      if (itemStack.m_41720_() == ModItems.CORRUPT_UPGRADED_NETHERITE_BOW.get()
         && itemStack.m_41793_()
         && EnchantmentHelper.m_44843_(Enchantments.f_44962_, itemStack) > 0) {
         Map<Enchantment, Integer> enchantments = EnchantmentHelper.m_44831_(itemStack);
         if (enchantments.containsKey(Enchantments.f_44962_)) {
            enchantments.remove(Enchantments.f_44962_);
            EnchantmentHelper.m_44865_(enchantments, itemStack);
         }
      }
   }

   public InteractionResult m_6225_(UseOnContext context) {
      InteractionResult iResult = InteractionResult.PASS;
      if (context.m_43723_() != null
         && context.m_43723_().m_6047_()
         && EnderUtil.isEnderRangedWeapon(context.m_43722_())
         && !context.m_43723_().m_36335_().m_41519_(context.m_43722_().m_41720_())) {
         iResult = ToolUtil.EnderSetTag(context);
      }

      return iResult == InteractionResult.PASS ? super.m_6225_(context) : iResult;
   }

   public void m_6787_(CreativeModeTab tab, NonNullList<ItemStack> list) {
      if (this.m_220152_(tab)) {
         ItemStack itemStack = new ItemStack(this);
         if (itemStack.m_41720_() == ModItems.NETHERITE_BOW.get()) {
            list.add(itemStack);
         } else if (GoldUtil.isGoldRangedWeapon(itemStack)) {
            list.add(itemStack);
         } else if (FireUtil.isFireRangedWeapon(itemStack)) {
            list.add(itemStack);
         } else if (EnderUtil.isEnderRangedWeapon(itemStack)) {
            list.add(itemStack);
         } else if (WaterUtil.isWaterRangedWeapon(itemStack)) {
            list.add(itemStack);
         } else if (WitherUtil.isWitherRangedWeapon(itemStack)) {
            list.add(itemStack);
         } else if (PoisonUtil.isPoisonRangedWeapon(itemStack)) {
            list.add(itemStack);
         } else if (PhantomUtil.isPhantomRangedWeapon(itemStack)) {
            list.add(itemStack);
         } else if (FeatherUtil.isFeatherRangedWeapon(itemStack)) {
            list.add(itemStack);
         } else if (CorruptUtil.isCorruptRangedWeapon(itemStack)) {
            list.add(itemStack);
         } else if (EchoUtil.isEchoRangedWeapon(itemStack)) {
            list.add(itemStack);
         } else if (tab == CreativeModeTab.f_40754_) {
            list.add(itemStack);
         }
      }
   }

   public void m_5551_(ItemStack itemStack, Level level, LivingEntity livingEntity, int timeLeft) {
      if (livingEntity instanceof Player playerentity) {
         boolean flag = playerentity.m_150110_().f_35937_ || EnchantmentHelper.getTagEnchantmentLevel(Enchantments.f_44952_, itemStack) > 0;
         ItemStack itemstack = playerentity.m_6298_(itemStack);
         int i = this.m_8105_(itemStack) - timeLeft;
         i = ForgeEventFactory.onArrowLoose(itemStack, level, playerentity, i, !itemstack.m_41619_() || flag);
         if (i < 0) {
            return;
         }

         if (!itemstack.m_41619_() || flag) {
            if (itemstack.m_41619_()) {
               itemstack = new ItemStack(Items.f_42412_);
            }

            float f = m_40661_(i);
            if (!((double)f < 0.1)) {
               boolean flag1 = playerentity.m_150110_().f_35937_
                  || itemstack.m_41720_() instanceof ArrowItem && ((ArrowItem)itemstack.m_41720_()).isInfinite(itemstack, itemStack, playerentity);
               if (!level.f_46443_) {
                  ArrowItem arrowitem = (ArrowItem)(itemstack.m_41720_() instanceof ArrowItem ? itemstack.m_41720_() : Items.f_42412_);
                  AbstractArrow abstractarrowentity = arrowitem.m_6394_(level, itemstack, playerentity);
                  abstractarrowentity = this.customArrow(abstractarrowentity);
                  abstractarrowentity.m_37251_(playerentity, playerentity.m_146909_(), playerentity.m_146908_(), 0.0F, f * 3.0F, 1.0F);
                  if (f == 1.0F) {
                     abstractarrowentity.m_36762_(true);
                  }

                  int j = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.f_44988_, itemStack);
                  if (j > 0) {
                     abstractarrowentity.m_36781_(3.0 + (double)j * 0.5 + 0.5);
                  } else {
                     abstractarrowentity.m_36781_(3.0);
                  }

                  int k = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.f_44989_, itemStack);
                  if (k > 0) {
                     abstractarrowentity.m_36735_(k);
                  }

                  if (EnchantmentHelper.getTagEnchantmentLevel(Enchantments.f_44990_, itemStack) > 0) {
                     abstractarrowentity.m_20254_(100);
                  }

                  itemStack.m_41622_(1, (ServerPlayer)playerentity, p_220009_1_ -> p_220009_1_.m_21190_(playerentity.m_7655_()));
                  if (flag1 || playerentity.m_150110_().f_35937_ && (itemstack.m_41720_() == Items.f_42737_ || itemstack.m_41720_() == Items.f_42738_)) {
                     abstractarrowentity.f_36705_ = Pickup.CREATIVE_ONLY;
                  }

                  if (this.m_5456_() != ModItems.NETHERITE_BOW.get()) {
                     abstractarrowentity.getPersistentData().m_128350_("getPowerForTime", f);
                  }

                  if (this.m_5456_() == ModItems.GOLD_UPGRADED_NETHERITE_BOW.get()) {
                     abstractarrowentity.m_20049_("GoldUpgradedNetheriteBow");
                     if (EnchantmentHelper.getTagEnchantmentLevel(Enchantments.f_44982_, itemStack) > 0) {
                        abstractarrowentity.getPersistentData()
                           .m_128405_("LootingGoldUpgradedNetheriteBow", EnchantmentHelper.getTagEnchantmentLevel(Enchantments.f_44982_, itemStack));
                     }
                  }

                  if (this.m_5456_() == ModItems.FIRE_UPGRADED_NETHERITE_BOW.get()) {
                     abstractarrowentity.m_20049_("FireUpgradedNetheriteBow");
                     if (EnchantmentHelper.getTagEnchantmentLevel(Enchantments.f_44990_, itemStack) > 0) {
                        abstractarrowentity.m_20049_("FlameFireUpgradedNetheriteBow");
                     }
                  }

                  if (this.m_5456_() == ModItems.ENDER_UPGRADED_NETHERITE_BOW.get()) {
                     abstractarrowentity.m_20049_("EnderUpgradedNetheriteBow");
                     if (itemStack.m_41784_().m_128471_("UpgradedNetherite_Tagged")) {
                        abstractarrowentity.getPersistentData()
                           .m_128385_("UpgradedNetherite_Position", itemStack.m_41784_().m_128465_("UpgradedNetherite_Position"));
                        abstractarrowentity.getPersistentData()
                           .m_128359_("UpgradedNetherite_Dimension", itemStack.m_41784_().m_128461_("UpgradedNetherite_Dimension"));
                        abstractarrowentity.getPersistentData()
                           .m_128379_("UpgradedNetherite_Tagged", itemStack.m_41784_().m_128471_("UpgradedNetherite_Tagged"));
                     }
                  }

                  if (this.m_5456_() == ModItems.WATER_UPGRADED_NETHERITE_BOW.get()) {
                     abstractarrowentity.m_20049_("WaterUpgradedNetheriteBow");
                  }

                  if (this.m_5456_() == ModItems.WITHER_UPGRADED_NETHERITE_BOW.get()) {
                     abstractarrowentity.m_20049_("WitherUpgradedNetheriteBow");
                  }

                  if (this.m_5456_() == ModItems.POISON_UPGRADED_NETHERITE_BOW.get()) {
                     abstractarrowentity.m_20049_("PoisonUpgradedNetheriteBow");
                  }

                  if (this.m_5456_() == ModItems.PHANTOM_UPGRADED_NETHERITE_BOW.get()) {
                     abstractarrowentity.m_20049_("PhantomUpgradedNetheriteBow");
                  }

                  if (this.m_5456_() == ModItems.FEATHER_UPGRADED_NETHERITE_BOW.get()) {
                     abstractarrowentity.m_20049_("FeatherUpgradedNetheriteBow");
                  }

                  if (this.m_5456_() == ModItems.CORRUPT_UPGRADED_NETHERITE_BOW.get()) {
                     abstractarrowentity.m_20049_("CorruptUpgradedNetheriteBow");
                     abstractarrowentity.getPersistentData().m_128405_("LootingCorruptUpgradedNetheriteBow", CorruptUtil.intWearingCorrupt(playerentity, true));
                  }

                  if (this.m_5456_() == ModItems.ECHO_UPGRADED_NETHERITE_BOW.get()) {
                     abstractarrowentity.m_20049_("EchoUpgradedNetheriteBow");
                  }

                  level.m_7967_(abstractarrowentity);
               }

               level.m_6263_(
                  null,
                  playerentity.m_20185_(),
                  playerentity.m_20186_(),
                  playerentity.m_20189_(),
                  SoundEvents.f_11687_,
                  SoundSource.PLAYERS,
                  1.0F,
                  1.0F / (level.f_46441_.m_188501_() * 0.4F + 1.2F) + f * 0.5F
               );
               if (!flag1 && !playerentity.m_150110_().f_35937_) {
                  itemstack.m_41774_(1);
                  if (itemstack.m_41619_()) {
                     playerentity.m_150109_().m_36057_(itemstack);
                  }
               }

               playerentity.m_36246_(Stats.f_12982_.m_12902_(this));
            }
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack itemStack, Level level, List<Component> tooltip, TooltipFlag tooltipFlag) {
      super.m_7373_(itemStack, level, tooltip, tooltipFlag);
      if (!UpgradedNetheriteConfig.DisableTooltips) {
         Item bow = itemStack.m_41720_();
         if (bow != ModItems.NETHERITE_BOW.get()) {
            if (Screen.m_96638_()) {
               if (!GoldUtil.isGoldRangedWeapon(itemStack)
                  || !UpgradedNetheriteConfig.EnableDamageBonusGoldWeapon && !UpgradedNetheriteConfig.EnableLootingBonus) {
                  if (!FireUtil.isFireRangedWeapon(itemStack)
                     || !UpgradedNetheriteConfig.EnableDamageBonusFireWeapon && !UpgradedNetheriteConfig.EnableAutoSmelt) {
                     if (EnderUtil.isEnderRangedWeapon(itemStack)) {
                        if (UpgradedNetheriteConfig.EnablePreventTeleport) {
                           tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                           tooltip.add(Component.m_237115_("upgradednetherite.OnHit.TT"));
                           tooltip.add(Component.m_237115_("upgradednetherite.Ender_Weapon.TT"));
                        }

                        if (UpgradedNetheriteConfig.EnableTeleportChest) {
                           tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                           tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
                           tooltip.add(Component.m_237115_("upgradednetherite.Ender_Tool.TT"));
                           if (itemStack.m_41783_() != null
                              && itemStack.m_41783_().m_128441_("UpgradedNetherite_Tagged")
                              && itemStack.m_41783_().m_128471_("UpgradedNetherite_Tagged")) {
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
                     } else if (!WaterUtil.isWaterRangedWeapon(itemStack)
                        || !UpgradedNetheriteConfig.EnableDamageBonusWaterWeapon && !UpgradedNetheriteConfig.EnableMiningSpeedUnderwater) {
                        if (WitherUtil.isWitherRangedWeapon(itemStack)) {
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
                        } else if (PoisonUtil.isPoisonRangedWeapon(itemStack)) {
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
                        } else if (!PhantomUtil.isPhantomRangedWeapon(itemStack)
                           || !UpgradedNetheriteConfig.EnableDamageBonusPhantomWeapon
                              && !UpgradedNetheriteConfig.EnableGlowingEffect
                              && !UpgradedNetheriteConfig.EnableReachEffect) {
                           if (FeatherUtil.isFeatherRangedWeapon(itemStack)) {
                              tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                              tooltip.add(Component.m_237115_("upgradednetherite.OnHit.TT"));
                              tooltip.add(Component.m_237115_("upgradednetherite.Feather_Bow.TT"));
                              tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                              tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
                              TooltipHelper.addTWO(tooltip, "upgradednetherite.Feather_Tool.TT", new Object[]{"§7• "});
                           } else if (CorruptUtil.isCorruptRangedWeapon(itemStack)) {
                              tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                              tooltip.add(Component.m_237115_("upgradednetherite.Malus.TT"));
                              tooltip.add(Component.m_237115_("upgradednetherite.Corrupt_Bonus2.TT"));
                              tooltip.add(Component.m_237115_("upgradednetherite.Corrupt_Enchant.TT"));
                              if (UpgradedNetheriteConfig.EnableDamageBonusCorruptWeapon || UpgradedNetheriteConfig.EnableLootingBonusCorruptWeapon) {
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
                                    tooltip,
                                    "upgradednetherite.Corrupt_Weapon2.TT",
                                    new Object[]{"§d" + UpgradedNetheriteConfig.DamageBonusCorruptWeapon + "%"}
                                 );
                              }

                              if (UpgradedNetheriteConfig.EnableLootingBonusCorruptWeapon) {
                                 TooltipHelper.addTWO(
                                    tooltip, "upgradednetherite.Corrupt_Weapon.TT", new Object[]{"§d" + UpgradedNetheriteConfig.LootingBonusCorruptWeapon}
                                 );
                              }
                           } else if (EchoUtil.isEchoRangedWeapon(itemStack)
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
                              TooltipHelper.addTWO(tooltip, "upgradednetherite.Phantom_Tool.TT", new Object[]{"§7• "});
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

                        if (UpgradedNetheriteConfig.EnableMiningSpeedUnderwater) {
                           tooltip.add(Component.m_237115_("upgradednetherite.Water_Tool.TT"));
                        }
                     }
                  } else {
                     tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                     if (UpgradedNetheriteConfig.EnableDamageBonusFireWeapon) {
                        float EnchantBonusx = 0.0F;
                        Map<Enchantment, Integer> enchantmentsx = EnchantmentHelper.m_44831_(itemStack);
                        if (!enchantmentsx.isEmpty() && enchantmentsx.containsKey(Enchantments.f_44990_)) {
                           int EnchantLevel = enchantmentsx.get(Enchantments.f_44990_);
                           EnchantBonusx = (float)EnchantLevel;
                        }

                        tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
                        if (EnchantBonusx >= 1.0F) {
                           TooltipHelper.addTWO(
                              tooltip,
                              "upgradednetherite.Fire_Weapon.TT",
                              new Object[]{"§6" + (UpgradedNetheriteConfig.DamageBonusFireWeapon + UpgradedNetheriteConfig.DamageBonusFireEnchantWeapon) + "%"}
                           );
                        } else {
                           TooltipHelper.addTWO(
                              tooltip, "upgradednetherite.Fire_Weapon.TT", new Object[]{"§6" + UpgradedNetheriteConfig.DamageBonusFireWeapon + "%"}
                           );
                           TooltipHelper.addTWO(
                              tooltip, "upgradednetherite.Fire_Bow.TT", new Object[]{"§d" + UpgradedNetheriteConfig.DamageBonusFireEnchantWeapon + "%"}
                           );
                        }
                     }

                     if (UpgradedNetheriteConfig.EnableAutoSmelt) {
                        TooltipHelper.addTWO(tooltip, "upgradednetherite.Fire_Tool.TT", new Object[]{"§7• "});
                     }
                  }
               } else {
                  tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                  tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
                  if (UpgradedNetheriteConfig.EnableDamageBonusGoldWeapon) {
                     TooltipHelper.addTWO(tooltip, "upgradednetherite.Gold_Weapon.TT", new Object[]{"§6" + UpgradedNetheriteConfig.DamageBonusGoldWeapon + "%"});
                  }

                  if (UpgradedNetheriteConfig.EnableLootingBonus) {
                     float EnchantBonusxx = 0.0F;
                     Map<Enchantment, Integer> enchantmentsxx = EnchantmentHelper.m_44831_(itemStack);
                     if (!enchantmentsxx.isEmpty() && enchantmentsxx.containsKey(Enchantments.f_44982_)) {
                        int EnchantLevel = enchantmentsxx.get(Enchantments.f_44982_);
                        EnchantBonusxx = (float)EnchantLevel;
                     }

                     if (EnchantBonusxx >= 3.0F) {
                        TooltipHelper.addTWO(
                           tooltip,
                           "upgradednetherite.Gold_Weapon2.TT",
                           new Object[]{"§6" + (UpgradedNetheriteConfig.LootingBonus + UpgradedNetheriteConfig.LootingEnchantBonus)}
                        );
                     } else {
                        TooltipHelper.addTWO(tooltip, "upgradednetherite.Gold_Weapon2.TT", new Object[]{"§6" + UpgradedNetheriteConfig.LootingBonus});
                        if (!(EnchantBonusxx > 0.0F)) {
                           tooltip.add(Component.m_237115_("upgradednetherite.Gold_Bow.TT"));
                        }

                        TooltipHelper.addTWO(tooltip, "upgradednetherite.Gold_Weapon3.TT", new Object[]{"§d" + UpgradedNetheriteConfig.LootingEnchantBonus});
                     }
                  }
               }
            } else {
               tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
               tooltip.add(Component.m_237115_("upgradednetherite.HoldShift.TT"));
               if (EnderUtil.isEnderRangedWeapon(itemStack)) {
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
}
