package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.api.generic.SubscribeConfig;
import com.aizistral.enigmaticlegacy.entities.UltimateWitherSkullEntity;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemSpellstoneCurio;
import com.aizistral.enigmaticlegacy.objects.Vector3;
import com.aizistral.enigmaticlegacy.packets.clients.PacketForceArrowRotations;
import com.aizistral.enigmaticlegacy.packets.clients.PacketPlayerMotion;
import com.aizistral.omniconfig.wrappers.Omniconfig;
import com.aizistral.omniconfig.wrappers.OmniconfigWrapper;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PacketDistributor.TargetPoint;
import top.theillusivec4.curios.api.SlotContext;

public class AngelBlessing extends ItemSpellstoneCurio {
   public static Omniconfig.IntParameter spellstoneCooldown;
   public static Omniconfig.DoubleParameter accelerationModifier;
   public static Omniconfig.DoubleParameter accelerationModifierElytra;
   public static Omniconfig.PerhapsParameter deflectChance;
   protected double range = 4.0;

   @SubscribeConfig
   public static void onConfig(OmniconfigWrapper builder) {
      builder.pushPrefix("AngelBlessing");
      spellstoneCooldown = builder.comment("Active ability cooldown for Angel's Blessing. Measured in ticks. 20 ticks equal to 1 second.")
         .getInt("Cooldown", 40);
      accelerationModifier = builder.comment(
            "Acceleration modifier for active ability of Angel's Blessing. The greater it is, the more momentum you will gain."
         )
         .max(256.0)
         .getDouble("AccelerationModifier", 1.0);
      accelerationModifierElytra = builder.comment("Separate acceleration modifier for active ability of Angel's Blessing when player is flying with Elytra.")
         .max(256.0)
         .getDouble("AccelerationModifierElytra", 0.6);
      deflectChance = builder.comment("Chance to deflect projectile when having Angel's Blessing equipped. Measured in percents.")
         .max(100.0)
         .getPerhaps("DeflectChance", 50);
      builder.popPrefix();
   }

   public AngelBlessing() {
      super(ItemSpellstoneCurio.getDefaultProperties().m_41497_(Rarity.RARE));
      this.immunityList.add(DamageSource.f_19315_.f_19326_);
      this.immunityList.add(DamageSource.f_19316_.f_19326_);
      this.resistanceList.put(DamageSource.f_19320_.f_19326_, () -> 2.0F);
      this.resistanceList.put(DamageSource.f_19317_.f_19326_, () -> 2.0F);
   }

   @Override
   public int getCooldown(Player player) {
      return player != null && reducedCooldowns.test(player) ? 15 : spellstoneCooldown.getValue();
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.angelBlessing1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.angelBlessing2");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(
            list, "tooltip.enigmaticlegacy.angelBlessingCooldown", ChatFormatting.GOLD, (float)this.getCooldown(Minecraft.m_91087_().f_91074_) / 20.0F
         );
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.angelBlessing3");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.angelBlessing4");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.angelBlessing5", ChatFormatting.GOLD, deflectChance.getValue().asPercentage() + "%");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.angelBlessing6");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.angelBlessing7");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.angelBlessing8");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.angelBlessing9");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }

      try {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(
            list,
            "tooltip.enigmaticlegacy.currentKeybind",
            ChatFormatting.LIGHT_PURPLE,
            ((Component)KeyMapping.m_90842_("key.spellstoneAbility").get()).getString().toUpperCase()
         );
      } catch (NullPointerException var6) {
      }
   }

   @Override
   public void triggerActiveAbility(Level world, ServerPlayer player, ItemStack stack) {
      if (!SuperpositionHandler.hasSpellstoneCooldown(player)) {
         Vector3 accelerationVec = new Vector3(player.m_20154_());
         Vector3 motionVec = new Vector3(player.m_20184_());
         if (player.m_21255_()) {
            accelerationVec = accelerationVec.multiply(accelerationModifierElytra.getValue());
            accelerationVec = accelerationVec.multiply(1.0 / (Math.max(0.15, motionVec.mag()) * 2.25));
         } else {
            accelerationVec = accelerationVec.multiply(accelerationModifier.getValue());
         }

         Vector3 finalMotion = new Vector3(motionVec.x + accelerationVec.x, motionVec.y + accelerationVec.y, motionVec.z + accelerationVec.z);
         EnigmaticLegacy.packetInstance.send(PacketDistributor.PLAYER.with(() -> player), new PacketPlayerMotion(finalMotion.x, finalMotion.y, finalMotion.z));
         player.m_20334_(finalMotion.x, finalMotion.y, finalMotion.z);
         world.m_5594_(null, player.m_20183_(), SoundEvents.f_11898_, SoundSource.PLAYERS, 1.0F, (float)(0.6F + Math.random() * 0.1));
         SuperpositionHandler.setSpellstoneCooldown(player, this.getCooldown(player));
      }
   }

   @Override
   public void curioTick(SlotContext context, ItemStack stack) {
      LivingEntity living = context.entity();
      if (!living.f_19853_.f_46443_) {
         List<AbstractHurtingProjectile> projectileEntities = living.f_19853_
            .m_45976_(
               AbstractHurtingProjectile.class,
               new AABB(
                  living.m_20185_() - this.range,
                  living.m_20186_() - this.range,
                  living.m_20189_() - this.range,
                  living.m_20185_() + this.range,
                  living.m_20186_() + this.range,
                  living.m_20189_() + this.range
               )
            );
         List<AbstractArrow> arrowEntities = living.f_19853_
            .m_45976_(
               AbstractArrow.class,
               new AABB(
                  living.m_20185_() - this.range,
                  living.m_20186_() - this.range,
                  living.m_20189_() - this.range,
                  living.m_20185_() + this.range,
                  living.m_20186_() + this.range,
                  living.m_20189_() + this.range
               )
            );
         List<ThrowableItemProjectile> potionEntities = living.f_19853_
            .m_45976_(
               ThrowableItemProjectile.class,
               new AABB(
                  living.m_20185_() - this.range,
                  living.m_20186_() - this.range,
                  living.m_20189_() - this.range,
                  living.m_20185_() + this.range,
                  living.m_20186_() + this.range,
                  living.m_20189_() + this.range
               )
            );

         for (AbstractHurtingProjectile entity : projectileEntities) {
            this.redirect(living, entity);
         }

         for (AbstractArrow entity : arrowEntities) {
            this.redirect(living, entity);
         }

         for (ThrowableItemProjectile entity : potionEntities) {
            this.redirect(living, entity);
         }
      }
   }

   public void redirect(LivingEntity bearer, Entity redirected) {
      if (!(redirected instanceof UltimateWitherSkullEntity) && !(redirected instanceof WitherSkull)) {
         Vector3 entityPos = Vector3.fromEntityCenter(redirected);
         Vector3 bearerPos = Vector3.fromEntityCenter(bearer);
         Vector3 redirection = entityPos.subtract(bearerPos);
         redirection = redirection.normalize();
         if (redirected instanceof AbstractArrow arrow && arrow.m_37282_() == bearer
            || redirected instanceof ThrowableItemProjectile projectile && projectile.m_37282_() == bearer) {
            if (redirected.m_19880_().contains("AB_ACCELERATED")) {
               if (redirected.f_19853_ instanceof ServerLevel var12) {
                  ;
               }

               return;
            }

            if (redirected.m_19880_().stream().anyMatch(tag -> tag.startsWith("AB_DEFLECTED"))) {
               return;
            }

            if (redirected instanceof ThrownTrident trident && trident.f_37557_ > 0) {
               return;
            }

            if (redirected.m_20049_("AB_ACCELERATED")) {
               redirected.m_20334_(redirected.m_20184_().f_82479_ * 1.75, redirected.m_20184_().f_82480_ * 1.75, redirected.m_20184_().f_82481_ * 1.75);
               if (redirected.f_19853_ instanceof ServerLevel level) {
                  EnigmaticLegacy.packetInstance
                     .send(
                        PacketDistributor.NEAR
                           .with(() -> new TargetPoint(bearer.m_20185_(), bearer.m_20186_(), bearer.m_20189_(), 64.0, bearer.f_19853_.m_46472_())),
                        new PacketForceArrowRotations(
                           redirected.m_19879_(),
                           redirected.m_146908_(),
                           redirected.m_146909_(),
                           redirected.m_20184_().f_82479_,
                           redirected.m_20184_().f_82480_,
                           redirected.m_20184_().f_82481_,
                           redirected.m_20185_(),
                           redirected.m_20186_(),
                           redirected.m_20189_()
                        )
                     );
               }
            }
         }
      }
   }
}
