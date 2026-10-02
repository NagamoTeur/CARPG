package shadows.apotheosis.adventure.compat;

import com.google.common.base.Predicates;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.util.CommonTooltipUtil;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;
import snownee.jade.api.config.IPluginConfig;

@WailaPlugin
public class AdventureHwylaPlugin implements IWailaPlugin, IEntityComponentProvider, IServerDataProvider<Entity> {
   public void register(IWailaCommonRegistration reg) {
      if (Apotheosis.enableAdventure) {
         reg.registerEntityDataProvider(this, LivingEntity.class);
      }
   }

   public void registerClient(IWailaClientRegistration reg) {
      if (Apotheosis.enableAdventure) {
         reg.registerEntityComponent(this, Entity.class);
      }
   }

   public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
      if (accessor.getEntity() instanceof LivingEntity living && accessor.getServerData().m_128471_("apoth.boss")) {
         ListTag bossAttribs = accessor.getServerData().m_128437_("apoth.modifiers", 10);
         AttributeMap map = living.m_21204_();

         for (Tag t : bossAttribs) {
            CompoundTag tag = (CompoundTag)t;
            Attribute attrib = (Attribute)ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation(tag.m_128461_("Name")));
            map.m_22146_(attrib).m_22113_(tag);
         }

         accessor.getServerData().m_128473_("apoth.modifiers");
         living.getPersistentData().m_128391_(accessor.getServerData());
         CommonTooltipUtil.appendBossData(living.f_19853_, living, tooltip::add);
      }
   }

   public void appendServerData(CompoundTag tag, ServerPlayer player, Level world, Entity entity, boolean something) {
      if (entity instanceof LivingEntity living && living.getPersistentData().m_128471_("apoth.boss")) {
         tag.m_128379_("apoth.boss", true);
         tag.m_128359_("apoth.rarity", living.getPersistentData().m_128461_("apoth.rarity"));
         AttributeMap map = living.m_21204_();
         ListTag bossAttribs = new ListTag();
         ForgeRegistries.ATTRIBUTES.getValues().stream().<AttributeInstance>map(map::m_22146_).filter(Predicates.notNull()).forEach(inst -> {
            for (AttributeModifier modif : inst.m_22122_()) {
               if (modif.m_22214_().startsWith("placebo_random_modifier_")) {
                  bossAttribs.add(inst.m_22136_());
               }
            }
         });
         tag.m_128365_("apoth.modifiers", bossAttribs);
      }
   }

   public ResourceLocation getUid() {
      return Apotheosis.loc("adventure");
   }
}
