package daripher.skilltree.item.gem;

import java.util.List;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GemModel implements BakedModel {
   private final BakedModel original;
   private final ItemOverrides itemHandler;

   public GemModel(BakedModel original, ModelBakery loader) {
      this.original = original;
      UnbakedModel missing = loader.m_119341_(ModelBakery.f_119230_);
      this.itemHandler = new ItemOverrides(loader, missing, id -> missing, loader.getAtlasSet()::m_117971_, List.of()) {
         public BakedModel m_173464_(
            @NotNull BakedModel original, @NotNull ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity entity, int seed
         ) {
            return GemModel.this.resolve(stack);
         }
      };
   }

   public BakedModel resolve(ItemStack stack) {
      GemType gemType = GemItem.getGemType(stack);
      ResourceLocation modelId = new ResourceLocation("skilltree", "item/gems/" + gemType.id().m_135815_());
      return Minecraft.m_91087_().m_91304_().getModel(modelId);
   }

   @NotNull
   public ItemOverrides m_7343_() {
      return this.itemHandler;
   }

   @NotNull
   public List<BakedQuad> m_213637_(BlockState pState, Direction pDirection, @NotNull RandomSource random) {
      return this.original.m_213637_(pState, pDirection, random);
   }

   public boolean m_7541_() {
      return this.original.m_7541_();
   }

   public boolean m_7539_() {
      return this.original.m_7539_();
   }

   public boolean m_7547_() {
      return this.original.m_7547_();
   }

   public boolean m_7521_() {
      return this.original.m_7521_();
   }

   @NotNull
   public TextureAtlasSprite m_6160_() {
      return this.original.m_6160_();
   }

   @NotNull
   public ItemTransforms m_7442_() {
      return this.original.m_7442_();
   }
}
