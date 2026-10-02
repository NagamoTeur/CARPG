package net.cisco.item;

import java.util.List;
import net.cisco.init.CiscoModModItems;
import net.cisco.init.CiscoModModTabs;
import net.cisco.procedures.FrostfangRightclickedProcedure;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class FrostfangItem extends SwordItem {
   public FrostfangItem() {
      super(new Tier() {
         public int m_6609_() {
            return 4500;
         }

         public float m_6624_() {
            return 4.0F;
         }

         public float m_6631_() {
            return 24.0F;
         }

         public int m_6604_() {
            return 1;
         }

         public int m_6601_() {
            return 20;
         }

         public Ingredient m_6282_() {
            return Ingredient.m_43927_(new ItemStack[]{new ItemStack((ItemLike)CiscoModModItems.FRIGIDIUMINGOT.get())});
         }
      }, 3, -1.5F, new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD).m_41486_());
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player entity, InteractionHand hand) {
      InteractionResultHolder<ItemStack> ar = super.m_7203_(world, entity, hand);
      FrostfangRightclickedProcedure.execute(world, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), entity, (ItemStack)ar.m_19095_());
      return ar;
   }

   public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
      super.m_7373_(itemstack, world, list, flag);
      list.add(Component.m_237113_("§5Legendary weapon of the 3rd Fell King Sylvi."));
      list.add(Component.m_237113_("§8-"));
      list.add(Component.m_237113_("§6[Unique Effect] Silver Wolf's Splendor : §fEnemy attacks cannot surpass 40 percent of your max health."));
      list.add(Component.m_237113_("§8-"));
      list.add(
         Component.m_237113_(
            "§a[Right Click Ability] §fIf user is above 50% hp grants a temporary boost to attack based on 15 % max health. If user is below 50% hp, heals them to 70 % then grants a small speed buff."
         )
      );
   }
}
