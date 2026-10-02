package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.common.entity.goal.carbuncle.StarbyPotionBehavior;
import com.hollingsworth.arsnouveau.common.entity.goal.carbuncle.StarbyTransportBehavior;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class BehaviorRegistry {
   private static final Map<ResourceLocation, BehaviorRegistry.CreateFromTag> REGISTRY = new HashMap<>();

   public static void register(ResourceLocation name, BehaviorRegistry.CreateFromTag creator) {
      REGISTRY.put(name, creator);
   }

   public static ChangeableBehavior create(Entity entity, CompoundTag tag) {
      BehaviorRegistry.CreateFromTag create = REGISTRY.get(new ResourceLocation(tag.m_128461_("id")));
      return create == null ? null : create.create(entity, tag);
   }

   private BehaviorRegistry() {
   }

   static {
      register(StarbyTransportBehavior.TRANSPORT_ID, (entity, tag) -> new StarbyTransportBehavior((Starbuncle)entity, tag));
      register(StarbyPotionBehavior.POTION_ID, (entity, tag) -> new StarbyPotionBehavior((Starbuncle)entity, tag));
   }

   public interface CreateFromTag {
      ChangeableBehavior create(Entity var1, CompoundTag var2);
   }
}
