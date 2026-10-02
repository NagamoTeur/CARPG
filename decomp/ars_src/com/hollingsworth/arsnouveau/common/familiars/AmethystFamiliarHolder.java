package com.hollingsworth.arsnouveau.common.familiars;

import com.hollingsworth.arsnouveau.api.familiar.AbstractFamiliarHolder;
import com.hollingsworth.arsnouveau.api.familiar.IFamiliar;
import com.hollingsworth.arsnouveau.common.entity.AmethystGolem;
import com.hollingsworth.arsnouveau.common.entity.ModEntities;
import com.hollingsworth.arsnouveau.common.entity.familiar.FamiliarAmethystGolem;
import com.hollingsworth.arsnouveau.common.lib.LibEntityNames;
import java.util.function.Predicate;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public class AmethystFamiliarHolder extends AbstractFamiliarHolder {
   public AmethystFamiliarHolder() {
      super(LibEntityNames.FAMILIAR_AMETHYST_GOLEM, e -> e instanceof AmethystGolem);
   }

   public AmethystFamiliarHolder(ResourceLocation id, Predicate<Entity> isConversionEntity) {
      super(id, isConversionEntity);
   }

   @Override
   public IFamiliar getSummonEntity(Level world, CompoundTag tag) {
      FamiliarAmethystGolem golem = new FamiliarAmethystGolem((EntityType<? extends PathfinderMob>)ModEntities.ENTITY_FAMILIAR_BOOKWYRM.get(), world);
      golem.setTagData(tag);
      return golem;
   }

   @Override
   public String getBookName() {
      return "Amethyst Golem";
   }

   @Override
   public String getBookDescription() {
      return "Reduces knockback taken by half and knocks back any attackers. Giving the golem an Amethyst Shard will grant 3 minutes of Shielding.";
   }
}
