package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBaseCurio;
import com.aizistral.enigmaticlegacy.objects.TransientPlayerData;
import com.aizistral.enigmaticlegacy.objects.Vector3;
import com.aizistral.enigmaticlegacy.packets.server.PacketUpdateElytraBoosting;
import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Wearable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PacketDistributor.PacketTarget;
import net.minecraftforge.network.simple.SimpleChannel;
import top.theillusivec4.caelus.api.CaelusApi;
import top.theillusivec4.curios.api.SlotContext;

public class EnigmaticElytra extends ItemBaseCurio implements Wearable {
   private static final AttributeModifier ELYTRA_MODIFIER = new AttributeModifier(
      UUID.fromString("44dfce5a-2f09-4f19-bc29-9b0324cd2a40"), "enigmaticlegacy:elytra_modifier", 1.0, Operation.ADDITION
   );
   @OnlyIn(Dist.CLIENT)
   private static boolean isBoosting;

   public EnigmaticElytra() {
      super(ItemBaseCurio.getDefaultProperties().m_41503_(5000).m_41486_().m_41497_(Rarity.EPIC));
      DispenserBlock.m_52672_(this, ArmorItem.f_40376_);
      MinecraftForge.EVENT_BUS.register(this);
   }

   public EquipmentSlot getEquipmentSlot(ItemStack stack) {
      return EquipmentSlot.CHEST;
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, Level world, List<Component> list, TooltipFlag flag) {
      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.enigmaticElytra1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.enigmaticElytra2");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.enigmaticElytra3");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }
   }

   @Override
   public void curioTick(SlotContext context, ItemStack stack) {
      if (context.entity() instanceof Player player && player.f_19853_.f_46443_) {
         this.handleBoosting(player);
      }

      LivingEntity livingEntity = context.entity();
      int ticks = livingEntity.m_21256_();
      if (ticks > 0 && livingEntity.m_21255_()) {
         stack.elytraFlightTick(livingEntity, ticks);
      }
   }

   @OnlyIn(Dist.CLIENT)
   private void handleBoosting(Player player) {
      if (Minecraft.m_91087_().f_91074_ == player) {
         if (Minecraft.m_91087_().f_91066_.f_92089_.m_90857_() && this.boostPlayer(player)) {
            if (!isBoosting) {
               SimpleChannel var2 = EnigmaticLegacy.packetInstance;
               PacketTarget var3 = PacketDistributor.SERVER.noArg();
               isBoosting = true;
               var2.send(var3, new PacketUpdateElytraBoosting(true));
            }
         } else if (isBoosting) {
            SimpleChannel var10000 = EnigmaticLegacy.packetInstance;
            PacketTarget var10001 = PacketDistributor.SERVER.noArg();
            isBoosting = false;
            var10000.send(var10001, new PacketUpdateElytraBoosting(false));
         }
      }
   }

   private boolean boostPlayer(Player player) {
      if (player.m_21255_()) {
         Vec3 vec31 = player.m_20154_();
         Vec3 vec32 = player.m_20184_();
         player.m_20256_(
            vec32.m_82520_(
               vec31.f_82479_ * 0.1 + (vec31.f_82479_ * 1.5 - vec32.f_82479_) * 0.5,
               vec31.f_82480_ * 0.1 + (vec31.f_82480_ * 1.5 - vec32.f_82480_) * 0.5,
               vec31.f_82481_ * 0.1 + (vec31.f_82481_ * 1.5 - vec32.f_82481_) * 0.5
            )
         );
         return true;
      } else {
         return false;
      }
   }

   public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
      Multimap<Attribute, AttributeModifier> attributes = HashMultimap.create();
      return attributes;
   }

   public boolean m_6832_(ItemStack repairedItem, ItemStack material) {
      return material.m_150930_(EnigmaticItems.ETHERIUM_INGOT);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      EquipmentSlot equipmentslot = Mob.m_147233_(itemstack);
      ItemStack itemstack1 = player.m_6844_(equipmentslot);
      if (itemstack1.m_41619_()) {
         player.m_8061_(equipmentslot, itemstack.m_41777_());
         if (!level.m_5776_()) {
            player.m_36246_(Stats.f_12982_.m_12902_(this));
         }

         itemstack.m_41764_(0);
         return InteractionResultHolder.m_19092_(itemstack, level.m_5776_());
      } else {
         return InteractionResultHolder.m_19100_(itemstack);
      }
   }

   public SoundEvent m_142602_() {
      return SoundEvents.f_11674_;
   }

   public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
      return entity instanceof Player && ElytraItem.m_41140_(stack);
   }

   public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
      if (entity instanceof Player player) {
         if (!entity.f_19853_.f_46443_) {
            int nextFlightTick = flightTicks + 1;
            if (nextFlightTick % 10 == 0) {
               if (nextFlightTick % 20 == 0) {
                  stack.m_41622_(1, entity, e -> e.m_21166_(EquipmentSlot.CHEST));
               }

               entity.m_146850_(GameEvent.f_223705_);
            }
         } else {
            this.handleBoosting(player);
         }

         return true;
      } else {
         return false;
      }
   }

   @SubscribeEvent
   public void onPlayerTick(PlayerTickEvent event) {
      if (event.phase == Phase.START) {
         ItemStack stack = null;
         AttributeInstance attribute = event.player.m_21051_(CaelusApi.getInstance().getFlightAttribute());
         attribute.m_22130_(ELYTRA_MODIFIER);
         if (!attribute.m_22109_(ELYTRA_MODIFIER)) {
            stack = SuperpositionHandler.getEnigmaticElytra(event.player);
            if (stack != null && stack.m_150930_(this) && ElytraItem.m_41140_(stack)) {
               attribute.m_22118_(ELYTRA_MODIFIER);
            }
         }

         if (event.player instanceof ServerPlayer player && TransientPlayerData.get(player).isElytraBoosting()) {
            this.boostPlayer(player);
            if (stack != null && stack.m_150930_(this)) {
               int flightTicks = player.m_21256_();
               int nextFlightTick = flightTicks + 1;
               if (nextFlightTick % 5 == 0) {
                  stack.m_41622_(1, player, e -> e.m_21166_(EquipmentSlot.CHEST));
               }
            }
         }
      }
   }

   @SubscribeEvent
   @OnlyIn(Dist.CLIENT)
   public void onPlayerTickClient(PlayerTickEvent event) {
      if (event.phase == Phase.START && event.player.f_19853_.m_5776_()) {
         Player player = event.player;
         if (TransientPlayerData.get(player).isElytraBoosting()) {
            if (!player.m_21255_()) {
               if (event.player == Minecraft.m_91087_().f_91074_) {
                  this.handleBoosting(player);
                  return;
               }

               TransientPlayerData.get(player).setElytraBoosting(false);
               return;
            }

            int amount = 3;
            double rangeModifier = 0.1;

            for (int counter = 0; counter <= amount; counter++) {
               Vector3 vec = Vector3.fromEntityCenter(player);
               vec = vec.add(Math.random() - 0.5, -1.0 + Math.random() - 0.5, Math.random() - 0.5);
               player.f_19853_
                  .m_6493_(
                     ParticleTypes.f_123799_,
                     true,
                     vec.x,
                     vec.y,
                     vec.z,
                     (Math.random() - 0.5) * 2.0 * rangeModifier,
                     (Math.random() - 0.5) * 2.0 * rangeModifier,
                     (Math.random() - 0.5) * 2.0 * rangeModifier
                  );
            }
         }
      }
   }
}
