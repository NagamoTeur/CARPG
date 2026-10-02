package net.cisco.procedures;

import javax.annotation.Nullable;
import net.cisco.CiscoModMod;
import net.cisco.init.CiscoModModItems;
import net.cisco.network.CiscoModModVariables;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber
public class DarksteelSetBonusProcedure {
   @SubscribeEvent
   public static void onEntityAttacked(LivingHurtEvent event) {
      if (event != null && event.getEntity() != null) {
         execute(
            event,
            event.getEntity().f_19853_,
            event.getEntity().m_20185_(),
            event.getEntity().m_20186_(),
            event.getEntity().m_20189_(),
            event.getEntity(),
            (double)event.getAmount()
         );
      }
   }

   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double amount) {
      execute(null, world, x, y, z, entity, amount);
   }

   private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, double amount) {
      if (entity != null) {
         if ((entity instanceof LivingEntity _entGetArmorxxx ? _entGetArmorxxx.m_6844_(EquipmentSlot.FEET) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.DARKSTEEL_ARMOR_BOOTS.get()
            && (entity instanceof LivingEntity _entGetArmorxx ? _entGetArmorxx.m_6844_(EquipmentSlot.LEGS) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.DARKSTEEL_ARMOR_LEGGINGS.get()
            && (entity instanceof LivingEntity _entGetArmorx ? _entGetArmorx.m_6844_(EquipmentSlot.CHEST) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.DARKSTEEL_ARMOR_CHESTPLATE.get()
            && (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.m_6844_(EquipmentSlot.HEAD) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.DARKSTEEL_ARMOR_HELMET.get()
            && amount > (double)(entity instanceof LivingEntity _livEnt ? _livEnt.m_21233_() : -1.0F)
            && !((CiscoModModVariables.PlayerVariables)entity.getCapability(CiscoModModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                  .orElse(new CiscoModModVariables.PlayerVariables()))
               .DarkProtectionUsed) {
            if ((new Object() {
                  public boolean checkGamemode(Entity _ent) {
                     if (_ent instanceof ServerPlayer _serverPlayer) {
                        return _serverPlayer.f_8941_.m_9290_() == GameType.SURVIVAL;
                     } else {
                        return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                           ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                              && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.SURVIVAL
                           : false;
                     }
                  }
               })
               .checkGamemode(entity)) {
               ((LivingHurtEvent)event).setAmount(0.0F);
               if (world instanceof Level _level) {
                  if (!_level.m_5776_()) {
                     _level.m_5594_(
                        null,
                        new BlockPos(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.totem.use")),
                        SoundSource.NEUTRAL,
                        1.0F,
                        1.0F
                     );
                  } else {
                     _level.m_7785_(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.totem.use")),
                        SoundSource.NEUTRAL,
                        1.0F,
                        1.0F,
                        false
                     );
                  }
               }

               if (entity instanceof LivingEntity _entity) {
                  _entity.m_21153_(10.0F);
               }
            }

            entity.m_20331_(true);
            CiscoModMod.queueServerWork(60, () -> entity.m_20331_(false));
            boolean _setval = true;
            entity.getCapability(CiscoModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
               capability.DarkProtectionUsed = _setval;
               capability.syncPlayerVariables(entity);
            });
            CiscoModMod.queueServerWork(6000, () -> {
               boolean _setvalx = false;
               entity.getCapability(CiscoModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                  capability.DarkProtectionUsed = _setvalx;
                  capability.syncPlayerVariables(entity);
               });
            });
         }
      }
   }
}
