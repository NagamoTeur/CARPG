package shadows.apotheosis.spawn.spawner;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

public class ApothSpawnerItem extends BlockItem {
   public ApothSpawnerItem() {
      super(Blocks.f_50085_, new Properties().m_41491_(CreativeModeTab.f_40753_));
   }

   public String getCreatorModId(ItemStack itemStack) {
      return "apotheosis";
   }

   public Component m_7626_(ItemStack stack) {
      if (stack.m_41782_() && stack.m_41783_().m_128441_("BlockEntityTag")) {
         CompoundTag tag = stack.m_41783_().m_128469_("BlockEntityTag");
         if (tag.m_128441_("SpawnData")) {
            String name = tag.m_128469_("SpawnData").m_128469_("entity").m_128461_("id");
            String key = "entity." + name.replace(':', '.');
            ChatFormatting color = ChatFormatting.WHITE;

            try {
               EntityType<?> t = (EntityType<?>)ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(name));
               MobCategory cat = t.m_20674_();
               switch (cat) {
                  case CREATURE:
                     color = ChatFormatting.DARK_GREEN;
                     break;
                  case MONSTER:
                     color = ChatFormatting.RED;
                     break;
                  case WATER_AMBIENT:
                  case WATER_CREATURE:
                     color = ChatFormatting.BLUE;
               }
            } catch (Exception var8) {
            }

            return Component.m_237110_("item.apotheosis.spawner", new Object[]{Component.m_237115_(key)}).m_130940_(color);
         }
      }

      return super.m_7626_(stack);
   }
}
