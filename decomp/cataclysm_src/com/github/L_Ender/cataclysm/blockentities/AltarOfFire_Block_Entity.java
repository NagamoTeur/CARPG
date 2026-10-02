package com.github.L_Ender.cataclysm.blockentities;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ignis_Entity;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import com.github.L_Ender.cataclysm.message.MessageUpdateblockentity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class AltarOfFire_Block_Entity extends BaseContainerBlockEntity {
   public int tickCount;
   private static final int NUM_SLOTS = 1;
   private NonNullList<ItemStack> stacks = NonNullList.m_122780_(1, ItemStack.f_41583_);
   public boolean summoningthis = false;
   public int summoningticks = 0;
   private final RandomSource rnd = RandomSource.m_216327_();

   public AltarOfFire_Block_Entity(BlockPos pos, BlockState state) {
      super((BlockEntityType)ModTileentites.ALTAR_OF_FIRE.get(), pos, state);
   }

   public static void commonTick(Level level, BlockPos pos, BlockState state, AltarOfFire_Block_Entity entity) {
      entity.tick();
   }

   public void tick() {
      this.tickCount++;
      this.summoningthis = false;
      if (!this.m_8020_(0).m_41619_() && this.m_8020_(0).m_41720_() == ModItems.BURNING_ASHES.get()) {
         this.summoningthis = true;
         if (this.summoningticks == 1) {
            ScreenShake_Entity.ScreenShake(this.f_58857_, Vec3.m_82512_(this.m_58899_()), 20.0F, 0.05F, 0, 150);
         }

         if (this.summoningticks > 118 && this.summoningticks < 121) {
            this.Sphereparticle(3.0F, 3.0F);
         }

         if (this.summoningticks > 121) {
            this.m_6836_(0, ItemStack.f_41583_);
            this.BlockBreaking(3, 3, 3);
            this.BasaltBreaking(16, 8, 16);
            Ignis_Entity ignis = (Ignis_Entity)((EntityType)ModEntities.IGNIS.get()).m_20615_(this.f_58857_);
            if (ignis != null) {
               ignis.m_6034_(
                  (double)((float)this.m_58899_().m_123341_() + 0.5F),
                  (double)(this.m_58899_().m_123342_() + 3),
                  (double)((float)this.m_58899_().m_123343_() + 0.5F)
               );
               if (!this.f_58857_.f_46443_) {
                  this.f_58857_.m_7967_(ignis);
               }
            }
         }
      }

      if (!this.summoningthis) {
         this.summoningticks = 0;
      } else {
         this.summoningticks++;
      }
   }

   private void BlockBreaking(int x, int y, int z) {
      int MthX = Mth.m_14143_((float)this.m_58899_().m_123341_());
      int MthY = Mth.m_14143_((float)this.m_58899_().m_123342_());
      int MthZ = Mth.m_14143_((float)this.m_58899_().m_123343_());

      for (int k2 = -x; k2 <= x; k2++) {
         for (int l2 = -z; l2 <= z; l2++) {
            for (int j = 0; j <= y; j++) {
               int i3 = MthX + k2;
               int k = MthY + j;
               int l = MthZ + l2;
               BlockPos blockpos = new BlockPos(i3, k, l);
               BlockState block = this.f_58857_.m_8055_(blockpos);
               if (block != Blocks.f_50016_.m_49966_() && !block.m_204336_(ModTag.ALTAR_DESTROY_IMMUNE)) {
                  this.f_58857_.m_46961_(blockpos, false);
               }
            }
         }
      }
   }

   private void BasaltBreaking(int x, int y, int z) {
      int MthX = Mth.m_14143_((float)this.m_58899_().m_123341_());
      int MthY = Mth.m_14143_((float)this.m_58899_().m_123342_());
      int MthZ = Mth.m_14143_((float)this.m_58899_().m_123343_());

      for (int k2 = -x; k2 <= x; k2++) {
         for (int l2 = -z; l2 <= z; l2++) {
            for (int j = -1; j <= y; j++) {
               int i3 = MthX + k2;
               int k = MthY + j;
               int l = MthZ + l2;
               BlockPos blockpos = new BlockPos(i3, k, l);
               BlockState blockstate = this.f_58857_.m_8055_(blockpos);
               Block block = blockstate.m_60734_();
               if (block != Blocks.f_50016_ && block == Blocks.f_50137_) {
                  this.f_58857_.m_46961_(blockpos, false);
               }
            }
         }
      }
   }

   private void Sphereparticle(float height, float size) {
      double d0 = (double)((float)this.m_58899_().m_123341_() + 0.5F);
      double d1 = (double)((float)this.m_58899_().m_123342_() + height);
      double d2 = (double)((float)this.m_58899_().m_123343_() + 0.5F);

      for (float i = -size; i <= size; i++) {
         for (float j = -size; j <= size; j++) {
            for (float k = -size; k <= size; k++) {
               double d3 = (double)j + (this.rnd.m_188500_() - this.rnd.m_188500_()) * 0.5;
               double d4 = (double)i + (this.rnd.m_188500_() - this.rnd.m_188500_()) * 0.5;
               double d5 = (double)k + (this.rnd.m_188500_() - this.rnd.m_188500_()) * 0.5;
               double d6 = (double)Mth.m_14116_((float)(d3 * d3 + d4 * d4 + d5 * d5)) / 0.5 + this.rnd.m_188583_() * 0.05;
               this.f_58857_.m_7106_(ParticleTypes.f_123744_, d0, d1, d2, d3 / d6, d4 / d6, d5 / d6);
               if (i != -size && i != size && j != -size && j != size) {
                  k += size * 2.0F - 1.0F;
               }
            }
         }
      }
   }

   public int m_6643_() {
      return this.stacks.size();
   }

   public ItemStack m_8020_(int index) {
      return (ItemStack)this.stacks.get(index);
   }

   public ItemStack m_7407_(int index, int count) {
      if (!((ItemStack)this.stacks.get(index)).m_41619_()) {
         ItemStack itemstack;
         if (((ItemStack)this.stacks.get(index)).m_41613_() <= count) {
            itemstack = (ItemStack)this.stacks.get(index);
            this.stacks.set(index, ItemStack.f_41583_);
         } else {
            itemstack = ((ItemStack)this.stacks.get(index)).m_41620_(count);
            if (((ItemStack)this.stacks.get(index)).m_41619_()) {
               this.stacks.set(index, ItemStack.f_41583_);
            }
         }

         return itemstack;
      } else {
         return ItemStack.f_41583_;
      }
   }

   public void m_6836_(int index, ItemStack stack) {
      if (!stack.m_41619_() && ItemStack.m_150942_(stack, (ItemStack)this.stacks.get(index))) {
         boolean var4 = true;
      } else {
         boolean var10000 = false;
      }

      this.stacks.set(index, stack);
      if (!stack.m_41619_() && stack.m_41613_() > this.m_6893_()) {
         stack.m_41764_(this.m_6893_());
      }

      this.m_183515_(this.m_5995_());
      if (!this.f_58857_.f_46443_) {
         Cataclysm.sendMSGToAll(new MessageUpdateblockentity(this.m_58899_().m_121878_(), (ItemStack)this.stacks.get(0)));
      }
   }

   public void m_142466_(CompoundTag compound) {
      super.m_142466_(compound);
      this.stacks = NonNullList.m_122780_(this.m_6643_(), ItemStack.f_41583_);
      this.summoningthis = compound.m_128471_("Summoningthis");
      ContainerHelper.m_18980_(compound, this.stacks);
   }

   public void m_183515_(CompoundTag compound) {
      super.m_183515_(compound);
      ContainerHelper.m_18973_(compound, this.stacks);
      compound.m_128379_("Summoningthis", this.summoningthis);
   }

   public void m_5856_(Player player) {
   }

   public void m_5785_(Player player) {
   }

   public int m_6893_() {
      return 1;
   }

   public boolean m_6542_(Player player) {
      return true;
   }

   public void m_6211_() {
      this.stacks.clear();
   }

   public boolean m_8077_() {
      return false;
   }

   public boolean m_7013_(int index, ItemStack stack) {
      return true;
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.m_195640_(this);
   }

   public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet) {
      if (packet != null && packet.m_131708_() != null) {
         this.stacks = NonNullList.m_122780_(this.m_6643_(), ItemStack.f_41583_);
         ContainerHelper.m_18980_(packet.m_131708_(), this.stacks);
      }
   }

   public CompoundTag m_5995_() {
      return this.m_187482_();
   }

   public ItemStack m_8016_(int index) {
      ItemStack lvt_2_1_ = (ItemStack)this.stacks.get(index);
      if (lvt_2_1_.m_41619_()) {
         return ItemStack.f_41583_;
      } else {
         this.stacks.set(index, ItemStack.f_41583_);
         return lvt_2_1_;
      }
   }

   public Component m_5446_() {
      return this.m_6820_();
   }

   protected Component m_6820_() {
      return Component.m_237115_("block.cataclysm.altar_of_fire");
   }

   protected AbstractContainerMenu m_6555_(int id, Inventory player) {
      return null;
   }

   public boolean m_7983_() {
      for (int i = 0; i < this.m_6643_(); i++) {
         if (!this.m_8020_(i).m_41619_()) {
            return false;
         }
      }

      return true;
   }
}
