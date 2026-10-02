package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.item.ICasterTool;
import com.hollingsworth.arsnouveau.api.nbt.ItemstackData;
import com.hollingsworth.arsnouveau.api.spell.ISpellCaster;
import com.hollingsworth.arsnouveau.api.spell.ITurretBehavior;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellCaster;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.IWrappedCaster;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.LivingCaster;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.PlayerCaster;
import com.hollingsworth.arsnouveau.client.renderer.item.ScryCasterRenderer;
import com.hollingsworth.arsnouveau.common.block.BasicSpellTurret;
import com.hollingsworth.arsnouveau.common.block.ScryerCrystal;
import com.hollingsworth.arsnouveau.common.spell.method.MethodTouch;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSourceImpl;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class ScryCaster extends ModItem implements ICasterTool, IAnimatable {
   AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public ScryCaster(Properties properties) {
      super(properties);
   }

   public ScryCaster() {
   }

   public InteractionResult m_6225_(UseOnContext pContext) {
      BlockPos pos = pContext.m_8083_();
      ItemStack stack = pContext.m_43722_();
      ScryCaster.Data data = new ScryCaster.Data(stack);
      if (pContext.m_43725_().m_8055_(pos).m_60734_() instanceof ScryerCrystal) {
         if (!pContext.m_43725_().f_46443_) {
            data.setScryPos(pos);
            PortUtil.sendMessage(pContext.m_43723_(), Component.m_237115_("ars_nouveau.dominion_wand.position_set"));
         }

         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
      ItemStack stack = pPlayer.m_21120_(pUsedHand);
      ISpellCaster caster = this.getSpellCaster(stack);
      return caster.castSpell(pLevel, pPlayer, pUsedHand, Component.m_237115_("ars_nouveau.invalid_spell"));
   }

   @Override
   public ISpellCaster getSpellCaster(CompoundTag tag) {
      return new ScryCaster.ScryCasterType(tag);
   }

   @NotNull
   @Override
   public ISpellCaster getSpellCaster(ItemStack stack) {
      return new ScryCaster.ScryCasterType(stack);
   }

   @Override
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
      this.getInformation(stack, worldIn, tooltip2, flagIn);
      ScryCaster.Data data = new ScryCaster.Data(stack);
      if (data.scryPos == null) {
         tooltip2.add(Component.m_237115_("ars_nouveau.scry_caster.no_pos"));
      } else {
         tooltip2.add(
            Component.m_237110_(
               "ars_nouveau.scryer_scroll.bound",
               new Object[]{data.getScryPos().m_123341_() + ", " + data.getScryPos().m_123342_() + ", " + data.getScryPos().m_123343_()}
            )
         );
      }

      super.m_7373_(stack, worldIn, tooltip2, flagIn);
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      super.initializeClient(consumer);
      consumer.accept(new IClientItemExtensions() {
         private final BlockEntityWithoutLevelRenderer renderer = new ScryCasterRenderer();

         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return this.renderer;
         }
      });
   }

   @Override
   public void registerControllers(AnimationData data) {
   }

   @Override
   public AnimationFactory getFactory() {
      return this.factory;
   }

   public static class Data extends ItemstackData {
      private BlockPos scryPos;

      public Data(ItemStack stack) {
         super(stack);
         CompoundTag tag1 = this.getItemTag(stack);
         if (tag1 != null && !tag1.m_128456_()) {
            if (tag1.m_128441_("scryPos")) {
               this.scryPos = BlockPos.m_122022_(tag1.m_128454_("scryPos"));
            }
         }
      }

      public void setScryPos(BlockPos pos) {
         this.scryPos = pos;
         this.writeItem();
      }

      @Nullable
      public BlockPos getScryPos() {
         return this.scryPos;
      }

      @Override
      public String getTagString() {
         return "an_scry_data";
      }

      @Override
      public void writeToNBT(CompoundTag tag) {
         if (this.scryPos != null) {
            tag.m_128356_("scryPos", this.scryPos.m_121878_());
         }
      }
   }

   public static class ScryCasterType extends SpellCaster {
      public ScryCasterType(ItemStack stack) {
         super(stack);
      }

      public ScryCasterType(CompoundTag itemTag) {
         super(itemTag);
      }

      @Override
      public InteractionResultHolder<ItemStack> castSpell(
         Level worldIn, LivingEntity entity, InteractionHand handIn, @org.jetbrains.annotations.Nullable Component invalidMessage, @NotNull Spell spell
      ) {
         ItemStack stack = entity.m_21120_(handIn);
         if (worldIn.f_46443_) {
            return InteractionResultHolder.m_19098_(entity.m_21120_(handIn));
         } else {
            spell = this.modifySpellBeforeCasting(worldIn, entity, handIn, spell);
            if (!spell.isValid() && invalidMessage != null) {
               PortUtil.sendMessageNoSpam(entity, invalidMessage);
               return new InteractionResultHolder(InteractionResult.SUCCESS, stack);
            } else {
               Player player = (Player)(entity instanceof Player thisPlayer ? thisPlayer : ANFakePlayer.getPlayer((ServerLevel)worldIn));
               IWrappedCaster wrappedCaster = (IWrappedCaster)(entity instanceof Player pCaster ? new PlayerCaster(pCaster) : new LivingCaster(entity));
               SpellResolver resolver = this.getSpellResolver(
                  new SpellContext(worldIn, spell, entity, wrappedCaster, stack), worldIn, (LivingEntity)player, handIn
               );
               ITurretBehavior behavior = BasicSpellTurret.TURRET_BEHAVIOR_MAP.get(spell.getCastMethod());
               if (behavior == null) {
                  PortUtil.sendMessage(entity, Component.m_237115_("ars_nouveau.scry_caster.invalid_behavior"));
                  return new InteractionResultHolder(InteractionResult.CONSUME, stack);
               } else {
                  ScryCaster.Data data = new ScryCaster.Data(stack);
                  boolean playerHoldingScroll = entity.m_21120_(InteractionHand.OFF_HAND).m_41720_() instanceof ScryerScroll;
                  BlockPos scryPos = playerHoldingScroll
                     ? (new ScryerScroll.ScryerScrollData(player.m_21120_(InteractionHand.OFF_HAND))).pos
                     : data.getScryPos();
                  if (scryPos == null) {
                     PortUtil.sendMessage(entity, Component.m_237115_("ars_nouveau.scry_caster.no_pos"));
                     return new InteractionResultHolder(InteractionResult.CONSUME, stack);
                  } else if (!worldIn.m_46749_(scryPos)) {
                     PortUtil.sendMessage(entity, Component.m_237115_("ars_nouveau.camera.not_loaded"));
                     return new InteractionResultHolder(InteractionResult.CONSUME, stack);
                  } else {
                     BlockState castingAtState = worldIn.m_8055_(scryPos);
                     if (!(castingAtState.m_60734_() instanceof ScryerCrystal)) {
                        PortUtil.sendMessage(entity, Component.m_237115_("ars_nouveau.scry_caster.not_crystal"));
                        return new InteractionResultHolder(InteractionResult.CONSUME, stack);
                     } else if (!resolver.canCast(player)) {
                        return new InteractionResultHolder(InteractionResult.CONSUME, stack);
                     } else {
                        BlockSourceImpl blockSource = new BlockSourceImpl((ServerLevel)worldIn, scryPos);
                        Direction direction = (Direction)castingAtState.m_61143_(ScryerCrystal.FACING);
                        Position position;
                        if (spell.getCastMethod() instanceof MethodTouch) {
                           position = BasicSpellTurret.getDispensePosition(blockSource);
                        } else {
                           position = ScryerCrystal.getDispensePosition(new BlockSourceImpl((ServerLevel)worldIn, scryPos), direction);
                        }

                        behavior.onCast(resolver, (ServerLevel)worldIn, scryPos, player, position, direction);
                        resolver.expendMana();
                        this.playSound(entity.m_20097_(), worldIn, entity, this.getCurrentSound(), SoundSource.PLAYERS);
                        return new InteractionResultHolder(InteractionResult.CONSUME, stack);
                     }
                  }
               }
            }
         }
      }
   }
}
