package immersive_armors;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import immersive_armors.client.render.entity.model.CapeModel;
import immersive_armors.client.render.entity.model.GearModel;
import immersive_armors.client.render.entity.model.HorizontalHeadModel;
import immersive_armors.client.render.entity.model.PrismarineModel;
import immersive_armors.client.render.entity.model.RightVerticalShoulderModel;
import immersive_armors.client.render.entity.model.ShoulderModel;
import immersive_armors.client.render.entity.model.VerticalHeadModel;
import immersive_armors.client.render.entity.piece.CapePiece;
import immersive_armors.client.render.entity.piece.GearPiece;
import immersive_armors.client.render.entity.piece.ItemPiece;
import immersive_armors.client.render.entity.piece.LowerBodyLayerPiece;
import immersive_armors.client.render.entity.piece.LowerLeggingsLayerPiece;
import immersive_armors.client.render.entity.piece.MiddleBodyLayerPiece;
import immersive_armors.client.render.entity.piece.MiddleLeggingsLayerPiece;
import immersive_armors.client.render.entity.piece.ModelPiece;
import immersive_armors.client.render.entity.piece.UpperBodyLayerPiece;
import immersive_armors.client.render.entity.piece.UpperLeggingsLayerPiece;
import net.minecraft.world.item.ItemStack;

public class ItemsClient {
   public static void setupPieces() {
      Items.BONE_ARMOR.lower(new MiddleLeggingsLayerPiece()).upper(new MiddleBodyLayerPiece());
      Items.WITHER_ARMOR.lower(new MiddleLeggingsLayerPiece()).upper(new MiddleBodyLayerPiece()).chest(new CapePiece(new CapeModel()));
      Items.WARRIOR_ARMOR
         .hidesSecondLayer(true, true, true, true)
         .lower(new LowerLeggingsLayerPiece())
         .upper(new LowerBodyLayerPiece())
         .lower(new MiddleLeggingsLayerPiece())
         .upper(new MiddleBodyLayerPiece())
         .lower(new UpperLeggingsLayerPiece())
         .upper(new UpperBodyLayerPiece())
         .head(new ModelPiece(new HorizontalHeadModel()).texture("horizontal"))
         .chest(new CapePiece(new CapeModel()));
      Items.HEAVY_ARMOR
         .hidesSecondLayer(true, true, true, true)
         .lower(new LowerLeggingsLayerPiece())
         .upper(new LowerBodyLayerPiece())
         .upper(new MiddleBodyLayerPiece())
         .lower(new UpperLeggingsLayerPiece())
         .upper(new UpperBodyLayerPiece())
         .head(new ModelPiece(new VerticalHeadModel()).texture("vertical"));
      Items.ROBE_ARMOR
         .hidesSecondLayer(true, true, true, true)
         .lower(new LowerLeggingsLayerPiece().colored())
         .upper(new LowerBodyLayerPiece().colored())
         .lower(new MiddleLeggingsLayerPiece().colored())
         .upper(new MiddleBodyLayerPiece().colored());
      Items.SLIME_ARMOR
         .lower(new LowerLeggingsLayerPiece().texture("leggings").translucent())
         .upper(new LowerBodyLayerPiece().texture("body").translucent())
         .lower(new MiddleLeggingsLayerPiece().texture("leggings").translucent())
         .upper(new MiddleBodyLayerPiece().texture("body").translucent())
         .lower(new UpperLeggingsLayerPiece().texture("leggings").translucent())
         .upper(new UpperBodyLayerPiece().texture("body").translucent());
      Items.DIVINE_ARMOR
         .hidesSecondLayer(true, true, true, true)
         .lower(new LowerLeggingsLayerPiece().colored())
         .upper(new LowerBodyLayerPiece().colored())
         .upper(new MiddleBodyLayerPiece().glint())
         .upper(new UpperBodyLayerPiece().colored())
         .chest(new CapePiece(new CapeModel()).colored());
      Items.PRISMARINE_ARMOR
         .lower(new MiddleLeggingsLayerPiece())
         .upper(new MiddleBodyLayerPiece())
         .upper(new UpperBodyLayerPiece())
         .full(new ModelPiece(new PrismarineModel()).texture("prismarine"));
      Items.WOODEN_ARMOR
         .lower(new MiddleLeggingsLayerPiece())
         .upper(new MiddleBodyLayerPiece())
         .lower(new UpperLeggingsLayerPiece())
         .upper(new UpperBodyLayerPiece())
         .chest(new ModelPiece(new ShoulderModel()).texture("shoulder"));
      Quaternion flip = new Quaternion(new Vector3f(1.0F, 0.0F, 0.0F), -90.0F, true);
      Quaternion rotate = new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), 180.0F, true);
      rotate.m_80148_(flip);
      Items.STEAMPUNK_ARMOR
         .hidesSecondLayer(false, true, true, true)
         .lower(new LowerLeggingsLayerPiece())
         .upper(new LowerBodyLayerPiece())
         .lower(new MiddleLeggingsLayerPiece())
         .upper(new MiddleBodyLayerPiece().translucent())
         .upper(new UpperBodyLayerPiece())
         .chest(new ItemPiece("leftArm", 0.05F, -0.2F, 0.1F, 1.0F, new ItemStack(net.minecraft.world.item.Items.f_42522_), rotate))
         .chest(new ItemPiece("body", 0.15F, 0.4F, -0.175F, 0.6F, new ItemStack(net.minecraft.world.item.Items.f_42524_)))
         .chest(new GearPiece<>(new GearModel("body", 8), "gear", 0.05F, 0.3F, 0.18F, 0.2F))
         .chest(new GearPiece<>(new GearModel("body", 5), "gear_small", -0.15F, 0.6F, 0.19F, -0.3F))
         .chest(new GearPiece<>(new GearModel("body", 5), "gear_small", -0.1F, 0.45F, -0.17F, -0.3F))
         .head(new GearPiece<>(new GearModel("head", 5), "gear_small", -0.3F, -0.3F, 0.0F, -0.2F, new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), 90.0F, true)))
         .chest(
            new GearPiece<>(new GearModel("leftArm", 5), "gear_small", 0.23F, 0.4F, 0.0F, -0.3F, new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), 90.0F, true))
         )
         .chest(new ModelPiece(new RightVerticalShoulderModel()).texture("shoulder"))
         .chest(new CapePiece(new CapeModel()));
   }
}
