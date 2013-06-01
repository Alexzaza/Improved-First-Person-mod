package kes5219.improvedfirstperson.client.renderplayerAPIbase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModelPlayer;
import net.minecraft.src.ModelPlayerAPI;
import net.minecraft.src.ModelPlayerBase;
import net.minecraft.util.MathHelper;

public class IFPModelPlayerBase  extends ModelPlayerBase {
	public IFPModelPlayerBase(ModelPlayerAPI modelPlayerAPI) {
		super(modelPlayerAPI);
	}

	@Override
	public void beforeSetRotationAngles(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7)
	{
		//modelPlayer.bipedRightLeg.rotationPointZ = 0.0f;
		//modelPlayer.bipedRightLeg.rotationPointX = -2.0f;
		//modelPlayer.bipedLeftLeg.rotationPointZ = 0.0f;
		//modelPlayer.bipedLeftLeg.rotationPointX = 2.0f;
		ItemStack item = ((EntityLiving)var7).getHeldItem();

		if(item != null && Item.itemsList[item.itemID] instanceof ItemBow) {
			modelPlayer.heldItemLeft = 1;
			modelPlayer.heldItemRight = 0;
		}
	}

	@Override
	public void afterSetRotationAngles(float var1, float var2, float var3, float var4, float var5, float partialTick, Entity entity) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = (EntityPlayer)entity;

		if (RenderManager.instance.playerViewY == 180.0f) {
			//if the inventory screen is open
			modelPlayer.bipedHead.isHidden = false;
			modelPlayer.bipedHeadwear.isHidden = false;
			return;
		}

		if ((mc.gameSettings.thirdPersonView == 0 && entity == mc.renderViewEntity) || mc.renderViewEntity.isPlayerSleeping()) {
			modelPlayer.bipedHead.isHidden = true;
			modelPlayer.bipedHeadwear.isHidden = true;
		} else {
			modelPlayer.bipedHead.isHidden = false;
			modelPlayer.bipedHeadwear.isHidden = false;
		}

		ItemStack item = player.getHeldItem();

		if (item != null && (Item.map.itemID == item.itemID || Item.emptyMap.itemID == item.itemID)) {
			modelPlayer.bipedRightArm.rotateAngleX = -(float)Math.PI/9;
			modelPlayer.bipedLeftArm.rotateAngleX = -(float)Math.PI/9;
			modelPlayer.bipedRightArm.rotateAngleZ = 0;
			modelPlayer.bipedLeftArm.rotateAngleZ = 0;

			modelPlayer.bipedRightArm.rotateAngleZ += MathHelper.cos(var3 * 0.09F) * 0.05F + 0.05F;
			modelPlayer.bipedLeftArm.rotateAngleZ -= MathHelper.cos(var3 * 0.09F) * 0.05F + 0.05F;
			modelPlayer.bipedRightArm.rotateAngleX += MathHelper.sin(var3 * 0.067F) * 0.05F;
			modelPlayer.bipedLeftArm.rotateAngleX -= MathHelper.sin(var3 * 0.067F) * 0.05F;
		}

		if (modelPlayer.aimedBow) {
			player.renderYawOffset = player.rotationYawHead + 40;
			modelPlayer.bipedLeftArm.rotateAngleY += 0.15F;

			var3 = var3 * 6.0f;
			modelPlayer.bipedRightArm.rotateAngleZ += 0.15F * (MathHelper.cos(var3 * 0.09F) * 0.05F + 0.05F);
			modelPlayer.bipedLeftArm.rotateAngleZ -= 0.15F * (MathHelper.cos(var3 * 0.09F) * 0.05F + 0.05F);
			modelPlayer.bipedRightArm.rotateAngleX += 0.15F * (MathHelper.sin(var3 * 0.067F) * 0.05F);
			modelPlayer.bipedLeftArm.rotateAngleX -= 0.15F * (MathHelper.sin(var3 * 0.067F) * 0.05F);

			float headAngle = modelPlayer.bipedHead.rotateAngleX * 15;
			float rot = headAngle / 45;
			boolean negative = rot < 0;

			rot *= rot * 2.5F;

			if (!negative)
			{
				modelPlayer.bipedLeftArm.rotateAngleY += rot;

				rot -= 0.5F;
				rot *= 0.75F;

				if (rot > 0)
					modelPlayer.bipedLeftArm.rotateAngleX -= rot;
			}
			else
			{
				modelPlayer.bipedLeftArm.rotateAngleZ -= rot;
				modelPlayer.bipedLeftArm.rotateAngleX += rot;
				modelPlayer.bipedRightArm.rotateAngleX += rot * 2;
			}
		}

		if (item == null || Item.itemsList[item.itemID] instanceof ItemBow) {
			modelPlayer.heldItemLeft = 0;
		}

		// Make player lean over when looking down
		if (entity.rotationPitch > 0)
		{
			float off;
			float fovMult = ((mc.thePlayer != entity || mc.gameSettings.thirdPersonView > 0) ? 0.75F : (mc.gameSettings.fovSetting + 1));

			if (fovMult > 1.75F)
				fovMult = 1.75F;

			if (!player.isUsingItem())
			{
				off = Math.abs(entity.rotationPitch / 250F * fovMult);
				modelPlayer.bipedLeftArm.rotateAngleZ -= off;
				modelPlayer.bipedRightArm.rotateAngleZ += off;
			}

			if (!modelPlayer.isSneak)
			{
				off = entity.rotationPitch / 180F * fovMult;
				modelPlayer.bipedBody.rotateAngleX += off;

				off = entity.rotationPitch / 16F * fovMult;
				modelPlayer.bipedRightLeg.rotationPointZ += off;
				modelPlayer.bipedLeftLeg.rotationPointZ += off;

				off = Math.abs(entity.rotationPitch / 50F * fovMult);
				modelPlayer.bipedRightLeg.rotationPointY -= off;
				modelPlayer.bipedLeftLeg.rotationPointY -= off;
			}
		}

		//modelPlayer.bipedLeftArm.rotateAngleZ = 0;
		//modelPlayer.bipedLeftArm.rotateAngleX = 0;
		//modelPlayer.bipedLeftArm.rotateAngleY = var1 * 0.001f;
	}

	ModelPlayer getModelPlayer()
	{
		return modelPlayer;
	}

}
