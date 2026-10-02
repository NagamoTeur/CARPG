package net.cisco.item;

import java.util.List;
import net.cisco.init.CiscoModModItems;
import net.cisco.init.CiscoModModTabs;
import net.cisco.procedures.GlaciesOnHitProcedure;
import net.cisco.procedures.GlaciesRightClickProcedure;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class GlaciesItem extends AxeItem {
   public GlaciesItem() {
      super(new Tier() {
         public int m_6609_() {
            return 4000;
         }

         public float m_6624_() {
            return 4.0F;
         }

         public float m_6631_() {
            return 13.0F;
         }

         public int m_6604_() {
            return 1;
         }

         public int m_6601_() {
            return 15;
         }

         public Ingredient m_6282_() {
            return Ingredient.m_43927_(new ItemStack[]{new ItemStack((ItemLike)CiscoModModItems.BRIGHTSTEEL_INGOT.get())});
         }
      }, 1.0F, -2.5F, new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD));
   }

   public boolean m_7579_(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
      boolean retval = super.m_7579_(itemstack, entity, sourceentity);
      GlaciesOnHitProcedure.execute(entity);
      return retval;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player entity, InteractionHand hand) {
      InteractionResultHolder<ItemStack> ar = super.m_7203_(world, entity, hand);
      GlaciesRightClickProcedure.execute(world, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), entity, (ItemStack)ar.m_19095_());
      return ar;
   }

   public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
      super.m_7373_(itemstack, world, list, flag);
      list.add(Component.m_237113_("§dA legendary axe passed down and wielded by the leader of the frost tribe."));
      list.add(Component.m_237113_("§8-"));
      list.add(Component.m_237113_("§6[Unique Effect]: Glacies' Bite: §fSlows enemies on hit."));
      list.add(Component.m_237113_("§8-"));
      list.add(Component.m_237113_("§a[Right Click Ability]: Entomb: §fEncase your armor in ice granting a sharp boost to armor but inflicts slowness."));
   }
}
