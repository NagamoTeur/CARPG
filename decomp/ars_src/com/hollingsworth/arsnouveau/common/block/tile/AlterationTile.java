package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.perk.ArmorPerkHolder;
import com.hollingsworth.arsnouveau.api.perk.IPerk;
import com.hollingsworth.arsnouveau.api.perk.IPerkHolder;
import com.hollingsworth.arsnouveau.api.perk.PerkSlot;
import com.hollingsworth.arsnouveau.api.util.PerkUtil;
import com.hollingsworth.arsnouveau.common.block.AlterationTable;
import com.hollingsworth.arsnouveau.common.block.ITickable;
import com.hollingsworth.arsnouveau.common.block.ThreePartBlock;
import com.hollingsworth.arsnouveau.common.items.PerkItem;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class AlterationTile extends ModdedTile implements IAnimatable, ITickable {
   public ItemStack armorStack = ItemStack.f_41583_;
   public ItemEntity renderEntity;
   public List<ItemStack> perkList = new ArrayList<>();
   public int newPerkTimer = 0;
   public AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public AlterationTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
      super(tileEntityTypeIn, pos, state);
   }

   public AlterationTile(BlockPos pos, BlockState state) {
      super(BlockRegistry.ARMOR_TILE, pos, state);
   }

   @Override
   public void registerControllers(AnimationData animationData) {
   }

   @Override
   public AnimationFactory getFactory() {
      return this.factory;
   }

   @Nullable
   public AlterationTile getLogicTile() {
      AlterationTile tile = this;
      if (!this.isMasterTile()) {
         tile = this.f_58857_.m_7702_(this.m_58899_().m_121945_(AlterationTable.getConnectedDirection(this.m_58900_()))) instanceof AlterationTile alterationTile
            ? alterationTile
            : null;
      }

      return tile;
   }

   public boolean isMasterTile() {
      return this.m_58900_().m_61143_(AlterationTable.PART) == ThreePartBlock.HEAD;
   }

   public void setArmorStack(ItemStack stack, Player player) {
      if (PerkUtil.getPerkHolder(stack) instanceof ArmorPerkHolder armorPerkHolder) {
         this.perkList = new ArrayList<>(PerkUtil.getPerksAsItems(stack).stream().map(Item::m_7968_).toList());
         armorPerkHolder.setPerks(new ArrayList<>());
         this.armorStack = stack.m_41777_();
         stack.m_41774_(1);
         this.newPerkTimer = 0;
         this.updateBlock();
      }
   }

   public void removePerk(Player player) {
      if (!this.perkList.isEmpty()) {
         ItemStack stack = this.perkList.get(0);
         if (!player.m_36356_(stack.m_41777_())) {
            this.f_58857_
               .m_7967_(new ItemEntity(this.f_58857_, player.m_20182_().m_7096_(), player.m_20182_().m_7098_(), player.m_20182_().m_7094_(), stack.m_41777_()));
         }

         this.perkList.remove(0);
      }

      this.updateBlock();
   }

   public void removeArmorStack(Player player) {
      if (PerkUtil.getPerkHolder(this.armorStack) instanceof ArmorPerkHolder armorPerkHolder) {
         armorPerkHolder.setPerks(
            this.perkList.stream().map(i -> i.m_41720_() instanceof PerkItem perkItem ? perkItem.perk : null).filter(Objects::nonNull).toList()
         );
      }

      if (!player.m_36356_(this.armorStack.m_41777_())) {
         this.f_58857_
            .m_7967_(
               new ItemEntity(this.f_58857_, player.m_20182_().m_7096_(), player.m_20182_().m_7098_(), player.m_20182_().m_7094_(), this.armorStack.m_41777_())
            );
      }

      this.armorStack = ItemStack.f_41583_;
      this.perkList = new ArrayList<>();
      this.updateBlock();
   }

   public void addPerkStack(ItemStack stack, Player player) {
      IPerkHolder<ItemStack> perkHolder = PerkUtil.getPerkHolder(this.armorStack);
      if (perkHolder instanceof ArmorPerkHolder armorPerkHolder) {
         if (this.perkList.size() < 3 && this.perkList.size() < armorPerkHolder.getSlotsForTier().size()) {
            PerkSlot foundSlot = this.getAvailableSlot(perkHolder);
            if (stack.m_41720_() instanceof PerkItem perkItem) {
               IPerk perk = perkItem.perk;
               if (foundSlot != null && perk.validForSlot(foundSlot, stack, player)) {
                  this.perkList.add(stack.m_41620_(1));
                  if (this.newPerkTimer <= 0) {
                     this.newPerkTimer = 40;
                  }

                  this.updateBlock();
               }
            }
         } else {
            PortUtil.sendMessage(player, Component.m_237115_("ars_nouveau.perk.max_perks"));
         }
      } else {
         PortUtil.sendMessage(player, Component.m_237115_("ars_nouveau.perk.set_armor"));
      }
   }

   private PerkSlot getAvailableSlot(IPerkHolder<ItemStack> perkHolder) {
      return this.perkList.size() >= perkHolder.getSlotsForTier().size() ? null : perkHolder.getSlotsForTier().get(this.perkList.size());
   }

   public void dropItems() {
      if (!this.armorStack.m_41619_()) {
         this.f_58857_
            .m_7967_(
               new ItemEntity(
                  this.f_58857_,
                  (double)this.f_58858_.m_123341_(),
                  (double)this.f_58858_.m_123342_(),
                  (double)this.f_58858_.m_123343_(),
                  this.armorStack.m_41777_()
               )
            );
      }

      for (ItemStack stack : this.perkList) {
         this.f_58857_
            .m_7967_(
               new ItemEntity(
                  this.f_58857_, (double)this.f_58858_.m_123341_(), (double)this.f_58858_.m_123342_(), (double)this.f_58858_.m_123343_(), stack.m_41777_()
               )
            );
      }
   }

   @Override
   public void m_183515_(CompoundTag tag) {
      super.m_183515_(tag);
      CompoundTag armorTag = new CompoundTag();
      this.armorStack.m_41739_(armorTag);
      tag.m_128365_("armorStack", armorTag);
      tag.m_128405_("numPerks", this.perkList.size());
      int count = 0;

      for (ItemStack i : this.perkList) {
         CompoundTag perkTag = new CompoundTag();
         i.m_41739_(perkTag);
         tag.m_128365_("perk" + count, perkTag);
         count++;
      }

      tag.m_128405_("newPerkTimer", this.newPerkTimer);
   }

   public void m_142466_(CompoundTag compound) {
      super.m_142466_(compound);
      this.armorStack = ItemStack.m_41712_(compound.m_128469_("armorStack"));
      int count = compound.m_128451_("numPerks");
      this.perkList = new ArrayList<>();

      for (int i = 0; i < count; i++) {
         CompoundTag perkTag = compound.m_128469_("perk" + i);
         ItemStack perk = ItemStack.m_41712_(perkTag);
         this.perkList.add(perk);
      }

      this.newPerkTimer = compound.m_128451_("newPerkTimer");
   }

   public AABB getRenderBoundingBox() {
      return super.getRenderBoundingBox().m_82400_(2.0);
   }

   @Override
   public void tick(Level level, BlockState state, BlockPos pos) {
      if (level.f_46443_ && this.newPerkTimer >= 0) {
         this.newPerkTimer--;
      }
   }
}
