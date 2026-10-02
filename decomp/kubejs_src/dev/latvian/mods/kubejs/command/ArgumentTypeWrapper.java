package dev.latvian.mods.kubejs.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.latvian.mods.kubejs.util.ClassWrapper;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.AngleArgument;
import net.minecraft.commands.arguments.ColorArgument;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.commands.arguments.CompoundTagArgument;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.EntitySummonArgument;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.commands.arguments.ItemEnchantmentArgument;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.commands.arguments.MobEffectArgument;
import net.minecraft.commands.arguments.NbtPathArgument;
import net.minecraft.commands.arguments.NbtTagArgument;
import net.minecraft.commands.arguments.ObjectiveArgument;
import net.minecraft.commands.arguments.ParticleArgument;
import net.minecraft.commands.arguments.RangeArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.SlotArgument;
import net.minecraft.commands.arguments.TimeArgument;
import net.minecraft.commands.arguments.UuidArgument;
import net.minecraft.commands.arguments.RangeArgument.Floats;
import net.minecraft.commands.arguments.RangeArgument.Ints;
import net.minecraft.commands.arguments.blocks.BlockPredicateArgument;
import net.minecraft.commands.arguments.blocks.BlockStateArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.coordinates.ColumnPosArgument;
import net.minecraft.commands.arguments.coordinates.RotationArgument;
import net.minecraft.commands.arguments.coordinates.SwizzleArgument;
import net.minecraft.commands.arguments.coordinates.Vec2Argument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.arguments.item.ItemPredicateArgument;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;

public enum ArgumentTypeWrapper {
   BOOLEAN(BoolArgumentType::bool, BoolArgumentType::getBool),
   FLOAT(FloatArgumentType::floatArg, FloatArgumentType::getFloat),
   DOUBLE(DoubleArgumentType::doubleArg, DoubleArgumentType::getDouble),
   INTEGER(IntegerArgumentType::integer, IntegerArgumentType::getInteger),
   LONG(LongArgumentType::longArg, LongArgumentType::getLong),
   STRING(StringArgumentType::string, StringArgumentType::getString),
   GREEDY_STRING(StringArgumentType::greedyString, StringArgumentType::getString),
   WORD(StringArgumentType::word, StringArgumentType::getString),
   ENTITY(EntityArgument::m_91449_, EntityArgument::m_91452_),
   ENTITIES(EntityArgument::m_91460_, EntityArgument::m_91461_),
   PLAYER(EntityArgument::m_91466_, EntityArgument::m_91474_),
   PLAYERS(EntityArgument::m_91470_, EntityArgument::m_91477_),
   GAME_PROFILE(GameProfileArgument::m_94584_, GameProfileArgument::m_94590_),
   BLOCK_POS(BlockPosArgument::m_118239_, BlockPosArgument::m_174395_),
   BLOCK_POS_LOADED(BlockPosArgument::m_118239_, BlockPosArgument::m_118242_),
   COLUMN_POS(ColumnPosArgument::m_118989_, ColumnPosArgument::m_118992_),
   VEC3(() -> Vec3Argument.m_120847_(false), Vec3Argument::m_120844_),
   VEC2(() -> Vec2Argument.m_174954_(false), Vec2Argument::m_120825_),
   VEC3_CENTERED(Vec3Argument::m_120841_, Vec3Argument::m_120844_),
   VEC2_CENTERED(Vec2Argument::m_120822_, Vec2Argument::m_120825_),
   BLOCK_STATE(BlockStateArgument::m_234650_, BlockStateArgument::m_116123_),
   BLOCK_PREDICATE(BlockPredicateArgument::m_234627_, BlockPredicateArgument::m_115573_),
   ITEM_STACK(ItemArgument::m_235279_, ItemArgument::m_120963_),
   ITEM_PREDICATE(ItemPredicateArgument::m_235353_, ItemPredicateArgument::m_121040_),
   COLOR(ColorArgument::m_85463_, ColorArgument::m_85466_),
   COMPONENT(ComponentArgument::m_87114_, ComponentArgument::m_87117_),
   MESSAGE(MessageArgument::m_96832_, MessageArgument::m_96835_),
   NBT_COMPOUND(CompoundTagArgument::m_87657_, CompoundTagArgument::m_87660_),
   NBT_TAG(NbtTagArgument::m_100659_, NbtTagArgument::m_100662_),
   NBT_PATH(NbtPathArgument::m_99487_, NbtPathArgument::m_99498_),
   PARTICLE(ParticleArgument::m_103931_, ParticleArgument::m_103937_),
   ANGLE(AngleArgument::m_83807_, AngleArgument::m_83810_),
   ROTATION(RotationArgument::m_120479_, RotationArgument::m_120482_),
   SWIZZLE(SwizzleArgument::m_120807_, SwizzleArgument::m_120810_),
   ITEM_SLOT(SlotArgument::m_111276_, SlotArgument::m_111279_),
   RESOURCE_LOCATION(ResourceLocationArgument::m_106984_, ResourceLocationArgument::m_107011_),
   MOB_EFFECT(MobEffectArgument::m_98426_, MobEffectArgument::m_98429_),
   ENTITY_ANCHOR(EntityAnchorArgument::m_90350_, EntityAnchorArgument::m_90353_),
   INT_RANGE(RangeArgument::m_105404_, Ints::m_105419_),
   FLOAT_RANGE(RangeArgument::m_105405_, Floats::m_170804_),
   ITEM_ENCHANTMENT(ItemEnchantmentArgument::m_95260_, ItemEnchantmentArgument::m_95263_),
   ENTITY_SUMMON(EntitySummonArgument::m_93335_, EntitySummonArgument::m_93338_),
   DIMENSION(DimensionArgument::m_88805_, DimensionArgument::m_88808_),
   TIME(TimeArgument::m_113037_, IntegerArgumentType::getInteger),
   UUID(UuidArgument::m_113850_, UuidArgument::m_113853_),
   OBJECTIVE(ObjectiveArgument::m_101957_, ObjectiveArgument::m_101960_);

   private final Function<CommandBuildContext, ? extends ArgumentType<?>> factory;
   private final ArgumentFunction<?> getter;
   private static Map<ResourceLocation, ClassWrapper<?>> byNameCache;

   public static ClassWrapper<?> byName(ResourceLocation name) {
      ClassWrapper<?> wrapper = getOrCacheByName().get(name);
      if (wrapper == null) {
         throw new IllegalStateException("No argument type found for " + name);
      } else {
         return wrapper;
      }
   }

   public static void printAll() {
      for (Entry<ResourceLocation, ClassWrapper<?>> argType : getOrCacheByName().entrySet()) {
         ConsoleJS.SERVER.info("Argument type: " + argType.getKey() + " -> " + argType.getValue());
      }
   }

   private static Map<ResourceLocation, ClassWrapper<?>> getOrCacheByName() {
      return byNameCache == null ? (byNameCache = (Map<ResourceLocation, ClassWrapper<?>>)Util.m_137469_(new HashMap(), map -> {
         for (Entry<Class<?>, ArgumentTypeInfo<?, ?>> entry : ArgumentTypeInfos.f_235379_.entrySet()) {
            ResourceLocation id = Registry.f_235729_.m_7981_(entry.getValue());
            byNameCache.put(id, new ClassWrapper<>(entry.getKey()));
         }
      })) : byNameCache;
   }

   private ArgumentTypeWrapper(Supplier<? extends ArgumentType<?>> factory, ArgumentFunction<?> getter) {
      this.factory = ctx -> factory.get();
      this.getter = getter;
   }

   private ArgumentTypeWrapper(Function<CommandBuildContext, ? extends ArgumentType<?>> argType, ArgumentFunction<?> getter) {
      this.factory = argType;
      this.getter = getter;
   }

   @Deprecated(
      forRemoval = true
   )
   public ArgumentType<?> create() {
      ConsoleJS.SERVER.error("Using argument types without the event as context is deprecated and will be removed soon!");
      ConsoleJS.SERVER.error("Please use create(event) instead!");
      return (ArgumentType<?>)this.factory.apply(new CommandBuildContext((RegistryAccess)RegistryAccess.f_123049_.get()));
   }

   public ArgumentType<?> create(CommandRegistryEventJS event) {
      return (ArgumentType<?>)this.factory.apply(event.context);
   }

   public Object getResult(CommandContext<CommandSourceStack> context, String input) throws CommandSyntaxException {
      return this.getter.getResult(context, input);
   }
}
