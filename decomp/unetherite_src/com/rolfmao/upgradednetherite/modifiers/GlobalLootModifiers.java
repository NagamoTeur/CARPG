package com.rolfmao.upgradednetherite.modifiers;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.rolfmao.upgradednetherite.config.UpgradedNetheriteConfig;
import com.rolfmao.upgradednetherite.utils.ToolUtil;
import com.rolfmao.upgradednetherite.utils.check.EnderUtil;
import com.rolfmao.upgradednetherite.utils.check.FireUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.jetbrains.annotations.NotNull;

public class GlobalLootModifiers {
   public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLM = DeferredRegister.create(
      Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, "upgradednetherite"
   );
   private static final RegistryObject<Codec<GlobalLootModifiers.AutoSmeltModifier>> AUTOSMELT_MODIFIER = GLM.register(
      "auto_smelt_tool", GlobalLootModifiers.AutoSmeltModifier.CODEC
   );
   private static final RegistryObject<Codec<GlobalLootModifiers.EnderTeleportModifier>> ENDERTP_MODIFIER = GLM.register(
      "ender_teleport_tool", GlobalLootModifiers.EnderTeleportModifier.CODEC
   );

   private static class AutoSmeltModifier extends LootModifier {
      public static final Supplier<Codec<GlobalLootModifiers.AutoSmeltModifier>> CODEC = Suppliers.memoize(
         () -> RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, GlobalLootModifiers.AutoSmeltModifier::new))
      );

      public AutoSmeltModifier(LootItemCondition[] conditionsIn) {
         super(conditionsIn);
      }

      @Nonnull
      @NotNull
      protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
         ItemStack tool = (ItemStack)context.m_78953_(LootContextParams.f_81463_);
         Entity killer = (Entity)context.m_78953_(LootContextParams.f_81459_);
         Entity entity = (Entity)context.m_78953_(LootContextParams.f_81455_);
         BlockState blockState = (BlockState)context.m_78953_(LootContextParams.f_81461_);
         Player player = null;
         Projectile arrow = null;
         if (entity instanceof Player) {
            player = (Player)entity;
         }

         if (player == null && killer instanceof Player) {
            player = (Player)killer;
         }

         if (killer instanceof Projectile) {
            arrow = (Projectile)killer;
         }

         ObjectArrayList<ItemStack> itemStackList = new ObjectArrayList();
         if (blockState != null && player != null && player.m_6047_()) {
            return generatedLoot;
         } else if (UpgradedNetheriteConfig.EnableAutoSmelt && (tool == null || !ToolUtil.getDisableEffect(tool))) {
            if (player != null && FireUtil.isFireToolOrWeapon(player.m_21205_()) && !ToolUtil.getDisableEffect(player.m_21205_())
               || arrow != null && !arrow.m_19880_().isEmpty() && FireUtil.isFireProjectile(arrow)) {
               if (tool != null) {
                  Integer FortuneLevel = 0;
                  Map<Enchantment, Integer> enchantments = EnchantmentHelper.m_44831_(tool);
                  if (!enchantments.isEmpty() && enchantments.containsKey(Enchantments.f_44987_)) {
                     FortuneLevel = enchantments.get(Enchantments.f_44987_);
                  }

                  Integer finalFortuneLevel = FortuneLevel;
                  generatedLoot.forEach(stack -> itemStackList.add(this.autoSmelt(stack, context.m_78952_(), finalFortuneLevel)));
               } else {
                  generatedLoot.forEach(stack -> itemStackList.add(this.autoSmelt(stack, context.m_78952_(), 0)));
               }

               return itemStackList;
            } else {
               return generatedLoot;
            }
         } else {
            return generatedLoot;
         }
      }

      protected ItemStack autoSmelt(ItemStack stack, Level level, Integer fortuneLevel) {
         Integer countBonus = 0;
         Optional<ItemStack> iStackSmelt = level.m_7465_()
            .m_44015_(RecipeType.f_44108_, new SimpleContainer(new ItemStack[]{stack}), level)
            .<ItemStack>map(AbstractCookingRecipe::m_8043_)
            .filter(itemStack -> !itemStack.m_41619_());
         if (UpgradedNetheriteConfig.EnableAutoSmeltFortune
            && fortuneLevel > 0
            && (
               stack.toString().contains("ore") && iStackSmelt.toString().contains("ingot")
                  || stack.toString().contains("log") && iStackSmelt.toString().contains("charcoal")
            )
            && fortuneLevel > 0) {
            Double rand = Math.random();
            if (rand >= (double)(2 / (fortuneLevel + 2))) {
               Integer randI = (int)(Math.random() * (double)(fortuneLevel + 1));
               countBonus = randI;
            }
         }

         Integer finalCountBonus = countBonus;
         return iStackSmelt.<ItemStack>map(
               itemStack -> ItemHandlerHelper.copyStackWithSize(itemStack, stack.m_41613_() * itemStack.m_41613_() * (1 + finalCountBonus))
            )
            .orElse(stack);
      }

      public Codec<? extends IGlobalLootModifier> codec() {
         return CODEC.get();
      }
   }

   private static class EnderTeleportModifier extends LootModifier {
      public static final Supplier<Codec<GlobalLootModifiers.EnderTeleportModifier>> CODEC = Suppliers.memoize(
         () -> RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, GlobalLootModifiers.EnderTeleportModifier::new))
      );

      public EnderTeleportModifier(LootItemCondition[] conditionsIn) {
         super(conditionsIn);
      }

      @Nonnull
      @NotNull
      protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
         Entity killer = (Entity)context.m_78953_(LootContextParams.f_81459_);
         Entity entity = (Entity)context.m_78953_(LootContextParams.f_81455_);
         BlockState blockState = (BlockState)context.m_78953_(LootContextParams.f_81461_);
         Projectile arrow = null;
         Player player = null;
         if (blockState != null && entity instanceof Player) {
            player = (Player)entity;
         } else if (blockState == null && killer instanceof Player) {
            player = (Player)killer;
         } else if (blockState == null && killer instanceof Projectile) {
            arrow = (Projectile)killer;
         }

         if (player != null && UpgradedNetheriteConfig.EnableTeleportChest) {
            ItemStack heldItem = player.m_21205_();
            if (EnderUtil.isEnderToolOrWeapon(player.m_21205_())
               && !ToolUtil.getDisableEffect(heldItem)
               && heldItem.m_41783_() != null
               && heldItem.m_41783_().m_128441_("UpgradedNetherite_Tagged")
               && heldItem.m_41783_().m_128471_("UpgradedNetherite_Tagged")) {
               Level level = player.f_19853_;
               String levelPath = level.m_46472_().m_135782_().m_135815_();
               if (!levelPath.equals(heldItem.m_41783_().m_128461_("UpgradedNetherite_Dimension"))) {
                  return generatedLoot;
               }

               BlockPos blockPos = new BlockPos(
                  heldItem.m_41783_().m_128465_("UpgradedNetherite_Position")[0],
                  heldItem.m_41783_().m_128465_("UpgradedNetherite_Position")[1],
                  heldItem.m_41783_().m_128465_("UpgradedNetherite_Position")[2]
               );
               BlockState state = level.m_8055_(blockPos);
               if (state.m_155947_()) {
                  BlockEntity blockEntity = level.m_7702_(blockPos);
                  if (blockEntity != null) {
                     IItemHandler iItemHandler = (IItemHandler)((ImmutablePair)blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                           .map(capability -> ImmutablePair.of(capability, blockEntity))
                           .get())
                        .getKey();
                     if (iItemHandler != null) {
                        ObjectArrayList<ItemStack> itemStackList = new ObjectArrayList();
                        generatedLoot.forEach(stack -> itemStackList.add(ItemHandlerHelper.insertItemStacked(iItemHandler, stack, false)));
                        return itemStackList;
                     }
                  }
               }
            }
         } else if (arrow != null
            && UpgradedNetheriteConfig.EnableTeleportChest
            && EnderUtil.isEnderProjectile(arrow)
            && arrow.getPersistentData().m_128471_("UpgradedNetherite_Tagged")) {
            Level levelx = arrow.f_19853_;
            String levelPathx = levelx.m_46472_().m_135782_().m_135815_();
            if (!levelPathx.equals(arrow.getPersistentData().m_128461_("UpgradedNetherite_Dimension"))) {
               return generatedLoot;
            }

            BlockPos blockPos = new BlockPos(
               arrow.getPersistentData().m_128465_("UpgradedNetherite_Position")[0],
               arrow.getPersistentData().m_128465_("UpgradedNetherite_Position")[1],
               arrow.getPersistentData().m_128465_("UpgradedNetherite_Position")[2]
            );
            BlockState state = levelx.m_8055_(blockPos);
            if (state.m_155947_()) {
               BlockEntity blockEntity = levelx.m_7702_(blockPos);
               if (blockEntity != null) {
                  IItemHandler iItemHandler = (IItemHandler)((ImmutablePair)blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                        .map(capability -> ImmutablePair.of(capability, blockEntity))
                        .get())
                     .getKey();
                  if (iItemHandler != null) {
                     ObjectArrayList<ItemStack> itemStackList = new ObjectArrayList();
                     generatedLoot.forEach(stack -> itemStackList.add(ItemHandlerHelper.insertItemStacked(iItemHandler, stack, false)));
                     return itemStackList;
                  }
               }
            }
         }

         return generatedLoot;
      }

      public Codec<? extends IGlobalLootModifier> codec() {
         return CODEC.get();
      }
   }
}
