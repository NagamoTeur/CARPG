package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.api.generic.SubscribeConfig;
import com.aizistral.enigmaticlegacy.api.items.ISpellstone;
import com.aizistral.enigmaticlegacy.entities.UltimateWitherSkullEntity;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBaseCurio;
import com.aizistral.enigmaticlegacy.items.generic.ItemSpellstoneCurio;
import com.aizistral.enigmaticlegacy.objects.Vector3;
import com.aizistral.enigmaticlegacy.packets.clients.PacketWitherParticles;
import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import com.aizistral.omniconfig.wrappers.Omniconfig;
import com.aizistral.omniconfig.wrappers.OmniconfigWrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
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
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.SlotContext;

public class EnigmaticItem extends ItemSpellstoneCurio implements ISpellstone {
   public static Omniconfig.IntParameter spellstoneCooldown;
   public Map<Player, Boolean> flightMap = new WeakHashMap<>();

   @SubscribeConfig
   public static void onConfig(OmniconfigWrapper builder) {
      builder.pushPrefix("EnigmaticItem");
      spellstoneCooldown = builder.comment("Active ability cooldown for Heart of Creation. Measured in ticks. 20 ticks equal to 1 second.")
         .getInt("Cooldown", 3);
      builder.popPrefix();
   }

   public EnigmaticItem() {
      super(ItemBaseCurio.getDefaultProperties().m_41497_(Rarity.EPIC).m_41486_());
      this.immunityList.add(DamageSource.f_19315_.f_19326_);
      this.immunityList.add(DamageSource.f_19316_.f_19326_);
      this.immunityList.add(DamageSource.f_19314_.f_19326_);
      this.immunityList.add(DamageSource.f_19311_.f_19326_);
      this.immunityList.add(DamageSource.f_19312_.f_19326_);
      this.immunityList.add(DamageSource.f_19309_.f_19326_);
      this.immunityList.add(DamageSource.f_19308_.f_19326_);
      this.immunityList.add(DamageSource.f_19305_.f_19326_);
      this.immunityList.add(DamageSource.f_19307_.f_19326_);
      this.immunityList.add(DamageSource.f_19310_.f_19326_);
      this.immunityList.add(DamageSource.f_19317_.f_19326_);
      this.immunityList.add(DamageSource.f_19313_.f_19326_);
      this.immunityList.add(DamageSource.f_19325_.f_19326_);
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.enigmaticItem1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.enigmaticItem2");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(
            list, "tooltip.enigmaticlegacy.enigmaticItemCooldown", ChatFormatting.GOLD, (float)spellstoneCooldown.getValue() / 20.0F
         );
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.enigmaticItem3");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.enigmaticItem4");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.enigmaticItem5");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.enigmaticItem6");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.enigmaticItem7");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.enigmaticItem8");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.enigmaticItem9");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.enigmaticItem10");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.enigmaticItem11");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }
   }

   @Override
   public void curioTick(SlotContext context, ItemStack stack) {
      if (context.entity().m_6060_()) {
         context.entity().m_20095_();
      }

      for (MobEffectInstance effect : new ArrayList(context.entity().m_21220_())) {
         if (!ForgeRegistries.MOB_EFFECTS.getKey(effect.m_19544_()).equals(new ResourceLocation("mana-and-artifice", "chrono-exhaustion"))
            && !effect.m_19544_().m_19486_()) {
            context.entity().m_21195_(effect.m_19544_());
         }
      }
   }

   public void handleEnigmaticFlight(Player player) {
      try {
         if (SuperpositionHandler.hasCurio(player, EnigmaticItems.ENIGMATIC_ITEM)) {
            this.flightMap.put(player, true);
            if (!player.m_150110_().f_35936_) {
               player.m_150110_().f_35936_ = true;
               player.m_6885_();
            }
         } else if (this.flightMap.get(player)) {
            if (!player.m_7500_()) {
               player.m_150110_().f_35936_ = false;
               player.m_150110_().f_35935_ = false;
               player.m_6885_();
            }

            this.flightMap.put(player, false);
         }
      } catch (NullPointerException var3) {
         this.flightMap.put(player, false);
      }
   }

   @Override
   public void triggerActiveAbility(Level world, ServerPlayer player, ItemStack stack) {
      if (!world.f_46443_ && !SuperpositionHandler.hasSpellstoneCooldown(player)) {
         this.launchWitherSkull(world, player, random.nextDouble() <= 0.25);
         SuperpositionHandler.setSpellstoneCooldown(player, spellstoneCooldown.getValue());
      }
   }

   private void launchWitherSkull(Level world, Player player, boolean invulnerable) {
      world.m_5898_((Player)null, 1024, new BlockPos(player.m_20182_()), 0);
      Vector3 look = new Vector3(player.m_20154_()).multiply(1.0, 0.0, 1.0);
      double playerRot = Math.toRadians((double)(player.m_146908_() + 90.0F));
      if (look.x == 0.0 && look.z == 0.0) {
         look = new Vector3(Math.cos(playerRot), 0.0, Math.sin(playerRot));
      }

      look = look.normalize().multiply(-2.0);
      double div = -0.75 + random.nextDouble() * 0.75;
      double mod = -0.5 + random.nextDouble() * 6.0;
      Vector3 pl = look.add(Vector3.fromEntityCenter(player)).add(0.0, 1.6, div * 0.1);
      Vector3 axis = look.normalize().crossProduct(new Vector3(-1.0, 0.0, -1.0)).normalize();
      double rot = mod * Math.PI / 4.0 - (Math.PI / 2);
      Vector3 axis1 = axis.multiply(div * 3.5 + 5.0).rotate(rot, look);
      if (axis1.y < 0.0) {
         axis1 = axis1.multiply(1.0, -1.0, 1.0);
      }

      Vector3 end = pl.add(axis1);
      LivingEntity doomedOne = SuperpositionHandler.getObservedEntity(player, world, 3.0F, 64);
      UltimateWitherSkullEntity witherskullentity = doomedOne != null
         ? new UltimateWitherSkullEntity(world, player, doomedOne)
         : new UltimateWitherSkullEntity(world, player);
      if (invulnerable) {
         witherskullentity.setSkullInvulnerable(true);
      }

      witherskullentity.m_6034_(end.x, end.y, end.z);
      world.m_7967_(witherskullentity);
      EnigmaticLegacy.packetInstance
         .send(
            PacketDistributor.NEAR
               .with(
                  () -> new TargetPoint(
                        witherskullentity.m_20185_(), witherskullentity.m_20186_(), witherskullentity.m_20189_(), 64.0, witherskullentity.f_19853_.m_46472_()
                     )
               ),
            new PacketWitherParticles(
               witherskullentity.m_20185_(),
               witherskullentity.m_20186_() + (double)(witherskullentity.m_20206_() / 2.0F),
               witherskullentity.m_20189_(),
               8,
               false
            )
         );
   }
}
