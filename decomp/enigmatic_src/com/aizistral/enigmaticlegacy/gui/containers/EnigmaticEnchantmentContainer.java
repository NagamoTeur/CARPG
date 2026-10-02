package com.aizistral.enigmaticlegacy.gui.containers;

import java.util.List;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags.Items;
import net.minecraftforge.event.ForgeEventFactory;

public class EnigmaticEnchantmentContainer extends EnchantmentMenu {
   private Container tableInventory = new SimpleContainer(2) {
      public void m_6596_() {
         super.m_6596_();
         EnigmaticEnchantmentContainer.this.m_6199_(this);
      }
   };
   private ContainerLevelAccess worldPosCallable;
   private RandomSource rand = RandomSource.m_216327_();
   private DataSlot xpSeed = DataSlot.m_39401_();
   public int[] enchantLevels = new int[3];
   public int[] f_39447_ = new int[]{-1, -1, -1};
   public int[] worldClue = new int[]{-1, -1, -1};

   public static EnigmaticEnchantmentContainer fromOld(EnchantmentMenu oldContainer, Player player) throws IllegalArgumentException, IllegalAccessException {
      EnigmaticEnchantmentContainer newContainer = new EnigmaticEnchantmentContainer(oldContainer.f_38840_, player.m_150109_(), oldContainer.f_39450_);
      newContainer.tableInventory = oldContainer.f_39449_;
      newContainer.enchantLevels = oldContainer.f_39446_;
      newContainer.f_39447_ = oldContainer.f_39447_;
      newContainer.worldClue = oldContainer.f_39448_;
      return newContainer;
   }

   private EnigmaticEnchantmentContainer(int id, Inventory Inventory, ContainerLevelAccess pos) {
      super(id, Inventory, pos);
      this.worldPosCallable = pos;
      this.m_38897_(new Slot(this.tableInventory, 0, 15, 47) {
         public boolean m_5857_(ItemStack stack) {
            return true;
         }

         public int m_6641_() {
            return 1;
         }
      });
      this.m_38897_(new Slot(this.tableInventory, 1, 35, 47) {
         public boolean m_5857_(ItemStack stack) {
            return stack.m_204117_(Items.GEMS_LAPIS);
         }
      });

      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 9; j++) {
            this.m_38897_(new Slot(Inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
         }
      }

      for (int k = 0; k < 9; k++) {
         this.m_38897_(new Slot(Inventory, k, 8 + k * 18, 142));
      }

      this.m_38895_(DataSlot.m_39406_(this.enchantLevels, 0));
      this.m_38895_(DataSlot.m_39406_(this.enchantLevels, 1));
      this.m_38895_(DataSlot.m_39406_(this.enchantLevels, 2));
      this.m_38895_(this.xpSeed).m_6422_(Inventory.f_35978_.m_36322_());
      this.m_38895_(DataSlot.m_39406_(this.f_39447_, 0));
      this.m_38895_(DataSlot.m_39406_(this.f_39447_, 1));
      this.m_38895_(DataSlot.m_39406_(this.f_39447_, 2));
      this.m_38895_(DataSlot.m_39406_(this.worldClue, 0));
      this.m_38895_(DataSlot.m_39406_(this.worldClue, 1));
      this.m_38895_(DataSlot.m_39406_(this.worldClue, 2));
   }

   public void m_6199_(Container inventoryIn) {
      if (inventoryIn == this.tableInventory) {
         ItemStack itemstack = inventoryIn.m_8020_(0);
         if (!itemstack.m_41619_() && itemstack.m_41792_()) {
            this.worldPosCallable.m_39292_((p_217002_2_, p_217002_3_) -> {
               int power = 0;

               for (int k = -1; k <= 1; k++) {
                  for (int l = -1; l <= 1; l++) {
                     if ((k != 0 || l != 0) && p_217002_2_.m_46859_(p_217002_3_.m_7918_(l, 0, k)) && p_217002_2_.m_46859_(p_217002_3_.m_7918_(l, 1, k))) {
                        power = (int)((float)power + this.getPower(p_217002_2_, p_217002_3_.m_7918_(l * 2, 0, k * 2)));
                        power = (int)((float)power + this.getPower(p_217002_2_, p_217002_3_.m_7918_(l * 2, 1, k * 2)));
                        if (l != 0 && k != 0) {
                           power = (int)((float)power + this.getPower(p_217002_2_, p_217002_3_.m_7918_(l * 2, 0, k)));
                           power = (int)((float)power + this.getPower(p_217002_2_, p_217002_3_.m_7918_(l * 2, 1, k)));
                           power = (int)((float)power + this.getPower(p_217002_2_, p_217002_3_.m_7918_(l, 0, k * 2)));
                           power = (int)((float)power + this.getPower(p_217002_2_, p_217002_3_.m_7918_(l, 1, k * 2)));
                        }
                     }
                  }
               }

               this.rand.m_188584_((long)this.xpSeed.m_6501_());

               for (int i1 = 0; i1 < 3; i1++) {
                  this.enchantLevels[i1] = EnchantmentHelper.m_220287_(this.rand, i1, power, itemstack);
                  this.f_39447_[i1] = -1;
                  this.worldClue[i1] = -1;
                  if (this.enchantLevels[i1] < i1 + 1) {
                     this.enchantLevels[i1] = 0;
                  }

                  this.enchantLevels[i1] = ForgeEventFactory.onEnchantmentLevelSet(p_217002_2_, p_217002_3_, i1, power, itemstack, this.enchantLevels[i1]);
               }

               for (int j1 = 0; j1 < 3; j1++) {
                  if (this.enchantLevels[j1] > 0) {
                     List<EnchantmentInstance> list = this.m_39471_(itemstack, j1, this.enchantLevels[j1]);
                     if (list != null && !list.isEmpty()) {
                        EnchantmentInstance enchantmentdata = list.get(this.rand.m_188503_(list.size()));
                        this.f_39447_[j1] = Registry.f_122825_.m_7447_(enchantmentdata.f_44947_);
                        this.worldClue[j1] = enchantmentdata.f_44948_;
                     }
                  }
               }

               this.m_38946_();
            });
         } else {
            for (int i = 0; i < 3; i++) {
               this.enchantLevels[i] = 0;
               this.f_39447_[i] = -1;
               this.worldClue[i] = -1;
            }
         }
      }
   }

   private float getPower(Level world, BlockPos pos) {
      return world.m_8055_(pos).getEnchantPowerBonus(world, pos);
   }

   public boolean m_6366_(Player playerIn, int id) {
      System.out.println("We hooked in!");
      ItemStack itemstack = this.tableInventory.m_8020_(0);
      ItemStack itemstack1 = this.tableInventory.m_8020_(1);
      int i = id + 1;
      if ((itemstack1.m_41619_() || itemstack1.m_41613_() < i) && !playerIn.m_150110_().f_35937_) {
         return false;
      } else if (this.enchantLevels[id] > 0
         && !itemstack.m_41619_()
         && (playerIn.f_36078_ >= i && playerIn.f_36078_ >= this.enchantLevels[id] || playerIn.m_150110_().f_35937_)) {
         this.worldPosCallable.m_39292_((p_217003_6_, p_217003_7_) -> {
            ItemStack itemstack2 = itemstack;
            List<EnchantmentInstance> list = this.m_39471_(itemstack, id, this.enchantLevels[id]);
            if (!list.isEmpty()) {
               playerIn.m_7408_(itemstack, i);
               boolean flag = itemstack.m_41720_() == net.minecraft.world.item.Items.f_42517_;
               if (flag) {
                  itemstack2 = new ItemStack(net.minecraft.world.item.Items.f_42690_);
                  CompoundTag compoundnbt = itemstack.m_41783_();
                  if (compoundnbt != null) {
                     itemstack2.m_41751_(compoundnbt.m_6426_());
                  }

                  this.tableInventory.m_6836_(0, itemstack2);
               }

               for (EnchantmentInstance enchantmentdata : list) {
                  if (flag) {
                     EnchantedBookItem.m_41153_(itemstack2, enchantmentdata);
                  } else {
                     itemstack2.m_41663_(enchantmentdata.f_44947_, enchantmentdata.f_44948_);
                  }
               }

               if (!playerIn.m_150110_().f_35937_) {
                  itemstack1.m_41774_(i);
                  if (itemstack1.m_41619_()) {
                     this.tableInventory.m_6836_(1, ItemStack.f_41583_);
                  }
               }

               playerIn.m_36220_(Stats.f_12964_);
               if (playerIn instanceof ServerPlayer) {
                  CriteriaTriggers.f_10575_.m_27668_((ServerPlayer)playerIn, itemstack2, i);
               }

               this.tableInventory.m_6596_();
               this.xpSeed.m_6422_(playerIn.m_36322_());
               this.m_6199_(this.tableInventory);
               p_217003_6_.m_5594_((Player)null, p_217003_7_, SoundEvents.f_11887_, SoundSource.BLOCKS, 1.0F, p_217003_6_.f_46441_.m_188501_() * 0.1F + 0.9F);
            }
         });
         return true;
      } else {
         return false;
      }
   }

   public List<EnchantmentInstance> m_39471_(ItemStack stack, int enchantSlot, int level) {
      this.rand.m_188584_((long)(this.xpSeed.m_6501_() + enchantSlot));
      List<EnchantmentInstance> list = EnchantmentHelper.m_220297_(this.rand, stack, level, false);
      if (stack.m_41720_() == net.minecraft.world.item.Items.f_42517_ && list.size() > 1) {
         list.remove(this.rand.m_188503_(list.size()));
      }

      return list;
   }

   @OnlyIn(Dist.CLIENT)
   public int m_39492_() {
      ItemStack itemstack = this.tableInventory.m_8020_(1);
      return itemstack.m_41619_() ? 0 : itemstack.m_41613_();
   }

   @OnlyIn(Dist.CLIENT)
   public int m_39493_() {
      return this.xpSeed.m_6501_();
   }

   public void m_6877_(Player playerIn) {
      super.m_6877_(playerIn);
      this.worldPosCallable.m_39292_((p_217004_2_, p_217004_3_) -> this.m_150411_(playerIn, this.tableInventory));
   }

   public boolean m_6875_(Player playerIn) {
      return m_38889_(this.worldPosCallable, playerIn, Blocks.f_50201_);
   }

   public ItemStack m_7648_(Player playerIn, int index) {
      ItemStack itemstack = ItemStack.f_41583_;
      Slot slot = (Slot)this.f_38839_.get(index);
      if (slot != null && slot.m_6657_()) {
         ItemStack itemstack1 = slot.m_7993_();
         itemstack = itemstack1.m_41777_();
         if (index == 0) {
            if (!this.m_38903_(itemstack1, 2, 38, true)) {
               return ItemStack.f_41583_;
            }
         } else if (index == 1) {
            if (!this.m_38903_(itemstack1, 2, 38, true)) {
               return ItemStack.f_41583_;
            }
         } else if (itemstack1.m_41720_() == net.minecraft.world.item.Items.f_42534_) {
            if (!this.m_38903_(itemstack1, 1, 2, true)) {
               return ItemStack.f_41583_;
            }
         } else {
            if (((Slot)this.f_38839_.get(0)).m_6657_() || !((Slot)this.f_38839_.get(0)).m_5857_(itemstack1)) {
               return ItemStack.f_41583_;
            }

            ItemStack itemstack2 = itemstack1.m_41777_();
            itemstack2.m_41764_(1);
            itemstack1.m_41774_(1);
            ((Slot)this.f_38839_.get(0)).m_5852_(itemstack2);
         }

         if (itemstack1.m_41619_()) {
            slot.m_5852_(ItemStack.f_41583_);
         } else {
            slot.m_6654_();
         }

         if (itemstack1.m_41613_() == itemstack.m_41613_()) {
            return ItemStack.f_41583_;
         }

         slot.m_142406_(playerIn, itemstack1);
      }

      return itemstack;
   }
}
