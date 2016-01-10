package com.dyn.achievements.handlers;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import com.dyn.achievements.achievement.AchievementHandler;
import com.dyn.achievements.achievement.AchievementPlus;
import com.dyn.achievements.achievement.AchievementPlus.AchievementType;
import com.dyn.achievements.achievement.Requirements.BaseRequirement;

public class EventHandler {

	@SubscribeEvent
	public void craftingEvent(PlayerEvent.ItemCraftedEvent event) {
		if (event.crafting != null) {
			for (AchievementPlus a : AchievementHandler.itemIds.get(AchievementType.CRAFT)
					.get(Item.getIdFromItem(event.crafting.getItem()))) {
				if (!a.isAwarded()) {
					for (BaseRequirement r : a.getRequirements().getRequirementsByType(AchievementType.CRAFT)) {
						if (r.getRequirementItemID() == Item.getIdFromItem(event.crafting.getItem())) {
							if (r.getTotalAquired() < r.getTotalNeeded()) {
								r.incrementTotal();
							}
						}
					}
					a.meetsRequirements(event.player);
				}
			}
		}
	}

	@SubscribeEvent
	public void smeltingEvent(PlayerEvent.ItemSmeltedEvent event) {
		if (event.smelting != null) {
			for (AchievementPlus a : AchievementHandler.itemIds.get(AchievementType.SMELT)
					.get(Item.getIdFromItem(event.smelting.getItem()))) {
				if (!a.isAwarded()) {
					for (BaseRequirement r : a.getRequirements().getRequirementsByType(AchievementType.SMELT)) {
						if (r.getRequirementItemID() == Item.getIdFromItem(event.smelting.getItem())) {
							if (r.getTotalAquired() < r.getTotalNeeded()) {
								r.incrementTotal();
							}
						}
					}
					a.meetsRequirements(event.player);
				}
			}
		}
	}

	@SubscribeEvent
	public void pickupEvent(PlayerEvent.ItemPickupEvent event) {
		if (event.pickedUp != null) {
			for (AchievementPlus a : AchievementHandler.itemIds.get(AchievementType.PICKUP)
					.get(Item.getIdFromItem(event.pickedUp.getEntityItem().getItem()))) {
				if (!a.isAwarded()) {
					for (BaseRequirement r : a.getRequirements().getRequirementsByType(AchievementType.PICKUP)) {
						if (r.getRequirementItemID() == Item.getIdFromItem(event.pickedUp.getEntityItem().getItem())) {
							if (r.getTotalAquired() < r.getTotalNeeded()) {
								r.incrementTotal();
							}
						}
					}
					a.meetsRequirements(event.player);
				}
			}
		}
	}

	@SubscribeEvent
	public void killEvent(LivingDeathEvent event) {
		if (event.source != null && event.source.getEntity() != null) {
			if (event.source.getEntity() instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.source.getEntity();
				for (AchievementPlus a : AchievementHandler.entityNames.get(AchievementType.KILL)
						.get(EntityList.getEntityString(event.entity))) {
					if (!a.isAwarded()) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(AchievementType.KILL)) {
							if (r.getRequirementItemEntity() == EntityList.getEntityString(event.entity)) {
								if (r.getTotalAquired() < r.getTotalNeeded()) {
									r.incrementTotal();
								}
							}
						}
						a.meetsRequirements(player);
					}
				}
			}
		}
	}

	/*
	 * @SubscribeEvent public void spawnEvent(LivingEvent.LivingSpawnEvent
	 * event) { EntityClientPlayerMP player =
	 * Minecraft.getMinecraft().thePlayer; if(player!=null)
	 * player.addChatComponentMessage( new ChatComponentTranslation("Spawned " +
	 * EntityList.getStringFromID(event.entityLiving.getEntityId())
	 * ).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.AQUA))); if
	 * (event.entityLiving instanceof EntityPlayerMP) {
	 * 
	 * } }
	 */

	// this is really dangerous as it happens every game tick,
	// we either should find an alternative, thread this, or keep code as
	// minimal as possible
	/*
	 * @SubscribeEvent public void onPlayerTick(TickEvent.PlayerTickEvent event)
	 * { World playerWorld = event.player.worldObj; if (playerWorld.isRaining())
	 * { event.player.addChatComponentMessage(new ChatComponentTranslation(
	 * "Its Raining") .setChatStyle(new
	 * ChatStyle().setColor(EnumChatFormatting.AQUA))); //
	 * event.player.addStat(InitExample.itsRaining.getAchievement(), 1); } }
	 */

}