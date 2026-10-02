package io.redspace.ironsspellbooks.api.util;

import com.mojang.math.Vector3f;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.entity.IMagicEntity;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.magic.SpellSelectionManager;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.IPresetSpellContainer;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.spells.SpellData;
import io.redspace.ironsspellbooks.capabilities.magic.TargetEntityCastData;
import io.redspace.ironsspellbooks.capabilities.magic.UpgradeData;
import io.redspace.ironsspellbooks.compat.Curios;
import io.redspace.ironsspellbooks.compat.tetra.TetraProxy;
import io.redspace.ironsspellbooks.config.ServerConfigs;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.VisualFallingBlockEntity;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.entity.spells.shield.ShieldEntity;
import io.redspace.ironsspellbooks.item.CastingItem;
import io.redspace.ironsspellbooks.item.Scroll;
import io.redspace.ironsspellbooks.item.SpellBook;
import io.redspace.ironsspellbooks.item.UniqueItem;
import io.redspace.ironsspellbooks.network.ServerboundCancelCast;
import io.redspace.ironsspellbooks.network.spell.ClientboundSyncTargetingData;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import io.redspace.ironsspellbooks.setup.Messages;
import io.redspace.ironsspellbooks.util.ModTags;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockCollisions;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;

public class Utils {
   public static final RandomSource random = RandomSource.m_216337_();

   public static long getServerTick() {
      return IronsSpellbooks.OVERWORLD.m_46467_();
   }

   public static String getStackTraceAsString() {
      Stream<StackTraceElement> trace = Arrays.stream(Thread.currentThread().getStackTrace());
      StringBuffer sb = new StringBuffer();
      trace.forEach(item -> {
         sb.append(item.toString());
         sb.append("\n");
      });
      return sb.toString();
   }

   public static void spawnInWorld(Level level, BlockPos pos, ItemStack remaining) {
      if (!remaining.m_41619_()) {
         ItemEntity itemEntity = new ItemEntity(level, (double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 0.5, (double)pos.m_123343_() + 0.5, remaining);
         itemEntity.m_32010_(40);
         itemEntity.m_20256_(itemEntity.m_20184_().m_82542_(0.0, 1.0, 0.0));
         level.m_7967_(itemEntity);
      }
   }

   public static boolean canBeUpgraded(ItemStack stack) {
      Item item = stack.m_41720_();
      return !ServerConfigs.UPGRADE_BLACKLIST_ITEMS.contains(item)
         && (
            stack.m_41720_() instanceof SpellBook
               || stack.m_41720_() instanceof ArmorItem
               || stack.m_41720_() instanceof CastingItem
               || ServerConfigs.UPGRADE_WHITELIST_ITEMS.contains(item)
         );
   }

   public static String timeFromTicks(float ticks, int decimalPlaces) {
      float ticks_to_seconds = 20.0F;
      float seconds_to_minutes = 60.0F;
      String affix = "s";
      float time = ticks / ticks_to_seconds;
      if (time > seconds_to_minutes) {
         time /= seconds_to_minutes;
         affix = "m";
      }

      return stringTruncation((double)time, decimalPlaces) + affix;
   }

   public static double softCapFormula(double x) {
      return x <= 1.75 ? x : 1.0 / (-16.0 * (x - 1.5)) + 2.0;
   }

   public static boolean isPlayerHoldingSpellBook(Player player) {
      Optional<SlotResult> slotResult = CuriosApi.getCuriosHelper().findCurio(player, Curios.SPELLBOOK_SLOT, 0);
      return slotResult.isPresent();
   }

   @Nullable
   public static ItemStack getPlayerSpellbookStack(@NotNull Player player) {
      return CuriosApi.getCuriosHelper().findCurio(player, Curios.SPELLBOOK_SLOT, 0).<ItemStack>map(SlotResult::stack).orElse(null);
   }

   public static void setPlayerSpellbookStack(@NotNull Player player, ItemStack itemStack) {
      CuriosApi.getCuriosHelper().setEquippedCurio(player, Curios.SPELLBOOK_SLOT, 0, itemStack);
   }

   public static ServerPlayer getServerPlayer(Level level, UUID uuid) {
      return level.m_7654_().m_6846_().m_11259_(uuid);
   }

   public static String stringTruncation(double f, int decimalPlaces) {
      if (f == Math.floor(f)) {
         return Integer.toString((int)f);
      } else {
         double multiplier = Math.pow(10.0, (double)decimalPlaces);
         double truncatedValue = Math.floor(f * multiplier) / multiplier;
         String result = Double.toString(truncatedValue);
         result = result.replaceAll("0*$", "");
         return result.endsWith(".") ? result.substring(0, result.length() - 1) : result;
      }
   }

   public static float intPow(float f, int exponent) {
      if (exponent == 0) {
         return 1.0F;
      } else {
         float b = f;

         for (int i = 1; i < Math.abs(exponent); i++) {
            b *= f;
         }

         return exponent < 0 ? 1.0F / b : b;
      }
   }

   public static double intPow(double d, int exponent) {
      if (exponent == 0) {
         return 1.0;
      } else {
         double b = d;

         for (int i = 1; i < Math.abs(exponent); i++) {
            b *= d;
         }

         return exponent < 0 ? 1.0 / b : b;
      }
   }

   public static float getAngle(Vec2 a, Vec2 b) {
      return getAngle((double)a.f_82470_, (double)a.f_82471_, (double)b.f_82470_, (double)b.f_82471_);
   }

   public static float getAngle(double ax, double ay, double bx, double by) {
      return (float)Math.atan2(by - ay, bx - ax) + 3.141F;
   }

   public static BlockHitResult getTargetOld(Level level, Player player, Fluid clipContext, double reach) {
      float f = player.m_146909_();
      float f1 = player.m_146908_();
      Vec3 vec3 = player.m_146892_();
      float f2 = Mth.m_14089_(-f1 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f3 = Mth.m_14031_(-f1 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f4 = -Mth.m_14089_(-f * (float) (Math.PI / 180.0));
      float f5 = Mth.m_14031_(-f * (float) (Math.PI / 180.0));
      float f6 = f3 * f4;
      float f7 = f2 * f4;
      Vec3 vec31 = vec3.m_82520_((double)f6 * reach, (double)f5 * reach, (double)f7 * reach);
      return level.m_45547_(new ClipContext(vec3, vec31, Block.OUTLINE, clipContext, player));
   }

   public static BlockHitResult getTargetBlock(Level level, LivingEntity entity, Fluid clipContext, double reach) {
      Vec3 rotation = entity.m_20154_().m_82541_().m_82490_(reach);
      Vec3 pos = entity.m_146892_();
      Vec3 dest = rotation.m_82549_(pos);
      return level.m_45547_(new ClipContext(pos, dest, Block.COLLIDER, clipContext, entity));
   }

   public static boolean hasLineOfSight(Level level, Vec3 start, Vec3 end, boolean checkForShields) {
      if (checkForShields) {
         List<ShieldEntity> shieldEntities = level.m_45976_(ShieldEntity.class, new AABB(start, end));
         if (shieldEntities.size() > 0) {
            HitResult shieldImpact = checkEntityIntersecting(shieldEntities.get(0), start, end, 0.0F);
            if (shieldImpact.m_6662_() != Type.MISS) {
               end = shieldImpact.m_82450_();
            }
         }
      }

      return level.m_45547_(new ClipContext(start, end, Block.COLLIDER, Fluid.NONE, null)).m_6662_() == Type.MISS;
   }

   public static boolean hasLineOfSight(Level level, Entity entity1, Entity entity2, boolean checkForShields) {
      return hasLineOfSight(level, entity1.m_146892_(), entity2.m_20191_().m_82399_(), checkForShields);
   }

   public static BlockHitResult raycastForBlock(Level level, Vec3 start, Vec3 end, Fluid clipContext) {
      return level.m_45547_(new ClipContext(start, end, Block.COLLIDER, clipContext, null));
   }

   public static HitResult checkEntityIntersecting(Entity entity, Vec3 start, Vec3 end, float bbInflation) {
      Vec3 hitPos = null;
      if (entity.isMultipartEntity()) {
         for (PartEntity p : entity.getParts()) {
            Vec3 hit = (Vec3)p.m_20191_().m_82400_((double)bbInflation).m_82371_(start, end).orElse(null);
            if (hit != null) {
               hitPos = hit;
               break;
            }
         }
      } else {
         hitPos = (Vec3)entity.m_20191_().m_82400_((double)bbInflation).m_82371_(start, end).orElse(null);
      }

      return (HitResult)(hitPos != null ? new EntityHitResult(entity, hitPos) : BlockHitResult.m_82426_(end, Direction.UP, new BlockPos(end)));
   }

   public static Vec3 getPositionFromEntityLookDirection(Entity originEntity, float distance) {
      Vec3 start = originEntity.m_146892_();
      return originEntity.m_20154_().m_82541_().m_82490_((double)distance).m_82549_(start);
   }

   public static HitResult raycastForEntity(Level level, Entity originEntity, float distance, boolean checkForBlocks) {
      Vec3 start = originEntity.m_146892_();
      Vec3 end = originEntity.m_20154_().m_82541_().m_82490_((double)distance).m_82549_(start);
      return raycastForEntity(level, originEntity, start, end, checkForBlocks);
   }

   public static HitResult raycastForEntity(Level level, Entity originEntity, float distance, boolean checkForBlocks, float bbInflation) {
      Vec3 start = originEntity.m_146892_();
      Vec3 end = originEntity.m_20154_().m_82541_().m_82490_((double)distance).m_82549_(start);
      return internalRaycastForEntity(level, originEntity, start, end, checkForBlocks, bbInflation, Utils::canHitWithRaycast);
   }

   public static HitResult raycastForEntity(Level level, Entity originEntity, Vec3 start, Vec3 end, boolean checkForBlocks) {
      return internalRaycastForEntity(level, originEntity, start, end, checkForBlocks, 0.0F, Utils::canHitWithRaycast);
   }

   public static HitResult raycastForEntity(
      Level level, Entity originEntity, Vec3 start, Vec3 end, boolean checkForBlocks, float bbInflation, Predicate<? super Entity> filter
   ) {
      return internalRaycastForEntity(level, originEntity, start, end, checkForBlocks, bbInflation, filter);
   }

   public static HitResult raycastForEntityOfClass(Level level, Entity originEntity, Vec3 start, Vec3 end, boolean checkForBlocks, Class<? extends Entity> c) {
      return internalRaycastForEntity(level, originEntity, start, end, checkForBlocks, 0.0F, entity -> entity.getClass() == c);
   }

   public static void releaseUsingHelper(LivingEntity entity, ItemStack itemStack, int ticksUsed) {
      if (entity instanceof ServerPlayer serverPlayer) {
         MagicData pmd = MagicData.getPlayerMagicData(serverPlayer);
         if (pmd.isCasting()) {
            serverSideCancelCast(serverPlayer);
            serverPlayer.m_5810_();
         }
      }
   }

   public static boolean serverSideInitiateCast(ServerPlayer serverPlayer) {
      SpellSelectionManager ssm = new SpellSelectionManager(serverPlayer);
      SpellSelectionManager.SelectionOption spellItem = ssm.getSelection();
      if (spellItem != null) {
         SpellData spellData = ssm.getSelectedSpellData();
         if (spellData != SpellData.EMPTY) {
            MagicData playerMagicData = MagicData.getPlayerMagicData(serverPlayer);
            if (playerMagicData.isCasting() && !playerMagicData.getCastingSpellId().equals(spellData.getSpell().getSpellId())) {
               ServerboundCancelCast.cancelCast(serverPlayer, playerMagicData.getCastType() != CastType.LONG);
            }

            return spellData.getSpell()
               .attemptInitiateCast(
                  ItemStack.f_41583_,
                  spellData.getSpell().getLevelFor(spellData.getLevel(), serverPlayer),
                  serverPlayer.f_19853_,
                  serverPlayer,
                  spellItem.getCastSource(),
                  true,
                  spellItem.slot
               );
         }
      } else if (getPlayerSpellbookStack(serverPlayer) == null) {
         ItemStack heldSpellbookStack = serverPlayer.m_21205_();
         if (!(heldSpellbookStack.m_41720_() instanceof SpellBook)) {
            heldSpellbookStack = serverPlayer.m_21206_();
         }

         if (heldSpellbookStack.m_41720_() instanceof SpellBook spellBook) {
            spellBook.onEquipFromUse(new SlotContext(Curios.SPELLBOOK_SLOT, serverPlayer, 0, false, true), heldSpellbookStack);
            setPlayerSpellbookStack(serverPlayer, heldSpellbookStack.m_41620_(1));
         }
      }

      return false;
   }

   public static boolean serverSideInitiateQuickCast(ServerPlayer serverPlayer, int slot) {
      SpellData spellData = new SpellSelectionManager(serverPlayer).getSpellSlot(slot).spellData;
      if (spellData != SpellData.EMPTY) {
         MagicData playerMagicData = MagicData.getPlayerMagicData(serverPlayer);
         if (playerMagicData.isCasting() && !playerMagicData.getCastingSpellId().equals(spellData.getSpell().getSpellId())) {
            ServerboundCancelCast.cancelCast(serverPlayer, playerMagicData.getCastType() != CastType.LONG);
         }

         return spellData.getSpell()
            .attemptInitiateCast(
               ItemStack.f_41583_,
               spellData.getSpell().getLevelFor(spellData.getLevel(), serverPlayer),
               serverPlayer.f_19853_,
               serverPlayer,
               CastSource.SPELLBOOK,
               true,
               Curios.SPELLBOOK_SLOT
            );
      } else {
         return false;
      }
   }

   private static HitResult internalRaycastForEntity(
      Level level, Entity originEntity, Vec3 start, Vec3 end, boolean checkForBlocks, float bbInflation, Predicate<? super Entity> filter
   ) {
      BlockHitResult blockHitResult = null;
      if (checkForBlocks) {
         blockHitResult = level.m_45547_(new ClipContext(start, end, Block.COLLIDER, Fluid.NONE, originEntity));
         end = blockHitResult.m_82450_();
      }

      AABB range = originEntity.m_20191_().m_82369_(end.m_82546_(start));
      List<HitResult> hits = new ArrayList<>();

      for (Entity target : level.m_6249_(originEntity, range, filter)) {
         HitResult hit = checkEntityIntersecting(target, start, end, bbInflation);
         if (hit.m_6662_() != Type.MISS) {
            hits.add(hit);
         }
      }

      if (!hits.isEmpty()) {
         hits.sort((o1, o2) -> o1.m_82450_().m_82557_(start) < o2.m_82450_().m_82557_(start) ? -1 : 1);
         return hits.get(0);
      } else {
         return checkForBlocks ? blockHitResult : BlockHitResult.m_82426_(end, Direction.UP, new BlockPos(end));
      }
   }

   public static void serverSideCancelCast(ServerPlayer serverPlayer) {
      ServerboundCancelCast.cancelCast(
         serverPlayer, MagicData.getPlayerMagicData(serverPlayer).getCastingSpell().getSpell().getCastType() == CastType.CONTINUOUS
      );
   }

   public static void serverSideCancelCast(ServerPlayer serverPlayer, boolean triggerCooldown) {
      ServerboundCancelCast.cancelCast(serverPlayer, triggerCooldown);
   }

   public static float smoothstep(float a, float b, float x) {
      x = 6.0F * x * x * x * x * x - 15.0F * x * x * x * x + 10.0F * x * x * x;
      return a + (b - a) * x;
   }

   private static boolean canHitWithRaycast(Entity entity) {
      return entity.m_6087_() && entity.m_6084_();
   }

   public static Vec2 rotationFromDirection(Vec3 vector) {
      float pitch = (float)Math.asin(vector.f_82480_);
      float yaw = (float)Math.atan2(vector.f_82479_, vector.f_82481_);
      return new Vec2(pitch, yaw);
   }

   public static boolean doMeleeAttack(Mob attacker, Entity target, DamageSource damageSource) {
      float f = (float)attacker.m_21133_(Attributes.f_22281_);
      float f1 = (float)attacker.m_21133_(Attributes.f_22282_);
      if (target instanceof LivingEntity) {
         f += EnchantmentHelper.m_44833_(attacker.m_21205_(), ((LivingEntity)target).m_6336_());
         f1 += (float)EnchantmentHelper.m_44894_(attacker);
      }

      int i = EnchantmentHelper.m_44914_(attacker);
      if (i > 0) {
         target.m_20254_(i * 4);
      }

      boolean flag = DamageSources.applyDamage(target, f, damageSource);
      if (flag) {
         if (f1 > 0.0F && target instanceof LivingEntity livingTarget) {
            ((LivingEntity)target)
               .m_147240_(
                  (double)(f1 * 0.5F),
                  (double)Mth.m_14031_(attacker.m_146908_() * (float) (Math.PI / 180.0)),
                  (double)(-Mth.m_14089_(attacker.m_146908_() * (float) (Math.PI / 180.0)))
               );
            attacker.m_20256_(attacker.m_20184_().m_82542_(0.6, 1.0, 0.6));
            livingTarget.m_6703_(attacker);
         }

         if (target instanceof Player player) {
            ItemStack pMobItemStack = attacker.m_21205_();
            ItemStack pPlayerItemStack = player.m_6117_() ? player.m_21211_() : ItemStack.f_41583_;
            if (!pMobItemStack.m_41619_()
               && !pPlayerItemStack.m_41619_()
               && pMobItemStack.m_41720_() instanceof AxeItem
               && pPlayerItemStack.m_150930_(Items.f_42740_)) {
               float f2 = 0.25F + (float)EnchantmentHelper.m_44926_(attacker) * 0.05F;
               if (attacker.m_217043_().m_188501_() < f2) {
                  player.m_36335_().m_41524_(Items.f_42740_, 100);
                  attacker.f_19853_.m_7605_(player, (byte)30);
               }
            }
         }

         attacker.m_19970_(attacker, target);
         attacker.m_21335_(target);
      }

      return flag;
   }

   public static void throwTarget(LivingEntity attacker, LivingEntity target, float multiplier, boolean ignoreKBResistance) {
      double d0 = attacker.m_21133_(Attributes.f_22282_) * (double)multiplier;
      double d1 = ignoreKBResistance ? 0.0 : target.m_21133_(Attributes.f_22278_);
      double d2 = d0 - d1;
      if (!(d2 <= 0.0)) {
         double d3 = target.m_20185_() - attacker.m_20185_();
         double d4 = target.m_20189_() - attacker.m_20189_();
         float f = (float)(random.m_188503_(21) - 10);
         double d5 = d2 * (double)(random.m_188501_() * 0.5F + 0.2F);
         Vec3 vec3 = new Vec3(d3, 0.0, d4).m_82541_().m_82490_(d5).m_82524_(f);
         double d6 = d2 * (double)random.m_188501_() * 0.5;
         target.m_5997_(vec3.f_82479_, d6, vec3.f_82481_);
         target.f_19864_ = true;
      }
   }

   public static double getRandomScaled(double scale) {
      return (2.0 * Math.random() - 1.0) * scale;
   }

   public static Vec3 getRandomVec3(double scale) {
      return new Vec3(getRandomScaled(scale), getRandomScaled(scale), getRandomScaled(scale));
   }

   public static Vector3f getRandomVec3f(double scale) {
      return new Vector3f((float)getRandomScaled(scale), (float)getRandomScaled(scale), (float)getRandomScaled(scale));
   }

   public static boolean shouldHealEntity(LivingEntity healer, LivingEntity target) {
      if (healer instanceof NeutralMob neutralMob && neutralMob.m_21674_(target)) {
         return false;
      }

      if (healer == target) {
         return true;
      } else if (target.m_6095_().m_204039_(ModTags.ALWAYS_HEAL) && !(healer instanceof Enemy)) {
         return true;
      } else if (target.m_7307_(healer) || healer.m_7307_(target)) {
         return true;
      } else if (healer.m_5647_() != null) {
         return target.m_20031_(healer.m_5647_());
      } else {
         return healer instanceof Player ? target instanceof Player : healer.m_6336_() == target.m_6336_() && healer instanceof Enemy ^ target instanceof Enemy;
      }
   }

   public static boolean canImbue(ItemStack itemStack) {
      if (itemStack.m_41720_() instanceof UniqueItem) {
         return false;
      } else {
         Item item = itemStack.m_41720_();
         if (ServerConfigs.IMBUE_BLACKLIST_ITEMS.contains(item)) {
            return false;
         } else if (ServerConfigs.IMBUE_WHITELIST_ITEMS.contains(item)) {
            return true;
         } else if (itemStack.m_41720_() instanceof SwordItem) {
            return true;
         } else {
            return ISpellContainer.isSpellContainer(itemStack) && !(itemStack.m_41720_() instanceof Scroll) && !(itemStack.m_41720_() instanceof SpellBook)
               ? true
               : TetraProxy.PROXY.canImbue(itemStack);
         }
      }
   }

   public static ItemStack handleShriving(ItemStack baseStack) {
      ItemStack result = baseStack.m_41777_();
      if (result.m_150930_((Item)ItemRegistry.SCROLL.get())) {
         return ItemStack.f_41583_;
      } else {
         boolean hasResult = false;
         if (ISpellContainer.isSpellContainer(result) && !(result.m_41720_() instanceof SpellBook) && !(result.m_41720_() instanceof UniqueItem)) {
            if (result.m_41720_() instanceof IPresetSpellContainer) {
               ISpellContainer spellContainer = ISpellContainer.get(result);
               spellContainer.getActiveSpells().forEach(spellData -> spellContainer.removeSpell(spellData.getSpell(), result));
            } else {
               result.m_41749_("ISB_Spells");
            }

            hasResult = true;
         }

         if (UpgradeData.hasUpgradeData(result)) {
            UpgradeData.removeUpgradeData(result);
            hasResult = true;
         }

         return hasResult ? result : ItemStack.f_41583_;
      }
   }

   public static boolean validAntiMagicTarget(Entity entity) {
      return entity instanceof AntiMagicSusceptible || entity instanceof Player player || entity instanceof IMagicEntity var1;
   }

   public static float findRelativeGroundLevel(Level level, Vec3 start, int maxSteps) {
      if (level.m_8055_(new BlockPos(start)).m_60828_(level, new BlockPos(start))) {
         for (int i = 0; i < maxSteps; i++) {
            start = start.m_82520_(0.0, 1.0, 0.0);
            BlockPos pos = new BlockPos(start);
            if (!level.m_8055_(pos).m_60828_(level, pos)) {
               return (float)pos.m_123342_();
            }
         }
      }

      return (float)level.m_45547_(new ClipContext(start, start.m_82520_(0.0, (double)(-maxSteps), 0.0), Block.COLLIDER, Fluid.NONE, null)).m_82450_().f_82480_;
   }

   public static Vec3 moveToRelativeGroundLevel(Level level, Vec3 start, int maxSteps) {
      return moveToRelativeGroundLevel(level, start, maxSteps, maxSteps);
   }

   public static Vec3 moveToRelativeGroundLevel(Level level, Vec3 start, int maxStepsUp, int maxStepsDown) {
      BlockCollisions blockcollisions = new BlockCollisions(level, null, new AABB(0.0, 0.0, 0.0, 0.5, 0.5, 0.5).m_82383_(start), true);
      if (blockcollisions.hasNext()) {
         for (int i = 1; i < maxStepsUp; i++) {
            blockcollisions = new BlockCollisions(level, null, new AABB(0.0, 0.0, 0.0, 0.5, 0.5, 0.5).m_82383_(start.m_82520_(0.0, (double)i * 0.5, 0.0)), true);
            if (!blockcollisions.hasNext()) {
               start = start.m_82520_(0.0, (double)i * 0.5, 0.0);
               break;
            }
         }
      }

      return level.m_45547_(new ClipContext(start, start.m_82520_(0.0, (double)(-maxStepsDown), 0.0), Block.COLLIDER, Fluid.NONE, null)).m_82450_();
   }

   public static boolean checkMonsterSpawnRules(ServerLevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
      return !pLevel.m_204166_(pPos).m_203656_(ModTags.NO_DEFAULT_SPAWNS)
         && pLevel.m_46791_() != Difficulty.PEACEFUL
         && Monster.m_219009_(pLevel, pPos, pRandom)
         && Monster.m_217057_((EntityType)EntityRegistry.NECROMANCER.get(), pLevel, pSpawnType, pPos, pRandom);
   }

   public static void sendTargetedNotification(ServerPlayer target, LivingEntity caster, AbstractSpell spell) {
      target.f_8906_
         .m_9829_(
            new ClientboundSetActionBarTextPacket(
               Component.m_237110_("ui.irons_spellbooks.spell_target_warning", new Object[]{caster.m_5446_().getString(), spell.getDisplayName(target)})
                  .m_130940_(ChatFormatting.LIGHT_PURPLE)
            )
         );
   }

   public static boolean preCastTargetHelper(Level level, LivingEntity caster, MagicData playerMagicData, AbstractSpell spell, int range, float aimAssist) {
      return preCastTargetHelper(level, caster, playerMagicData, spell, range, aimAssist, true);
   }

   public static boolean preCastTargetHelper(
      Level level, LivingEntity caster, MagicData playerMagicData, AbstractSpell spell, int range, float aimAssist, boolean sendFailureMessage
   ) {
      return preCastTargetHelper(level, caster, playerMagicData, spell, range, aimAssist, sendFailureMessage, x -> true);
   }

   public static boolean preCastTargetHelper(
      Level level,
      LivingEntity caster,
      MagicData playerMagicData,
      AbstractSpell spell,
      int range,
      float aimAssist,
      boolean sendFailureMessage,
      Predicate<LivingEntity> filter
   ) {
      if (raycastForEntity(caster.f_19853_, caster, (float)range, true, aimAssist) instanceof EntityHitResult entityHit
         && entityHit.m_82443_() instanceof LivingEntity livingTarget
         && filter.test(livingTarget)) {
         playerMagicData.setAdditionalCastData(new TargetEntityCastData(livingTarget));
         if (caster instanceof ServerPlayer serverPlayer) {
            if (spell.getCastType() != CastType.INSTANT) {
               Messages.sendToPlayer(new ClientboundSyncTargetingData(livingTarget, spell), serverPlayer);
            }

            serverPlayer.f_8906_
               .m_9829_(
                  new ClientboundSetActionBarTextPacket(
                     Component.m_237110_(
                           "ui.irons_spellbooks.spell_target_success", new Object[]{livingTarget.m_5446_().getString(), spell.getDisplayName(serverPlayer)}
                        )
                        .m_130940_(ChatFormatting.GREEN)
                  )
               );
         }

         if (livingTarget instanceof ServerPlayer serverPlayer) {
            sendTargetedNotification(serverPlayer, caster, spell);
         }

         return true;
      }

      if (sendFailureMessage && caster instanceof ServerPlayer serverPlayer) {
         serverPlayer.f_8906_
            .m_9829_(new ClientboundSetActionBarTextPacket(Component.m_237115_("ui.irons_spellbooks.cast_error_target").m_130940_(ChatFormatting.RED)));
      }

      return false;
   }

   public static Vector3f deconstructRGB(int color) {
      int red = color >> 16 & 0xFF;
      int green = color >> 8 & 0xFF;
      int blue = color & 0xFF;
      return new Vector3f((float)red / 255.0F, (float)green / 255.0F, (float)blue / 255.0F);
   }

   public static int packRGB(Vector3f color) {
      int red = (int)(color.m_122239_() * 255.0F);
      int green = (int)(color.m_122260_() * 255.0F);
      int blue = (int)(color.m_122269_() * 255.0F);
      return red << 16 | green << 8 | blue;
   }

   public static CompoundTag saveAllItems(CompoundTag pTag, NonNullList<ItemStack> pList, String location) {
      ListTag listtag = new ListTag();

      for (int i = 0; i < pList.size(); i++) {
         ItemStack itemstack = (ItemStack)pList.get(i);
         if (!itemstack.m_41619_()) {
            CompoundTag compoundtag = new CompoundTag();
            compoundtag.m_128344_("Slot", (byte)i);
            itemstack.m_41739_(compoundtag);
            listtag.add(compoundtag);
         }
      }

      if (!listtag.isEmpty()) {
         pTag.m_128365_(location, listtag);
      }

      return pTag;
   }

   public static void loadAllItems(CompoundTag pTag, NonNullList<ItemStack> pList, String location) {
      ListTag listtag = pTag.m_128437_(location, 10);

      for (int i = 0; i < listtag.size(); i++) {
         CompoundTag compoundtag = listtag.m_128728_(i);
         int j = compoundtag.m_128445_("Slot") & 255;
         if (j >= 0 && j < pList.size()) {
            pList.set(j, ItemStack.m_41712_(compoundtag));
         }
      }
   }

   public static float getWeaponDamage(LivingEntity entity, MobType entityForDamageBonus) {
      if (entity != null) {
         float weapon = (float)entity.m_21133_(Attributes.f_22281_);
         float fist = (float)entity.m_21172_(Attributes.f_22281_);
         if (weapon <= fist) {
            weapon -= fist;
         }

         float enchant = EnchantmentHelper.m_44833_(entity.m_21205_(), entityForDamageBonus);
         return weapon + enchant;
      } else {
         return 0.0F;
      }
   }

   public static void createTremorBlock(Level level, BlockPos blockPos, float impulseStrength) {
      if (level.m_8055_(blockPos.m_7494_()).m_60795_() || level.m_8055_(blockPos.m_7494_().m_7494_()).m_60795_()) {
         VisualFallingBlockEntity fallingblockentity = new VisualFallingBlockEntity(
            level, (double)blockPos.m_123341_(), (double)blockPos.m_123342_(), (double)blockPos.m_123343_(), level.m_8055_(blockPos), 10
         );
         fallingblockentity.m_20334_(0.0, (double)impulseStrength, 0.0);
         level.m_7967_(fallingblockentity);
         if (!level.m_8055_(blockPos.m_7494_()).m_60795_()) {
            VisualFallingBlockEntity fallingblockentity2 = new VisualFallingBlockEntity(
               level, (double)blockPos.m_123341_(), (double)(blockPos.m_123342_() + 1), (double)blockPos.m_123343_(), level.m_8055_(blockPos.m_7494_()), 10
            );
            fallingblockentity2.m_20334_(0.0, (double)impulseStrength, 0.0);
            level.m_7967_(fallingblockentity2);
         }
      }
   }
}
