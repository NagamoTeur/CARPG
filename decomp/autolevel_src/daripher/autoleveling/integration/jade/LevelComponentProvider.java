package daripher.autoleveling.integration.jade;

import daripher.autoleveling.event.MobsLevelingEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum LevelComponentProvider implements IEntityComponentProvider {
   INSTANCE;

   private static final ResourceLocation ID = new ResourceLocation("autoleveling", "level");

   public ResourceLocation getUid() {
      return ID;
   }

   public void appendTooltip(ITooltip tooltip, EntityAccessor entityAccessor, IPluginConfig pluginConfig) {
      Entity entity = entityAccessor.getEntity();
      boolean showLevel = MobsLevelingEvents.hasLevel(entity) && MobsLevelingEvents.shouldShowLevel(entity);
      if (showLevel) {
         int level = MobsLevelingEvents.getLevel((LivingEntity)entity) + 1;
         tooltip.add(Component.m_237110_("jade.autoleveling.tooltip", new Object[]{level}));
      }
   }
}
