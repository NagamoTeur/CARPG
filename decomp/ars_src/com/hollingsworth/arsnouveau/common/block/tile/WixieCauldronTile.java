package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.client.ITooltipProvider;
import com.hollingsworth.arsnouveau.api.item.IWandable;
import com.hollingsworth.arsnouveau.api.potion.PotionData;
import com.hollingsworth.arsnouveau.api.recipe.CraftingManager;
import com.hollingsworth.arsnouveau.api.recipe.IRecipeWrapper;
import com.hollingsworth.arsnouveau.api.recipe.MultiRecipeWrapper;
import com.hollingsworth.arsnouveau.api.recipe.PotionCraftingManager;
import com.hollingsworth.arsnouveau.api.recipe.PotionIngredient;
import com.hollingsworth.arsnouveau.api.util.SourceUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.client.util.ColorPos;
import com.hollingsworth.arsnouveau.common.block.WixieCauldron;
import com.hollingsworth.arsnouveau.common.entity.EntityFollowProjectile;
import com.hollingsworth.arsnouveau.common.entity.EntityWixie;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

public class WixieCauldronTile extends SummoningTile implements ITooltipProvider, IWandable {
   private List<BlockPos> cachedInventories;
   public List<BlockPos> boundedInvs = new ArrayList<>();
   private ItemStack setStack;
   private ItemStack stackBeingCrafted;
   public int entityID;
   public boolean hasSource;
   public boolean isCraftingPotion;
   private boolean needsPotionStorage;
   public CraftingManager craftManager = new CraftingManager();
   private int craftCooldown;
   public int craftingIndex;

   public WixieCauldronTile(BlockPos pos, BlockState state) {
      super(BlockRegistry.WIXIE_CAULDRON_TYPE, pos, state);
   }

   @Override
   public void tick() {
      super.tick();
      if (this.f_58857_ != null && !this.f_58857_.m_5776_()) {
         if (this.craftCooldown > 0) {
            this.craftCooldown--;
         } else {
            if (!this.hasSource && this.f_58857_.m_46467_() % 5L == 0L && SourceUtil.takeSourceWithParticles(this.f_58858_, this.f_58857_, 6, 50) != null) {
               this.hasSource = true;
               this.f_58857_.m_46597_(this.f_58858_, (BlockState)this.f_58857_.m_8055_(this.f_58858_).m_61124_(WixieCauldron.FILLED, true));
               this.m_6596_();
            }

            if (this.hasSource) {
               if (this.f_58857_.m_46467_() % 100L == 0L) {
                  this.updateInventories();
               }

               if (!this.f_58857_.m_5776_() && this.f_58857_.m_46467_() % 20L == 0L && this.craftManager.isCraftCompleted()) {
                  this.rotateCraft();
               }
            }
         }
      }
   }

   @Override
   public void onFinishedConnectionLast(@Nullable BlockPos storedPos, @Nullable LivingEntity storedEntity, Player playerEntity) {
      if (storedPos != null) {
         BlockEntity blockEntity = this.f_58857_.m_7702_(storedPos);
         if (blockEntity != null) {
            IItemHandler itemHandler = (IItemHandler)blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null);
            if (itemHandler != null) {
               storedPos = storedPos.m_7949_();
               if (!this.boundedInvs.contains(storedPos)) {
                  this.boundedInvs.add(storedPos);
                  PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.wixie_cauldron.bound"));
               } else {
                  this.boundedInvs.remove(storedPos);
                  PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.wixie_cauldron.removed"));
               }

               this.updateBlock();
            }
         }
      }
   }

   @Override
   public void onWanded(Player playerEntity) {
      if (!this.boundedInvs.isEmpty()) {
         this.boundedInvs = new ArrayList<>();
         this.setStack = ItemStack.f_41583_;
         PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.wixie_cauldron.cleared"));
         this.updateBlock();
      }
   }

   @Override
   public List<ColorPos> getWandHighlight(List<ColorPos> list) {
      for (BlockPos blockPos : this.boundedInvs) {
         list.add(ColorPos.centered(blockPos, ParticleColor.FROM_HIGHLIGHT));
      }

      return list;
   }

   public void rotateCraft() {
      BlockPos leftBound = this.f_58858_.m_7495_().m_122019_().m_122029_();
      BlockPos rightBound = this.f_58858_.m_7494_().m_122012_().m_122024_();
      List<ItemStack> itemStacks = new ArrayList<>();
      if (this.setStack != null && !this.setStack.m_41619_()) {
         itemStacks.add(this.setStack);
      }

      for (BlockPos pos : BlockPos.m_121940_(leftBound, rightBound)) {
         BlockEntity instructions = this.f_58857_.m_7702_(pos);
         if (instructions instanceof ArcanePedestalTile) {
            ArcanePedestalTile pedestalTile = (ArcanePedestalTile)instructions;
            if (!pedestalTile.getStack().m_41619_() && !pedestalTile.hasSignal) {
               itemStacks.add(pedestalTile.getStack().m_41777_());
            }
         }
      }

      if (!itemStacks.isEmpty()) {
         if (this.craftingIndex >= itemStacks.size()) {
            this.craftingIndex = 0;
         }

         ItemStack nextStack = itemStacks.get(this.craftingIndex);
         MultiRecipeWrapper recipeWrapper = this.getRecipesForStack(nextStack);
         this.craftingIndex++;
         if (recipeWrapper != null && !recipeWrapper.isEmpty()) {
            Map<Item, Integer> count = this.getInventoryCount();
            IRecipeWrapper.InstructionsForRecipe instructions = recipeWrapper.canCraft(count, this.f_58857_, this.f_58858_);
            if (instructions != null) {
               if (!recipeWrapper.isEmpty() && instructions.recipe().recipeIngredients.get(0) instanceof PotionIngredient potionIngred) {
                  Ingredient itemIngred = instructions.recipe().recipeIngredients.get(1);
                  List<ItemStack> needed = new ArrayList<>(Arrays.asList(itemIngred.m_43908_()));
                  Potion potionNeeded = PotionUtils.m_43579_(potionIngred.getStack());
                  Potion potionOutput = PotionUtils.m_43579_(instructions.recipe().outputStack);
                  boolean foundInput = potionNeeded == Potions.f_43599_ || findNeededPotion(potionNeeded, 300, this.f_58857_, this.f_58858_) != null;
                  boolean foundRoomForOutput = findPotionStorage(this.f_58857_, this.f_58858_, potionOutput) != null;
                  if (!foundRoomForOutput || !foundInput) {
                     return;
                  }

                  this.craftManager = new PotionCraftingManager(potionNeeded, needed, potionOutput);
                  this.f_58857_.m_7260_(this.f_58858_, this.f_58857_.m_8055_(this.f_58858_), this.f_58857_.m_8055_(this.f_58858_), 3);
               } else {
                  this.craftManager = new CraftingManager(instructions.recipe().outputStack.m_41777_(), instructions.itemsNeeded());
                  this.f_58857_.m_7260_(this.f_58858_, this.f_58857_.m_8055_(this.f_58858_), this.f_58857_.m_8055_(this.f_58858_), 3);
               }

               this.stackBeingCrafted = nextStack.m_41777_();
               this.updateBlock();
            }
         }
      }
   }

   public boolean hasWixie() {
      return !this.converted || this.f_58857_.m_6815_(this.entityID) != null;
   }

   public boolean isCraftingDone() {
      return this.craftManager.canBeCompleted();
   }

   public boolean needsPotion() {
      if (this.craftManager instanceof PotionCraftingManager potionCraftingManager && potionCraftingManager.needsPotion()) {
         return true;
      }

      return false;
   }

   public Potion getNeededPotion() {
      return this.craftManager instanceof PotionCraftingManager potionCraftingManager ? potionCraftingManager.getPotionNeeded() : null;
   }

   public void givePotion() {
      if (this.craftManager instanceof PotionCraftingManager potionCraftingManager) {
         potionCraftingManager.setObtainedPotion(true);
         this.f_58857_.m_7260_(this.f_58858_, this.f_58857_.m_8055_(this.f_58858_), this.f_58857_.m_8055_(this.f_58858_), 3);
      }
   }

   public boolean giveItem(ItemStack stack) {
      boolean res = this.craftManager.giveItem(stack.m_41720_());
      this.f_58857_.m_7260_(this.f_58858_, this.f_58857_.m_8055_(this.f_58858_), this.f_58857_.m_8055_(this.f_58858_), 3);
      return res;
   }

   public void attemptFinish() {
      if (this.craftManager.canBeCompleted() && !this.craftManager.isCraftCompleted()) {
         this.craftManager.completeCraft(this);
         this.craftCooldown = 1;
         this.stackBeingCrafted = ItemStack.f_41583_;
         this.updateBlock();
      }
   }

   public MultiRecipeWrapper getRecipesForStack(ItemStack stack) {
      return MultiRecipeWrapper.fromStack(stack, this.f_58857_);
   }

   public void updateInventories() {
      if (this.boundedInvs.isEmpty()) {
         this.cachedInventories = new ArrayList<>();

         for (BlockPos bPos : BlockPos.m_121940_(this.f_58858_.m_122013_(6).m_122030_(6).m_6625_(2), this.f_58858_.m_122020_(6).m_122025_(6).m_6630_(2))) {
            if (this.f_58857_.m_46749_(bPos)) {
               BlockEntity blockEntity = this.f_58857_.m_7702_(bPos);
               if (blockEntity != null && !(blockEntity instanceof ArcanePedestalTile) && blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent()
                  )
                {
                  this.cachedInventories.add(bPos.m_7949_());
               }
            }
         }

         this.m_6596_();
      }
   }

   @javax.annotation.Nullable
   public static BlockPos findPotionStorage(Level level, BlockPos worldPosition, Potion passedPot) {
      for (BlockPos bPos : BlockPos.m_121925_(worldPosition.m_6625_(2), 4, 3, 4)) {
         if (level.m_7702_(bPos) instanceof PotionJarTile tile && tile.canAccept(new PotionData(passedPot), 300)) {
            return bPos.m_7949_();
         }
      }

      return null;
   }

   @javax.annotation.Nullable
   public static BlockPos findNeededPotion(Potion passedPot, int amount, Level level, BlockPos worldPosition) {
      for (BlockPos bPos : BlockPos.m_121925_(worldPosition.m_6625_(2), 4, 3, 4)) {
         if (level.m_7702_(bPos) instanceof PotionJarTile tile && tile.getAmount() >= amount && tile.getData().areSameEffects(new PotionData(passedPot))) {
            return bPos.m_7949_();
         }
      }

      return null;
   }

   @Override
   public void convertedEffect() {
      super.convertedEffect();
      if (this.tickCounter >= 120 && !this.f_58857_.f_46443_) {
         this.converted = true;
         this.f_58857_
            .m_46597_(
               this.f_58858_,
               (BlockState)((BlockState)this.f_58857_.m_8055_(this.f_58858_).m_61124_(WixieCauldron.FILLED, false)).m_61124_(SummoningTile.CONVERTED, true)
            );
         EntityWixie wixie = new EntityWixie(this.f_58857_, this.f_58858_);
         wixie.m_6034_((double)this.f_58858_.m_123341_() + 0.5, (double)this.f_58858_.m_123342_() + 1.0, (double)this.f_58858_.m_123343_() + 0.5);
         this.f_58857_.m_7967_(wixie);
         ParticleUtil.spawnPoof((ServerLevel)this.f_58857_, this.f_58858_.m_7494_());
         this.entityID = wixie.m_19879_();
         this.tickCounter = 0;
         this.m_6596_();
      } else {
         if (this.tickCounter % 10 == 0 && !this.f_58857_.f_46443_) {
            RandomSource r = this.f_58857_.f_46441_;
            int min = -2;
            int max = 2;
            EntityFollowProjectile proj1 = new EntityFollowProjectile(
               this.f_58857_,
               this.f_58858_.m_7918_(r.m_188503_(max - min) + min, 3, r.m_188503_(max - min) + min),
               this.f_58858_,
               r.m_188503_(255),
               r.m_188503_(255),
               r.m_188503_(255)
            );
            this.f_58857_.m_7967_(proj1);
         }
      }
   }

   private Map<Item, Integer> getInventoryCount() {
      List<BlockPos> stale = new ArrayList<>();
      Map<Item, Integer> itemsAvailable = new HashMap<>();
      if (this.cachedInventories == null && this.boundedInvs.isEmpty()) {
         return itemsAvailable;
      } else {
         for (BlockPos p : this.getInventories()) {
            BlockEntity blockEntity = this.f_58857_.m_7702_(p);
            if (blockEntity == null) {
               stale.add(p);
            } else {
               IItemHandler handler = (IItemHandler)blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null);
               if (handler == null) {
                  stale.add(p);
               } else {
                  for (int i = 0; i < handler.getSlots(); i++) {
                     ItemStack stack = handler.getStackInSlot(i);
                     if (stack == null) {
                        System.out.println("======");
                        System.out.println("A MOD IS RETURNING A NULL STACK. THIS IS NOT ALLOWED YOU NERD. TELL THIS MOD AUTHOR TO FIX IT");
                        System.out.println(blockEntity.toString());
                        System.out.println("AT POS " + p.toString());
                     } else if (!itemsAvailable.containsKey(stack.m_41720_())) {
                        itemsAvailable.put(stack.m_41720_(), stack.m_41613_());
                     } else {
                        itemsAvailable.put(stack.m_41720_(), itemsAvailable.get(stack.m_41720_()) + stack.m_41613_());
                     }
                  }
               }
            }
         }

         if (this.boundedInvs.isEmpty()) {
            for (BlockPos px : stale) {
               this.cachedInventories.remove(px);
            }
         }

         return itemsAvailable;
      }
   }

   @Override
   public void m_142466_(CompoundTag compound) {
      super.m_142466_(compound);
      this.setStack = ItemStack.f_41583_;
      this.stackBeingCrafted = ItemStack.f_41583_;
      if (compound.m_128441_("crafting")) {
         this.setStack = ItemStack.m_41712_(compound.m_128469_("crafting"));
      }

      if (compound.m_128441_("currentCraft")) {
         this.stackBeingCrafted = ItemStack.m_41712_(compound.m_128469_("currentCraft"));
      }

      this.craftManager = CraftingManager.fromTag(compound);
      this.entityID = compound.m_128451_("entityid");
      this.hasSource = compound.m_128471_("hasmana");
      this.isCraftingPotion = compound.m_128471_("isPotion");
      this.needsPotionStorage = compound.m_128471_("storage");
      this.craftingIndex = compound.m_128451_("craftingIndex");
      this.boundedInvs = new ArrayList<>();
      if (compound.m_128441_("boundedInvs")) {
         ListTag list = compound.m_128437_("boundedInvs", 10);

         for (int i = 0; i < list.size(); i++) {
            CompoundTag tag = list.m_128728_(i);
            BlockPos pos = new BlockPos(tag.m_128451_("x"), tag.m_128451_("y"), tag.m_128451_("z"));
            this.boundedInvs.add(pos);
         }
      }

      this.craftCooldown = compound.m_128451_("craftCooldown");
   }

   @Override
   public void m_183515_(CompoundTag compound) {
      super.m_183515_(compound);
      if (this.setStack != null) {
         CompoundTag itemTag = new CompoundTag();
         this.setStack.m_41739_(itemTag);
         compound.m_128365_("crafting", itemTag);
      }

      if (this.stackBeingCrafted != null) {
         CompoundTag itemTag = new CompoundTag();
         this.stackBeingCrafted.m_41739_(itemTag);
         compound.m_128365_("currentCraft", itemTag);
      }

      if (this.craftManager != null) {
         this.craftManager.write(compound);
      }

      compound.m_128405_("entityid", this.entityID);
      compound.m_128379_("hasmana", this.hasSource);
      compound.m_128379_("isPotion", this.isCraftingPotion);
      compound.m_128379_("storage", this.needsPotionStorage);
      compound.m_128405_("craftingIndex", this.craftingIndex);
      ListTag boundedList = new ListTag();

      for (BlockPos pos : this.boundedInvs) {
         CompoundTag tag = new CompoundTag();
         tag.m_128405_("x", pos.m_123341_());
         tag.m_128405_("y", pos.m_123342_());
         tag.m_128405_("z", pos.m_123343_());
         boundedList.add(tag);
      }

      compound.m_128365_("boundedInvs", boundedList);
      compound.m_128405_("craftCooldown", this.craftCooldown);
   }

   @Override
   public void getTooltip(List<Component> tooltip) {
      if (this.craftCooldown <= 0) {
         if (this.stackBeingCrafted != null && !this.stackBeingCrafted.m_41619_()) {
            if (this.isOff) {
               tooltip.add(Component.m_237115_("ars_nouveau.tooltip.turned_off"));
            }

            if (!this.boundedInvs.isEmpty()) {
               tooltip.add(Component.m_237110_("ars_nouveau.cauldron.num_bounded", new Object[]{this.boundedInvs.size()}));
            }

            if (this.craftManager != null && !(this.craftManager instanceof PotionCraftingManager)) {
               tooltip.add(
                  Component.m_237113_(
                     Component.m_237115_("ars_nouveau.wixie.crafting").getString() + Component.m_237115_(this.stackBeingCrafted.m_41778_()).getString()
                  )
               );
               if (this.stackBeingCrafted.m_41720_() == Items.f_42589_) {
                  PotionUtils.m_43555_(this.stackBeingCrafted, tooltip, 1.0F);
               }
            } else if (this.craftManager instanceof PotionCraftingManager potionCraftingManager) {
               ItemStack potionStack = new ItemStack(Items.f_42589_);
               PotionUtils.m_43549_(potionStack, potionCraftingManager.potionOut);
               tooltip.add(Component.m_237113_(Component.m_237115_("ars_nouveau.wixie.crafting").getString() + potionStack.m_41786_().getString()));
               PotionUtils.m_43555_(potionStack, tooltip, 1.0F);
            }

            if (!this.hasSource) {
               tooltip.add(Component.m_237115_("ars_nouveau.wixie.need_mana").m_130940_(ChatFormatting.GOLD));
            }

            if (this.craftManager != null && !this.craftManager.neededItems.isEmpty()) {
               ItemStack neededStack = this.craftManager.neededItems.get(0);
               tooltip.add(
                  Component.m_237113_(Component.m_237115_("ars_nouveau.wixie.needs").getString() + Component.m_237115_(neededStack.m_41778_()).getString())
                     .m_130940_(ChatFormatting.GOLD)
               );
               if (neededStack.m_41720_() == Items.f_42589_) {
                  PotionUtils.m_43555_(neededStack, tooltip, 1.0F);
               }
            }

            if (this.craftManager instanceof PotionCraftingManager potionCraftingManager && potionCraftingManager.needsPotion()) {
               ItemStack potionStack = new ItemStack(Items.f_42589_);
               PotionUtils.m_43549_(potionStack, potionCraftingManager.getPotionNeeded());
               tooltip.add(
                  Component.m_237113_(Component.m_237115_("ars_nouveau.wixie.needs").getString() + potionStack.m_41786_().getString())
                     .m_130940_(ChatFormatting.GOLD)
               );
            }

            if (this.needsPotionStorage) {
               tooltip.add(Component.m_237115_("ars_nouveau.wixie.needs_storage").m_130940_(ChatFormatting.GOLD));
            }
         } else {
            tooltip.add(Component.m_237115_("ars_nouveau.no_stack_crafting").m_130940_(ChatFormatting.GOLD));
         }
      }
   }

   public void setSetStack(ItemStack setStack) {
      this.setStack = setStack;
      this.updateBlock();
   }

   public List<BlockPos> getInventories() {
      return this.boundedInvs.isEmpty() ? this.cachedInventories : this.boundedInvs;
   }

   public boolean needsPotionStorage() {
      return this.needsPotionStorage;
   }

   public void setNeedsPotionStorage(boolean needsPotionStorage) {
      this.needsPotionStorage = needsPotionStorage;
      this.updateBlock();
   }
}
