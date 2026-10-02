package com.hollingsworth.arsnouveau.common.ritual;

import com.hollingsworth.arsnouveau.api.recipe.SummonRitualRecipe;
import com.hollingsworth.arsnouveau.api.ritual.AbstractRitual;
import com.hollingsworth.arsnouveau.common.lib.EntityTags;
import com.hollingsworth.arsnouveau.common.lib.RitualLib;
import com.hollingsworth.arsnouveau.setup.RecipeRegistry;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraftforge.registries.ForgeRegistries;

public class RitualAnimalSummoning extends AbstractRitual {
   private final MobCategory category = MobCategory.CREATURE;
   private WeightedRandomList<? extends WeightedEntry> mobs;
   private Optional<SummonRitualRecipe> recipe;

   private Optional<SummonRitualRecipe> getRecipe() {
      return this.getWorld()
         .m_7465_()
         .m_44013_((RecipeType)RecipeRegistry.SUMMON_RITUAL_TYPE.get())
         .stream()
         .filter(r -> r.matches(this.getConsumedItems()))
         .findFirst();
   }

   private WeightedRandomList<? extends WeightedEntry> getMobs(Level world) {
      if (this.recipe.isPresent()) {
         SummonRitualRecipe summonRitualRecipe = this.recipe.get();
         if (summonRitualRecipe.mobSource == SummonRitualRecipe.MobSource.MOB_LIST) {
            return WeightedRandomList.m_146328_(summonRitualRecipe.mobs);
         }
      }

      return WeightedRandomList.m_146328_(
         ((Biome)world.m_204166_(this.getPos()).get())
            .m_47518_()
            .m_151798_(this.category)
            .m_146338_()
            .stream()
            .filter(mob -> !mob.f_48404_.m_204039_(EntityTags.ANIMAL_SUMMON_BLACKLIST))
            .collect(Collectors.toList())
      );
   }

   @Override
   public void onStart() {
      super.onStart();
      if (this.tile != null && this.getWorld() != null && this.getPos() != null) {
         if (this.recipe == null) {
            this.recipe = this.getRecipe();
         }

         this.mobs = this.getMobs(this.getWorld());
      }
   }

   @Override
   protected void tick() {
      Level world = this.getWorld();
      if (world != null && this.getPos() != null) {
         if (this.recipe == null) {
            this.recipe = this.getRecipe();
         }

         if (this.mobs == null) {
            this.mobs = this.getMobs(world);
         }

         if (world.m_46467_() % 60L == 0L && !world.f_46443_) {
            BlockPos summonPos = this.getPos()
               .m_7494_()
               .m_122030_(this.rand.m_188503_(3) - this.rand.m_188503_(6))
               .m_122013_(this.rand.m_188503_(3) - this.rand.m_188503_(6));
            Optional<? extends WeightedEntry> opt = this.mobs.m_216829_(this.rand);
            opt.ifPresent(entry -> {
               if (entry instanceof SpawnerData spawnerData) {
                  Entity mob = spawnerData.f_48404_.m_20615_(world);
                  if (mob == null) {
                     return;
                  }

                  this.summon(mob, summonPos);
                  this.incrementProgress();
               }

               if (entry instanceof SummonRitualRecipe.WeightedMobType weightedMobType) {
                  EntityType<? extends Entity> entityType = (EntityType<? extends Entity>)ForgeRegistries.ENTITY_TYPES.getValue(weightedMobType.mob());
                  if (entityType == null) {
                     return;
                  }

                  Entity mob = entityType.m_20615_(world);
                  if (mob == null) {
                     return;
                  }

                  this.summon(mob, summonPos);
                  this.incrementProgress();
               }
            });
            this.recipe.ifPresentOrElse(recipe -> {
               if (this.getProgress() >= recipe.count) {
                  this.setFinished();
               }
            }, () -> {
               if (this.getProgress() >= 5) {
                  this.setFinished();
               }
            });
         }
      }
   }

   public void summon(Entity mob, BlockPos pos) {
      mob.m_6034_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
      mob.f_19853_.m_7967_(mob);
      if (mob.f_19853_ instanceof ServerLevel serverLevel) {
         for (ServerPlayer player : serverLevel.m_6907_()) {
            serverLevel.m_8624_(
               player, ParticleTypes.f_123810_, false, (double)pos.m_123341_(), (double)pos.m_123342_() + 0.1, (double)pos.m_123343_(), 10, 0.1, 0.1, 0.1, 0.05
            );
         }
      }
   }

   @Override
   public boolean canStart() {
      if (this.getConsumedItems().size() == 0) {
         return true;
      } else {
         if (this.recipe == null) {
            this.recipe = this.getRecipe();
         }

         return this.recipe.isPresent();
      }
   }

   @Override
   public boolean canConsumeItem(ItemStack stack) {
      return this.getWorld().m_7465_().m_44013_((RecipeType)RecipeRegistry.SUMMON_RITUAL_TYPE.get()).stream().anyMatch(r -> r.catalyst.test(stack));
   }

   @Override
   public String getLangName() {
      return "Summon Animals";
   }

   @Override
   public String getLangDescription() {
      return "Summon a random variety of wild animals native to the biome it's in.";
   }

   @Override
   public ResourceLocation getRegistryName() {
      return new ResourceLocation("ars_nouveau", RitualLib.ANIMAL_SUMMON);
   }
}
