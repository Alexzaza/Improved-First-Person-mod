package kes5219.improvedfirstperson.classtransformer;

import java.util.Map;


import net.minecraft.world.World;

import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;

@TransformerExclusions({"kes5219.improvedfirstperson.classtransformer"})
@MCVersion("1.5.2")
public class ImprovedFirstPersonLoadingPlugin implements IFMLLoadingPlugin {

	 
	public String[] getLibraryRequestClass() {
		return null;
	}

	 
	public String[] getASMTransformerClass() {
		boolean isObfuscated = !World.class.getSimpleName().equals("World");
		
		ObfuscationTable.ClassItemRenderer = isObfuscated ? "bfx" : "net.minecraft.client.renderer.ItemRenderer";
		ObfuscationTable.MethodRenderItemInFirstPerson = isObfuscated ? "a" : "renderItemInFirstPerson";
		ObfuscationTable.ClassEntityRenderer = isObfuscated ? "bfq" : "net.minecraft.client.renderer.EntityRenderer";
		ObfuscationTable.FieldRendererUpdateCount = isObfuscated ? "t" : "rendererUpdateCount";
		ObfuscationTable.MethodRenderWorld = isObfuscated ? "a" : "renderWorld";
		ObfuscationTable.MethodRenderWorldDesc = isObfuscated ? "(FJ)V" : "(FJ)V";
		ObfuscationTable.MethodGetMouseOver = isObfuscated ? "a" : "getMouseOver";
		ObfuscationTable.MethodGetMouseOverDesc = isObfuscated ? "(F)V" : "(F)V";
		ObfuscationTable.ClassRenderFish = isObfuscated ? "bhd" : "net.minecraft.client.renderer.entity.RenderFish";
		ObfuscationTable.MethodDoRenderFishHook = isObfuscated ? "a" : "doRenderFishHook";
		ObfuscationTable.MethodDoRenderFishHookDesc = isObfuscated ? "(Lsw;DDDFF)V" : "(Lnet/minecraft/entity/projectile/EntityFishHook;DDDFF)V";
		ObfuscationTable.ClassRenderGlobal = isObfuscated ? "bfy" : "net.minecraft.client.renderer.RenderGlobal";
		ObfuscationTable.MethodRenderEntities = isObfuscated ? "a" : "renderEntities";		
		ObfuscationTable.MethodRenderEntitiesDesc = isObfuscated ? "(Larc;Lbgh;F" : "(Lnet/minecraft/util/Vec3;Lnet/minecraft/client/renderer/culling/ICamera;F)V";
		
		return new String[] {"kes5219.improvedfirstperson.classtransformer.ClassTransformer"};
	}

	 
	public String getModContainerClass() {
		return null;
	}

	 
	public String getSetupClass() {
		return null;
	}

	 
	public void injectData(Map<String, Object> data) {
		
	}
}