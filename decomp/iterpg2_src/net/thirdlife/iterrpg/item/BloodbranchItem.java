package net.thirdlife.iterrpg.item;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.thirdlife.iterrpg.procedures.BloodBranchChargeProcedure;

public class BloodbranchItem extends Item {
   public BloodbranchItem() {
      super(new Properties().m_41491_(CreativeModeTab.f_40757_).m_41503_(512).m_41497_(Rarity.UNCOMMON));
   }

   public UseAnim m_6164_(ItemStack itemstack) {
      return UseAnim.BOW;
   }

   public int m_8105_(ItemStack itemstack) {
      return 32;
   }

   public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
      super.m_7373_(itemstack, world, list, flag);
      list.add(Component.m_237115_("iterpg.bloodbranch.desc.line1"));
      list.add(Component.m_237115_("iterpg.bloodbranch.desc.line2"));
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player entity, InteractionHand hand) {
      InteractionResultHolder<ItemStack> ar = super.m_7203_(world, entity, hand);
      ItemStack itemstack = (ItemStack)ar.m_19095_();
      double x = entity.m_20185_();
      double y = entity.m_20186_();
      double z = entity.m_20189_();
      BloodBranchChargeProcedure.execute(world, entity, itemstack);
      return ar;
   }
}
