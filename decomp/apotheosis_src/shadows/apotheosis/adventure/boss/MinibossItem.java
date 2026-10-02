package shadows.apotheosis.adventure.boss;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.adventure.AdventureConfig;
import shadows.apotheosis.adventure.AdventureModule;
import shadows.apotheosis.adventure.compat.GameStagesCompat;
import shadows.apotheosis.adventure.loot.LootCategory;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.apotheosis.util.ChancedEffectInstance;
import shadows.apotheosis.util.GearSet;
import shadows.apotheosis.util.NameHelper;
import shadows.apotheosis.util.SupportingEntity;
import shadows.placebo.codec.PlaceboCodecs;
import shadows.placebo.json.NBTAdapter;
import shadows.placebo.json.PSerializer;
import shadows.placebo.json.RandomAttributeModifier;
import shadows.placebo.json.TypeKeyed.TypeKeyedBase;
import shadows.placebo.json.WeightedJsonReloadListener.IDimensional;
import shadows.placebo.json.WeightedJsonReloadListener.ILuckyWeighted;

public final class MinibossItem
   extends TypeKeyedBase<MinibossItem>
   implements ILuckyWeighted,
   IDimensional,
   GameStagesCompat.IStaged,
   MinibossManager.IEntityMatch {
   public static final String NAME_GEN = "use_name_generation";
   public static final Codec<MinibossItem> CODEC = RecordCodecBuilder.create(
      inst -> inst.group(
               Codec.intRange(0, Integer.MAX_VALUE).fieldOf("weight").forGetter(ILuckyWeighted::getWeight),
               Codec.floatRange(0.0F, Float.MAX_VALUE).optionalFieldOf("quality", 0.0F).forGetter(ILuckyWeighted::getQuality),
               ExtraCodecs.f_184349_.fieldOf("chance").forGetter(a -> a.chance),
               Codec.STRING.optionalFieldOf("name", "").forGetter(a -> a.name),
               PlaceboCodecs.setOf(ForgeRegistries.ENTITY_TYPES.getCodec()).fieldOf("entities").forGetter(a -> a.entities),
               BossStats.CODEC.fieldOf("stats").forGetter(a -> a.stats),
               PlaceboCodecs.setOf(Codec.STRING).optionalFieldOf("stages").forGetter(a -> Optional.ofNullable(a.stages)),
               PlaceboCodecs.setOf(ResourceLocation.f_135803_).fieldOf("dimensions").forGetter(a -> a.dimensions),
               Codec.BOOL.optionalFieldOf("affixed", false).forGetter(a -> a.affixed),
               GearSet.SetPredicate.CODEC.listOf().optionalFieldOf("valid_gear_sets", Collections.emptyList()).forGetter(a -> a.gearSets),
               NBTAdapter.EITHER_CODEC.optionalFieldOf("nbt").forGetter(a -> Optional.ofNullable(a.nbt)),
               SupportingEntity.CODEC.listOf().optionalFieldOf("supporting_entities", Collections.emptyList()).forGetter(a -> a.support),
               SupportingEntity.CODEC.optionalFieldOf("mount").forGetter(a -> Optional.ofNullable(a.mount)),
               Exclusion.CODEC.listOf().optionalFieldOf("exclusions", Collections.emptyList()).forGetter(a -> a.exclusions),
               Codec.BOOL.optionalFieldOf("finalize", false).forGetter(a -> a.finalize)
            )
            .apply(inst, MinibossItem::new)
   );
   public static final PSerializer<MinibossItem> SERIALIZER = PSerializer.fromCodec("Apotheotic Miniboss", CODEC);
   protected final int weight;
   protected final float quality;
   protected final float chance;
   protected final String name;
   protected final Set<EntityType<?>> entities;
   protected final BossStats stats;
   @Nullable
   protected final Set<String> stages;
   protected final Set<ResourceLocation> dimensions;
   protected final boolean affixed;
   protected final List<GearSet.SetPredicate> gearSets;
   @Nullable
   protected final CompoundTag nbt;
   protected final List<SupportingEntity> support;
   @Nullable
   protected final SupportingEntity mount;
   protected final List<Exclusion> exclusions;
   protected final boolean finalize;

   public MinibossItem(
      int weight,
      float quality,
      float chance,
      String name,
      Set<EntityType<?>> entities,
      BossStats stats,
      Optional<Set<String>> stages,
      Set<ResourceLocation> dimensions,
      boolean affixed,
      List<GearSet.SetPredicate> gearSets,
      Optional<CompoundTag> nbt,
      List<SupportingEntity> support,
      Optional<SupportingEntity> mount,
      List<Exclusion> exclusions,
      boolean finalize
   ) {
      this.weight = weight;
      this.quality = quality;
      this.chance = chance;
      this.name = name;
      this.entities = entities;
      this.stats = stats;
      this.stages = stages.orElse(null);
      this.dimensions = dimensions;
      this.affixed = affixed;
      this.gearSets = gearSets;
      this.nbt = nbt.orElse(null);
      this.support = support;
      this.mount = mount.orElse(null);
      this.exclusions = exclusions;
      this.finalize = finalize;
   }

   public int getWeight() {
      return (R)this.weight;
   }

   public float getQuality() {
      return (R)this.quality;
   }

   public float getChance() {
      return this.chance;
   }

   @Override
   public Set<EntityType<?>> getEntities() {
      return this.entities;
   }

   public void transformMiniboss(ServerLevelAccessor level, Mob mob, RandomSource random, float luck) {
      Vec3 pos = mob.m_20318_(0.0F);
      if (this.nbt != null && this.nbt.m_128441_("Passengers")) {
         ListTag passengers = this.nbt.m_128437_("Passengers", 10);

         for (int i = 0; i < passengers.size(); i++) {
            Entity entity = EntityType.m_20645_(passengers.m_128728_(i), level.m_6018_(), Function.identity());
            if (entity != null) {
               entity.m_7998_(mob, true);
            }
         }
      }

      mob.m_146884_(pos);
      this.initBoss(random, mob, luck);
      if (this.nbt != null) {
         mob.m_7378_(this.nbt);
      }

      if (this.mount != null) {
         Mob mountedEntity = this.mount.create(mob.m_9236_(), mob.m_20185_() + 0.5, mob.m_20186_(), mob.m_20189_() + 0.5);
         mob.m_7998_(mountedEntity, true);
         level.m_7967_(mountedEntity);
      }

      if (this.support != null) {
         for (SupportingEntity support : this.support) {
            Mob supportingMob = support.create(mob.m_9236_(), mob.m_20185_() + 0.5, mob.m_20186_(), mob.m_20189_() + 0.5);
            level.m_7967_(supportingMob);
         }
      }
   }

   public void initBoss(RandomSource rand, Mob mob, float luck) {
      mob.getPersistentData().m_128379_("apoth.miniboss", true);
      int duration = mob instanceof Creeper ? 6000 : Integer.MAX_VALUE;

      for (ChancedEffectInstance inst : this.stats.effects()) {
         if (rand.m_188501_() <= inst.chance()) {
            mob.m_7292_(inst.create(rand, duration));
         }
      }

      for (RandomAttributeModifier modif : this.stats.modifiers()) {
         modif.apply(rand, mob);
      }

      if ("use_name_generation".equals(this.name)) {
         NameHelper.setEntityName(rand, mob);
      } else if (!Strings.isNullOrEmpty(this.name)) {
         mob.m_6593_(Component.m_237115_(this.name));
      }

      if (mob.m_8077_()) {
         mob.m_20340_(true);
      }

      if (!this.gearSets.isEmpty()) {
         GearSet set = BossArmorManager.INSTANCE.getRandomSet(rand, luck, this.gearSets);
         Preconditions.checkNotNull(set, String.format("Failed to find a valid gear set for the miniboss %s.", this.getId()));
         set.apply(mob);
      }

      int guaranteed = -1;
      if (this.affixed) {
         boolean anyValid = false;

         for (EquipmentSlot t : EquipmentSlot.values()) {
            ItemStack s = mob.m_6844_(t);
            if (!s.m_41619_() && !LootCategory.forItem(s).isNone()) {
               anyValid = true;
               break;
            }
         }

         if (!anyValid) {
            AdventureModule.LOGGER.error("Attempted to affix a miniboss with ID " + this.getId() + " but it is not wearing any affixable items!");
            return;
         }

         guaranteed = rand.m_188503_(6);

         ItemStack temp;
         for (temp = mob.m_6844_(EquipmentSlot.values()[guaranteed]);
            temp.m_41619_() || LootCategory.forItem(temp) == LootCategory.NONE;
            temp = mob.m_6844_(EquipmentSlot.values()[guaranteed])
         ) {
            guaranteed = rand.m_188503_(6);
         }

         LootRarity rarity = LootRarity.random(rand, luck, AdventureConfig.AFFIX_CONVERT_RARITIES.get(mob.f_19853_.m_46472_().m_135782_()));
         BossItem.modifyBossItem(temp, rand, mob.m_7770_(), luck, rarity, this.stats);
         mob.m_6593_(((MutableComponent)mob.m_7770_()).m_130948_(Style.f_131099_.m_131148_(rarity.color())));
         mob.m_21409_(EquipmentSlot.values()[guaranteed], 2.0F);
      }

      for (EquipmentSlot s : EquipmentSlot.values()) {
         ItemStack stack = mob.m_6844_(s);
         if (!stack.m_41619_() && s.ordinal() != guaranteed && rand.m_188501_() < this.stats.enchantChance()) {
            BossItem.enchantBossItem(rand, stack, Apotheosis.enableEnch ? this.stats.enchLevels()[0] : this.stats.enchLevels()[1], true);
            mob.m_8061_(s, stack);
         }
      }

      mob.m_21153_(mob.m_21233_());
   }

   public MinibossItem validate() {
      Preconditions.checkArgument(this.weight >= 0, "Miniboss Item " + this.id + " has a negative weight!");
      Preconditions.checkArgument(this.quality >= 0.0F, "Miniboss Item " + this.id + " has a negative quality!");
      Preconditions.checkNotNull(this.entities, "Miniboss Item " + this.id + " has null entity match list!");
      Preconditions.checkNotNull(this.stats, "Miniboss Item " + this.id + " has no stats!");
      return this;
   }

   public Set<ResourceLocation> getDimensions() {
      return this.dimensions;
   }

   @Override
   public Set<String> getStages() {
      return this.stages;
   }

   public PSerializer<? extends MinibossItem> getSerializer() {
      return SERIALIZER;
   }

   public boolean requiresNbtAccess() {
      return this.exclusions.stream().anyMatch(Exclusion::requiresNbtAccess);
   }

   public boolean isExcluded(Mob mob, ServerLevelAccessor level, MobSpawnType type) {
      CompoundTag tag = this.requiresNbtAccess() ? mob.m_20240_(new CompoundTag()) : null;
      return this.exclusions.stream().anyMatch(ex -> ex.isExcluded(mob, level, type, tag));
   }

   public boolean shouldFinalize() {
      return this.finalize;
   }
}
