package shadows.apotheosis.core.attributeslib.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Comparator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.core.Registry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import shadows.apotheosis.util.Comparators;

public abstract class ModifierSource<T> implements Comparable<ModifierSource<T>> {
   protected final ModifierSourceType<T> type;
   protected final Comparator<T> comparator;
   protected final T data;

   public ModifierSource(ModifierSourceType<T> type, Comparator<T> comparator, T data) {
      this.type = type;
      this.comparator = comparator;
      this.data = data;
   }

   public abstract void render(Font var1, int var2, int var3, PoseStack var4, ItemRenderer var5, int var6);

   public ModifierSourceType<T> getType() {
      return this.type;
   }

   public final T getData() {
      return this.data;
   }

   public int compareTo(ModifierSource<T> o) {
      return this.comparator.compare(this.getData(), o.getData());
   }

   public static class EffectModifierSource extends ModifierSource<MobEffectInstance> {
      public EffectModifierSource(MobEffectInstance data) {
         super(ModifierSourceType.MOB_EFFECT, Comparator.comparing(MobEffectInstance::m_19544_, Comparators.idComparator(Registry.f_122823_)), data);
      }

      @Override
      public void render(Font font, int x, int y, PoseStack stack, ItemRenderer itemRenderer, int pBlitOffset) {
         MobEffectTextureManager texMgr = Minecraft.m_91087_().m_91306_();
         MobEffect effect = this.data.m_19544_();
         TextureAtlasSprite sprite = texMgr.m_118732_(effect);
         RenderSystem.m_157456_(0, sprite.m_118414_().m_118330_());
         float scale = 0.5F;
         stack.m_85836_();
         stack.m_85841_(scale, scale, 1.0F);
         stack.m_85837_((double)((float)x / scale), (double)((float)y / scale), (double)pBlitOffset);
         GuiComponent.m_93200_(stack, 0, 0, 0, 18, 18, sprite);
         stack.m_85849_();
      }
   }

   public static class ItemModifierSource extends ModifierSource<ItemStack> {
      public ItemModifierSource(ItemStack data) {
         super(
            ModifierSourceType.EQUIPMENT,
            Comparator.comparing(LivingEntity::m_147233_)
               .reversed()
               .thenComparing(Comparator.comparing(ItemStack::m_41720_, Comparators.idComparator(Registry.f_122827_))),
            data
         );
      }

      @Override
      public void render(Font font, int x, int y, PoseStack stack, ItemRenderer itemRenderer, int pBlitOffset) {
         PoseStack mvStack = RenderSystem.m_157191_();
         mvStack.m_85836_();
         float scale = 0.5F;
         mvStack.m_85841_(scale, scale, 1.0F);
         mvStack.m_85837_((double)(1.0F + (float)x / scale), (double)(1.0F + (float)y / scale), 0.0);
         itemRenderer.m_115218_(this.data, 0, 0);
         mvStack.m_85849_();
         RenderSystem.m_157182_();
      }
   }
}
