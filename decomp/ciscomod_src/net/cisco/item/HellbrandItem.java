package net.cisco.item;

import java.util.List;
import net.cisco.init.CiscoModModItems;
import net.cisco.init.CiscoModModTabs;
import net.cisco.procedures.HellbrandLivingEntityIsHitWithToolProcedure;
import net.cisco.procedures.HellbrandRightclickedProcedure;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class HellbrandItem extends SwordItem {
   public HellbrandItem() {
      super(new Tier() {
         public int m_6609_() {
            return 4500;
         }

         public float m_6624_() {
            return 4.0F;
         }

         public float m_6631_() {
            return 27.0F;
         }

         public int m_6604_() {
            return 1;
         }

         public int m_6601_() {
            return 20;
         }

         public Ingredient m_6282_() {
            return Ingredient.m_43927_(new ItemStack[]{new ItemStack((ItemLike)CiscoModModItems.DEMONIUM_INGOT.get())});
         }
      }, 3, -2.4F, new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD).m_41486_());
   }

   public boolean m_7579_(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
      boolean retval = super.m_7579_(itemstack, entity, sourceentity);
      HellbrandLivingEntityIsHitWithToolProcedure.execute(entity);
      return retval;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player entity, InteractionHand hand) {
      InteractionResultHolder<ItemStack> ar = super.m_7203_(world, entity, hand);
      HellbrandRightclickedProcedure.execute(world, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), entity, (ItemStack)ar.m_19095_());
      return ar;
   }

   public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
      super.m_7373_(itemstack, world, list, flag);
      list.add(Component.m_237113_("§5Legendary weapon of the 2nd Fell King Bjorn."));
      list.add(Component.m_237113_("§8-"));
      list.add(Component.m_237113_("§6[Unique Effect] Hellbrand : §finflicts hellbrand effect on targets hit tripling damage dealt by this weapon."));
      list.add(Component.m_237113_("§8-"));
      list.add(
         Component.m_237113_(
            "§a[Right Click Ability] §fIf user is above 50% hp grants a temporary boost to attack based on 60% max armor. If user is below 50% hp,grants 5 seconds of invunerability."
         )
      );
   }
}
