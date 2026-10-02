package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.api.generic.SubscribeConfig;
import com.aizistral.enigmaticlegacy.api.items.ISpellstone;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemSpellstoneCurio;
import com.aizistral.enigmaticlegacy.objects.TransientPlayerData;
import com.aizistral.enigmaticlegacy.objects.Vector3;
import com.aizistral.enigmaticlegacy.packets.clients.PacketPlayerSetlook;
import com.aizistral.enigmaticlegacy.packets.clients.PacketPortalParticles;
import com.aizistral.enigmaticlegacy.packets.clients.PacketRecallParticles;
import com.aizistral.omniconfig.wrappers.Omniconfig;
import com.aizistral.omniconfig.wrappers.OmniconfigWrapper;
import java.util.List;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PacketDistributor.TargetPoint;

public class EyeOfNebula extends ItemSpellstoneCurio implements ISpellstone {
   public static Omniconfig.IntParameter spellstoneCooldown;
   public static Omniconfig.PerhapsParameter dodgeProbability;
   public static Omniconfig.DoubleParameter dodgeRange;
   public static Omniconfig.DoubleParameter phaseRange;
   public static Omniconfig.PerhapsParameter magicResistance;
   public static Omniconfig.PerhapsParameter magicBoost;
   public static Omniconfig.PerhapsParameter attackEmpower;

   @SubscribeConfig
   public static void onConfig(OmniconfigWrapper builder) {
      builder.pushPrefix("EyeOfNebula");
      spellstoneCooldown = builder.comment("Active ability cooldown for Eye of the Nebula. Measured in ticks. 20 ticks equal to 1 second.")
         .getInt("Cooldown", 60);
      dodgeProbability = builder.comment(
            "Probability for Eye of the Nebula to teleport it's bearer from any attack without receiving any damage. Defined as percentage."
         )
         .max(100.0)
         .getPerhaps("DodgeChance", 15);
      dodgeRange = builder.comment("Range in which Eye of the Nebula searches for a position to teleport it's bearer to when dodging the attack.")
         .min(1.0)
         .max(128.0)
         .getDouble("DodgeRange", 16.0);
      phaseRange = builder.comment("Range in which Eye of the Nebula can reach an entity when using it's active ability.")
         .min(1.0)
         .max(128.0)
         .getDouble("PhaseRange", 32.0);
      magicResistance = builder.comment("Magic Damage resistance provided by Eye of the Nebula. Defined as percentage.")
         .max(100.0)
         .getPerhaps("MagicResistance", 65);
      magicBoost = builder.comment("Magic Damage boost provided by Eye of the Nebula. Defined as percentage.").getPerhaps("MagicBoost", 40);
      attackEmpower = builder.comment("Attack damage increase for next attack after using active ability. Defined as percentage.")
         .getPerhaps("AttackEmpower", 150);
      builder.popPrefix();
   }

   public EyeOfNebula() {
      super(ItemSpellstoneCurio.getDefaultProperties().m_41497_(Rarity.EPIC));
      Supplier<Float> magicResistanceSupplier = () -> magicResistance.getValue().asModifierInverted();
      this.resistanceList.put(DamageSource.f_19319_.m_19385_(), magicResistanceSupplier);
      this.resistanceList.put(DamageSource.f_19323_.m_19385_(), magicResistanceSupplier);
      this.resistanceList.put(DamageSource.f_19320_.m_19385_(), magicResistanceSupplier);
   }

   @Override
   public int getCooldown(Player player) {
      return player != null && reducedCooldowns.test(player) ? 30 : spellstoneCooldown.getValue();
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.eyeOfNebula1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.eyeOfNebula2");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(
            list, "tooltip.enigmaticlegacy.eyeOfNebulaCooldown", ChatFormatting.GOLD, (float)this.getCooldown(Minecraft.m_91087_().f_91074_) / 20.0F
         );
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.eyeOfNebula3");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.eyeOfNebula4", ChatFormatting.GOLD, magicBoost.getValue().asPercentage() + "%");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.eyeOfNebula5", ChatFormatting.GOLD, magicResistance.getValue().asPercentage() + "%");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.eyeOfNebula6", ChatFormatting.GOLD, dodgeProbability.getValue().asPercentage() + "%");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.eyeOfNebula7");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.eyeOfNebula8", ChatFormatting.GOLD, attackEmpower.getValue().asPercentage() + "%");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.eyeOfNebula9");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.eyeOfNebula10");
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
         LivingEntity target = SuperpositionHandler.getObservedEntity(player, world, 3.0F, (int)phaseRange.getValue());
         if (target != null) {
            Vector3 targetPos = Vector3.fromEntityCenter(target);
            Vector3 chaserPos = Vector3.fromEntityCenter(player);
            Vector3 dir = targetPos.subtract(chaserPos);
            dir = dir.normalize();
            dir = dir.multiply(1.5);
            dir = targetPos.add(dir);
            world.m_5594_(null, player.m_20183_(), SoundEvents.f_11852_, SoundSource.PLAYERS, 1.0F, (float)(0.8F + Math.random() * 0.2));
            EnigmaticLegacy.packetInstance
               .send(
                  PacketDistributor.NEAR
                     .with(() -> new TargetPoint(player.m_20185_(), player.m_20186_(), player.m_20189_(), 128.0, player.f_19853_.m_46472_())),
                  new PacketPortalParticles(player.m_20185_(), player.m_20186_() + (double)(player.m_20206_() / 2.0F), player.m_20189_(), 72, 1.0, false)
               );
            player.m_6021_(dir.x, target.m_20186_() + 0.25, dir.z);
            EnigmaticLegacy.packetInstance
               .send(
                  PacketDistributor.PLAYER.with(() -> player),
                  new PacketPlayerSetlook(target.m_20185_(), target.m_20186_() - 1.0 + (double)(target.m_20206_() / 2.0F), target.m_20189_())
               );
            world.m_5594_(null, player.m_20183_(), SoundEvents.f_11852_, SoundSource.PLAYERS, 1.0F, (float)(0.8F + Math.random() * 0.2));
            EnigmaticLegacy.packetInstance
               .send(
                  PacketDistributor.NEAR
                     .with(() -> new TargetPoint(player.m_20185_(), player.m_20186_(), player.m_20189_(), 128.0, player.f_19853_.m_46472_())),
                  new PacketRecallParticles(player.m_20185_(), player.m_20186_() + (double)(player.m_20206_() / 2.0F), player.m_20189_(), 24, false)
               );
            SuperpositionHandler.setSpellstoneCooldown(player, this.getCooldown(player));
            TransientPlayerData.get(player).setEyeOfNebulaPower(true);
         }
      }
   }
}
