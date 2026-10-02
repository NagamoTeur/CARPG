package shadows.apotheosis.adventure.boss;

import com.google.common.base.Preconditions;
import com.google.gson.annotations.SerializedName;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.ForgeRegistries;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.adventure.AdventureConfig;
import shadows.apotheosis.adventure.affix.AffixHelper;
import shadows.apotheosis.adventure.compat.GameStagesCompat;
import shadows.apotheosis.adventure.loot.LootCategory;
import shadows.apotheosis.adventure.loot.LootController;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.apotheosis.ench.asm.EnchHooks;
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

public final class BossItem extends TypeKeyedBase<BossItem> implements ILuckyWeighted, IDimensional, LootRarity.Clamped, GameStagesCompat.IStaged {
   public static final Codec<AABB> AABB_CODEC = RecordCodecBuilder.create(
      inst -> inst.group(
               Codec.DOUBLE.fieldOf("width").forGetter(a -> Math.abs(a.f_82291_ - a.f_82288_)),
               Codec.DOUBLE.fieldOf("height").forGetter(a -> Math.abs(a.f_82292_ - a.f_82289_))
            )
            .apply(inst, (width, height) -> new AABB(0.0, 0.0, 0.0, width, height, width))
   );
   public static final Codec<BossItem> CODEC = RecordCodecBuilder.create(
      inst -> inst.group(
               Codec.intRange(0, Integer.MAX_VALUE).fieldOf("weight").forGetter(ILuckyWeighted::getWeight),
               Codec.floatRange(0.0F, Float.MAX_VALUE).optionalFieldOf("quality", 0.0F).forGetter(ILuckyWeighted::getQuality),
               ForgeRegistries.ENTITY_TYPES.getCodec().fieldOf("entity").forGetter(a -> a.entity),
               AABB_CODEC.fieldOf("size").forGetter(a -> a.size),
               LootRarity.mapCodec(BossStats.CODEC).fieldOf("stats").forGetter(a -> a.stats),
               PlaceboCodecs.setOf(Codec.STRING).optionalFieldOf("stages").forGetter(a -> Optional.ofNullable(a.stages)),
               GearSet.SetPredicate.CODEC.listOf().fieldOf("valid_gear_sets").forGetter(a -> a.gearSets),
               NBTAdapter.EITHER_CODEC.optionalFieldOf("nbt").forGetter(a -> Optional.ofNullable(a.nbt)),
               PlaceboCodecs.setOf(ResourceLocation.f_135803_).fieldOf("dimensions").forGetter(a -> a.dimensions),
               LootRarity.CODEC.optionalFieldOf("min_rarity", LootRarity.COMMON).forGetter(a -> a.minRarity),
               LootRarity.CODEC.optionalFieldOf("max_rarity", LootRarity.MYTHIC).forGetter(a -> a.maxRarity),
               SupportingEntity.CODEC.optionalFieldOf("mount").forGetter(a -> Optional.ofNullable(a.mount))
            )
            .apply(inst, BossItem::new)
   );
   public static final PSerializer<BossItem> SERIALIZER = PSerializer.fromCodec("Apotheotic Boss", CODEC);
   public static final Predicate<Goal> IS_VILLAGER_ATTACK = a -> a instanceof NearestAttackableTargetGoal
         && ((NearestAttackableTargetGoal)a).f_26048_ == Villager.class;
   protected final int weight;
   protected final float quality;
   protected final EntityType<?> entity;
   protected final AABB size;
   protected final Map<LootRarity, BossStats> stats;
   @Nullable
   protected final Set<String> stages;
   @SerializedName("valid_gear_sets")
   protected final List<GearSet.SetPredicate> gearSets;
   @Nullable
   protected final CompoundTag nbt;
   protected final Set<ResourceLocation> dimensions;
   @SerializedName("min_rarity")
   protected final LootRarity minRarity;
   @SerializedName("max_rarity")
   protected final LootRarity maxRarity;
   @Nullable
   protected final SupportingEntity mount;

   public BossItem(
      int weight,
      float quality,
      EntityType<?> entity,
      AABB size,
      Map<LootRarity, BossStats> stats,
      Optional<Set<String>> stages,
      List<GearSet.SetPredicate> armorSets,
      Optional<CompoundTag> nbt,
      Set<ResourceLocation> dimensions,
      LootRarity minRarity,
      LootRarity maxRarity,
      Optional<SupportingEntity> mount
   ) {
      this.weight = weight;
      this.quality = quality;
      this.entity = entity;
      this.size = size;
      this.stats = stats;
      this.stages = stages.orElse(null);
      this.gearSets = armorSets;
      this.nbt = nbt.orElse(null);
      this.dimensions = dimensions;
      this.minRarity = minRarity;
      this.maxRarity = maxRarity;
      this.mount = mount.orElse(null);
      Preconditions.checkArgument(minRarity.ordinal() <= maxRarity.ordinal(), "Min rarity must be less than or equal to max rarity.");
   }

   public int getWeight() {
      return (R)this.weight;
   }

   public float getQuality() {
      return (R)this.quality;
   }

   @Override
   public LootRarity getMinRarity() {
      return this.minRarity;
   }

   @Override
   public LootRarity getMaxRarity() {
      return this.maxRarity;
   }

   public AABB getSize() {
      return this.size;
   }

   public EntityType<?> getEntity() {
      return this.entity;
   }

   public Mob createBoss(ServerLevelAccessor world, BlockPos pos, RandomSource random, float luck) {
      return this.createBoss(world, pos, random, luck, null);
   }

   public Mob createBoss(ServerLevelAccessor world, BlockPos pos, RandomSource random, float luck, @Nullable LootRarity rarity) {
      CompoundTag fakeNbt = this.nbt == null ? new CompoundTag() : this.nbt;
      fakeNbt.m_128359_("id", EntityType.m_20613_(this.entity).toString());
      Mob entity = (Mob)EntityType.m_20645_(fakeNbt, world.m_6018_(), Function.identity());
      if (this.nbt != null) {
         entity.m_20258_(this.nbt);
      }

      this.initBoss(random, entity, luck, rarity);
      if (this.nbt != null) {
         entity.m_7378_(this.nbt);
      }

      if (this.mount != null) {
         Mob mountedEntity = this.mount.create(world.m_6018_(), (double)pos.m_123341_() + 0.5, (double)pos.m_123342_(), (double)pos.m_123343_() + 0.5);
         entity.m_7998_(mountedEntity, true);
         entity = mountedEntity;
      }

      entity.m_7678_((double)pos.m_123341_() + 0.5, (double)pos.m_123342_(), (double)pos.m_123343_() + 0.5, random.m_188501_() * 360.0F, 0.0F);
      return entity;
   }

   public void initBoss(RandomSource rand, Mob entity, float luck, @Nullable LootRarity rarity) {
      if (rarity == null) {
         rarity = LootRarity.random(rand, luck, this);
      }

      rarity = this.clamp(rarity);
      BossStats stats = this.stats.get(rarity);
      int duration = entity instanceof Creeper ? 6000 : Integer.MAX_VALUE;

      for (ChancedEffectInstance inst : stats.effects()) {
         if (rand.m_188501_() <= inst.chance()) {
            entity.m_7292_(inst.create(rand, duration));
         }
      }

      for (RandomAttributeModifier modif : stats.modifiers()) {
         modif.apply(rand, entity);
      }

      entity.f_21345_.f_25345_.removeIf(IS_VILLAGER_ATTACK);
      String name = NameHelper.setEntityName(rand, entity);
      GearSet set = BossArmorManager.INSTANCE.getRandomSet(rand, luck, this.gearSets);
      set.apply(entity);
      boolean anyValid = false;

      for (EquipmentSlot t : EquipmentSlot.values()) {
         ItemStack s = entity.m_6844_(t);
         if (!s.m_41619_() && !LootCategory.forItem(s).isNone()) {
            anyValid = true;
            break;
         }
      }

      if (!anyValid) {
         throw new RuntimeException("Attempted to apply boss gear set " + set.getId() + " but it had no valid affix loot items generated.");
      } else {
         int guaranteed = rand.m_188503_(6);

         for (ItemStack temp = entity.m_6844_(EquipmentSlot.values()[guaranteed]);
            temp.m_41619_() || LootCategory.forItem(temp) == LootCategory.NONE;
            temp = entity.m_6844_(EquipmentSlot.values()[guaranteed])
         ) {
            guaranteed = rand.m_188503_(6);
         }

         for (EquipmentSlot s : EquipmentSlot.values()) {
            ItemStack stack = entity.m_6844_(s);
            if (!stack.m_41619_()) {
               if (s.ordinal() == guaranteed) {
                  entity.m_21409_(s, 2.0F);
               }

               if (s.ordinal() == guaranteed) {
                  entity.m_8061_(s, modifyBossItem(stack, rand, Component.m_237113_(name), luck, rarity, stats));
                  entity.m_6593_(((MutableComponent)entity.m_7770_()).m_130948_(Style.f_131099_.m_131148_(rarity.color())));
               } else if (rand.m_188501_() < stats.enchantChance()) {
                  enchantBossItem(rand, stack, Apotheosis.enableEnch ? stats.enchLevels()[0] : stats.enchLevels()[1], true);
                  entity.m_8061_(s, stack);
               }
            }
         }

         entity.getPersistentData().m_128379_("apoth.boss", true);
         entity.getPersistentData().m_128359_("apoth.rarity", rarity.id());
         entity.m_21153_(entity.m_21233_());
         if (AdventureConfig.bossGlowOnSpawn) {
            entity.m_7292_(new MobEffectInstance(MobEffects.f_19619_, 3600));
         }
      }
   }

   public static void enchantBossItem(RandomSource rand, ItemStack stack, int level, boolean treasure) {
      List<EnchantmentInstance> ench = EnchantmentHelper.m_220297_(rand, stack, level, treasure);
      Map<Enchantment, Integer> map = ench.stream().filter(d -> !d.f_44947_.m_6589_()).collect(Collectors.toMap(d -> d.f_44947_, d -> d.f_44948_, Math::max));
      map.putAll(EnchantmentHelper.m_44831_(stack));
      EnchantmentHelper.m_44865_(map, stack);
   }

   public static ItemStack modifyBossItem(ItemStack stack, RandomSource rand, @Nullable Component bossName, float luck, LootRarity rarity, BossStats stats) {
      enchantBossItem(rand, stack, Apotheosis.enableEnch ? stats.enchLevels()[2] : stats.enchLevels()[3], true);
      NameHelper.setItemName(rand, stack);
      stack = LootController.createLootItem(stack, LootCategory.forItem(stack), rarity, rand);
      Component bossOwnerName = Component.m_237110_(NameHelper.ownershipFormat, new Object[]{bossName});
      Component name = AffixHelper.getName(stack);
      if (bossName != null && name.m_214077_() instanceof TranslatableContents tc) {
         String oldKey = tc.m_237508_();
         String newKey = "misc.apotheosis.affix_name.two".equals(oldKey) ? "misc.apotheosis.affix_name.three" : "misc.apotheosis.affix_name.four";
         Object[] newArgs = new Object[tc.m_237523_().length + 1];
         newArgs[0] = bossOwnerName;

         for (int i = 1; i < newArgs.length; i++) {
            newArgs[i] = tc.m_237523_()[i - 1];
         }

         Component copy = Component.m_237110_(newKey, newArgs).m_130948_(name.m_7383_().m_131155_(false));
         AffixHelper.setName(stack, copy);
      }

      Map<Enchantment, Integer> enchMap = new HashMap<>();

      for (Entry<Enchantment, Integer> e : EnchantmentHelper.m_44831_(stack).entrySet()) {
         if (e.getKey() != null) {
            enchMap.put(e.getKey(), Math.min(EnchHooks.getMaxLevel(e.getKey()), e.getValue() + rand.m_188503_(2)));
         }
      }

      if (AdventureConfig.curseBossItems) {
         List<Enchantment> curses = ForgeRegistries.ENCHANTMENTS
            .getValues()
            .stream()
            .filter(ex -> ex.canApplyAtEnchantingTable(stack) && ex.m_6589_())
            .collect(Collectors.toList());
         if (!curses.isEmpty()) {
            Enchantment curse = curses.get(rand.m_188503_(curses.size()));
            enchMap.put(curse, Mth.m_216271_(rand, 1, EnchHooks.getMaxLevel(curse)));
         }
      }

      EnchantmentHelper.m_44865_(enchMap, stack);
      stack.m_41783_().m_128379_("apoth_boss", true);
      return stack;
   }

   public BossItem validate() {
      Preconditions.checkArgument(this.weight >= 0, "Boss Item " + this.id + " has a negative weight!");
      Preconditions.checkArgument(this.quality >= 0.0F, "Boss Item " + this.id + " has a negative quality!");
      Preconditions.checkNotNull(this.entity, "Boss Item " + this.id + " has null entity type!");
      Preconditions.checkNotNull(this.size, "Boss Item " + this.id + " has no size!");
      if (this.minRarity != null) {
         Preconditions.checkArgument(this.maxRarity == null || this.maxRarity.isAtLeast(this.minRarity));
      }

      if (this.maxRarity != null) {
         Preconditions.checkArgument(this.minRarity == null || this.maxRarity.isAtLeast(this.minRarity));
      }

      if (this.mount != null) {
         Preconditions.checkNotNull(this.mount.entity, "Boss Item " + this.id + " has an invalid mount");
      }

      for (LootRarity r = LootRarity.max(LootRarity.COMMON, this.minRarity); r != LootRarity.ANCIENT; r = LootRarity.LIST.get(r.ordinal() + 1)) {
         Preconditions.checkNotNull(this.stats.get(r));
         if (r == this.maxRarity) {
            break;
         }
      }

      return this;
   }

   public Set<ResourceLocation> getDimensions() {
      return this.dimensions;
   }

   @Override
   public Set<String> getStages() {
      return this.stages;
   }

   public PSerializer<? extends BossItem> getSerializer() {
      return SERIALIZER;
   }
}
