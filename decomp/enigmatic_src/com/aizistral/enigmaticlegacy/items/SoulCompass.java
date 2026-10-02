package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.api.items.ICursed;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBase;
import com.google.common.base.Objects;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SoulCompass extends ItemBase implements ICursed {
   @OnlyIn(Dist.CLIENT)
   private SoulCompass.CompassWobble wobble;
   @OnlyIn(Dist.CLIENT)
   private SoulCompass.CompassWobble wobbleRandom;
   @Nullable
   @OnlyIn(Dist.CLIENT)
   private BlockPos nearestCrystal;

   public SoulCompass() {
      super(getDefaultProperties().m_41497_(Rarity.EPIC).m_41487_(1).m_41486_());
   }

   @OnlyIn(Dist.CLIENT)
   public void setNearestCrystal(BlockPos nearestCrystal) {
      if (!Objects.equal(this.nearestCrystal, nearestCrystal)) {
         if (nearestCrystal != null && this.nearestCrystal != null) {
            this.wobble.rotation = 1.0;
            this.wobble.deltaRotation = 0.3;
         } else {
            this.wobble.rotation = this.wobbleRandom.rotation;
            this.wobble.deltaRotation = this.wobbleRandom.deltaRotation;
         }

         this.nearestCrystal = nearestCrystal;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public float getAngle(BlockPos one, BlockPos two) {
      float angle = (float)Math.toDegrees(Math.atan2((double)(one.m_123343_() - two.m_123343_()), (double)(one.m_123341_() - two.m_123341_())));
      if (angle < 0.0F) {
         angle += 360.0F;
      }

      return angle;
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, Level world, List<Component> list, TooltipFlag flag) {
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.soulCompass1");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }

      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      ItemLoreHelper.indicateCursedOnesOnly(list);
   }

   @OnlyIn(Dist.CLIENT)
   public void registerVariants() {
      this.wobble = new SoulCompass.CompassWobble(0.1);
      this.wobbleRandom = new SoulCompass.CompassWobble(0.6);
      ItemProperties.register(
         this,
         new ResourceLocation("angle"),
         new ClampedItemPropertyFunction() {
            private final SoulCompass.CompassWobble wobble;
            private final SoulCompass.CompassWobble wobbleRandom;

            {
               this.wobble = SoulCompass.this.wobble;
               this.wobbleRandom = SoulCompass.this.wobbleRandom;
            }

            public float m_142187_(ItemStack stack, @Nullable ClientLevel levelx, @Nullable LivingEntity living, int seed) {
               Entity entity = (Entity)(living != null ? living : stack.m_41609_());
               if (entity == null) {
                  return 0.0F;
               } else {
                  if (levelx == null && entity.f_19853_ instanceof ClientLevel levelx) {
                     ;
                  }

                  assert levelx != null;

                  BlockPos target = this.getTargetPosition();
                  long gameTime = levelx.m_46467_();
                  if (target != null
                     && !(entity.m_20182_().m_82531_((double)target.m_123341_() + 0.5, entity.m_20182_().m_7098_(), (double)target.m_123343_() + 0.5) < 1.0E-5F)
                     )
                   {
                     boolean isLocalPlayer = living instanceof Player && ((Player)living).m_7578_();
                     double bodyRotation = 0.0;
                     if (!isLocalPlayer) {
                        return this.randomAngle(gameTime, seed);
                     } else {
                        assert living != null;

                        bodyRotation = (double)living.m_146908_();
                        if (SuperpositionHandler.isTheCursedOne((Player)living)
                           && SuperpositionHandler.hasExactStack((Player)living, stack)
                           && !levelx.m_204166_(living.m_20183_()).m_203565_(Biomes.f_48199_)) {
                           bodyRotation = Mth.m_14109_(bodyRotation / 360.0, 1.0);
                           double angle = SoulCompass.this.getAngleTo(Vec3.m_82512_(target), entity) / (float) (Math.PI * 2);
                           if (this.wobble.shouldUpdate(gameTime)) {
                              this.wobble.update(gameTime, 0.5 - (bodyRotation - 0.25));
                           }

                           double otherAngle = angle + this.wobble.rotation;
                           return Mth.m_14091_((float)otherAngle, 1.0F);
                        } else {
                           return this.randomAngle(gameTime, seed);
                        }
                     }
                  } else {
                     return this.randomAngle(gameTime, seed);
                  }
               }
            }

            private float randomAngle(long gameTime, int seed) {
               if (this.wobbleRandom.shouldUpdate(gameTime)) {
                  this.wobbleRandom.update(gameTime, Math.random());
               }

               double randomAngle = this.wobbleRandom.rotation + (double)((float)this.hash(seed) / 2.1474836E9F);
               return Mth.m_14091_((float)randomAngle, 1.0F);
            }

            private int hash(int value) {
               return value * 1327217883;
            }

            @Nullable
            private BlockPos getTargetPosition() {
               return SoulCompass.this.nearestCrystal;
            }

            @Nullable
            private BlockPos getSpawnPosition(ClientLevel level) {
               return level.m_6042_().f_63858_() ? level.m_220360_() : null;
            }

            @Nullable
            private BlockPos getLodestonePosition(Level level, CompoundTag tag) {
               boolean flag = tag.m_128441_("LodestonePos");
               boolean flag1 = tag.m_128441_("LodestoneDimension");
               if (flag && flag1) {
                  Optional<ResourceKey<Level>> optional = Level.f_46427_.parse(NbtOps.f_128958_, tag.m_128423_("LodestoneDimension")).result();
                  if (optional.isPresent() && level.m_46472_() == optional.get()) {
                     return NbtUtils.m_129239_(tag.m_128469_("LodestonePos"));
                  }
               }

               return null;
            }

            private double getFrameRotation(ItemFrame frame) {
               Direction direction = frame.m_6350_();
               int i = direction.m_122434_().m_122478_() ? 90 * direction.m_122421_().m_122540_() : 0;
               return (double)Mth.m_14098_(180 + direction.m_122416_() * 90 + frame.m_31823_() * 45 + i);
            }
         }
      );
   }

   private double getAngleTo(Vec3 pos, Entity entity) {
      return Math.atan2(pos.m_7094_() - entity.m_20189_(), pos.m_7096_() - entity.m_20185_());
   }

   @OnlyIn(Dist.CLIENT)
   private static class CompassWobble {
      double needleMobility;
      double rotation;
      private double deltaRotation;
      private long lastUpdateTick;

      CompassWobble(double needleMobility) {
         this.needleMobility = needleMobility;
      }

      boolean shouldUpdate(long pGameTime) {
         return this.lastUpdateTick != pGameTime;
      }

      void update(long gameTime, double wobbleAmount) {
         this.lastUpdateTick = gameTime;
         double var = wobbleAmount - this.rotation;
         var = Mth.m_14109_(var + 0.5, 1.0) - 0.5;
         this.deltaRotation = this.deltaRotation + var * this.needleMobility;
         this.deltaRotation *= 0.8;
         this.rotation = Mth.m_14109_(this.rotation + this.deltaRotation, 1.0);
      }
   }
}
