package com.hollingsworth.arsnouveau.api.familiar;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import java.util.function.Predicate;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class AbstractFamiliarHolder {
   public Predicate<Entity> isEntity;
   private ResourceLocation id;

   public AbstractFamiliarHolder(String id, Predicate<Entity> isConversionEntity) {
      this(new ResourceLocation("ars_nouveau", id), isConversionEntity);
   }

   public AbstractFamiliarHolder(ResourceLocation id, Predicate<Entity> isConversionEntity) {
      this.id = id;
      this.isEntity = isConversionEntity;
   }

   public abstract IFamiliar getSummonEntity(Level var1, CompoundTag var2);

   public ItemStack getOutputItem() {
      return new ItemStack(ArsNouveauAPI.getInstance().getFamiliarItem(this.getRegistryName()));
   }

   public ResourceLocation getRegistryName() {
      return this.id;
   }

   public Component getLangDescription() {
      return Component.m_237115_(this.id.m_135827_() + ".familiar_desc." + this.id.m_135815_());
   }

   public Component getLangName() {
      return Component.m_237115_(this.id.m_135827_() + ".familiar_name." + this.id.m_135815_());
   }

   public String getBookName() {
      return "";
   }

   public String getBookDescription() {
      return "";
   }
}
