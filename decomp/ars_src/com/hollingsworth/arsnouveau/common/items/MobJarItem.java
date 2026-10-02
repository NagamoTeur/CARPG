package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.client.renderer.item.MobJarItemRenderer;
import com.hollingsworth.arsnouveau.common.block.tile.MobJarTile;
import com.hollingsworth.arsnouveau.common.lib.EntityTags;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class MobJarItem extends BlockItem implements IAnimatable {
   AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public MobJarItem(Block pBlock, Properties pProperties) {
      super(pBlock, pProperties);
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      super.initializeClient(consumer);
      consumer.accept(new IClientItemExtensions() {
         MobJarItemRenderer renderer = new MobJarItemRenderer();

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

   public void m_7373_(ItemStack stack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
      super.m_7373_(stack, pLevel, pTooltip, pFlag);
      if (pLevel != null) {
         Entity entity = fromItem(stack, pLevel);
         if (entity != null) {
            pTooltip.add(entity.m_5446_());
            if (entity.m_8077_()) {
               MutableComponent name = entity.m_6095_().m_20676_().m_6881_();
               name.m_130940_(ChatFormatting.GRAY);
               pTooltip.add(name);
            }

            if (entity.m_6095_().m_204039_(EntityTags.DRYGMY_BLACKLIST)) {
               Component blacklisted = Component.m_237115_("ars_nouveau.drygmy.blacklist").m_130940_(ChatFormatting.DARK_RED);
               pTooltip.add(blacklisted);
            }
         }
      }
   }

   public static Entity fromItem(ItemStack stack, Level level) {
      if (!stack.m_41782_()) {
         return null;
      } else {
         CompoundTag blockTag = stack.m_41783_().m_128469_("BlockEntityTag");
         CompoundTag entityTag = blockTag.m_128469_("entityTag");
         return entityTag.m_128456_() ? null : MobJarTile.loadEntityFromTag(level, entityTag);
      }
   }
}
