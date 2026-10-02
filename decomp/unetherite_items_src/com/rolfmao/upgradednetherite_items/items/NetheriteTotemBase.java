package com.rolfmao.upgradednetherite_items.items;

import com.rolfmao.upgradedcore.compat.ExternalMods;
import com.rolfmao.upgradedcore.helpers.TextHelper;
import com.rolfmao.upgradednetherite.utils.check.CorruptUtil;
import com.rolfmao.upgradednetherite_creative.config.UpgradedNetheriteCreativeConfig;
import com.rolfmao.upgradednetherite_items.config.UpgradedNetheriteItemsConfig;
import com.rolfmao.upgradednetherite_items.init.ModItems;
import com.rolfmao.upgradednetherite_ultimate.config.UpgradedNetheriteUltimateConfig;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class NetheriteTotemBase extends Item {
   public NetheriteTotemBase(Properties builderIn) {
      super(builderIn);
   }

   public boolean isPiglinCurrency(ItemStack stack) {
      return ItemStack.m_41758_(stack, new ItemStack((ItemLike)ModItems.GOLD_UPGRADED_NETHERITE_TOTEM.get()));
   }

   public void m_6787_(CreativeModeTab tab, NonNullList<ItemStack> list) {
      if (this.m_220152_(tab)) {
         ItemStack stack = new ItemStack(this);
         if (ExternalMods.UPGRADEDNETHERITE.isLoaded() && ItemStack.m_41758_(stack, new ItemStack((ItemLike)ModItems.NETHERITE_TOTEM.get()))) {
            list.add(stack);
         } else if (ExternalMods.UPGRADEDNETHERITE.isLoaded()
            && ItemStack.m_41758_(stack, new ItemStack((ItemLike)ModItems.GOLD_UPGRADED_NETHERITE_TOTEM.get()))) {
            list.add(stack);
         } else if (ExternalMods.UPGRADEDNETHERITE.isLoaded()
            && ItemStack.m_41758_(stack, new ItemStack((ItemLike)ModItems.FIRE_UPGRADED_NETHERITE_TOTEM.get()))) {
            list.add(stack);
         } else if (ExternalMods.UPGRADEDNETHERITE.isLoaded()
            && ItemStack.m_41758_(stack, new ItemStack((ItemLike)ModItems.ENDER_UPGRADED_NETHERITE_TOTEM.get()))) {
            list.add(stack);
         } else if (ExternalMods.UPGRADEDNETHERITE.isLoaded()
            && ItemStack.m_41758_(stack, new ItemStack((ItemLike)ModItems.WATER_UPGRADED_NETHERITE_TOTEM.get()))) {
            list.add(stack);
         } else if (ExternalMods.UPGRADEDNETHERITE.isLoaded()
            && ItemStack.m_41758_(stack, new ItemStack((ItemLike)ModItems.WITHER_UPGRADED_NETHERITE_TOTEM.get()))) {
            list.add(stack);
         } else if (ExternalMods.UPGRADEDNETHERITE.isLoaded()
            && ItemStack.m_41758_(stack, new ItemStack((ItemLike)ModItems.POISON_UPGRADED_NETHERITE_TOTEM.get()))) {
            list.add(stack);
         } else if (ExternalMods.UPGRADEDNETHERITE.isLoaded()
            && ItemStack.m_41758_(stack, new ItemStack((ItemLike)ModItems.PHANTOM_UPGRADED_NETHERITE_TOTEM.get()))) {
            list.add(stack);
         } else if (ExternalMods.UPGRADEDNETHERITE.isLoaded()
            && ItemStack.m_41758_(stack, new ItemStack((ItemLike)ModItems.FEATHER_UPGRADED_NETHERITE_TOTEM.get()))) {
            list.add(stack);
         } else if (ExternalMods.UPGRADEDNETHERITE.isLoaded()
            && ItemStack.m_41758_(stack, new ItemStack((ItemLike)ModItems.CORRUPT_UPGRADED_NETHERITE_TOTEM.get()))) {
            list.add(stack);
         } else if (ExternalMods.UPGRADEDNETHERITE.isLoaded()
            && ItemStack.m_41758_(stack, new ItemStack((ItemLike)ModItems.ECHO_UPGRADED_NETHERITE_TOTEM.get()))) {
            list.add(stack);
         } else if (ExternalMods.UPGRADEDNETHERITE_ULTIMATE.isLoaded()
            && ItemStack.m_41758_(stack, new ItemStack((ItemLike)ModItems.ULTIMATE_UPGRADED_NETHERITE_TOTEM.get()))) {
            list.add(stack);
         } else if (ExternalMods.UPGRADEDNETHERITE_CREATIVE.isLoaded()
            && ItemStack.m_41758_(stack, new ItemStack((ItemLike)ModItems.CREATIVE_UPGRADED_NETHERITE_TOTEM.get()))) {
            list.add(stack);
         } else if (tab == CreativeModeTab.f_40754_) {
            list.add(stack);
         }
      }
   }

   public boolean isEnabled() {
      if (this.m_7968_().m_150930_((Item)ModItems.GOLD_UPGRADED_NETHERITE_TOTEM.get())
         || this.m_7968_().m_150930_((Item)ModItems.FIRE_UPGRADED_NETHERITE_TOTEM.get())
         || this.m_7968_().m_150930_((Item)ModItems.ENDER_UPGRADED_NETHERITE_TOTEM.get())
         || this.m_7968_().m_150930_((Item)ModItems.WATER_UPGRADED_NETHERITE_TOTEM.get())
         || this.m_7968_().m_150930_((Item)ModItems.WITHER_UPGRADED_NETHERITE_TOTEM.get())
         || this.m_7968_().m_150930_((Item)ModItems.POISON_UPGRADED_NETHERITE_TOTEM.get())
         || this.m_7968_().m_150930_((Item)ModItems.PHANTOM_UPGRADED_NETHERITE_TOTEM.get())
         || this.m_7968_().m_150930_((Item)ModItems.FEATHER_UPGRADED_NETHERITE_TOTEM.get())
         || this.m_7968_().m_150930_((Item)ModItems.CORRUPT_UPGRADED_NETHERITE_TOTEM.get())
         || this.m_7968_().m_150930_((Item)ModItems.ECHO_UPGRADED_NETHERITE_TOTEM.get())) {
         return ExternalMods.UPGRADEDNETHERITE.isLoaded();
      } else if (this.m_7968_().m_150930_((Item)ModItems.ULTIMATE_UPGRADED_NETHERITE_TOTEM.get())) {
         return ExternalMods.UPGRADEDNETHERITE_ULTIMATE.isLoaded();
      } else {
         return this.m_7968_().m_150930_((Item)ModItems.CREATIVE_UPGRADED_NETHERITE_TOTEM.get())
            ? ExternalMods.UPGRADEDNETHERITE_CREATIVE.isLoaded()
            : this.m_7968_().m_150930_((Item)ModItems.NETHERITE_TOTEM.get());
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      super.m_7373_(stack, worldIn, tooltip, flagIn);
      if (ExternalMods.UPGRADEDNETHERITE.isLoaded()
         && !UpgradedNetheriteItemsConfig.DisableTooltips
         && !ItemStack.m_41758_(stack, new ItemStack((ItemLike)ModItems.NETHERITE_TOTEM.get()))) {
         if (Screen.m_96638_()) {
            if (ExternalMods.UPGRADEDNETHERITE_ULTIMATE.isLoaded()
               && ItemStack.m_41758_(stack, new ItemStack((ItemLike)ModItems.ULTIMATE_UPGRADED_NETHERITE_TOTEM.get()))) {
               if (UpgradedNetheriteUltimateConfig.EnableUltimateGoldArmorEffect
                     && (!stack.m_41782_() || !stack.m_41783_().m_128441_("UpgradedNetherite_DisableGold"))
                  || UpgradedNetheriteUltimateConfig.EnableUltimateFireArmorEffect
                     && (!stack.m_41782_() || !stack.m_41783_().m_128441_("UpgradedNetherite_DisableFire"))
                  || UpgradedNetheriteUltimateConfig.EnableUltimateEnderArmorEffect
                     && (!stack.m_41782_() || !stack.m_41783_().m_128441_("UpgradedNetherite_DisableEnder"))
                  || UpgradedNetheriteUltimateConfig.EnableUltimateWaterArmorEffect
                     && (!stack.m_41782_() || !stack.m_41783_().m_128441_("UpgradedNetherite_DisableWater"))
                  || UpgradedNetheriteUltimateConfig.EnableUltimateWitherArmorEffect
                     && (!stack.m_41782_() || !stack.m_41783_().m_128441_("UpgradedNetherite_DisableWither"))
                  || UpgradedNetheriteUltimateConfig.EnableUltimatePoisonArmorEffect
                     && (!stack.m_41782_() || !stack.m_41783_().m_128441_("UpgradedNetherite_DisablePoison"))
                  || UpgradedNetheriteUltimateConfig.EnableUltimatePhantomArmorEffect
                     && (!stack.m_41782_() || !stack.m_41783_().m_128441_("UpgradedNetherite_DisablePhantom"))
                  || UpgradedNetheriteUltimateConfig.EnableUltimateFeatherArmorEffect
                     && (!stack.m_41782_() || !stack.m_41783_().m_128441_("UpgradedNetherite_DisableFeather"))) {
                  tooltip.add(Component.m_237115_("upgradednetherite_items.Blank.TT"));
                  tooltip.add(Component.m_237115_("upgradednetherite_ultimate.BonusFrom.TT"));
                  if (UpgradedNetheriteUltimateConfig.EnableUltimateGoldArmorEffect
                     && (!stack.m_41782_() || !stack.m_41783_().m_128441_("UpgradedNetherite_DisableGold"))) {
                     tooltip.add(Component.m_237115_("upgradednetherite_ultimate.Golderite.TT"));
                  }

                  if (UpgradedNetheriteUltimateConfig.EnableUltimateFireArmorEffect
                     && (!stack.m_41782_() || !stack.m_41783_().m_128441_("UpgradedNetherite_DisableFire"))) {
                     tooltip.add(Component.m_237115_("upgradednetherite_ultimate.Blazerite.TT"));
                  }

                  if (UpgradedNetheriteUltimateConfig.EnableUltimateEnderArmorEffect
                     && (!stack.m_41782_() || !stack.m_41783_().m_128441_("UpgradedNetherite_DisableEnder"))) {
                     tooltip.add(Component.m_237115_("upgradednetherite_ultimate.Enderite.TT"));
                  }

                  if (UpgradedNetheriteUltimateConfig.EnableUltimateWaterArmorEffect
                     && (!stack.m_41782_() || !stack.m_41783_().m_128441_("UpgradedNetherite_DisableWater"))) {
                     tooltip.add(Component.m_237115_("upgradednetherite_ultimate.Prismarite.TT"));
                  }

                  if (UpgradedNetheriteUltimateConfig.EnableUltimateWitherArmorEffect
                     && (!stack.m_41782_() || !stack.m_41783_().m_128441_("UpgradedNetherite_DisableWither"))) {
                     tooltip.add(Component.m_237115_("upgradednetherite_ultimate.Witherite.TT"));
                  }

                  if (UpgradedNetheriteUltimateConfig.EnableUltimatePoisonArmorEffect
                     && (!stack.m_41782_() || !stack.m_41783_().m_128441_("UpgradedNetherite_DisablePoison"))) {
                     tooltip.add(Component.m_237115_("upgradednetherite_ultimate.Spiderite.TT"));
                  }

                  if (UpgradedNetheriteUltimateConfig.EnableUltimatePhantomArmorEffect
                     && (!stack.m_41782_() || !stack.m_41783_().m_128441_("UpgradedNetherite_DisablePhantom"))) {
                     tooltip.add(Component.m_237115_("upgradednetherite_ultimate.Phanterite.TT"));
                  }

                  if (UpgradedNetheriteUltimateConfig.EnableUltimateFeatherArmorEffect
                     && (!stack.m_41782_() || !stack.m_41783_().m_128441_("UpgradedNetherite_DisableFeather"))) {
                     tooltip.add(Component.m_237115_("upgradednetherite_ultimate.Featherite.TT"));
                  }

                  if (UpgradedNetheriteUltimateConfig.EnableUltimateEchoArmorEffect
                     && (!stack.m_41782_() || !stack.m_41783_().m_128441_("UpgradedNetherite_DisableEcho"))) {
                     tooltip.add(Component.m_237115_("upgradednetherite_ultimate.Echorite.TT"));
                  }
               }
            } else if (ExternalMods.UPGRADEDNETHERITE.isLoaded()
               && ItemStack.m_41758_(stack, new ItemStack((ItemLike)ModItems.GOLD_UPGRADED_NETHERITE_TOTEM.get()))) {
               tooltip.add(Component.m_237115_("upgradednetherite_items.Blank.TT"));
               tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
               if (UpgradedNetheriteItemsConfig.EnableReduceDamageGoldTotem) {
                  tooltip.add(
                     TextHelper.TCWO("upgradednetherite_items.Gold_Totem.TT", new Object[]{"§6" + UpgradedNetheriteItemsConfig.ReduceDamageGoldTotem + "%"})
                  );
               }
            } else if (ExternalMods.UPGRADEDNETHERITE.isLoaded()
               && ItemStack.m_41758_(stack, new ItemStack((ItemLike)ModItems.FIRE_UPGRADED_NETHERITE_TOTEM.get()))) {
               tooltip.add(Component.m_237115_("upgradednetherite_items.Blank.TT"));
               tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
               if (UpgradedNetheriteItemsConfig.EnableReduceDamageFireTotem) {
                  tooltip.add(
                     TextHelper.TCWO("upgradednetherite_items.Fire_Totem.TT", new Object[]{"§6" + UpgradedNetheriteItemsConfig.ReduceDamageFireTotem + "%"})
                  );
               }
            } else if (ExternalMods.UPGRADEDNETHERITE.isLoaded()
               && ItemStack.m_41758_(stack, new ItemStack((ItemLike)ModItems.ENDER_UPGRADED_NETHERITE_TOTEM.get()))) {
               tooltip.add(Component.m_237115_("upgradednetherite_items.Blank.TT"));
               tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
               if (UpgradedNetheriteItemsConfig.EnableReduceDamageEnderTotem) {
                  tooltip.add(
                     TextHelper.TCWO("upgradednetherite_items.Ender_Totem.TT", new Object[]{"§6" + UpgradedNetheriteItemsConfig.ReduceDamageEnderTotem + "%"})
                  );
               }

               tooltip.add(Component.m_237115_("upgradednetherite_items.Ender_Totem2.TT"));
            } else if (ExternalMods.UPGRADEDNETHERITE.isLoaded()
               && ItemStack.m_41758_(stack, new ItemStack((ItemLike)ModItems.WATER_UPGRADED_NETHERITE_TOTEM.get()))) {
               tooltip.add(Component.m_237115_("upgradednetherite_items.Blank.TT"));
               tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
               if (UpgradedNetheriteItemsConfig.EnableReduceDamageWaterTotem) {
                  tooltip.add(
                     TextHelper.TCWO("upgradednetherite_items.Water_Totem.TT", new Object[]{"§6" + UpgradedNetheriteItemsConfig.ReduceDamageWaterTotem + "%"})
                  );
               }
            } else if (ExternalMods.UPGRADEDNETHERITE.isLoaded()
               && ItemStack.m_41758_(stack, new ItemStack((ItemLike)ModItems.WITHER_UPGRADED_NETHERITE_TOTEM.get()))) {
               tooltip.add(Component.m_237115_("upgradednetherite_items.Blank.TT"));
               tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
               if (UpgradedNetheriteItemsConfig.EnableReduceDamageWitherTotem) {
                  tooltip.add(
                     TextHelper.TCWO("upgradednetherite_items.Wither_Totem.TT", new Object[]{"§6" + UpgradedNetheriteItemsConfig.ReduceDamageWitherTotem + "%"})
                  );
               }
            } else if (ExternalMods.UPGRADEDNETHERITE.isLoaded()
               && ItemStack.m_41758_(stack, new ItemStack((ItemLike)ModItems.POISON_UPGRADED_NETHERITE_TOTEM.get()))) {
               tooltip.add(Component.m_237115_("upgradednetherite_items.Blank.TT"));
               tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
               if (UpgradedNetheriteItemsConfig.EnableReduceDamagePoisonTotem) {
                  tooltip.add(
                     TextHelper.TCWO("upgradednetherite_items.Poison_Totem.TT", new Object[]{"§6" + UpgradedNetheriteItemsConfig.ReduceDamagePoisonTotem + "%"})
                  );
               }
            } else if (ExternalMods.UPGRADEDNETHERITE.isLoaded()
               && ItemStack.m_41758_(stack, new ItemStack((ItemLike)ModItems.PHANTOM_UPGRADED_NETHERITE_TOTEM.get()))) {
               tooltip.add(Component.m_237115_("upgradednetherite_items.Blank.TT"));
               tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
               if (UpgradedNetheriteItemsConfig.EnableReduceDamagePhantomTotem) {
                  tooltip.add(
                     TextHelper.TCWO(
                        "upgradednetherite_items.Phantom_Totem.TT", new Object[]{"§6" + UpgradedNetheriteItemsConfig.ReduceDamagePhantomTotem + "%"}
                     )
                  );
               }
            } else if (ExternalMods.UPGRADEDNETHERITE.isLoaded()
               && ItemStack.m_41758_(stack, new ItemStack((ItemLike)ModItems.FEATHER_UPGRADED_NETHERITE_TOTEM.get()))) {
               tooltip.add(Component.m_237115_("upgradednetherite_items.Blank.TT"));
               tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
               if (UpgradedNetheriteItemsConfig.EnableReduceDamageFeatherTotem) {
                  tooltip.add(
                     TextHelper.TCWO(
                        "upgradednetherite_items.Feather_Totem.TT", new Object[]{"§6" + UpgradedNetheriteItemsConfig.ReduceDamageFeatherTotem + "%"}
                     )
                  );
               }
            } else if (ExternalMods.UPGRADEDNETHERITE.isLoaded()
               && ItemStack.m_41758_(stack, new ItemStack((ItemLike)ModItems.ECHO_UPGRADED_NETHERITE_TOTEM.get()))) {
               tooltip.add(Component.m_237115_("upgradednetherite_items.Blank.TT"));
               tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
               if (UpgradedNetheriteItemsConfig.EnableReduceDamageEchoTotem) {
                  tooltip.add(
                     TextHelper.TCWO("upgradednetherite_items.Echo_Totem.TT", new Object[]{"§6" + UpgradedNetheriteItemsConfig.ReduceDamageEchoTotem + "%"})
                  );
               }
            } else if (ExternalMods.UPGRADEDNETHERITE.isLoaded()
               && ItemStack.m_41758_(stack, new ItemStack((ItemLike)ModItems.CORRUPT_UPGRADED_NETHERITE_TOTEM.get()))) {
               tooltip.add(Component.m_237115_("upgradednetherite_items.Blank.TT"));
               tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
               if (Minecraft.m_91087_().f_91074_ != null
                  && CorruptUtil.intWearingCorrupt(Minecraft.m_91087_().f_91074_, true) > 0
                  && UpgradedNetheriteItemsConfig.EnableReduceDamageCorruptTotem) {
                  tooltip.add(
                     TextHelper.TCWO(
                        "upgradednetherite_items.Corrupt_Totem.TT",
                        new Object[]{
                           "§6"
                              + CorruptUtil.intWearingCorrupt(Minecraft.m_91087_().f_91074_, true) * UpgradedNetheriteItemsConfig.ReduceDamageCorruptTotem
                              + "%"
                        }
                     )
                  );
               }

               if (UpgradedNetheriteItemsConfig.EnableReduceDamageCorruptTotem) {
                  tooltip.add(
                     TextHelper.TCWO(
                        "upgradednetherite_items.Corrupt_Totem2.TT", new Object[]{"§d" + UpgradedNetheriteItemsConfig.ReduceDamageCorruptTotem + "%"}
                     )
                  );
               }
            } else if (ExternalMods.UPGRADEDNETHERITE_CREATIVE.isLoaded()
               && ItemStack.m_41758_(stack, new ItemStack((ItemLike)ModItems.CREATIVE_UPGRADED_NETHERITE_TOTEM.get()))
               && (
                  UpgradedNetheriteCreativeConfig.EnableCreativeNoHarmful
                     || UpgradedNetheriteCreativeConfig.EnableCreativeSaturation
                     || UpgradedNetheriteCreativeConfig.EnableCreativeHeal
               )) {
               tooltip.add(Component.m_237115_("upgradednetherite_items.Blank.TT"));
               tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
               if (UpgradedNetheriteCreativeConfig.EnableCreativeNoHarmful) {
                  tooltip.add(Component.m_237115_("upgradednetherite_creative.Creative_Bonus3.TT"));
               }

               if (UpgradedNetheriteCreativeConfig.EnableCreativeSaturation) {
                  tooltip.add(Component.m_237115_("upgradednetherite_creative.Creative_Bonus4.TT"));
               }

               if (UpgradedNetheriteCreativeConfig.EnableCreativeHeal) {
                  tooltip.add(Component.m_237115_("upgradednetherite_creative.Creative_Bonus5.TT"));
               }
            } else {
               tooltip.add(Component.m_237115_("upgradednetherite_items.Blank.TT"));
               tooltip.add(Component.m_237115_("upgradednetherite_items.Disabled.TT"));
            }
         } else {
            tooltip.add(Component.m_237115_("upgradednetherite_items.Blank.TT"));
            tooltip.add(Component.m_237115_("upgradednetherite_items.HoldShift.TT"));
         }
      }
   }
}
