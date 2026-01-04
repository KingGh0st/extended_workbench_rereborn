package com.garouelcazador.extended_workbench.client.model.custom;

import net.minecraft.client.model.TridentModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

public class ExtendedTridentModel extends TridentModel {
   public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("extended_workbench", "textures/entity/extended_trident.png");

   public ExtendedTridentModel(ModelPart p_171016_) {
      super(p_171016_);
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 4).addBox(-0.5F, 5.0F, -0.5F, 1.0F, 27.0F, 1.0F, CubeDeformation.NONE).texOffs(12, 6).addBox(-1.0F, 32.0F, -1.0F, 2.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.offset(0.0F, -3.0F, 0.0F));
      partdefinition.addOrReplaceChild("base", CubeListBuilder.create().texOffs(4, 0).addBox(-2.0F, 2.0F, -0.5F, 4.0F, 2.0F, 1.0F, CubeDeformation.NONE).texOffs(14, 0).addBox(-1.0F, 4.0F, -0.5F, 2.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offset(0.0F, -3.0F, 0.0F));
      partdefinition.addOrReplaceChild("left_spike", CubeListBuilder.create().texOffs(8, 3).addBox(-3.0F, -2.0F, -0.5F, 1.0F, 5.0F, 1.0F, CubeDeformation.NONE), PartPose.offset(0.0F, -3.0F, 0.0F));
      partdefinition.addOrReplaceChild("middle_spike", CubeListBuilder.create().texOffs(4, 3).addBox(-1.375F, -3.0F, -0.5F, 1.0F, 5.0F, 1.0F, CubeDeformation.NONE).texOffs(4, 3).addBox(0.375F, -3.0F, -0.5F, 1.0F, 5.0F, 1.0F, CubeDeformation.NONE), PartPose.offset(0.0F, -3.0F, 0.0F));
      partdefinition.addOrReplaceChild("right_spike", CubeListBuilder.create().texOffs(8, 3).mirror().addBox(2.0F, -2.0F, -0.5F, 1.0F, 5.0F, 1.0F, CubeDeformation.NONE).mirror(false), PartPose.offset(0.0F, -3.0F, 0.0F));
      partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
      return LayerDefinition.create(meshdefinition, 32, 32);
   }
}
