package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.client.ITooltipProvider;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.api.util.SourceUtil;
import com.hollingsworth.arsnouveau.client.particle.GlowParticleData;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.entity.EntityDrygmy;
import com.hollingsworth.arsnouveau.common.entity.EntityFollowProjectile;
import com.hollingsworth.arsnouveau.common.lib.EntityTags;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.Config;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;

public class DrygmyTile extends SummoningTile implements ITooltipProvider {
   public int progress;
   public int bonus;
   public boolean needsMana;
   private List<LivingEntity> nearbyEntities;

   public DrygmyTile(BlockPos pos, BlockState state) {
      super(BlockRegistry.DRYGMY_TILE, pos, state);
   }

   @Override
   public void tick() {
      super.tick();
      if (this.f_58857_.f_46443_) {
         for (int i = 0; i < this.progress / 2; i++) {
            this.f_58857_
               .m_7106_(
                  GlowParticleData.createData(new ParticleColor(50, 255, 20)),
                  (double)this.m_58899_().m_123341_() + 0.5 + ParticleUtil.inRange(-0.1, 0.1),
                  (double)(this.m_58899_().m_123342_() + 1) + ParticleUtil.inRange(-0.1, 0.1),
                  (double)this.m_58899_().m_123343_() + 0.5 + ParticleUtil.inRange(-0.1, 0.1),
                  0.0,
                  0.0,
                  0.0
               );
         }
      } else {
         if (this.f_58857_.m_46467_() % 100L == 0L) {
            this.refreshEntitiesAndBonus();
         }

         if (this.f_58857_.m_46467_() % 80L == 0L
            && this.needsMana
            && SourceUtil.takeSourceWithParticles(this.f_58858_, this.f_58857_, 7, (Integer)Config.DRYGMY_MANA_COST.get()) != null) {
            this.needsMana = false;
            this.updateBlock();
         }

         if (this.f_58857_.m_46467_() % 100L == 0L && !this.needsMana && this.progress >= this.getMaxProgress() && !this.getNearbyEntities().isEmpty()) {
            this.generateItems();
         }
      }
   }

   public List<LivingEntity> getNearbyEntities() {
      if (this.nearbyEntities == null) {
         this.refreshEntitiesAndBonus();
      }

      return this.nearbyEntities;
   }

   @Nullable
   public LivingEntity getRandomEntity() {
      return this.getNearbyEntities().isEmpty() ? null : this.getNearbyEntities().get(new Random().nextInt(this.getNearbyEntities().size()));
   }

   public void giveProgress() {
      if (this.progress < this.getMaxProgress()) {
         this.progress++;
         this.updateBlock();
      }
   }

   public int getMaxProgress() {
      return (Integer)Config.DRYGMY_MAX_PROGRESS.get();
   }

   @Override
   public void convertedEffect() {
      super.convertedEffect();
      if (this.tickCounter >= 120 && !this.f_58857_.f_46443_) {
         this.converted = true;
         this.f_58857_.m_46597_(this.f_58858_, (BlockState)this.f_58857_.m_8055_(this.f_58858_).m_61124_(SummoningTile.CONVERTED, true));
         EntityDrygmy entityDrygmy = new EntityDrygmy(this.f_58857_, true);
         entityDrygmy.m_6034_((double)this.f_58858_.m_123341_() + 0.5, (double)this.f_58858_.m_123342_() + 1.0, (double)this.f_58858_.m_123343_() + 0.5);
         entityDrygmy.homePos = new BlockPos(this.m_58899_());
         this.f_58857_.m_7967_(entityDrygmy);
         ParticleUtil.spawnPoof((ServerLevel)this.f_58857_, this.f_58858_.m_7494_());
         this.tickCounter = 0;
      } else {
         if (this.tickCounter % 10 == 0 && !this.f_58857_.f_46443_) {
            RandomSource r = this.f_58857_.f_46441_;
            int min = -2;
            int max = 2;
            EntityFollowProjectile proj1 = new EntityFollowProjectile(
               this.f_58857_,
               this.f_58858_.m_7918_(r.m_188503_(max - min) + min, 3, r.m_188503_(max - min) + min),
               this.f_58858_,
               r.m_188503_(255),
               r.m_188503_(255),
               r.m_188503_(255)
            );
            this.f_58857_.m_7967_(proj1);
         }
      }
   }

   public void refreshEntitiesAndBonus() {
      this.nearbyEntities = this.f_58857_
         .m_45976_(LivingEntity.class, new AABB(this.m_58899_().m_122013_(10).m_122025_(10).m_6625_(6), this.m_58899_().m_122020_(10).m_122030_(10).m_6630_(6)));

      for (BlockPos b : BlockPos.m_121925_(this.m_58899_(), 10, 10, 10)) {
         BlockEntity var6 = this.f_58857_.m_7702_(b);
         if (var6 instanceof MobJarTile) {
            MobJarTile mobJarTile = (MobJarTile)var6;
            if (mobJarTile.getEntity() instanceof LivingEntity livingEntity) {
               this.nearbyEntities.add(livingEntity);
            }
         }
      }

      this.nearbyEntities = this.nearbyEntities.stream().filter(l -> !(l instanceof EntityDrygmy) && !(l instanceof Player)).collect(Collectors.toList());
      Set<ResourceLocation> uniqueEntities = this.nearbyEntities.stream().map(l -> EntityType.m_20613_(l.m_6095_())).collect(Collectors.toSet());
      this.bonus = uniqueEntities.size() * (Integer)Config.DRYGMY_UNIQUE_BONUS.get()
         + Math.min((Integer)Config.DRYGMY_QUANTITY_CAP.get(), this.nearbyEntities.size());
      this.updateBlock();
   }

   public void generateItems() {
      List<ItemStack> stacks = new ArrayList<>();
      ANFakePlayer fakePlayer = ANFakePlayer.getPlayer((ServerLevel)this.f_58857_);
      DamageSource damageSource = DamageSource.m_19344_(fakePlayer);
      int numberItems = (Integer)Config.DRYGMY_BASE_ITEM.get() + this.bonus;
      int exp = 0;

      for (LivingEntity entity : this.getNearbyEntities()) {
         if (!entity.m_6095_().m_204039_(EntityTags.DRYGMY_BLACKLIST)) {
            LootTable loottable = this.f_58857_.m_7654_().m_129898_().m_79217_(entity.m_5743_());
            Builder lootcontext$builder = new Builder((ServerLevel)this.f_58857_)
               .m_230911_(this.f_58857_.m_213780_())
               .m_78972_(LootContextParams.f_81455_, entity)
               .m_78972_(LootContextParams.f_81460_, entity.m_20182_())
               .m_78972_(LootContextParams.f_81457_, damageSource)
               .m_78984_(LootContextParams.f_81458_, fakePlayer)
               .m_78984_(LootContextParams.f_81459_, damageSource.m_7640_());
            lootcontext$builder = lootcontext$builder.m_78972_(LootContextParams.f_81456_, fakePlayer).m_78963_(fakePlayer.m_36336_());
            LootContext ctx = lootcontext$builder.m_78975_(LootContextParamSets.f_81415_);
            stacks.addAll(loottable.m_230922_(ctx));
            int oldExp = 0;
            if (entity instanceof Mob mob) {
               oldExp = mob.f_21364_;
            }

            exp += entity.m_213860_();
            if (entity instanceof Mob mob) {
               mob.f_21364_ = oldExp;
            }
         }
      }

      int itemsPicked = 0;
      if (!stacks.isEmpty()) {
         for (int i = 0; i < numberItems; i++) {
            ItemStack stack = stacks.get(this.f_58857_.f_46441_.m_188503_(stacks.size())).m_41777_();
            itemsPicked += stack.m_41613_();
            BlockUtil.insertItemAdjacent(this.f_58857_, this.f_58858_, stack);
            if (itemsPicked >= numberItems) {
               break;
            }
         }
      }

      exp = (int)((double)exp * 0.25);
      if (exp > 3) {
         int numGreater = exp / 12;
         exp -= numGreater * 12;
         int numLesser = exp / 3;
         if (exp - numLesser * 3 > 0) {
            numLesser++;
         }

         if (numGreater > 0) {
            BlockUtil.insertItemAdjacent(this.f_58857_, this.f_58858_, new ItemStack((ItemLike)ItemsRegistry.GREATER_EXPERIENCE_GEM.get(), numGreater));
         }

         if (numLesser > 0) {
            BlockUtil.insertItemAdjacent(this.f_58857_, this.f_58858_, new ItemStack((ItemLike)ItemsRegistry.EXPERIENCE_GEM.get(), numLesser));
         }
      }

      this.progress = 0;
      this.needsMana = true;
      this.updateBlock();
   }

   @Override
   public void m_142466_(CompoundTag compound) {
      this.progress = compound.m_128451_("progress");
      this.bonus = compound.m_128451_("bonus");
      this.needsMana = compound.m_128471_("needsMana");
      super.m_142466_(compound);
   }

   @Override
   public void m_183515_(CompoundTag tag) {
      super.m_183515_(tag);
      tag.m_128405_("progress", this.progress);
      tag.m_128405_("bonus", this.bonus);
      tag.m_128379_("needsMana", this.needsMana);
   }

   @Override
   public void getTooltip(List<Component> tooltip) {
      if (this.isOff) {
         tooltip.add(Component.m_237115_("ars_nouveau.tooltip.turned_off"));
      }

      if (this.needsMana) {
         tooltip.add(Component.m_237115_("ars_nouveau.wixie.need_mana"));
      }
   }
}
