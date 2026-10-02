package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.client.ITooltipProvider;
import com.hollingsworth.arsnouveau.api.event.SpellCastEvent;
import com.hollingsworth.arsnouveau.api.event.SpellResolveEvent;
import com.hollingsworth.arsnouveau.api.item.IWandable;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.api.util.LevelPosMap;
import com.hollingsworth.arsnouveau.common.block.ITickable;
import com.hollingsworth.arsnouveau.common.block.SpellSensor;
import com.hollingsworth.arsnouveau.common.datagen.BlockTagProvider;
import com.hollingsworth.arsnouveau.common.items.SpellParchment;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.List;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag.Default;
import net.minecraft.world.level.ClipBlockStateContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SculkSensorPhase;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class SpellSensorTile extends ModdedTile implements ITickable, IWandable, ITooltipProvider {
   public static LevelPosMap SENSOR_MAP = new LevelPosMap((level, pos) -> !(level.m_7702_(pos) instanceof SpellSensorTile));
   public int outputDuration;
   public int outputStrength;
   public boolean onCooldown;
   public int listenRange = 8;
   public boolean isOnResolve = false;
   public ItemStack parchment = ItemStack.f_41583_;

   public SpellSensorTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
      super(tileEntityTypeIn, pos, state);
   }

   public SpellSensorTile(BlockPos pos, BlockState state) {
      super((BlockEntityType<?>)BlockRegistry.SPELL_SENSOR_TILE.get(), pos, state);
   }

   public static void onSpellCast(SpellCastEvent event) {
      SENSOR_MAP.applyForRange(event.getWorld(), new BlockPos(event.context.getCaster().getPosition()), 8.0, pos -> {
         if (event.getWorld().m_7702_(pos) instanceof SpellSensorTile tile && !tile.isOnResolve) {
            tile.onSignal(event.context.getCaster().getPosition(), event.spell);
         }

         return false;
      });
   }

   public static void onSpellResolve(SpellResolveEvent.Post event) {
      HitResult resolveSource = event.resolver.hitResult;
      if (resolveSource != null) {
         SENSOR_MAP.applyForRange(event.world, resolveSource.m_82450_(), 8.0, pos -> {
            if (event.world.m_7702_(pos) instanceof SpellSensorTile tile && tile.isOnResolve) {
               tile.onSignal(resolveSource.m_82450_(), event.spell);
            }

            return false;
         });
      }
   }

   @Override
   public void onWanded(Player playerEntity) {
      this.isOnResolve = !this.isOnResolve;
      this.updateBlock();
   }

   public void onSignal(Vec3 pos, Spell spell) {
      if (!this.onCooldown && this.outputDuration <= 0) {
         Vec3 thisPos = new Vec3((double)this.f_58858_.m_123341_() + 0.5, (double)this.f_58858_.m_123342_() + 0.5, (double)this.f_58858_.m_123343_() + 0.5);
         if (!(BlockUtil.distanceFrom(pos, thisPos) > (double)this.listenRange) && !isOccluded(this.f_58857_, thisPos, pos)) {
            int str = 0;
            if (!this.parchment.m_41619_()) {
               if (this.parchment.m_41720_() instanceof SpellParchment spellParchment) {
                  Spell listeningSpell = spellParchment.getSpellCaster(this.parchment.m_41784_()).getSpell();
                  List<AbstractSpellPart> spellParts = listeningSpell.recipe.stream().filter(Objects::nonNull).toList();
                  List<AbstractSpellPart> spellParts1 = spell.recipe.stream().filter(Objects::nonNull).toList();
                  if (!spellParts.equals(spellParts1)) {
                     return;
                  }
               }

               str = 15;
            } else {
               str = spell.recipe.size();
            }

            this.outputDuration = 40;
            this.outputStrength = str;
            this.f_58857_.m_46597_(this.f_58858_, (BlockState)this.m_58900_().m_61124_(SpellSensor.PHASE, SculkSensorPhase.ACTIVE));
            this.m_6596_();
            this.f_58857_.m_46672_(this.f_58858_, this.m_58900_().m_60734_());
         }
      }
   }

   private static boolean isOccluded(Level pLevel, Vec3 pFrom, Vec3 pTo) {
      Vec3 vec3 = new Vec3((double)Mth.m_14107_(pFrom.f_82479_) + 0.5, (double)Mth.m_14107_(pFrom.f_82480_) + 0.5, (double)Mth.m_14107_(pFrom.f_82481_) + 0.5);
      Vec3 vec31 = new Vec3((double)Mth.m_14107_(pTo.f_82479_) + 0.5, (double)Mth.m_14107_(pTo.f_82480_) + 0.5, (double)Mth.m_14107_(pTo.f_82481_) + 0.5);

      for (Direction direction : Direction.values()) {
         Vec3 vec32 = vec3.m_231075_(direction, 1.0E-5F);
         if (pLevel.m_151353_(new ClipBlockStateContext(vec32, vec31, p_223780_ -> p_223780_.m_204336_(BlockTagProvider.OCCLUDES_SPELL_SENSOR))).m_6662_()
            != Type.BLOCK) {
            return false;
         }
      }

      return true;
   }

   @Override
   public void tick() {
      if (!this.f_58857_.f_46443_) {
         if (this.outputDuration > 0) {
            this.outputDuration--;
            if (this.outputDuration <= 0) {
               this.outputStrength = 0;
               this.onCooldown = true;
               this.f_58857_.m_46597_(this.f_58858_, (BlockState)this.m_58900_().m_61124_(SpellSensor.PHASE, SculkSensorPhase.INACTIVE));
               this.m_6596_();
               this.f_58857_.m_46672_(this.f_58858_, this.m_58900_().m_60734_());
               this.f_58857_.m_186460_(this.f_58858_, this.m_58900_().m_60734_(), 1);
            }
         }

         if (this.f_58857_.m_46467_() % 20L == 0L) {
            SENSOR_MAP.addPosition(this.f_58857_, this.f_58858_);
         }
      }
   }

   @Override
   public void m_183515_(CompoundTag tag) {
      super.m_183515_(tag);
      tag.m_128405_("outputDuration", this.outputDuration);
      tag.m_128405_("outputStrength", this.outputStrength);
      tag.m_128379_("isOnResolve", this.isOnResolve);
      tag.m_128365_("parchment", this.parchment.m_41739_(new CompoundTag()));
   }

   public void m_142466_(CompoundTag pTag) {
      super.m_142466_(pTag);
      this.outputDuration = pTag.m_128451_("outputDuration");
      this.outputStrength = pTag.m_128451_("outputStrength");
      this.isOnResolve = pTag.m_128471_("isOnResolve");
      this.parchment = ItemStack.m_41712_(pTag.m_128469_("parchment"));
   }

   @Override
   public void getTooltip(List<Component> tooltip) {
      if (this.isOnResolve) {
         tooltip.add(Component.m_237115_("ars_nouveau.sensor.on_resolve"));
      } else {
         tooltip.add(Component.m_237115_("ars_nouveau.sensor.on_cast"));
      }

      if (!this.parchment.m_41619_() && this.parchment.m_41720_() instanceof SpellParchment spellParchment) {
         spellParchment.getInformation(this.parchment, this.f_58857_, tooltip, Default.NORMAL);
      }
   }
}
