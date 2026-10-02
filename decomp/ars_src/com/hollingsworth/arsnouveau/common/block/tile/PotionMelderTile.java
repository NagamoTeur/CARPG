package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.client.ITooltipProvider;
import com.hollingsworth.arsnouveau.api.item.IWandable;
import com.hollingsworth.arsnouveau.api.potion.PotionData;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.api.util.NBTUtil;
import com.hollingsworth.arsnouveau.api.util.SourceUtil;
import com.hollingsworth.arsnouveau.client.particle.GlowParticleData;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.client.util.ColorPos;
import com.hollingsworth.arsnouveau.common.block.ITickable;
import com.hollingsworth.arsnouveau.common.entity.EntityFlyingItem;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.Config;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.PlayState;
import software.bernie.ars_nouveau.geckolib3.core.builder.AnimationBuilder;
import software.bernie.ars_nouveau.geckolib3.core.controller.AnimationController;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class PotionMelderTile extends ModdedTile implements IAnimatable, ITickable, IWandable, ITooltipProvider {
   int timeMixing;
   boolean isMixing;
   boolean hasSource;
   public boolean isOff;
   int lastMixedColor;
   public List<BlockPos> fromJars = new ArrayList<>();
   public BlockPos toPos;
   AnimationFactory manager = GeckoLibUtil.createFactory(this);

   public PotionMelderTile(BlockPos pos, BlockState state) {
      super(BlockRegistry.POTION_MELDER_TYPE, pos, state);
   }

   @Override
   public void tick() {
      if (this.f_58857_.f_46443_) {
         BlockPos pos = this.m_58899_();
         if (this.f_58857_.f_46441_.m_188503_(6) == 0) {
            this.f_58857_
               .m_7106_(
                  ParticleTypes.f_123772_,
                  (double)pos.m_123341_() + ParticleUtil.inRange(-0.25, 0.25) + 0.5,
                  (double)(pos.m_123342_() + 1),
                  (double)pos.m_123343_() + 0.5 + ParticleUtil.inRange(-0.25, 0.25),
                  0.0,
                  0.0,
                  0.0
               );
         }
      }

      int maxMergeTicks = 160;
      if (this.isOff) {
         this.isMixing = false;
         this.timeMixing = 0;
      } else {
         if (!this.f_58857_.f_46443_
            && !this.hasSource
            && this.f_58857_.m_46467_() % 20L == 0L
            && SourceUtil.takeSourceWithParticles(this.f_58858_, this.f_58857_, 5, (Integer)Config.MELDER_SOURCE_COST.get()) != null) {
            this.hasSource = true;
            this.updateBlock();
         }

         if (this.hasSource
            && this.toPos != null
            && this.f_58857_.m_46749_(this.toPos)
            && this.takeJarsValid()
            && this.f_58857_.m_7702_(this.toPos) instanceof PotionJarTile combJar) {
            PotionJarTile var16 = (PotionJarTile)this.f_58857_.m_7702_(this.fromJars.get(0));
            PotionJarTile tile2 = (PotionJarTile)this.f_58857_.m_7702_(this.fromJars.get(1));
            PotionData data = var16.getData().mergeEffects(tile2.getData());
            if (!combJar.canAccept(data, (Integer)Config.MELDER_OUTPUT.get())) {
               this.isMixing = false;
               this.timeMixing = 0;
            } else {
               this.isMixing = true;
               this.timeMixing++;
               ParticleColor color1 = ParticleColor.fromInt(var16.getColor());
               ParticleColor color2 = ParticleColor.fromInt(tile2.getColor());
               if (!this.f_58857_.f_46443_) {
                  if (this.timeMixing % 20 == 0 && this.timeMixing > 0 && this.timeMixing <= 60) {
                     EntityFlyingItem item = new EntityFlyingItem(
                           this.f_58857_,
                           var16.m_58899_().m_7494_(),
                           this.f_58858_,
                           Math.round(255.0F * color1.getRed()),
                           Math.round(255.0F * color1.getGreen()),
                           Math.round(255.0F * color1.getBlue())
                        )
                        .withNoTouch();
                     item.setDistanceAdjust(2.0F);
                     this.f_58857_.m_7967_(item);
                     EntityFlyingItem item2 = new EntityFlyingItem(
                           this.f_58857_,
                           tile2.m_58899_().m_7494_(),
                           this.f_58858_,
                           Math.round(255.0F * color2.getRed()),
                           Math.round(255.0F * color2.getGreen()),
                           Math.round(255.0F * color2.getBlue())
                        )
                        .withNoTouch();
                     item2.setDistanceAdjust(2.0F);
                     this.f_58857_.m_7967_(item2);
                  }

                  if (!this.f_58857_.f_46443_ && this.timeMixing >= maxMergeTicks) {
                     this.timeMixing = 0;
                     this.mergePotions(combJar, var16, tile2, data);
                  }
               } else {
                  if (this.timeMixing >= 120) {
                     for (int i = 0; i < 3; i++) {
                        double d0 = (double)this.f_58858_.m_123341_() + 0.5 + ParticleUtil.inRange(-0.25, 0.25);
                        double d1 = (double)(this.f_58858_.m_123342_() + 1) + ParticleUtil.inRange(-0.1, 0.4);
                        double d2 = (double)this.f_58858_.m_123343_() + 0.5 + ParticleUtil.inRange(-0.25, 0.25);
                        this.f_58857_
                           .m_7106_(GlowParticleData.createData(ParticleColor.fromInt(PotionUtils.m_43564_(data.fullEffects()))), d0, d1, d2, 0.0, 0.01F, 0.0);
                     }

                     this.lastMixedColor = PotionUtils.m_43564_(data.fullEffects());
                  }

                  if (this.timeMixing >= 160) {
                     this.timeMixing = 0;
                  }
               }
            }
         } else {
            this.isMixing = false;
            this.timeMixing = 0;
         }
      }
   }

   public void mergePotions(PotionJarTile combJar, PotionJarTile take1, PotionJarTile take2, PotionData data) {
      combJar.add(data, (Integer)Config.MELDER_OUTPUT.get());
      take1.remove((Integer)Config.MELDER_INPUT_COST.get());
      take2.remove((Integer)Config.MELDER_INPUT_COST.get());
      this.hasSource = false;
      ParticleColor color2 = ParticleColor.fromInt(combJar.getColor());
      EntityFlyingItem item2 = new EntityFlyingItem(
            this.f_58857_,
            new Vec3((double)this.f_58858_.m_123341_() + 0.5, (double)this.f_58858_.m_123342_() + 1.0, (double)this.f_58858_.m_123343_() + 0.5),
            new Vec3(combJar.getX() + 0.5, combJar.getY(), combJar.getZ() + 0.5),
            Math.round(255.0F * color2.getRed()),
            Math.round(255.0F * color2.getGreen()),
            Math.round(255.0F * color2.getBlue())
         )
         .withNoTouch();
      item2.setDistanceAdjust(2.0F);
      this.f_58857_.m_7967_(item2);
      this.updateBlock();
   }

   public boolean takeJarsValid() {
      if (this.fromJars.size() < 2) {
         return false;
      } else {
         for (BlockPos p : this.fromJars) {
            if (!this.f_58857_.m_46749_(p)
               || !(this.f_58857_.m_7702_(p) instanceof PotionJarTile jar)
               || jar.getAmount() < (Integer)Config.MELDER_INPUT_COST.get()) {
               return false;
            }
         }

         return true;
      }
   }

   public PotionData getCombinedResult(PotionJarTile jar1, PotionJarTile jar2) {
      return jar1.getData().mergeEffects(jar2.getData());
   }

   @Override
   public void onWanded(Player playerEntity) {
      this.toPos = null;
      this.fromJars = new ArrayList<>();
      PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.connections.cleared"));
      this.updateBlock();
   }

   @Override
   public void onFinishedConnectionFirst(@Nullable BlockPos storedPos, @Nullable LivingEntity storedEntity, Player playerEntity) {
      if (storedPos != null) {
         if (!this.closeEnough(storedPos, this.f_58858_)) {
            PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.melder.too_far"));
            return;
         }

         this.toPos = storedPos;
         PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.melder.to_set"));
         this.updateBlock();
      }
   }

   @Override
   public void onFinishedConnectionLast(@Nullable BlockPos storedPos, @Nullable LivingEntity storedEntity, Player playerEntity) {
      if (storedPos != null) {
         if (!this.closeEnough(storedPos, this.f_58858_)) {
            PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.melder.too_far"));
            return;
         }

         if (this.fromJars.size() >= 2) {
            PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.melder.from_capped"));
            return;
         }

         this.fromJars.add(storedPos);
         PortUtil.sendMessage(playerEntity, Component.m_237110_("ars_nouveau.melder.from_set", new Object[]{this.fromJars.size()}));
         this.updateBlock();
      }
   }

   @Override
   public List<ColorPos> getWandHighlight(List<ColorPos> list) {
      if (this.toPos != null) {
         list.add(ColorPos.centered(this.toPos, ParticleColor.TO_HIGHLIGHT));
      }

      for (BlockPos p : this.fromJars) {
         list.add(ColorPos.centered(p, ParticleColor.FROM_HIGHLIGHT));
      }

      return list;
   }

   public boolean closeEnough(BlockPos pos1, BlockPos pos2) {
      return BlockUtil.distanceFrom(pos1, pos2) <= 3.0;
   }

   private <E extends BlockEntity & IAnimatable> PlayState idlePredicate(AnimationEvent<E> event) {
      event.getController().setAnimation(new AnimationBuilder().addAnimation("stir"));
      return this.isMixing ? PlayState.CONTINUE : PlayState.STOP;
   }

   @Override
   public void registerControllers(AnimationData animationData) {
      animationData.addAnimationController(new AnimationController<>(this, "rotate_controller", 0.0F, this::idlePredicate));
      animationData.setResetSpeedInTicks(0.0);
   }

   @Override
   public AnimationFactory getFactory() {
      return this.manager;
   }

   public void m_142466_(CompoundTag nbt) {
      super.m_142466_(nbt);
      this.fromJars = new ArrayList<>();
      this.timeMixing = nbt.m_128451_("mixing");
      this.isMixing = nbt.m_128471_("isMixing");
      this.hasSource = nbt.m_128471_("hasMana");

      for (int counter = 0; NBTUtil.hasBlockPos(nbt, "from_" + counter); counter++) {
         BlockPos pos = NBTUtil.getBlockPos(nbt, "from_" + counter);
         if (!this.fromJars.contains(pos)) {
            this.fromJars.add(pos);
         }
      }

      this.toPos = NBTUtil.getNullablePos(nbt, "to_pos");
      this.isOff = nbt.m_128471_("off");
      this.lastMixedColor = nbt.m_128451_("lastMixedColor");
   }

   @Override
   public void m_183515_(CompoundTag compound) {
      compound.m_128405_("mixing", this.timeMixing);
      compound.m_128379_("isMixing", this.isMixing);
      compound.m_128379_("hasMana", this.hasSource);
      compound.m_128405_("lastMixedColor", this.lastMixedColor);
      NBTUtil.storeBlockPos(compound, "to_pos", this.toPos);
      int counter = 0;

      for (BlockPos p : this.fromJars) {
         NBTUtil.storeBlockPos(compound, "from_" + counter, p);
         counter++;
      }

      compound.m_128379_("off", this.isOff);
   }

   @Override
   public void getTooltip(List<Component> tooltip) {
      if (!this.hasSource) {
         tooltip.add(Component.m_237115_("ars_nouveau.apparatus.nomana").m_6270_(Style.f_131099_.m_131140_(ChatFormatting.GOLD)));
      }

      if (this.fromJars.size() < 2) {
         tooltip.add(
            Component.m_237110_("ars_nouveau.melder.from_set", new Object[]{this.fromJars.size()}).m_6270_(Style.f_131099_.m_131140_(ChatFormatting.GOLD))
         );
      }

      if (this.toPos == null) {
         tooltip.add(Component.m_237115_("ars_nouveau.melder.no_to_pos").m_6270_(Style.f_131099_.m_131140_(ChatFormatting.GOLD)));
      }

      if (this.toPos != null && this.fromJars.size() == 2 && this.hasSource && !this.isMixing && !this.takeJarsValid()) {
         tooltip.add(Component.m_237115_("ars_nouveau.melder.needs_potion").m_6270_(Style.f_131099_.m_131140_(ChatFormatting.GOLD)));
      }

      if (this.fromJars.size() >= 2 && this.toPos != null && this.f_58857_.m_7702_(this.toPos) instanceof PotionJarTile combJar) {
         PotionJarTile tile1 = (PotionJarTile)this.f_58857_.m_7702_(this.fromJars.get(0));
         PotionJarTile tile2 = (PotionJarTile)this.f_58857_.m_7702_(this.fromJars.get(1));
         int inputCost = (Integer)Config.MELDER_INPUT_COST.get();
         if (tile1 == null || tile1.getAmount() < inputCost || tile2 == null || tile2.getAmount() < inputCost) {
            return;
         }

         PotionData data = this.getCombinedResult(tile1, tile2);
         if (!combJar.canAccept(data, (Integer)Config.MELDER_OUTPUT.get())) {
            tooltip.add(Component.m_237115_("ars_nouveau.melder.destination_invalid").m_6270_(Style.f_131099_.m_131140_(ChatFormatting.GOLD)));
         }
      }
   }

   public int getColor() {
      return this.lastMixedColor == 0 ? new ParticleColor(200, 0, 200).getColor() : this.lastMixedColor;
   }
}
