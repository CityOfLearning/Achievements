package com.dyn.achievements.handlers;

import net.minecraft.client.Minecraft;
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
			System.out.println(event.crafting.getDisplayName());
			for (AchievementPlus a : AchievementHandler.itemNames.get(AchievementType.CRAFT)
					.get(event.crafting.getDisplayName())) {
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
			System.out.println(event.smelting.getDisplayName());
			for (AchievementPlus a : AchievementHandler.itemNames.get(AchievementType.SMELT)
					.get(event.smelting.getDisplayName())) {
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
			System.out.println(event.pickedUp.getEntityItem().getDisplayName());
			for (AchievementPlus a : AchievementHandler.itemNames.get(AchievementType.PICKUP)
					.get(event.pickedUp.getEntityItem().getDisplayName())) {
				System.out.print(a.getName());
				if (!a.isAwarded()) {
					for (BaseRequirement r : a.getRequirements().getRequirementsByType(AchievementType.PICKUP)) {
						System.out.print(r.getRequirementEntityName());
						if (r.getRequirementEntityName().equals(event.pickedUp.getEntityItem().getDisplayName())) {
							System.out.println(r.getTotalAquired());
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
			//we need to know that this particular player did the killing
			if (event.source.getEntity() instanceof EntityPlayer  && event.source.getEntity().equals(Minecraft.getMinecraft().thePlayer)) {
				EntityPlayer player = (EntityPlayer) event.source.getEntity();
				for (AchievementPlus a : AchievementHandler.entityNames.get(AchievementType.KILL)
						.get(EntityList.getEntityString(event.entity))) {
					if (!a.isAwarded()) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(AchievementType.KILL)) {
							if (r.getRequirementEntityName() == EntityList.getEntityString(event.entity)) {
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

	// how do we know which player brewed the potion
	/*
	 * @SubscribeEvent public void brewEvent(PotionBrewEvent event) { for(int
	 * i=0;i<3;i++){ if (event.getItem(1) != null) { for (AchievementPlus a :
	 * AchievementHandler.itemIds.get(AchievementType.BREW)
	 * .get(Item.getIdFromItem(event.getItem(i).getItem()))) { if
	 * (!a.isAwarded()) { for (BaseRequirement r :
	 * a.getRequirements().getRequirementsByType(AchievementType.BREW)) { if
	 * (r.getRequirementItemID() ==
	 * Item.getIdFromItem(event.getItem(i).getItem())) { if (r.getTotalAquired()
	 * < r.getTotalNeeded()) { r.incrementTotal(); } } }
	 * a.meetsRequirements(player); } } } } }
	 */

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