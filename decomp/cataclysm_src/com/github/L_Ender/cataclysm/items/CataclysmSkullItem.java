package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.Cataclysm;
import java.util.function.Consumer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class CataclysmSkullItem extends StandingAndWallBlockItem implements ICurioItem {
   public CataclysmSkullItem(Block floorBlock, Block wallBlock, Properties properties) {
      super(floorBlock, wallBlock, properties);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      EquipmentSlot equipmentslot = Mob.m_147233_(itemstack);
      ItemStack itemstack1 = player.m_6844_(equipmentslot);
      if (itemstack1.m_41619_()) {
         player.m_8061_(equipmentslot, itemstack.m_41620_(1));
         if (!level.m_5776_()) {
            player.m_36246_(Stats.f_12982_.m_12902_(this));
         }

         return InteractionResultHolder.m_19092_(itemstack, level.m_5776_());
      } else {
         return InteractionResultHolder.m_19100_(itemstack);
      }
   }

   public boolean canEquip(ItemStack stack, EquipmentSlot slot, Entity entity) {
      return slot == EquipmentSlot.HEAD;
   }

   @Nullable
   public EquipmentSlot getEquipmentSlot(ItemStack stack) {
      return EquipmentSlot.HEAD;
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept((IClientItemExtensions)Cataclysm.PROXY.getISTERProperties());
   }
}
