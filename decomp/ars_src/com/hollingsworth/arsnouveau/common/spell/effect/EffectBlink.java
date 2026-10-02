package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.item.inv.InteractType;
import com.hollingsworth.arsnouveau.api.item.inv.InventoryManager;
import com.hollingsworth.arsnouveau.api.item.inv.SlotReference;
import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.TileCaster;
import com.hollingsworth.arsnouveau.common.block.tile.PortalTile;
import com.hollingsworth.arsnouveau.common.items.WarpScroll;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketWarpPosition;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.NotNull;

public class EffectBlink extends AbstractEffect {
   public static EffectBlink INSTANCE = new EffectBlink();

   private EffectBlink() {
      super(GlyphLib.EffectBlinkID, "Blink");
   }

   @Override
   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      Vec3 vec = this.safelyGetHitPos(rayTraceResult);
      double distance = (double)((Integer)this.GENERIC_INT.get()).intValue() + (Double)this.AMP_VALUE.get() * spellStats.getAmpMultiplier();
      if (spellContext.getCaster() instanceof TileCaster) {
         InventoryManager manager = spellContext.getCaster().getInvManager();
         SlotReference reference = manager.findItem(
            i -> i.m_41720_() == ItemsRegistry.WARP_SCROLL.m_5456_() || i.m_41720_() == ItemsRegistry.STABLE_WARP_SCROLL.m_5456_(), InteractType.EXTRACT
         );
         if (!reference.isEmpty()) {
            ItemStack stack = reference.getHandler().getStackInSlot(reference.getSlot());
            WarpScroll.WarpScrollData data = WarpScroll.WarpScrollData.get(stack);
            if (data.isValid() && data.canTeleportWithDim(world)) {
               warpEntity(rayTraceResult.m_82443_(), data);
               return;
            }
         }
      }

      if (rayTraceResult.m_82443_().equals(shooter)) {
         blinkForward(world, shooter, distance);
      } else {
         if (this.isRealPlayer(shooter)) {
            WarpScroll.WarpScrollData scrollData = WarpScroll.WarpScrollData.get(shooter.m_21206_());
            if (scrollData.isValid()) {
               WarpScroll.WarpScrollData data = WarpScroll.WarpScrollData.get(shooter.m_21206_());
               if (data.isValid() && data.canTeleportWithDim(world)) {
                  warpEntity(rayTraceResult.m_82443_(), data);
               }
            } else {
               shooter.m_6021_(vec.m_7096_(), vec.m_7098_(), vec.m_7094_());
            }
         } else if (spellContext.getType() == SpellContext.CasterType.RUNE && rayTraceResult.m_82443_() instanceof LivingEntity living) {
            blinkForward(world, living, distance);
         }
      }
   }

   public static void warpEntity(Entity entity, WarpScroll.WarpScrollData warpScrollData) {
      if (entity != null) {
         if (entity instanceof LivingEntity living) {
            Event event = ForgeEventFactory.onEnderTeleport(
               living, (double)warpScrollData.getPos().m_123341_(), (double)warpScrollData.getPos().m_123342_(), (double)warpScrollData.getPos().m_123343_()
            );
            if (event.isCanceled()) {
               return;
            }
         }

         ServerLevel dimension = PortalTile.getServerLevel(warpScrollData.getDimension(), (ServerLevel)entity.f_19853_);
         if (dimension != null) {
            PortalTile.teleportEntityTo(entity, dimension, warpScrollData.getPos(), warpScrollData.getRotation());
         }
      }
   }

   public static void warpEntity(Entity entity, BlockPos warpPos) {
      if (entity != null) {
         Level world = entity.f_19853_;
         if (entity instanceof LivingEntity living) {
            Event event = ForgeEventFactory.onEnderTeleport(living, (double)warpPos.m_123341_(), (double)warpPos.m_123342_(), (double)warpPos.m_123343_());
            if (event.isCanceled()) {
               return;
            }
         }

         ((ServerLevel)entity.f_19853_)
            .m_8767_(
               ParticleTypes.f_123760_,
               entity.m_20185_(),
               entity.m_20186_() + 1.0,
               entity.m_20189_(),
               4,
               (world.f_46441_.m_188500_() - 0.5) * 2.0,
               -world.f_46441_.m_188500_(),
               (world.f_46441_.m_188500_() - 0.5) * 2.0,
               0.1F
            );
         entity.m_6021_((double)warpPos.m_123341_() + 0.5, (double)warpPos.m_123342_(), (double)warpPos.m_123343_() + 0.5);
         Networking.sendToNearby(
            world,
            entity,
            new PacketWarpPosition(entity.m_19879_(), entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), entity.m_146909_(), entity.m_146908_())
         );
         entity.f_19853_.m_5594_(null, entity.m_20183_(), SoundEvents.f_12052_, SoundSource.NEUTRAL, 1.0F, 1.0F);
         ((ServerLevel)entity.f_19853_)
            .m_8767_(
               ParticleTypes.f_123760_,
               (double)entity.m_20183_().m_123341_() + 0.5,
               (double)entity.m_20183_().m_123342_() + 1.0,
               (double)entity.m_20183_().m_123343_() + 0.5,
               4,
               (world.f_46441_.m_188500_() - 0.5) * 2.0,
               -world.f_46441_.m_188500_(),
               (world.f_46441_.m_188500_() - 0.5) * 2.0,
               0.1F
            );
      }
   }

   @Override
   public void onResolveBlock(
      BlockHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      Vec3 vec = rayTraceResult.m_82450_();
      if (this.isRealPlayer(shooter) && isValidTeleport(world, rayTraceResult.m_82425_().m_121945_(rayTraceResult.m_82434_()))) {
         warpEntity(shooter, new BlockPos(vec));
      }
   }

   public static void blinkForward(Level world, Entity shooter, double distance) {
      Vec3 lookVec = new Vec3(shooter.m_20154_().m_7096_(), 0.0, shooter.m_20154_().m_7094_());
      Vec3 vec = shooter.m_20182_().m_82549_(lookVec.m_82490_(distance));
      BlockPos pos = new BlockPos(vec);
      if (!isValidTeleport(world, pos)) {
         pos = getForward(world, pos, shooter, distance) == null
            ? getForward(world, pos.m_6630_(2), shooter, distance)
            : getForward(world, pos, shooter, distance);
      }

      if (pos != null) {
         warpEntity(shooter, pos);
      }
   }

   public static BlockPos getForward(Level world, BlockPos pos, Entity shooter, double distance) {
      Vec3 lookVec = new Vec3(shooter.m_20154_().m_7096_(), 0.0, shooter.m_20154_().m_7094_());
      Vec3 oldVec = new Vec3((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_()).m_82549_(lookVec.m_82490_(distance));

      for (double i = distance; i >= 0.0; i--) {
         Vec3 vec = oldVec.m_82549_(lookVec.m_82490_(i));
         BlockPos sendPos = new BlockPos(vec);
         if (i <= 0.0) {
            return null;
         }

         if (isValidTeleport(world, sendPos)) {
            return sendPos;
         }
      }

      return null;
   }

   @Override
   public void buildConfig(Builder builder) {
      super.buildConfig(builder);
      this.addGenericInt(builder, 8, "Base teleport distance", "distance");
      this.addAmpConfig(builder, 3.0);
   }

   public static boolean isValidTeleport(Level world, BlockPos pos) {
      return !world.m_8055_(pos).m_60815_() && !world.m_8055_(pos.m_7494_()).m_60815_() && !world.m_8055_(pos.m_6630_(2)).m_60815_();
   }

   @Override
   public int getDefaultManaCost() {
      return 50;
   }

   @Override
   public SpellTier defaultTier() {
      return SpellTier.THREE;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(new AbstractAugment[]{AugmentAmplify.INSTANCE, AugmentDampen.INSTANCE});
   }

   @Override
   public String getBookDescription() {
      return "Teleports the caster to a location. If an entity is hit and the caster is holding a Warp Scroll in the offhand, the entity will be warped to the location on the Warp Scroll. When used on Self, the caster blinks forward. Spell Turrets and Runes can warp entities using Warp Scrolls from adjacent inventories without consuming the scroll.";
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.MANIPULATION});
   }
}
