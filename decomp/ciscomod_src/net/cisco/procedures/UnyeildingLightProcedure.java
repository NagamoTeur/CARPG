package net.cisco.procedures;

import javax.annotation.Nullable;
import net.cisco.CiscoModMod;
import net.cisco.init.CiscoModModItems;
import net.cisco.network.CiscoModModVariables;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber
public class UnyeildingLightProcedure {
   @SubscribeEvent
   public static void onEntityDeath(LivingDeathEvent event) {
      if (event != null && event.getEntity() != null) {
         execute(event, event.getEntity().f_19853_, event.getEntity().m_20185_(), event.getEntity().m_20186_(), event.getEntity().m_20189_(), event.getEntity());
      }
   }

   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      execute(null, world, x, y, z, entity);
   }

   private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if ((entity instanceof LivingEntity _entGetArmorxxx ? _entGetArmorxxx.m_6844_(EquipmentSlot.HEAD) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.ASCENDED_HERO_HELMET.get()
            && (entity instanceof LivingEntity _entGetArmorxx ? _entGetArmorxx.m_6844_(EquipmentSlot.CHEST) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.ASCENDED_HERO_CHESTPLATE.get()
            && (entity instanceof LivingEntity _entGetArmorx ? _entGetArmorx.m_6844_(EquipmentSlot.LEGS) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.ASCENDED_HERO_LEGGINGS.get()
            && (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.m_6844_(EquipmentSlot.FEET) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.ASCENDED_HERO_BOOTS.get()
            && !((CiscoModModVariables.PlayerVariables)entity.getCapability(CiscoModModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                  .orElse(new CiscoModModVariables.PlayerVariables()))
               .UnyieldingUsed) {
            if (event != null && event.isCancelable()) {
               event.setCanceled(true);
            }

            if (entity instanceof LivingEntity _entity) {
               _entity.m_21153_(4.0F);
            }

            if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
               _entity.m_7292_(new MobEffectInstance(MobEffects.f_19617_, 60, 1));
            }

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
                     x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.totem.use")), SoundSource.NEUTRAL, 1.0F, 1.0F, false
                  );
               }
            }

            if (world.m_5776_()) {
               Minecraft.m_91087_().f_91063_.m_109113_(new ItemStack(Items.f_42747_));
            }

            CiscoModMod.queueServerWork(1, () -> {
               boolean _setval = true;
               entity.getCapability(CiscoModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                  capability.UnyieldingUsed = _setval;
                  capability.syncPlayerVariables(entity);
               });
            });
            CiscoModMod.queueServerWork(12000, () -> {
               boolean _setval = false;
               entity.getCapability(CiscoModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                  capability.UnyieldingUsed = _setval;
                  capability.syncPlayerVariables(entity);
               });
            });
         } else if (((CiscoModModVariables.PlayerVariables)entity.getCapability(CiscoModModVariables.PLAYER_VARIABLES_CAPABILITY, null)
               .orElse(new CiscoModModVariables.PlayerVariables()))
            .UnyieldingUsed) {
            if (world instanceof ServerLevel _levelx) {
               LightningBolt entityToSpawn = (LightningBolt)EntityType.f_20465_.m_20615_(_levelx);
               entityToSpawn.m_20219_(Vec3.m_82539_(new BlockPos(x, y, z)));
               entityToSpawn.m_20874_(true);
               _levelx.m_7967_(entityToSpawn);
            }

            boolean _setval = false;
            entity.getCapability(CiscoModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
               capability.UnyieldingUsed = _setval;
               capability.syncPlayerVariables(entity);
            });
            if (world instanceof Level _levelx) {
               if (!_levelx.m_5776_()) {
                  _levelx.m_5594_(
                     null,
                     new BlockPos(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.conduit.deactivate")),
                     SoundSource.NEUTRAL,
                     1.0F,
                     1.0F
                  );
               } else {
                  _levelx.m_7785_(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.conduit.deactivate")),
                     SoundSource.NEUTRAL,
                     1.0F,
                     1.0F,
                     false
                  );
               }
            }
         }
      }
   }
}
