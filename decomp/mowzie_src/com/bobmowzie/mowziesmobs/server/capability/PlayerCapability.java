package com.bobmowzie.mowziesmobs.server.capability;

import com.bobmowzie.mowziesmobs.MowziesMobs;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoFirstPersonRenderer;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoPlayer;
import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ability.PlayerAbility;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaFollowerToPlayer;
import com.bobmowzie.mowziesmobs.server.item.ItemEarthTalisman;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import com.bobmowzie.mowziesmobs.server.message.mouse.MessageLeftMouseDown;
import com.bobmowzie.mowziesmobs.server.message.mouse.MessageLeftMouseUp;
import com.bobmowzie.mowziesmobs.server.message.mouse.MessageRightMouseDown;
import com.bobmowzie.mowziesmobs.server.message.mouse.MessageRightMouseUp;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import com.bobmowzie.mowziesmobs.server.power.Power;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.fml.LogicalSide;

public class PlayerCapability {
   public static ResourceLocation ID = new ResourceLocation("mowziesmobs", "player_cap");

   public interface IPlayerCapability extends INBTSerializable<CompoundTag> {
      Power[] getPowers();

      void tick(PlayerTickEvent var1);

      void addedToWorld(EntityJoinLevelEvent var1);

      boolean isVerticalSwing();

      void setVerticalSwing(boolean var1);

      int getUntilSunstrike();

      void setUntilSunstrike(int var1);

      int getUntilAxeSwing();

      void setUntilAxeSwing(int var1);

      void setAxeCanAttack(boolean var1);

      boolean getAxeCanAttack();

      boolean isMouseRightDown();

      void setMouseRightDown(boolean var1);

      boolean isMouseLeftDown();

      void setMouseLeftDown(boolean var1);

      boolean isPrevSneaking();

      void setPrevSneaking(boolean var1);

      int getTribeCircleTick();

      void setTribeCircleTick(int var1);

      List<EntityUmvuthanaFollowerToPlayer> getTribePack();

      void setTribePack(List<EntityUmvuthanaFollowerToPlayer> var1);

      int getTribePackRadius();

      void setTribePackRadius(int var1);

      int getPackSize();

      Vec3 getPrevMotion();

      void removePackMember(EntityUmvuthanaFollowerToPlayer var1);

      void addPackMember(EntityUmvuthanaFollowerToPlayer var1);

      void setUsingSolarBeam(boolean var1);

      boolean getUsingSolarBeam();

      float getPrevCooledAttackStrength();

      void setPrevCooledAttackStrength(float var1);

      @OnlyIn(Dist.CLIENT)
      GeckoPlayer.GeckoPlayerThirdPerson getGeckoPlayer();
   }

   public static class PlayerCapabilityImp implements PlayerCapability.IPlayerCapability {
      public boolean verticalSwing = false;
      public int untilSunstrike = 0;
      public int untilAxeSwing = 0;
      private int prevTime;
      private int time;
      public boolean mouseRightDown = false;
      public boolean mouseLeftDown = false;
      public boolean prevSneaking;
      private float prevCooledAttackStrength;
      public int tribeCircleTick;
      public List<EntityUmvuthanaFollowerToPlayer> tribePack = new ArrayList<>();
      public int tribePackRadius = 3;
      @OnlyIn(Dist.CLIENT)
      private GeckoPlayer.GeckoPlayerThirdPerson geckoPlayer;
      private boolean usingSolarBeam;
      public boolean axeCanAttack;
      public Vec3 prevMotion;
      public Power[] powers = new Power[0];

      @Override
      public boolean isVerticalSwing() {
         return this.verticalSwing;
      }

      @Override
      public void setVerticalSwing(boolean verticalSwing) {
         this.verticalSwing = verticalSwing;
      }

      @Override
      public int getUntilSunstrike() {
         return this.untilSunstrike;
      }

      @Override
      public void setUntilSunstrike(int untilSunstrike) {
         this.untilSunstrike = untilSunstrike;
      }

      @Override
      public int getUntilAxeSwing() {
         return this.untilAxeSwing;
      }

      @Override
      public void setUntilAxeSwing(int untilAxeSwing) {
         this.untilAxeSwing = untilAxeSwing;
      }

      @Override
      public void setAxeCanAttack(boolean axeCanAttack) {
         this.axeCanAttack = axeCanAttack;
      }

      @Override
      public boolean getAxeCanAttack() {
         return this.axeCanAttack;
      }

      @Override
      public boolean isMouseRightDown() {
         return this.mouseRightDown;
      }

      @Override
      public void setMouseRightDown(boolean mouseRightDown) {
         this.mouseRightDown = mouseRightDown;
      }

      @Override
      public boolean isMouseLeftDown() {
         return this.mouseLeftDown;
      }

      @Override
      public void setMouseLeftDown(boolean mouseLeftDown) {
         this.mouseLeftDown = mouseLeftDown;
      }

      @Override
      public boolean isPrevSneaking() {
         return this.prevSneaking;
      }

      @Override
      public void setPrevSneaking(boolean prevSneaking) {
         this.prevSneaking = prevSneaking;
      }

      @Override
      public int getTribeCircleTick() {
         return this.tribeCircleTick;
      }

      @Override
      public void setTribeCircleTick(int tribeCircleTick) {
         this.tribeCircleTick = tribeCircleTick;
      }

      @Override
      public List<EntityUmvuthanaFollowerToPlayer> getTribePack() {
         return this.tribePack;
      }

      @Override
      public void setTribePack(List<EntityUmvuthanaFollowerToPlayer> tribePack) {
         this.tribePack = tribePack;
      }

      @Override
      public int getTribePackRadius() {
         return this.tribePackRadius;
      }

      @Override
      public void setTribePackRadius(int tribePackRadius) {
         this.tribePackRadius = tribePackRadius;
      }

      @Override
      public Vec3 getPrevMotion() {
         return this.prevMotion;
      }

      @Override
      public void setUsingSolarBeam(boolean b) {
         this.usingSolarBeam = b;
      }

      @Override
      public boolean getUsingSolarBeam() {
         return this.usingSolarBeam;
      }

      @Override
      public float getPrevCooledAttackStrength() {
         return this.prevCooledAttackStrength;
      }

      @Override
      public void setPrevCooledAttackStrength(float cooledAttackStrength) {
         this.prevCooledAttackStrength = cooledAttackStrength;
      }

      @OnlyIn(Dist.CLIENT)
      @Override
      public GeckoPlayer.GeckoPlayerThirdPerson getGeckoPlayer() {
         return this.geckoPlayer;
      }

      @Override
      public void addedToWorld(EntityJoinLevelEvent event) {
         if (event.getLevel().m_5776_()) {
            Player player = (Player)event.getEntity();
            this.geckoPlayer = new GeckoPlayer.GeckoPlayerThirdPerson(player);
            if (event.getEntity() == Minecraft.m_91087_().f_91074_) {
               GeckoFirstPersonRenderer.GECKO_PLAYER_FIRST_PERSON = new GeckoPlayer.GeckoPlayerFirstPerson(player);
            }
         }
      }

      @Override
      public void tick(PlayerTickEvent event) {
         Player player = event.player;
         this.tribeCircleTick++;
         this.prevMotion = player.m_20182_().m_82546_(new Vec3(player.f_19854_, player.f_19855_, player.f_19856_));
         this.prevTime = this.time;
         if (this.untilSunstrike > 0) {
            this.untilSunstrike--;
         }

         if (this.untilAxeSwing > 0) {
            this.untilAxeSwing--;
         }

         if (event.side == LogicalSide.SERVER) {
            for (ItemStack itemStack : event.player.m_150109_().f_35974_) {
               if (itemStack.m_41720_() instanceof ItemEarthTalisman) {
                  player.m_7292_(new MobEffectInstance((MobEffect)EffectHandler.GEOMANCY.get(), 20, 0, false, false));
               }
            }

            if (player.m_21206_().m_41720_() instanceof ItemEarthTalisman) {
               player.m_7292_(new MobEffectInstance((MobEffect)EffectHandler.GEOMANCY.get(), 20, 0, false, false));
            }

            List<EntityUmvuthanaFollowerToPlayer> pack = this.tribePack;
            float theta = (float) (Math.PI * 2) / (float)pack.size();

            for (int i = 0; i < pack.size(); i++) {
               EntityUmvuthanaFollowerToPlayer barakoan = pack.get(i);
               barakoan.index = i;
               float distanceToPlayer = player.m_20270_(barakoan);
               if (barakoan.m_5448_() == null && barakoan.getActiveAbility() == null) {
                  if (distanceToPlayer > 4.0F) {
                     barakoan.m_21573_()
                        .m_26519_(
                           player.m_20185_() + (double)((float)this.tribePackRadius * Mth.m_14089_(theta * (float)i)),
                           player.m_20186_(),
                           player.m_20189_() + (double)((float)this.tribePackRadius * Mth.m_14031_(theta * (float)i)),
                           0.45
                        );
                  } else {
                     barakoan.m_21573_().m_26573_();
                  }

                  if (distanceToPlayer > 20.0F && player.m_20096_()) {
                     this.tryTeleportBarakoan(player, barakoan);
                  }
               }
            }
         }

         Ability iceBreathAbility = AbilityHandler.INSTANCE.getAbility(player, AbilityHandler.ICE_BREATH_ABILITY);
         if (iceBreathAbility != null && !iceBreathAbility.isUsing()) {
            for (ItemStack stack : player.m_150109_().f_35974_) {
               this.restoreIceCrystalStack(player, stack);
            }

            for (ItemStack stack : player.m_150109_().f_35976_) {
               this.restoreIceCrystalStack(player, stack);
            }
         }

         this.useIceCrystalStack(player);
         if (event.side == LogicalSide.CLIENT) {
            if (Minecraft.m_91087_().f_91066_.f_92096_.m_90857_() && !this.mouseLeftDown) {
               this.mouseLeftDown = true;
               MowziesMobs.NETWORK.sendToServer(new MessageLeftMouseDown());

               for (int ix = 0; ix < this.powers.length; ix++) {
                  this.powers[ix].onLeftMouseDown(player);
               }

               AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(player);
               if (abilityCapability != null) {
                  for (Ability ability : abilityCapability.getAbilities()) {
                     if (ability instanceof PlayerAbility) {
                        ((PlayerAbility)ability).onLeftMouseDown(player);
                     }
                  }
               }
            }

            if (Minecraft.m_91087_().f_91066_.f_92095_.m_90857_() && !this.mouseRightDown) {
               this.mouseRightDown = true;
               MowziesMobs.NETWORK.sendToServer(new MessageRightMouseDown());

               for (int ix = 0; ix < this.powers.length; ix++) {
                  this.powers[ix].onRightMouseDown(player);
               }

               AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(player);
               if (abilityCapability != null) {
                  for (Ability abilityx : abilityCapability.getAbilities()) {
                     if (abilityx instanceof PlayerAbility) {
                        ((PlayerAbility)abilityx).onRightMouseDown(player);
                     }
                  }
               }
            }

            if (!Minecraft.m_91087_().f_91066_.f_92096_.m_90857_() && this.mouseLeftDown) {
               this.mouseLeftDown = false;
               MowziesMobs.NETWORK.sendToServer(new MessageLeftMouseUp());

               for (int ix = 0; ix < this.powers.length; ix++) {
                  this.powers[ix].onLeftMouseUp(player);
               }

               AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(player);
               if (abilityCapability != null) {
                  for (Ability abilityxx : abilityCapability.getAbilities()) {
                     if (abilityxx instanceof PlayerAbility) {
                        ((PlayerAbility)abilityxx).onLeftMouseUp(player);
                     }
                  }
               }
            }

            if (!Minecraft.m_91087_().f_91066_.f_92095_.m_90857_() && this.mouseRightDown) {
               this.mouseRightDown = false;
               MowziesMobs.NETWORK.sendToServer(new MessageRightMouseUp());

               for (int ix = 0; ix < this.powers.length; ix++) {
                  this.powers[ix].onRightMouseUp(player);
               }

               AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(player);
               if (abilityCapability != null) {
                  for (Ability abilityxxx : abilityCapability.getAbilities()) {
                     if (abilityxxx instanceof PlayerAbility) {
                        ((PlayerAbility)abilityxxx).onRightMouseUp(player);
                     }
                  }
               }
            }
         }

         if (player.m_6144_() && !this.prevSneaking) {
            for (int ix = 0; ix < this.powers.length; ix++) {
               this.powers[ix].onSneakDown(player);
            }

            AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(player);
            if (abilityCapability != null) {
               for (Ability abilityxxxx : abilityCapability.getAbilities()) {
                  if (abilityxxxx instanceof PlayerAbility) {
                     ((PlayerAbility)abilityxxxx).onSneakDown(player);
                  }
               }
            }
         } else if (!player.m_6144_() && this.prevSneaking) {
            for (int ix = 0; ix < this.powers.length; ix++) {
               this.powers[ix].onSneakUp(player);
            }

            AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(player);
            if (abilityCapability != null) {
               for (Ability abilityxxxxx : abilityCapability.getAbilities()) {
                  if (abilityxxxxx instanceof PlayerAbility) {
                     ((PlayerAbility)abilityxxxxx).onSneakUp(player);
                  }
               }
            }
         }

         this.prevSneaking = player.m_6144_();
      }

      private void restoreIceCrystalStack(Player entity, ItemStack stack) {
         if (stack.m_41720_() == ItemHandler.ICE_CRYSTAL && !(Boolean)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.ICE_CRYSTAL.breakable.get()) {
            stack.m_41721_(Math.max(stack.m_41773_() - 1, 0));
         }
      }

      private void useIceCrystalStack(Player player) {
         ItemStack stack = player.m_21211_();
         if (stack.m_41720_() == ItemHandler.ICE_CRYSTAL) {
            Ability iceBreathAbility = AbilityHandler.INSTANCE.getAbility(player, AbilityHandler.ICE_BREATH_ABILITY);
            if (iceBreathAbility != null && iceBreathAbility.isUsing()) {
               InteractionHand handIn = player.m_7655_();
               if (stack.m_41773_() + 5 < stack.m_41776_()) {
                  stack.m_41622_(5, player, p -> p.m_21190_(handIn));
               } else {
                  if ((Boolean)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.ICE_CRYSTAL.breakable.get()) {
                     stack.m_41622_(5, player, p -> p.m_21190_(handIn));
                  }

                  iceBreathAbility.end();
               }
            }
         }
      }

      private void tryTeleportBarakoan(Player player, EntityUmvuthanaFollowerToPlayer barakoan) {
         int x = Mth.m_14107_(player.m_20185_()) - 2;
         int z = Mth.m_14107_(player.m_20189_()) - 2;
         int y = Mth.m_14107_(player.m_20191_().f_82289_);

         for (int l = 0; l <= 4; l++) {
            for (int i1 = 0; i1 <= 4; i1++) {
               if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && barakoan.isTeleportFriendlyBlock(x, z, y, l, i1)) {
                  barakoan.m_7678_((double)((float)(x + l) + 0.5F), (double)y, (double)((float)(z + i1) + 0.5F), barakoan.m_146908_(), barakoan.m_146909_());
                  barakoan.m_21573_().m_26573_();
                  return;
               }
            }
         }
      }

      public int getTick() {
         return this.time;
      }

      public void decrementTime() {
         this.time--;
      }

      @Override
      public int getPackSize() {
         return this.tribePack.size();
      }

      @Override
      public void removePackMember(EntityUmvuthanaFollowerToPlayer tribePlayer) {
         this.tribePack.remove(tribePlayer);
      }

      @Override
      public void addPackMember(EntityUmvuthanaFollowerToPlayer tribePlayer) {
         this.tribePack.add(tribePlayer);
      }

      @Override
      public Power[] getPowers() {
         return this.powers;
      }

      public CompoundTag serializeNBT() {
         CompoundTag compound = new CompoundTag();
         compound.m_128405_("untilSunstrike", this.untilSunstrike);
         compound.m_128405_("untilAxeSwing", this.untilAxeSwing);
         compound.m_128405_("prevTime", this.prevTime);
         compound.m_128405_("time", this.time);
         return compound;
      }

      public void deserializeNBT(CompoundTag compound) {
         this.untilSunstrike = compound.m_128451_("untilSunstrike");
         this.untilAxeSwing = compound.m_128451_("untilAxeSwing");
         this.prevTime = compound.m_128451_("prevTime");
         this.time = compound.m_128451_("time");
      }
   }

   public static class PlayerProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {
      private final LazyOptional<PlayerCapability.IPlayerCapability> instance = LazyOptional.of(PlayerCapability.PlayerCapabilityImp::new);

      public CompoundTag serializeNBT() {
         return (CompoundTag)((PlayerCapability.IPlayerCapability)this.instance.orElseThrow(NullPointerException::new)).serializeNBT();
      }

      public void deserializeNBT(CompoundTag nbt) {
         ((PlayerCapability.IPlayerCapability)this.instance.orElseThrow(NullPointerException::new)).deserializeNBT(nbt);
      }

      @Nonnull
      public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
         return CapabilityHandler.PLAYER_CAPABILITY.orEmpty(cap, this.instance.cast());
      }
   }
}
