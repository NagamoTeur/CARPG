package shadows.apotheosis.garden;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import shadows.apotheosis.Apotheosis;

public class EnderLeadItem extends Item {
   public EnderLeadItem() {
      super(new Properties().m_41487_(1).m_41503_(15).m_41491_(Apotheosis.APOTH_GROUP));
   }

   public boolean m_8120_(ItemStack stack) {
      return false;
   }

   public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
      if (stack.m_41698_("entity_data").m_128456_() && entity instanceof Animal) {
         CompoundTag tag = entity.serializeNBT();
         if (!player.f_19853_.f_46443_) {
            entity.m_146870_();
            stack.m_41783_().m_128365_("entity_data", tag);
            stack.m_41783_().m_128359_("name", entity.m_5446_().getString());
            this.playSound(player);
         }

         return true;
      } else {
         return super.onLeftClickEntity(stack, player, entity);
      }
   }

   public InteractionResult m_6225_(UseOnContext ctx) {
      CompoundTag tag = ctx.m_43722_().m_41698_("entity_data");
      if (!tag.m_128456_()) {
         BlockPos pos = ctx.m_8083_().m_121945_(ctx.m_43719_());
         if (!ctx.m_43725_().f_46443_) {
            Entity e = EntityType.m_20645_(tag, ctx.m_43725_(), a -> a);
            if (e != null) {
               e.m_6034_((double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 0.5, (double)pos.m_123343_() + 0.5);
               ctx.m_43725_().m_7967_(e);
               ctx.m_43722_().m_41783_().m_128473_("entity_data");
               this.playSound(ctx.m_43723_());
               ctx.m_43722_().m_41622_(1, ctx.m_43723_(), pl -> pl.m_21190_(ctx.m_43724_()));
               return InteractionResult.SUCCESS;
            }
         }
      }

      return InteractionResult.FAIL;
   }

   public CompoundTag getShareTag(ItemStack stack) {
      CompoundTag tag = super.getShareTag(stack);
      if (tag == null) {
         return null;
      } else {
         tag = tag.m_6426_();
         CompoundTag entity = new CompoundTag();
         if (tag.m_128469_("entity_data").m_128441_("id")) {
            entity.m_128359_("id", tag.m_128469_("entity_data").m_128461_("id"));
         }

         tag.m_128365_("entity_data", entity);
         return tag;
      }
   }

   void playSound(Player player) {
      player.f_19853_.m_5594_(null, player.m_20183_(), SoundEvents.f_11852_, SoundSource.AMBIENT, 1.0F, 1.0F);
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      if (stack.m_41782_()) {
         CompoundTag tag = stack.m_41698_("entity_data");
         if (tag.m_128456_()) {
            tooltip.add(Component.m_237115_("info.apotheosis.noentity").m_130940_(ChatFormatting.GRAY));
         } else {
            tooltip.add(Component.m_237110_("info.apotheosis.containedentity", new Object[]{stack.m_41783_().m_128461_("name")}).m_130940_(ChatFormatting.GRAY));
         }
      }
   }

   public boolean m_5812_(ItemStack stack) {
      return stack.m_41782_() && !stack.m_41698_("entity_data").m_128456_();
   }
}
