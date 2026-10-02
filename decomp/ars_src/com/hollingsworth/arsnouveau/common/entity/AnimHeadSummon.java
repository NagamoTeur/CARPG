package com.hollingsworth.arsnouveau.common.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PlayerHeadItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

public class AnimHeadSummon extends AnimBlockSummon implements IEntityAdditionalSpawnData {
   public CompoundTag head_data = new CompoundTag();

   public AnimHeadSummon(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
   }

   public AnimHeadSummon(Level pLevel, BlockState state, CompoundTag head_data) {
      this((EntityType<? extends TamableAnimal>)ModEntities.ANIMATED_HEAD.get(), pLevel);
      this.blockState = state;
      this.head_data = head_data;
   }

   @Override
   public EntityType<?> m_6095_() {
      return (EntityType<?>)ModEntities.ANIMATED_HEAD.get();
   }

   @Override
   public void returnToFallingBlock(BlockState blockState) {
      if (!this.f_19853_.f_46443_ && this.dropItem) {
         EnchantedFallingBlock fallingBlock = new EnchantedSkull(this.f_19853_, this.m_20183_(), blockState);
         fallingBlock.m_5602_(this.m_21826_());
         fallingBlock.m_20256_(this.m_20184_());
         if (blockState.m_60734_() == Blocks.f_50316_) {
            fallingBlock.blockData = this.head_data;
         }

         this.f_19853_.m_7967_(fallingBlock);
      }
   }

   @Override
   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   @Override
   public void m_7380_(CompoundTag pCompound) {
      super.m_7380_(pCompound);
      pCompound.m_128365_("head_data", this.head_data);
   }

   @Override
   public void m_7378_(CompoundTag pCompound) {
      super.m_7378_(pCompound);
      this.head_data = pCompound.m_128469_("head_data");
   }

   public ItemStack getStack() {
      Item item = this.getBlockState().m_60734_().m_5456_();
      ItemStack stack = item.m_7968_();
      if (item instanceof PlayerHeadItem) {
         stack.m_41751_(this.head_data);
      }

      return stack;
   }

   public static CompoundTag getHeadTagFromName(String playerName) {
      CompoundTag compoundtag = new CompoundTag();
      compoundtag.m_128359_("SkullOwner", playerName);
      return compoundtag;
   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      buffer.writeInt(Block.m_49956_(this.blockState));
      buffer.m_130079_(this.head_data);
   }

   public void readSpawnData(FriendlyByteBuf additionalData) {
      this.blockState = Block.m_49803_(additionalData.readInt());
      this.head_data = additionalData.m_130260_();
   }
}
