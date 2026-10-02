package com.hollingsworth.arsnouveau.api.recipe;

import com.hollingsworth.arsnouveau.api.potion.PotionData;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.common.block.tile.PotionJarTile;
import com.hollingsworth.arsnouveau.common.block.tile.WixieCauldronTile;
import com.hollingsworth.arsnouveau.common.entity.EntityFlyingItem;
import com.hollingsworth.arsnouveau.common.util.PotionUtil;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class PotionCraftingManager extends CraftingManager {
   private boolean hasObtainedPotion;
   private Potion potionNeeded;
   public Potion potionOut;

   public PotionCraftingManager() {
   }

   public PotionCraftingManager(Potion potionNeeded, List<ItemStack> itemsNeeded, Potion potionOut) {
      super(ItemStack.f_41583_, itemsNeeded);
      this.potionNeeded = potionNeeded;
      this.potionOut = potionOut;
      this.neededItems = itemsNeeded;
      this.remainingItems = itemsNeeded;
      this.outputStack = ItemStack.f_41583_;
      this.hasObtainedPotion = potionNeeded == Potions.f_43598_ || potionNeeded == Potions.f_43599_;
   }

   @Override
   public boolean canBeCompleted() {
      return !this.needsPotion() && super.canBeCompleted();
   }

   public boolean needsPotion() {
      return !this.hasObtainedPotion;
   }

   public Potion getPotionNeeded() {
      return this.potionNeeded;
   }

   public void setObtainedPotion(boolean hasObtainedPotion) {
      this.hasObtainedPotion = hasObtainedPotion;
   }

   @Override
   public void completeCraft(WixieCauldronTile tile) {
      Level level = tile.m_58904_();
      BlockPos worldPosition = tile.m_58899_();
      BlockPos jarPos = WixieCauldronTile.findPotionStorage(tile.m_58904_(), tile.m_58899_(), this.potionOut);
      if (jarPos == null) {
         if (!tile.needsPotionStorage()) {
            tile.setNeedsPotionStorage(true);
            level.m_7260_(worldPosition, level.m_8055_(worldPosition), level.m_8055_(worldPosition), 3);
         }
      } else {
         if (level.m_7702_(jarPos) instanceof PotionJarTile jar) {
            tile.setNeedsPotionStorage(false);
            jar.add(new PotionData(this.potionOut), 300);
            ParticleColor color2 = ParticleColor.fromInt(jar.getColor());
            EntityFlyingItem flying = new EntityFlyingItem(
                  level,
                  new Vec3((double)worldPosition.m_123341_() + 0.5, (double)worldPosition.m_123342_() + 1.0, (double)worldPosition.m_123343_() + 0.5),
                  new Vec3((double)jarPos.m_123341_() + 0.5, (double)jarPos.m_123342_(), (double)jarPos.m_123343_() + 0.5),
                  Math.round(255.0F * color2.getRed()),
                  Math.round(255.0F * color2.getGreen()),
                  Math.round(255.0F * color2.getBlue())
               )
               .withNoTouch();
            level.m_7967_(flying);
         }

         super.completeCraft(tile);
      }
   }

   @Override
   public void write(CompoundTag tag) {
      super.write(tag);
      CompoundTag outputTag = new CompoundTag();
      PotionUtil.addPotionToTag(this.potionOut, outputTag);
      tag.m_128365_("potionout", outputTag);
      CompoundTag neededTag = new CompoundTag();
      PotionUtil.addPotionToTag(this.getPotionNeeded(), neededTag);
      tag.m_128365_("potionNeeded", neededTag);
      tag.m_128379_("gotPotion", this.hasObtainedPotion);
   }

   @Override
   public void read(CompoundTag tag) {
      super.read(tag);
      this.potionOut = PotionUtils.m_43577_(tag.m_128469_("potionout"));
      this.potionNeeded = PotionUtils.m_43577_(tag.m_128469_("potionNeeded"));
      this.hasObtainedPotion = tag.m_128471_("gotPotion");
   }
}
