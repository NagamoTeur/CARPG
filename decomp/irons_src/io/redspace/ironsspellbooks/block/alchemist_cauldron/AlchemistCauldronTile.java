package io.redspace.ironsspellbooks.block.alchemist_cauldron;

import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.spells.SpellData;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.config.ServerConfigs;
import io.redspace.ironsspellbooks.item.InkItem;
import io.redspace.ironsspellbooks.item.Scroll;
import io.redspace.ironsspellbooks.item.consumables.SimpleElixir;
import io.redspace.ironsspellbooks.registries.BlockRegistry;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import net.minecraft.Util;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.Nullable;

public class AlchemistCauldronTile extends BlockEntity implements WorldlyContainer {
   public static int MAX_LEVELS = 4;
   public static final Object2ObjectOpenHashMap<Item, AlchemistCauldronInteraction> INTERACTIONS = newInteractionMap();
   public final NonNullList<ItemStack> inputItems = NonNullList.m_122780_(MAX_LEVELS, ItemStack.f_41583_);
   public final NonNullList<ItemStack> outputItems = NonNullList.m_122780_(MAX_LEVELS, ItemStack.f_41583_);
   private final int[] cooktimes = new int[MAX_LEVELS];

   public AlchemistCauldronTile(BlockPos pWorldPosition, BlockState pBlockState) {
      super((BlockEntityType)BlockRegistry.ALCHEMIST_CAULDRON_TILE.get(), pWorldPosition, pBlockState);
   }

   public static void serverTick(Level level, BlockPos pos, BlockState blockState, AlchemistCauldronTile cauldronTile) {
      for (int i = 0; i < cauldronTile.inputItems.size(); i++) {
         ItemStack itemStack = (ItemStack)cauldronTile.inputItems.get(i);
         if (!itemStack.m_41619_() && cauldronTile.isBoiling(blockState)) {
            cauldronTile.cooktimes[i]++;
         } else {
            cauldronTile.cooktimes[i] = 0;
         }

         if (cauldronTile.cooktimes[i] > 100) {
            cauldronTile.tryMeltInput(itemStack);
            cauldronTile.cooktimes[i] = 0;
         }
      }

      RandomSource random = Utils.random;
      if (cauldronTile.isBoiling(blockState)) {
         float waterLevel = Mth.m_14179_((float)cauldronTile.getLiquidLevel() / (float)MAX_LEVELS, 0.25F, 0.9F);
         MagicManager.spawnParticles(
            level,
            ParticleTypes.f_123772_,
            (double)((float)pos.m_123341_() + Mth.m_216283_(random, 0.2F, 0.8F)),
            (double)((float)pos.m_123342_() + waterLevel),
            (double)((float)pos.m_123343_() + Mth.m_216283_(random, 0.2F, 0.8F)),
            1,
            0.0,
            0.0,
            0.0,
            0.0,
            false
         );
      }
   }

   public InteractionResult handleUse(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand) {
      ItemStack itemStack = player.m_21120_(hand);
      if (level.m_7702_(pos) instanceof AlchemistCauldronTile tile) {
         ItemStack cauldronInteractionResult = ((AlchemistCauldronInteraction)INTERACTIONS.get(itemStack.m_41720_()))
            .interact(tile, blockState, level, pos, itemStack);
         if (cauldronInteractionResult != null) {
            player.m_21008_(hand, ItemUtils.m_41813_(itemStack, player, cauldronInteractionResult));
            this.m_6596_();
            return InteractionResult.m_19078_(level.f_46443_);
         }

         if (this.isValidInput(itemStack)) {
            if (!level.f_46443_) {
               for (int i = 0; i < this.inputItems.size(); i++) {
                  ItemStack stack = (ItemStack)this.inputItems.get(i);
                  if (stack.m_41619_()) {
                     ItemStack input = player.m_150110_().f_35937_ ? itemStack.m_41777_() : itemStack.m_41620_(1);
                     input.m_41764_(1);
                     this.inputItems.set(i, input);
                     player.m_21008_(hand, itemStack);
                     this.m_6596_();
                     break;
                  }
               }
            }

            return InteractionResult.m_19078_(level.f_46443_);
         }

         if ((itemStack.m_41619_() || player.m_6047_()) && hand.equals(InteractionHand.MAIN_HAND)) {
            for (ItemStack item : this.inputItems) {
               if (!item.m_41619_()) {
                  if (!level.f_46443_) {
                     ItemStack take = item.m_41620_(1);
                     if (player.m_21120_(hand).m_41619_()) {
                        player.m_21008_(hand, take);
                     } else if (!player.m_150109_().m_36054_(take)) {
                        player.m_36176_(take, false);
                     }

                     this.m_6596_();
                  }

                  return InteractionResult.m_19078_(level.f_46443_);
               }
            }
         }
      }

      return InteractionResult.PASS;
   }

   protected boolean isBaseIngredientPresent(ItemStack stack) {
      return this.isBaseIngredientPresent(stack2 -> CauldronPlatformHelper.itemMatches(stack, stack2), 1);
   }

   protected boolean isBaseIngredientPresent(Predicate<ItemStack> baseIngredientPredicate, int minCount) {
      int count = 0;

      for (ItemStack stack : this.outputItems) {
         if (baseIngredientPredicate.test(stack)) {
            count += stack.m_41613_();
            if (count >= minCount) {
               return true;
            }
         }
      }

      return false;
   }

   protected void convertOutput(Predicate<ItemStack> itemToReplace, ItemStack outputItem, int maxCount) {
      int count = 0;

      for (int i = this.outputItems.size() - 1; i >= 0; i--) {
         ItemStack stack = (ItemStack)this.outputItems.get(i);
         if (itemToReplace.test(stack)) {
            this.outputItems.set(i, outputItem.m_41777_());
            if (++count >= maxCount) {
               return;
            }
         }
      }
   }

   public boolean addToOutput(ItemStack itemStack) {
      for (int i = 0; i < this.outputItems.size(); i++) {
         ItemStack stack = (ItemStack)this.outputItems.get(i);
         if (stack.m_41619_()) {
            this.outputItems.set(i, itemStack);
            return true;
         }
      }

      return false;
   }

   public void tryMeltInput(ItemStack itemStack) {
      if (this.f_58857_ != null && !this.f_58857_.f_46443_) {
         boolean shouldMelt = false;
         boolean success = true;
         if (itemStack.m_150930_((Item)ItemRegistry.SCROLL.get()) && this.isBaseIngredientPresent(CauldronPlatformHelper.IS_WATER, 1)) {
            if ((double)Utils.random.m_188501_() < (Double)ServerConfigs.SCROLL_RECYCLE_CHANCE.get()) {
               ItemStack result = new ItemStack(getInkFromScroll(itemStack));
               this.convertOutput(CauldronPlatformHelper.IS_WATER, result, 1);
            } else {
               success = false;
            }

            shouldMelt = true;
         }

         if (!shouldMelt && this.isBrewable(itemStack)) {
            for (int i = 0; i < this.outputItems.size(); i++) {
               ItemStack potentialPotionBase = (ItemStack)this.outputItems.get(i);
               if (!potentialPotionBase.m_41619_()) {
                  ItemStack output = CauldronPlatformHelper.getNonDestructiveBrewingResult(potentialPotionBase, itemStack, this.f_58857_);
                  if (!output.m_41619_()) {
                     this.outputItems.set(i, output.m_41777_());
                     shouldMelt = true;
                  }
               }
            }
         }

         if (!shouldMelt && AlchemistCauldronRecipeRegistry.isValidIngredient(itemStack)) {
            for (int ix = 0; ix < this.outputItems.size(); ix++) {
               ItemStack potentialPotionBase = ((ItemStack)this.outputItems.get(ix)).m_41777_();
               if (!potentialPotionBase.m_41619_()) {
                  AlchemistCauldronRecipe recipe = AlchemistCauldronRecipeRegistry.getRecipeForInputs(potentialPotionBase, itemStack);
                  if (recipe != null
                     && this.isBaseIngredientPresent(stack -> CauldronPlatformHelper.itemMatches(stack, potentialPotionBase), recipe.getInput().m_41613_())) {
                     ItemStack result = recipe.getResult();
                     int toConsume = recipe.getInput().m_41613_();
                     this.convertOutput(stack -> CauldronPlatformHelper.itemMatches(stack, potentialPotionBase.m_41777_()), ItemStack.f_41583_, toConsume);
                     int c = result.m_41613_();

                     for (int j = 0; j < c; j++) {
                        this.addToOutput(result.m_41620_(1));
                     }

                     shouldMelt = true;
                     break;
                  }
               }
            }
         }

         if (shouldMelt) {
            itemStack.m_41774_(1);
            this.m_6596_();
            if (success) {
               this.f_58857_.m_5594_(null, this.m_58899_(), SoundEvents.f_11772_, SoundSource.MASTER, 1.0F, 1.0F);
               this.f_58857_.markAndNotifyBlock(this.m_58899_(), this.f_58857_.m_46745_(this.m_58899_()), this.m_58900_(), this.m_58900_(), 1, 1);
            } else {
               this.f_58857_.m_5594_(null, this.m_58899_(), SoundEvents.f_11914_, SoundSource.MASTER, 1.0F, 1.0F);
            }

            this.collapseContainer(this.outputItems);
         }
      }
   }

   public void collapseContainer(NonNullList<ItemStack> container) {
      for (int i = 0; i < container.size(); i++) {
         if (((ItemStack)container.get(i)).m_41619_()) {
            for (int j = i + 1; j < container.size(); j++) {
               ItemStack stack = (ItemStack)container.get(j);
               if (!stack.m_41619_()) {
                  container.set(i, stack);
                  container.set(j, ItemStack.f_41583_);
                  break;
               }
            }
         }
      }
   }

   public boolean isValidInput(ItemStack itemStack) {
      return itemStack.m_150930_((Item)ItemRegistry.SCROLL.get()) || this.isBrewable(itemStack) || AlchemistCauldronRecipeRegistry.isValidIngredient(itemStack);
   }

   public boolean isBrewable(ItemStack itemStack) {
      return (Boolean)ServerConfigs.ALLOW_CAULDRON_BREWING.get()
         && this.f_58857_ != null
         && CauldronPlatformHelper.isBrewingIngredient(itemStack, this.f_58857_);
   }

   public int getItemWaterColor(ItemStack itemStack) {
      if (this.m_58904_() == null) {
         return 0;
      } else if (itemStack.m_41720_() instanceof SimpleElixir simpleElixir) {
         return simpleElixir.getMobEffect().m_19544_().m_19484_();
      } else if (itemStack.m_150930_((Item)ItemRegistry.INK_COMMON.get())) {
         return 2236962;
      } else if (itemStack.m_150930_((Item)ItemRegistry.INK_UNCOMMON.get())) {
         return 1196800;
      } else if (itemStack.m_150930_((Item)ItemRegistry.INK_RARE.get())) {
         return 997444;
      } else if (itemStack.m_150930_((Item)ItemRegistry.INK_EPIC.get())) {
         return 10825376;
      } else if (itemStack.m_150930_((Item)ItemRegistry.INK_LEGENDARY.get())) {
         return 16559900;
      } else if (itemStack.m_150930_((Item)ItemRegistry.BLOOD_VIAL.get())) {
         return 5965590;
      } else {
         return PotionUtils.m_43579_(itemStack) != Potions.f_43598_ ? PotionUtils.m_43575_(itemStack) : BiomeColors.m_108811_(this.m_58904_(), this.m_58899_());
      }
   }

   public int getAverageWaterColor() {
      float f = 0.0F;
      float f1 = 0.0F;
      float f2 = 0.0F;
      int i = 0;

      for (ItemStack itemStack : this.outputItems) {
         if (!itemStack.m_41619_()) {
            int k = this.getItemWaterColor(itemStack);
            f += (float)(k >> 16 & 0xFF) / 255.0F;
            f1 += (float)(k >> 8 & 0xFF) / 255.0F;
            f2 += (float)(k >> 0 & 0xFF) / 255.0F;
            i++;
         }
      }

      f = f / (float)i * 255.0F;
      f1 = f1 / (float)i * 255.0F;
      f2 = f2 / (float)i * 255.0F;
      return (int)f << 16 | (int)f1 << 8 | (int)f2;
   }

   public static Item getInkFromScroll(ItemStack scrollStack) {
      if (scrollStack.m_41720_() instanceof Scroll scroll) {
         ISpellContainer spellContainer = ISpellContainer.get(scrollStack);
         SpellData spellData = spellContainer.getSpellAtIndex(0);
         SpellRarity rarity = spellData.getSpell().getRarity(spellData.getLevel());
         return InkItem.getInkForRarity(rarity);
      } else {
         return Items.f_41852_;
      }
   }

   public void m_6596_() {
      super.m_6596_();
      if (this.f_58857_ != null) {
         this.f_58857_.m_7260_(this.f_58858_, this.m_58900_(), this.m_58900_(), 2);
      }
   }

   public boolean m_6542_(Player pPlayer) {
      return false;
   }

   public void m_142466_(CompoundTag pTag) {
      Utils.loadAllItems(pTag, this.inputItems, "Items");
      Utils.loadAllItems(pTag, this.outputItems, "Results");
      super.m_142466_(pTag);
   }

   protected void m_183515_(@Nonnull CompoundTag tag) {
      Utils.saveAllItems(tag, this.inputItems, "Items");
      Utils.saveAllItems(tag, this.outputItems, "Results");
      super.m_183515_(tag);
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.m_195640_(this);
   }

   public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
      this.handleUpdateTag(pkt.m_131708_());
      if (this.f_58857_ != null) {
         this.f_58857_.m_7260_(this.f_58858_, this.m_58900_(), this.m_58900_(), 3);
      }
   }

   public CompoundTag m_5995_() {
      CompoundTag tag = new CompoundTag();
      this.m_183515_(tag);
      return tag;
   }

   public void handleUpdateTag(CompoundTag tag) {
      this.inputItems.clear();
      this.outputItems.clear();
      if (tag != null) {
         this.m_142466_(tag);
      }
   }

   public void drops() {
      SimpleContainer simpleContainer = new SimpleContainer(this.inputItems.size());

      for (int i = 0; i < this.inputItems.size(); i++) {
         simpleContainer.m_6836_(i, (ItemStack)this.inputItems.get(i));
      }

      if (this.f_58857_ != null) {
         Containers.m_19002_(this.f_58857_, this.f_58858_, simpleContainer);
      }
   }

   protected static ItemStack waterBottle() {
      ItemStack stack = new ItemStack(Items.f_42589_);
      PotionUtils.m_43549_(stack, Potions.f_43599_);
      return stack;
   }

   static Object2ObjectOpenHashMap<Item, AlchemistCauldronInteraction> newInteractionMap() {
      Object2ObjectOpenHashMap<Item, AlchemistCauldronInteraction> map = (Object2ObjectOpenHashMap<Item, AlchemistCauldronInteraction>)Util.m_137469_(
         new Object2ObjectOpenHashMap(), o2o -> o2o.defaultReturnValue((AlchemistCauldronInteraction)(tile, blockState, level, pos, itemstack) -> null)
      );
      map.put(Items.f_42447_, (AlchemistCauldronInteraction)(tile, blockState, level, pos, itemstack) -> {
         if (tile.outputItems.stream().anyMatch(ItemStack::m_41619_)) {
            level.m_5594_(null, pos, SoundEvents.f_11778_, SoundSource.BLOCKS, 1.0F, 1.0F);

            for (int i = 0; i < tile.outputItems.size(); i++) {
               if (((ItemStack)tile.outputItems.get(i)).m_41619_()) {
                  tile.outputItems.set(i, waterBottle());
               }
            }

            return new ItemStack(Items.f_42446_);
         } else {
            return null;
         }
      });
      map.put(Items.f_42446_, (AlchemistCauldronInteraction)(tile, blockState, level, pos, itemstack) -> {
         if (tile.outputItems.stream().allMatch(CauldronPlatformHelper.IS_WATER)) {
            tile.outputItems.clear();
            level.m_5594_(null, pos, SoundEvents.f_11781_, SoundSource.BLOCKS, 1.0F, 1.0F);
            return new ItemStack(Items.f_42447_);
         } else {
            return null;
         }
      });
      map.put(
         Items.f_42590_,
         (AlchemistCauldronInteraction)(tile, blockState, level, pos, itemstack) -> {
            for (int i = tile.outputItems.size() - 1; i >= 0; i--) {
               ItemStack stack = (ItemStack)tile.outputItems.get(i);
               if (!stack.m_41619_()) {
                  level.m_5594_(
                     null, pos, CauldronPlatformHelper.IS_WATER.test(stack) ? SoundEvents.f_11770_ : SoundEvents.f_11771_, SoundSource.BLOCKS, 1.0F, 1.0F
                  );
                  return stack.m_41620_(1);
               }
            }

            return null;
         }
      );
      createBottleEmptyInteraction(map, () -> Items.f_42589_);
      createBottleEmptyInteraction(map, ItemRegistry.INK_COMMON);
      createBottleEmptyInteraction(map, ItemRegistry.INK_UNCOMMON);
      createBottleEmptyInteraction(map, ItemRegistry.INK_RARE);
      createBottleEmptyInteraction(map, ItemRegistry.INK_EPIC);
      createBottleEmptyInteraction(map, ItemRegistry.INK_LEGENDARY);
      createBottleEmptyInteraction(map, ItemRegistry.BLOOD_VIAL);
      createBottleEmptyInteraction(map, ItemRegistry.OAKSKIN_ELIXIR);
      createBottleEmptyInteraction(map, ItemRegistry.GREATER_OAKSKIN_ELIXIR);
      createBottleEmptyInteraction(map, ItemRegistry.EVASION_ELIXIR);
      createBottleEmptyInteraction(map, ItemRegistry.GREATER_EVASION_ELIXIR);
      createBottleEmptyInteraction(map, ItemRegistry.INVISIBILITY_ELIXIR);
      createBottleEmptyInteraction(map, ItemRegistry.GREATER_INVISIBILITY_ELIXIR);
      createBottleEmptyInteraction(map, ItemRegistry.GREATER_HEALING_POTION);
      MinecraftForge.EVENT_BUS.post(new AlchemistCauldronBuildInteractionsEvent(map));
      return map;
   }

   protected static void createBottleEmptyInteraction(Object2ObjectOpenHashMap<Item, AlchemistCauldronInteraction> map, Supplier<Item> item) {
      map.put(item.get(), (AlchemistCauldronInteraction)(tile, blockState, level, pos, itemstack) -> {
         for (int i = 0; i < tile.outputItems.size(); i++) {
            ItemStack stack = (ItemStack)tile.outputItems.get(i);
            if (stack.m_41619_()) {
               ItemStack input = itemstack.m_41777_();
               input.m_41764_(1);
               tile.outputItems.set(i, input);
               level.m_5594_(null, pos, SoundEvents.f_11769_, SoundSource.BLOCKS, 1.0F, 1.0F);
               return new ItemStack(Items.f_42590_);
            }
         }

         return null;
      });
   }

   public int[] m_7071_(Direction pSide) {
      return new int[]{0, 1, 2, 3};
   }

   public boolean m_7155_(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
      return this.inputItems.stream().anyMatch(ItemStack::m_41619_) && this.isValidInput(pItemStack);
   }

   public boolean m_7157_(int pIndex, ItemStack pStack, Direction pDirection) {
      return false;
   }

   public void m_6211_() {
      this.inputItems.clear();
      this.outputItems.clear();
   }

   public int m_6643_() {
      return MAX_LEVELS;
   }

   public boolean m_7983_() {
      return this.inputItems.stream().allMatch(ItemStack::m_41619_);
   }

   public ItemStack m_8020_(int pSlot) {
      return ItemStack.f_41583_;
   }

   public ItemStack m_7407_(int pSlot, int pAmount) {
      return pSlot >= 0 && pSlot <= this.inputItems.size() ? (ItemStack)this.inputItems.remove(pSlot) : ItemStack.f_41583_;
   }

   public ItemStack m_8016_(int pSlot) {
      return pSlot >= 0 && pSlot <= this.inputItems.size() ? (ItemStack)this.inputItems.remove(pSlot) : ItemStack.f_41583_;
   }

   public void m_6836_(int pSlot, ItemStack pStack) {
      if (pSlot >= 0 && pSlot <= this.inputItems.size()) {
         this.inputItems.set(pSlot, pStack);
      }
   }

   public boolean isBoiling(BlockState blockState) {
      return AlchemistCauldronBlock.isLit(blockState) && this.getLiquidLevel() >= 1;
   }

   public int getLiquidLevel() {
      return this.outputItems.stream().filter(itemstack -> !itemstack.m_41619_()).toList().size();
   }
}
