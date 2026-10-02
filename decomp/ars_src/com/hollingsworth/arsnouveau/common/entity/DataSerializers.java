package com.hollingsworth.arsnouveau.common.entity;

import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries.Keys;

public class DataSerializers {
   public static final DeferredRegister<EntityDataSerializer<?>> DS = DeferredRegister.create(Keys.ENTITY_DATA_SERIALIZERS, "ars_nouveau");
   public static final RegistryObject<EntityDataSerializer<Vec3>> VEC3 = DS.register("vec3", () -> EntityDataSerializer.m_238095_((buffer, vec) -> {
         buffer.writeDouble(vec.f_82479_);
         buffer.writeDouble(vec.f_82480_);
         buffer.writeDouble(vec.f_82481_);
      }, buffer -> new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble())));
}
