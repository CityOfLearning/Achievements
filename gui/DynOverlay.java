package com.dyn.achievements.gui;

import com.dyn.server.ServerMod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;

public class DynOverlay extends Gui {

	private static final ResourceLocation TABS = new ResourceLocation("dyn", "textures/gui/sm_notification.png");

	private final Minecraft mc;
	private int windowWidth;
	private int windowHeight;

	public DynOverlay() {
		mc = Minecraft.getMinecraft();
	}

	public void drawOverlay() {

		updateWindowScale();
		GlStateManager.disableDepth();
		GlStateManager.depthMask(false);
		int xPos = windowWidth - 120;
		int yPos = windowHeight - 32;
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.enableTexture2D();
		GlStateManager.disableLighting();

		if (ServerMod.opped) {
			mc.getTextureManager().bindTexture(TABS);
			this.drawTexturedModalRect(xPos, yPos, 0, 202, 40, 32);
			mc.fontRendererObj.drawString("Achiev", xPos + 5, yPos + 4, -256);
			mc.fontRendererObj.drawString("ements", xPos + 4, yPos + 12, -256);
			mc.fontRendererObj.drawString("(N)", xPos + 13, yPos + 20, -1);

			mc.getTextureManager().bindTexture(TABS);
			this.drawTexturedModalRect(xPos + 40, yPos, 0, 202, 40, 32);
			mc.fontRendererObj.drawString("Mentor", xPos + 44, yPos + 4, -256);
			mc.fontRendererObj.drawString("GUI", xPos + 52, yPos + 12, -256);
			mc.fontRendererObj.drawString("(M)", xPos + 53, yPos + 20, -1);

			mc.getTextureManager().bindTexture(TABS);
			this.drawTexturedModalRect(xPos + 80, yPos, 0, 202, 40, 32);
			mc.fontRendererObj.drawString("Hide", xPos + 90, yPos + 4, -256);
			mc.fontRendererObj.drawString("GUI", xPos + 92, yPos + 12, -256);
			mc.fontRendererObj.drawString("(H)", xPos + 93, yPos + 20, -1);
		} else {
			mc.getTextureManager().bindTexture(TABS);
			this.drawTexturedModalRect(xPos + 40, yPos, 0, 202, 40, 32);
			mc.fontRendererObj.drawString("Achiev", xPos + 45, yPos + 4, -256);
			mc.fontRendererObj.drawString("ements", xPos + 44, yPos + 12, -256);
			mc.fontRendererObj.drawString("(N)", xPos + 53, yPos + 20, -1);

			mc.getTextureManager().bindTexture(TABS);
			this.drawTexturedModalRect(xPos + 80, yPos, 0, 202, 40, 32);
			mc.fontRendererObj.drawString("Hide", xPos + 90, yPos + 4, -256);
			mc.fontRendererObj.drawString("GUI", xPos + 92, yPos + 12, -256);
			mc.fontRendererObj.drawString("(H)", xPos + 93, yPos + 20, -1);
		}

		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.disableLighting();
		GlStateManager.enableRescaleNormal();
		GlStateManager.enableColorMaterial();
		GlStateManager.enableLighting();
		GlStateManager.disableLighting();
		GlStateManager.depthMask(true);
		GlStateManager.enableDepth();
	}

	private void updateWindowScale() {
		GlStateManager.viewport(0, 0, mc.displayWidth, mc.displayHeight);
		GlStateManager.matrixMode(5889);
		GlStateManager.loadIdentity();
		GlStateManager.matrixMode(5888);
		GlStateManager.loadIdentity();
		windowWidth = mc.displayWidth;
		windowHeight = mc.displayHeight;
		ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		windowWidth = scaledresolution.getScaledWidth();
		windowHeight = scaledresolution.getScaledHeight();
		GlStateManager.clear(256);
		GlStateManager.matrixMode(5889);
		GlStateManager.loadIdentity();
		GlStateManager.ortho(0.0D, windowWidth, windowHeight, 0.0D, 1000.0D, 3000.0D);
		GlStateManager.matrixMode(5888);
		GlStateManager.loadIdentity();
		GlStateManager.translate(0.0F, 0.0F, -2000.0F);
	}
}
