package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.block.IPedestalMachine;
import com.hollingsworth.arsnouveau.api.client.ITooltipProvider;
import com.hollingsworth.arsnouveau.api.source.AbstractSourceMachine;
import com.hollingsworth.arsnouveau.api.source.ISpecialSourceProvider;
import com.hollingsworth.arsnouveau.api.util.SourceUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleLineData;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.block.ITickable;
import com.hollingsworth.arsnouveau.common.crafting.recipes.ImbuementRecipe;
import com.hollingsworth.arsnouveau.common.entity.EntityFlyingItem;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.RecipeRegistry;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
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

public class ImbuementTile extends AbstractSourceMachine implements Container, ITickable, IAnimatable, ITooltipProvider, IPedestalMachine {
   private final LazyOptional<IItemHandler> itemHandler = LazyOptional.of(() -> new InvWrapper(this));
   public ItemStack stack = ItemStack.f_41583_;
   public ItemEntity entity;
   public boolean draining;
   ImbuementRecipe recipe;
   int backoff;
   public float frames;
   boolean hasRecipe;
   int craftTicks;
   AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public ImbuementTile(BlockPos pos, BlockState state) {
      super(BlockRegistry.IMBUEMENT_TILE, pos, state);
   }

   @Override
   public int getTransferRate() {
      return 0;
   }

   @Override
   public void lightPedestal(Level level) {
      if (level != null) {
         for (BlockPos pos : this.pedestalList(this.m_58899_(), 1, level)) {
            ParticleUtil.spawnOrb(level, ParticleColor.makeRandomColor(255, 255, 255, level.f_46441_), pos.m_7494_(), 300);
         }
      }
   }

   @Override
   public void tick() {
      if (this.f_58857_ != null) {
         if (this.f_58857_.f_46443_) {
            int baseAge = this.draining ? 20 : 40;
            int randBound = this.draining ? 3 : 6;
            int numParticles = this.draining ? 2 : 1;
            float scaleAge = this.draining ? (float)ParticleUtil.inRange(0.1, 0.2) : (float)ParticleUtil.inRange(0.05, 0.15);
            if (this.f_58857_.f_46441_.m_188503_(randBound) == 0 && !Minecraft.m_91087_().m_91104_()) {
               for (int i = 0; i < numParticles; i++) {
                  Vec3 particlePos = new Vec3(this.getX(), this.getY(), this.getZ()).m_82520_(0.5, 0.5, 0.5);
                  particlePos = particlePos.m_82549_(ParticleUtil.pointInSphere());
                  this.f_58857_
                     .m_7106_(
                        ParticleLineData.createData(new ParticleColor(255, 25, 180), scaleAge, baseAge + this.f_58857_.f_46441_.m_188503_(20)),
                        particlePos.m_7096_(),
                        particlePos.m_7098_(),
                        particlePos.m_7094_(),
                        this.getX() + 0.5,
                        this.getY() + 0.5,
                        this.getZ() + 0.5
                     );
               }
            }

            if (!this.stack.m_41619_() && this.recipe == null) {
               this.recipe = this.getRecipeNow();
            }
         } else {
            this.hasRecipe = this.recipe != null;
            if (this.backoff > 0) {
               this.backoff--;
            } else if (!this.stack.m_41619_()) {
               if (this.craftTicks > 0) {
                  this.craftTicks--;
               }

               if (this.recipe == null) {
                  ImbuementRecipe foundRecipe = this.getRecipeNow();
                  if (foundRecipe != null) {
                     this.recipe = foundRecipe;
                     this.craftTicks = 100;
                  }
               }

               if (this.recipe != null && this.recipe.matches(this, this.f_58857_)) {
                  int transferRate = 200;
                  if (this.f_58857_.m_46467_() % 20L == 0L && this.getSource() < this.recipe.source) {
                     if (!this.canAcceptSource(Math.min(200, this.recipe.source))) {
                        return;
                     }

                     ISpecialSourceProvider takePos = SourceUtil.takeSource(this.f_58858_, this.f_58857_, 2, Math.min(200, this.recipe.source));
                     if (takePos != null) {
                        this.addSource(transferRate);
                        EntityFlyingItem item = new EntityFlyingItem(this.f_58857_, takePos.getCurrentPos().m_7494_(), this.f_58858_, 255, 50, 80)
                           .withNoTouch();
                        item.setDistanceAdjust(2.0F);
                        this.f_58857_.m_7967_(item);
                        if (!this.draining) {
                           this.draining = true;
                           this.updateBlock();
                        }
                     } else {
                        this.addSource(10);
                        if (this.draining) {
                           this.draining = false;
                           this.updateBlock();
                        }
                     }
                  }

                  if (this.getSource() >= this.recipe.source && this.craftTicks <= 0) {
                     this.m_6836_(0, this.recipe.output.m_41777_());
                     this.addSource(-this.recipe.source);
                     ParticleUtil.spawnTouchPacket(this.f_58857_, this.f_58858_, ParticleUtil.defaultParticleColorWrapper());
                     this.updateBlock();
                  }
               } else {
                  this.backoff = 20;
                  this.recipe = null;
                  if (this.draining) {
                     this.draining = false;
                     this.updateBlock();
                  }
               }
            }
         }
      }
   }

   @Override
   public void m_142466_(CompoundTag tag) {
      this.stack = ItemStack.m_41712_((CompoundTag)tag.m_128423_("itemStack"));
      this.draining = tag.m_128471_("draining");
      this.hasRecipe = tag.m_128471_("hasRecipe");
      this.craftTicks = tag.m_128451_("craftTicks");
      super.m_142466_(tag);
   }

   @Override
   public void m_183515_(CompoundTag tag) {
      super.m_183515_(tag);
      if (this.stack != null) {
         CompoundTag reagentTag = new CompoundTag();
         this.stack.m_41739_(reagentTag);
         tag.m_128365_("itemStack", reagentTag);
      }

      tag.m_128379_("draining", this.draining);
      tag.m_128379_("hasRecipe", this.hasRecipe);
      tag.m_128405_("craftTicks", this.craftTicks);
   }

   @Override
   public int getMaxSource() {
      return 10000000;
   }

   public int m_6643_() {
      return 1;
   }

   public int m_6893_() {
      return 1;
   }

   public boolean m_7013_(int slot, ItemStack stack) {
      if (!stack.m_41619_() && this.stack.m_41619_()) {
         this.stack = stack.m_41777_();
         ImbuementRecipe recipe = this.getRecipeNow();
         this.stack = ItemStack.f_41583_;
         return recipe != null;
      } else {
         return false;
      }
   }

   public boolean m_7983_() {
      return this.stack.m_41619_();
   }

   public ItemStack m_8020_(int index) {
      return this.stack;
   }

   public ItemStack m_7407_(int index, int count) {
      ItemStack split = this.stack.m_41620_(count);
      this.updateBlock();
      return split;
   }

   public ItemStack m_8016_(int index) {
      ItemStack stack = this.stack.m_41777_();
      this.stack = ItemStack.f_41583_;
      this.m_6596_();
      return stack;
   }

   public void m_6836_(int index, ItemStack stack) {
      this.stack = stack;
      this.craftTicks = 100;
      this.updateBlock();
   }

   public boolean m_6542_(Player player) {
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
   public void registerControllers(AnimationData data) {
      data.addAnimationController(new AnimationController<>(this, "controller", 1.0F, this::idlePredicate));
      data.addAnimationController(new AnimationController<>(this, "slowcraft_controller", 1.0F, this::slowCraftPredicate));
   }

   private PlayState slowCraftPredicate(AnimationEvent<?> animationEvent) {
      animationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("imbue_slow"));
      return PlayState.CONTINUE;
   }

   private PlayState idlePredicate(AnimationEvent<?> animationEvent) {
      animationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("float"));
      return PlayState.CONTINUE;
   }

   @Override
   public AnimationFactory getFactory() {
      return this.factory;
   }

   public List<ItemStack> getPedestalItems() {
      ArrayList<ItemStack> pedestalItems = new ArrayList<>();

      for (BlockPos p : this.getNearbyPedestals()) {
         BlockEntity var5 = this.f_58857_.m_7702_(p);
         if (var5 instanceof ArcanePedestalTile) {
            ArcanePedestalTile pedestalTile = (ArcanePedestalTile)var5;
            if (!pedestalTile.getStack().m_41619_()) {
               pedestalItems.add(pedestalTile.getStack());
            }
         }
      }

      return pedestalItems;
   }

   public List<BlockPos> getNearbyPedestals() {
      return this.pedestalList(this.m_58899_(), 1, this.m_58904_());
   }

   @Nullable
   public ImbuementRecipe getRecipeNow() {
      return this.f_58857_
         .m_7465_()
         .m_44013_((RecipeType)RecipeRegistry.IMBUEMENT_TYPE.get())
         .stream()
         .filter(f -> f.matches(this, this.f_58857_))
         .findFirst()
         .orElse(null);
   }

   @Override
   public void getTooltip(List<Component> tooltip) {
      ImbuementRecipe recipe = this.getRecipeNow();
      if (recipe != null && !recipe.output.m_41619_() && this.stack != null && !this.stack.m_41619_()) {
         tooltip.add(Component.m_237110_("ars_nouveau.crafting", new Object[]{recipe.output.m_41786_()}));
         if (recipe.source > 0) {
            tooltip.add(
               Component.m_237110_("ars_nouveau.crafting_progress", new Object[]{Math.min(100, this.getSource() * 100 / recipe.source)})
                  .m_130940_(ChatFormatting.GOLD)
            );
         }
      }
   }
}
