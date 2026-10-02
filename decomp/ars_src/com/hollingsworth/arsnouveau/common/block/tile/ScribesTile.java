package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.client.ITooltipProvider;
import com.hollingsworth.arsnouveau.api.item.IWandable;
import com.hollingsworth.arsnouveau.api.util.NBTUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.block.ITickable;
import com.hollingsworth.arsnouveau.common.block.ScribesBlock;
import com.hollingsworth.arsnouveau.common.block.ThreePartBlock;
import com.hollingsworth.arsnouveau.common.crafting.recipes.GlyphRecipe;
import com.hollingsworth.arsnouveau.common.entity.EntityFlyingItem;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketOneShotAnimation;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.PlayState;
import software.bernie.ars_nouveau.geckolib3.core.builder.AnimationBuilder;
import software.bernie.ars_nouveau.geckolib3.core.controller.AnimationController;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class ScribesTile extends ModdedTile implements IAnimatable, ITickable, Container, ITooltipProvider, IAnimationListener, IWandable {
   private final LazyOptional<IItemHandler> itemHandler = LazyOptional.of(() -> new InvWrapper(this));
   private ItemStack stack = ItemStack.f_41583_;
   boolean synced;
   public List<ItemStack> consumedStacks = new ArrayList<>();
   public GlyphRecipe recipe;
   ResourceLocation recipeID;
   public boolean crafting;
   public int craftingTicks;
   public boolean autoYoink = true;
   AnimationFactory factory = GeckoLibUtil.createFactory(this);
   AnimationController<ScribesTile> controller;

   public ScribesTile(BlockPos pos, BlockState state) {
      super(BlockRegistry.SCRIBES_TABLE_TILE, pos, state);
   }

   @Override
   public void tick() {
      if (this.m_58900_().m_61143_(ScribesBlock.PART) == ThreePartBlock.HEAD) {
         if (!this.f_58857_.f_46443_ && !this.synced) {
            this.updateBlock();
            this.synced = true;
         }

         if (this.craftingTicks > 0) {
            this.craftingTicks--;
         }

         if (this.recipeID != null && this.recipeID.equals(new ResourceLocation(""))) {
            this.recipe = null;
         }

         if (this.recipeID != null && !this.recipeID.toString().isEmpty() && (this.recipe == null || !this.recipe.id.equals(this.recipeID))) {
            this.recipe = (GlyphRecipe)this.f_58857_.m_7465_().m_44043_(this.recipeID).orElse(null);
            this.m_6596_();
         }

         if (!this.f_58857_.f_46443_ && this.f_58857_.m_46467_() % 5L == 0L && this.recipe != null) {
            boolean foundStack = false;

            for (ItemEntity e : this.f_58857_.m_45976_(ItemEntity.class, new AABB(this.m_58899_()).m_82400_(2.0))) {
               if (this.canConsumeItemstack(e.m_32055_())) {
                  ItemStack copyStack = e.m_32055_().m_41777_();
                  copyStack.m_41764_(1);
                  this.consumedStacks.add(copyStack);
                  e.m_32055_().m_41774_(1);
                  ParticleUtil.spawnTouchPacket(this.f_58857_, e.m_20097_(), ParticleUtil.defaultParticleColorWrapper());
                  this.updateBlock();
                  foundStack = true;
                  break;
               }
            }

            if (!foundStack && this.f_58857_.m_46467_() % 20L == 0L && this.autoYoink) {
               this.takeNearby();
            }

            if (this.getRemainingRequired().isEmpty() && !this.crafting) {
               this.crafting = true;
               this.craftingTicks = 120;
               Networking.sendToNearby(this.f_58857_, this.m_58899_(), new PacketOneShotAnimation(this.m_58899_(), 0));
               this.updateBlock();
            }
         }

         if (this.f_58857_.f_46443_ && this.craftingTicks == 0 && this.crafting) {
            this.crafting = false;
            this.m_6596_();
         }

         if (!this.f_58857_.f_46443_ && this.crafting && this.craftingTicks == 0 && this.recipe != null) {
            this.f_58857_.m_7967_(new ItemEntity(this.f_58857_, this.getX() + 0.5, this.getY() + 1.1, this.getZ() + 0.5, this.recipe.output.m_41777_()));
            this.recipe = null;
            this.recipeID = new ResourceLocation("");
            this.crafting = false;
            this.consumedStacks = new ArrayList<>();
            this.updateBlock();
         }
      }
   }

   public void takeNearby() {
      for (BlockPos bPos : BlockPos.m_121940_(this.f_58858_.m_122013_(6).m_122030_(6).m_6625_(2), this.f_58858_.m_122020_(6).m_122025_(6).m_6630_(2))) {
         if (this.f_58857_.m_7702_(bPos) != null && this.f_58857_.m_7702_(bPos).getCapability(ForgeCapabilities.ITEM_HANDLER, null).isPresent()) {
            IItemHandler handler = (IItemHandler)this.f_58857_.m_7702_(bPos).getCapability(ForgeCapabilities.ITEM_HANDLER, null).orElse(null);
            if (handler != null) {
               for (int i = 0; i < handler.getSlots(); i++) {
                  ItemStack stack = handler.getStackInSlot(i);
                  if (this.canConsumeItemstack(stack)) {
                     ItemStack stack1 = handler.extractItem(i, 1, false);
                     stack1.m_41777_().m_41764_(1);
                     this.consumedStacks.add(stack1);
                     EntityFlyingItem flyingItem = new EntityFlyingItem(this.f_58857_, bPos, this.m_58899_());
                     flyingItem.setStack(stack1);
                     this.f_58857_.m_7967_(flyingItem);
                     this.updateBlock();
                     return;
                  }
               }
            }
         }
      }
   }

   public boolean consumeStack(ItemStack stack) {
      if (!this.canConsumeItemstack(stack)) {
         return false;
      } else {
         ItemStack copyStack = stack.m_41620_(1);
         this.consumedStacks.add(copyStack);
         ParticleUtil.spawnTouchPacket(this.f_58857_, this.m_58899_().m_7494_(), ParticleUtil.defaultParticleColorWrapper());
         this.updateBlock();
         return true;
      }
   }

   public void refundConsumed() {
      for (ItemStack i : this.consumedStacks) {
         ItemEntity entity = new ItemEntity(this.f_58857_, this.getX(), this.getY(), this.getZ(), i);
         this.f_58857_.m_7967_(entity);
         this.consumedStacks = new ArrayList<>();
      }

      if (this.recipe != null) {
         int exp = this.recipe.exp;
         if (this.f_58857_ instanceof ServerLevel serverLevel) {
            ExperienceOrb.m_147082_(serverLevel, new Vec3(this.getX(), this.getY(), this.getZ()), exp);
         }
      }

      this.recipe = null;
      this.recipeID = null;
      this.craftingTicks = 0;
      this.crafting = false;
      this.updateBlock();
   }

   public void setRecipe(GlyphRecipe recipe, Player player) {
      if (getTotalPlayerExperience(player) < recipe.exp && !player.m_7500_()) {
         PortUtil.sendMessage(player, Component.m_237115_("ars_nouveau.not_enough_exp").m_130940_(ChatFormatting.GOLD));
      } else {
         if (!player.m_7500_()) {
            player.m_6756_(-recipe.exp);
         }

         ScribesTile tile = this.getLogicTile();
         if (tile != null) {
            tile.refundConsumed();
            tile.recipe = recipe;
            tile.recipeID = recipe.m_6423_();
            PortUtil.sendMessage(player, Component.m_237115_("ars_nouveau.scribes_table.started_crafting").m_130940_(ChatFormatting.GOLD));
            tile.updateBlock();
         }
      }
   }

   public static int getTotalPlayerExperience(Player player) {
      return (int)((float)getExperienceForLevel(player.f_36078_) + player.f_36080_ * (float)player.m_36323_());
   }

   public static int getLevelsFromExp(int exp) {
      if (exp <= 352) {
         return (int)(Math.sqrt((double)(exp + 9)) - 3.0);
      } else {
         return exp <= 1507 ? (int)(8.1 + Math.sqrt(0.4 * ((double)exp - 195.975))) : (int)(18.056 + Math.sqrt(0.222 * ((double)exp - 752.986)));
      }
   }

   public static int getExperienceForLevel(int level) {
      if (level == 0) {
         return 0;
      } else if (level > 0 && level < 17) {
         return (int)(Math.pow((double)level, 2.0) + (double)(6 * level));
      } else {
         return level > 16 && level < 32
            ? (int)(2.5 * Math.pow((double)level, 2.0) - 40.5 * (double)level + 360.0)
            : (int)(4.5 * Math.pow((double)level, 2.0) - 162.5 * (double)level + 2220.0);
      }
   }

   @Nullable
   public ScribesTile getLogicTile() {
      ScribesTile tile = this;
      if (!this.isMasterTile()) {
         BlockEntity tileEntity = this.f_58857_.m_7702_(this.m_58899_().m_121945_(ScribesBlock.getConnectedDirection(this.m_58900_())));
         tile = tileEntity instanceof ScribesTile ? (ScribesTile)tileEntity : null;
      }

      return tile;
   }

   public boolean isMasterTile() {
      return this.m_58900_().m_61143_(ScribesBlock.PART) == ThreePartBlock.HEAD;
   }

   public boolean canConsumeItemstack(ItemStack stack) {
      return this.recipe == null ? false : this.getRemainingRequired().stream().anyMatch(i -> i.test(stack));
   }

   public List<Ingredient> getRemainingRequired() {
      if (this.consumedStacks.isEmpty()) {
         return this.recipe.inputs;
      } else {
         List<Ingredient> unaccountedIngredients = new ArrayList<>();
         List<ItemStack> remainingItems = new ArrayList<>();

         for (ItemStack stack : this.consumedStacks) {
            remainingItems.add(stack.m_41777_());
         }

         for (Ingredient ingred : this.recipe.inputs) {
            ItemStack matchingStack = null;

            for (ItemStack item : remainingItems) {
               if (ingred.test(item)) {
                  matchingStack = item;
                  break;
               }
            }

            if (matchingStack != null) {
               remainingItems.remove(matchingStack);
            } else {
               unaccountedIngredients.add(ingred);
            }
         }

         return unaccountedIngredients;
      }
   }

   @Override
   public void onWanded(Player playerEntity) {
      this.autoYoink = !this.autoYoink;
      this.updateBlock();
   }

   public void m_142466_(CompoundTag compound) {
      super.m_142466_(compound);
      this.stack = ItemStack.m_41712_((CompoundTag)compound.m_128423_("itemStack"));
      if (compound.m_128441_("recipe")) {
         this.recipeID = new ResourceLocation(compound.m_128461_("recipe"));
      }

      CompoundTag itemsTag = new CompoundTag();
      itemsTag.m_128405_("numStacks", this.consumedStacks.size());
      this.consumedStacks = NBTUtil.readItems(compound, "consumed");
      this.craftingTicks = compound.m_128451_("craftingTicks");
      this.crafting = compound.m_128471_("crafting");
      this.autoYoink = !compound.m_128441_("autoYoink") || compound.m_128471_("autoYoink");
   }

   @Override
   public void m_183515_(CompoundTag compound) {
      if (this.stack != null) {
         CompoundTag reagentTag = new CompoundTag();
         this.stack.m_41739_(reagentTag);
         compound.m_128365_("itemStack", reagentTag);
      }

      if (this.recipe != null) {
         compound.m_128359_("recipe", this.recipe.m_6423_().toString());
      } else {
         compound.m_128359_("recipe", "");
      }

      NBTUtil.writeItems(compound, "consumed", this.consumedStacks);
      compound.m_128405_("craftingTicks", this.craftingTicks);
      compound.m_128379_("crafting", this.crafting);
      compound.m_128379_("autoYoink", this.autoYoink);
   }

   private <E extends BlockEntity & IAnimatable> PlayState idlePredicate(AnimationEvent<E> event) {
      return PlayState.CONTINUE;
   }

   @Override
   public void startAnimation(int arg) {
      if (this.controller != null) {
         this.controller.markNeedsReload();
         this.controller.setAnimation(new AnimationBuilder().addAnimation("create_glyph"));
      }
   }

   @Override
   public void registerControllers(AnimationData data) {
      this.controller = new AnimationController<>(this, "controller", 1.0F, this::idlePredicate);
      data.addAnimationController(this.controller);
   }

   public AABB getRenderBoundingBox() {
      return super.getRenderBoundingBox().m_82400_(2.0);
   }

   @Override
   public AnimationFactory getFactory() {
      return this.factory;
   }

   public int m_6643_() {
      return 1;
   }

   public boolean m_7983_() {
      return this.stack == null || this.stack.m_41619_();
   }

   public ItemStack m_8020_(int pIndex) {
      return this.stack;
   }

   public ItemStack m_7407_(int pIndex, int pCount) {
      ItemStack removed = this.stack.m_41777_().m_41620_(pCount);
      this.stack.m_41774_(pCount);
      this.updateBlock();
      return removed;
   }

   public ItemStack m_8016_(int pIndex) {
      ItemStack stack = this.stack.m_41777_();
      this.stack = ItemStack.f_41583_;
      this.updateBlock();
      return stack;
   }

   public void m_6836_(int pIndex, ItemStack pStack) {
      this.stack = pStack;
      this.m_6596_();
   }

   public boolean m_6542_(Player pPlayer) {
      return true;
   }

   public void m_6211_() {
      this.stack = ItemStack.f_41583_;
      this.updateBlock();
   }

   @NotNull
   public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
      return cap == ForgeCapabilities.ITEM_HANDLER ? this.itemHandler.cast() : super.getCapability(cap, side);
   }

   public void invalidateCaps() {
      this.itemHandler.invalidate();
      super.invalidateCaps();
   }

   @Override
   public void getTooltip(List<Component> tooltip) {
      if (!this.isMasterTile()) {
         ScribesTile tile = this.getLogicTile();
         if (tile != null) {
            tile.getTooltip(tooltip);
         }
      } else {
         if (this.recipe != null) {
            tooltip.add(Component.m_237110_("ars_nouveau.crafting", new Object[]{this.recipe.output.m_41786_()}));
            tooltip.add(Component.m_237115_("ars_nouveau.scribes_table.throw_items").m_130940_(ChatFormatting.GOLD));
         }

         if (!this.autoYoink) {
            tooltip.add(Component.m_237115_("ars_nouveau.scribes_table.auto_take_disabled").m_130940_(ChatFormatting.GOLD));
         }
      }
   }

   public ItemStack getStack() {
      return this.stack;
   }

   public void setStack(ItemStack stack) {
      this.stack = stack;
      this.updateBlock();
   }
}
