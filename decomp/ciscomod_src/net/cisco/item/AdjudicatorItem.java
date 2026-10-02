package net.cisco.item;

import java.util.List;
import net.cisco.init.CiscoModModItems;
import net.cisco.init.CiscoModModTabs;
import net.cisco.procedures.AdjudicatorRightclickedProcedure;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class AdjudicatorItem extends AxeItem {
   public AdjudicatorItem() {
      super(new Tier() {
         public int m_6609_() {
            return 4000;
         }

         public float m_6624_() {
            return 4.0F;
         }

         public float m_6631_() {
            return 19.0F;
         }

         public int m_6604_() {
            return 1;
         }

         public int m_6601_() {
            return 2;
         }

         public Ingredient m_6282_() {
            return Ingredient.m_43927_(new ItemStack[]{new ItemStack((ItemLike)CiscoModModItems.BRIGHTSTEEL_INGOT.get())});
         }
      }, 1.0F, -2.3F, new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD).m_41486_());
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player entity, InteractionHand hand) {
      InteractionResultHolder<ItemStack> ar = super.m_7203_(world, entity, hand);
      AdjudicatorRightclickedProcedure.execute(world, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), entity, (ItemStack)ar.m_19095_());
      return ar;
   }

   public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
      super.m_7373_(itemstack, world, list, flag);
      list.add(
         Component.m_237113_(
            "§dThe \"lawbringers \" were a race of celestial beings who dealt judgement to gods who commited any one of the unique set of unforgiveable crimes."
         )
      );
      list.add(Component.m_237113_("§8-"));
      list.add(Component.m_237113_("§6[Unique Effect] : Divine Justice : §fDeals significant damage to targets below 20 percent health."));
      list.add(Component.m_237113_("§8-"));
      list.add(Component.m_237113_("§a[Right Click Ability] : §fCleanses certain negative effects and grants a heal effect."));
   }
}
