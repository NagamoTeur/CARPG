package com.hollingsworth.arsnouveau.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PlayerHeadItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class EnchantedSkull extends EnchantedFallingBlock implements IEntityAdditionalSpawnData {
   public EnchantedSkull(EntityType<? extends ColoredProjectile> p_31950_, Level p_31951_) {
      super(p_31950_, p_31951_);
   }

   public EnchantedSkull(Level world, double v, double y, double v1, BlockState blockState) {
      super(world, v, y, v1, blockState);
   }

   public EnchantedSkull(Level world, BlockPos pos, BlockState blockState) {
      super(world, pos, blockState);
   }

   @Override
   public EntityType<?> m_6095_() {
      return (EntityType<?>)ModEntities.ENCHANTED_HEAD_BLOCK.get();
   }

   @Override
   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   @Nullable
   public ItemEntity m_19983_(ItemStack pStack) {
      if (pStack.m_41720_() instanceof PlayerHeadItem) {
         pStack.m_41751_(this.blockData);
      }

      return this.m_5552_(pStack, 0.0F);
   }

   public ItemStack getStack() {
      Item item = this.getBlockState().m_60734_().m_5456_();
      ItemStack stack = item.m_7968_();
      if (item instanceof PlayerHeadItem) {
         stack.m_41751_(this.blockData);
      }

      return stack;
   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      buffer.writeInt(Block.m_49956_(this.blockState));
      buffer.m_130079_(this.blockData);
   }

   public void readSpawnData(FriendlyByteBuf additionalData) {
      this.blockState = Block.m_49803_(additionalData.readInt());
      this.blockData = additionalData.m_130260_();
   }
}
