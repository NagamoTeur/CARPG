package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.block.IPedestalMachine;
import com.hollingsworth.arsnouveau.api.enchanting_apparatus.IEnchantingRecipe;
import com.hollingsworth.arsnouveau.api.util.SourceUtil;
import com.hollingsworth.arsnouveau.client.particle.GlowParticleData;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleLineData;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.client.util.ColorPos;
import com.hollingsworth.arsnouveau.common.block.ITickable;
import com.hollingsworth.arsnouveau.common.network.HighlightAreaPacket;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketOneShotAnimation;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.SoundRegistry;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.PlayState;
import software.bernie.ars_nouveau.geckolib3.core.builder.AnimationBuilder;
import software.bernie.ars_nouveau.geckolib3.core.controller.AnimationController;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class EnchantingApparatusTile extends SingleItemTile implements Container, IPedestalMachine, ITickable, IAnimatable, IAnimationListener {
   private final LazyOptional<IItemHandler> itemHandler = LazyOptional.of(() -> new InvWrapper(this));
   private int counter;
   public boolean isCrafting;
   public static final int craftingLength = 210;
   AnimationController<EnchantingApparatusTile> craftController;
   AnimationController<EnchantingApparatusTile> idleController;
   AnimationFactory manager = GeckoLibUtil.createFactory(this);

   public EnchantingApparatusTile(BlockPos pos, BlockState state) {
      super(BlockRegistry.ENCHANTING_APP_TILE, pos, state);
   }

   @Override
   public void lightPedestal(Level level) {
      if (level != null) {
         for (BlockPos pos : this.pedestalList()) {
            ParticleUtil.spawnOrb(level, ParticleColor.makeRandomColor(255, 255, 255, level.f_46441_), pos.m_7494_(), 300);
         }
      }
   }

   @Override
   public void tick() {
      if (!this.f_58857_.f_46443_) {
         if (this.isCrafting) {
            if (this.getRecipe(this.stack, null) == null) {
               this.isCrafting = false;
               this.m_6596_();
            }

            this.counter++;
         }

         if (this.counter > 210) {
            this.counter = 0;
            if (this.isCrafting) {
               IEnchantingRecipe recipe = this.getRecipe(this.stack, null);
               List<ItemStack> pedestalItems = this.getPedestalItems();
               if (recipe != null) {
                  pedestalItems.forEach(i -> {
                     ItemStack var1x = null;
                  });
                  this.stack = recipe.getResult(pedestalItems, this.stack, this);
                  this.clearItems();
                  this.m_6596_();
                  ParticleUtil.spawnPoof((ServerLevel)this.f_58857_, this.f_58858_);
                  this.f_58857_.m_5594_(null, this.m_58899_(), (SoundEvent)SoundRegistry.APPARATUS_FINISH.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
               }

               this.isCrafting = false;
               this.m_6596_();
            }

            this.updateBlock();
         }
      } else {
         if (this.isCrafting) {
            Level world = this.m_58904_();
            BlockPos pos = this.m_58899_().m_7637_(0.0, 0.5, 0.0);
            RandomSource rand = world.m_213780_();
            Vec3 particlePos = new Vec3((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_()).m_82520_(0.5, 0.0, 0.5);
            particlePos = particlePos.m_82549_(ParticleUtil.pointInSphere());
            world.m_7106_(
               ParticleLineData.createData(new ParticleColor(rand.m_188503_(255), rand.m_188503_(255), rand.m_188503_(255))),
               particlePos.m_7096_(),
               particlePos.m_7098_(),
               particlePos.m_7094_(),
               (double)pos.m_123341_() + 0.5,
               (double)(pos.m_123342_() + 1),
               (double)pos.m_123343_() + 0.5
            );

            for (BlockPos p : this.pedestalList()) {
               BlockEntity var8 = this.f_58857_.m_7702_(p);
               if (var8 instanceof ArcanePedestalTile) {
                  ArcanePedestalTile pedestalTile = (ArcanePedestalTile)var8;
                  if (pedestalTile.getStack() != null && !pedestalTile.getStack().m_41619_()) {
                     this.m_58904_()
                        .m_7106_(
                           GlowParticleData.createData(new ParticleColor(rand.m_188503_(255), rand.m_188503_(255), rand.m_188503_(255))),
                           (double)p.m_123341_() + 0.5 + ParticleUtil.inRange(-0.2, 0.2),
                           (double)p.m_123342_() + 1.5 + ParticleUtil.inRange(-0.3, 0.3),
                           (double)p.m_123343_() + 0.5 + ParticleUtil.inRange(-0.2, 0.2),
                           0.0,
                           0.0,
                           0.0
                        );
                  }
               }
            }
         }
      }
   }

   public void clearItems() {
      for (BlockPos blockPos : this.pedestalList()) {
         BlockEntity state = this.f_58857_.m_7702_(blockPos);
         if (state instanceof ArcanePedestalTile) {
            ArcanePedestalTile tile = (ArcanePedestalTile)state;
            if (tile.getStack() != null) {
               tile.setStack(tile.getStack().getCraftingRemainingItem());
               BlockState statex = this.f_58857_.m_8055_(blockPos);
               this.f_58857_.m_7260_(blockPos, statex, statex, 3);
               tile.m_6596_();
            }
         }
      }
   }

   public List<BlockPos> pedestalList() {
      return this.pedestalList(this.m_58899_(), 3, this.m_58904_());
   }

   public List<ItemStack> getPedestalItems() {
      ArrayList<ItemStack> pedestalItems = new ArrayList<>();

      for (BlockPos blockPos : this.pedestalList()) {
         BlockEntity var5 = this.f_58857_.m_7702_(blockPos);
         if (var5 instanceof ArcanePedestalTile) {
            ArcanePedestalTile tile = (ArcanePedestalTile)var5;
            if (tile.getStack() != null && !tile.getStack().m_41619_()) {
               pedestalItems.add(tile.getStack());
            }
         }
      }

      return pedestalItems;
   }

   public IEnchantingRecipe getRecipe(ItemStack stack, @Nullable Player playerEntity) {
      List<ItemStack> pedestalItems = this.getPedestalItems();
      return ArsNouveauAPI.getInstance()
         .getEnchantingApparatusRecipes(this.f_58857_)
         .stream()
         .filter(r -> r.isMatch(pedestalItems, stack, this, playerEntity))
         .findFirst()
         .orElse(null);
   }

   public boolean attemptCraft(ItemStack catalyst, @Nullable Player playerEntity) {
      if (this.isCrafting) {
         return false;
      } else if (!this.craftingPossible(catalyst, playerEntity)) {
         return false;
      } else {
         IEnchantingRecipe recipe = this.getRecipe(catalyst, playerEntity);
         if (recipe.consumesSource()) {
            SourceUtil.takeSourceWithParticles(this.f_58858_, this.f_58857_, 10, recipe.getSourceCost());
         }

         this.isCrafting = true;
         this.updateBlock();
         Networking.sendToNearby(this.f_58857_, this.f_58858_, new PacketOneShotAnimation(this.f_58858_));
         this.f_58857_.m_5594_(null, this.m_58899_(), (SoundEvent)SoundRegistry.APPARATUS_CHANNEL.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
         return true;
      }
   }

   public boolean craftingPossible(ItemStack stack, Player playerEntity) {
      if (!this.isCrafting && !stack.m_41619_()) {
         IEnchantingRecipe recipe = this.getRecipe(stack, playerEntity);
         if (recipe == null && playerEntity != null) {
            List<ColorPos> colorPos = new ArrayList<>();

            for (BlockPos pos : this.pedestalList()) {
               if (this.f_58857_.m_7702_(pos) instanceof ArcanePedestalTile tile) {
                  colorPos.add(ColorPos.centeredAbove(tile.m_58899_()));
               }
            }

            Networking.sendToNearby(this.f_58857_, this.f_58858_, new HighlightAreaPacket(colorPos, 60));
         }

         return recipe != null
            && (!recipe.consumesSource() || recipe.consumesSource() && SourceUtil.hasSourceNearby(this.f_58858_, this.f_58857_, 10, recipe.getSourceCost()));
      } else {
         return false;
      }
   }

   @Override
   public void m_142466_(CompoundTag compound) {
      this.isCrafting = compound.m_128471_("is_crafting");
      this.counter = compound.m_128451_("counter");
      super.m_142466_(compound);
   }

   @Override
   public void m_183515_(CompoundTag tag) {
      super.m_183515_(tag);
      tag.m_128379_("is_crafting", this.isCrafting);
      tag.m_128405_("counter", this.counter);
   }

   @Override
   public ItemStack m_8020_(int index) {
      return this.isCrafting ? ItemStack.f_41583_ : super.m_8020_(index);
   }

   @Override
   public boolean m_7013_(int slot, ItemStack newStack) {
      return !this.isCrafting && !newStack.m_41619_() ? this.stack.m_41619_() && this.craftingPossible(newStack, null) : false;
   }

   @Override
   public ItemStack m_7407_(int index, int count) {
      return this.isCrafting ? ItemStack.f_41583_ : super.m_7407_(index, count);
   }

   @Override
   public ItemStack m_8016_(int index) {
      return this.isCrafting ? ItemStack.f_41583_ : super.m_8016_(index);
   }

   @Override
   public void m_6836_(int index, ItemStack stack) {
      if (!this.isCrafting) {
         super.m_6836_(index, stack);
         this.attemptCraft(stack, null);
      }
   }

   @Override
   public void registerControllers(AnimationData animationData) {
      this.idleController = new AnimationController<>(this, "controller", 0.0F, this::idlePredicate);
      animationData.addAnimationController(this.idleController);
      this.craftController = new AnimationController<>(this, "craft_controller", 0.0F, this::craftPredicate);
      animationData.addAnimationController(this.craftController);
      animationData.setResetSpeedInTicks(0.0);
   }

   @Override
   public AnimationFactory getFactory() {
      return this.manager;
   }

   private <E extends BlockEntity & IAnimatable> PlayState idlePredicate(AnimationEvent<E> event) {
      event.getController().setAnimation(new AnimationBuilder().addAnimation("floating"));
      return PlayState.CONTINUE;
   }

   private <E extends BlockEntity & IAnimatable> PlayState craftPredicate(AnimationEvent<E> event) {
      return !this.isCrafting ? PlayState.STOP : PlayState.CONTINUE;
   }

   @Override
   public void startAnimation(int arg) {
      try {
         if (this.craftController != null) {
            this.craftController.markNeedsReload();
            this.craftController.setAnimation(new AnimationBuilder().addAnimation("enchanting"));
         }
      } catch (Exception var3) {
         var3.printStackTrace();
      }
   }
}
