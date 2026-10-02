package net.thirdlife.iterrpg.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.thirdlife.iterrpg.procedures.RuneSummonAirElementalProcedure;

public class AirRuneItem extends Item {
   public AirRuneItem() {
      super(new Properties().m_41491_(CreativeModeTab.f_40759_).m_41487_(1).m_41497_(Rarity.UNCOMMON));
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player entity, InteractionHand hand) {
      InteractionResultHolder<ItemStack> ar = super.m_7203_(world, entity, hand);
      ItemStack itemstack = (ItemStack)ar.m_19095_();
      double x = entity.m_20185_();
      double y = entity.m_20186_();
      double z = entity.m_20189_();
      RuneSummonAirElementalProcedure.execute(world, x, y, z, entity, itemstack);
      return ar;
   }
}
