package com.dyn.achievements.handlers;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.brewing.PotionBrewEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;

import java.util.Random;

import com.dyn.achievements.achievement.AchievementPlus;
import com.dyn.achievements.achievement.AchievementType;
import com.dyn.achievements.achievement.Requirements.BaseRequirement;
import com.dyn.server.packets.PacketDispatcher;
import com.dyn.server.packets.client.SyncAchievementsMessage;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class EventHandler {

	@SubscribeEvent
	public void craftingEvent(PlayerEvent.ItemCraftedEvent event) {
		if (event.crafting != null) {
			if (AchievementHandler.getItemNames().containsKey(AchievementType.CRAFT)) {
				for (AchievementPlus a : AchievementHandler.getItemNames().get(AchievementType.CRAFT)
						.get(event.crafting.getDisplayName())) {
					if (a.getWorldId() > 0 && event.player.dimension == a.getWorldId()) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(AchievementType.CRAFT)) {
							if (r.getRequirementEntityName().equals(event.crafting.getDisplayName())) {
								PacketDispatcher.sendTo(new SyncAchievementsMessage(
										"" + a.getId() + " " + AchievementType.CRAFT + " " + r.getRequirementID()),
										(EntityPlayerMP) event.player);
							}
						}
					} else if (a.getWorldId() == 0) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(AchievementType.CRAFT)) {
							if (r.getRequirementEntityName().equals(event.crafting.getDisplayName())) {
								PacketDispatcher.sendTo(new SyncAchievementsMessage(
										"" + a.getId() + " " + AchievementType.CRAFT + " " + r.getRequirementID()),
										(EntityPlayerMP) event.player);
							}
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void smeltingEvent(PlayerEvent.ItemSmeltedEvent event) {
		if (event.smelting != null) {
			if (AchievementHandler.getItemNames().containsKey(AchievementType.SMELT)) {
				for (AchievementPlus a : AchievementHandler.getItemNames().get(AchievementType.SMELT)
						.get(event.smelting.getDisplayName())) {
					if (a.getWorldId() > 0 && event.player.dimension == a.getWorldId()) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(AchievementType.SMELT)) {
							if (r.getRequirementItemID() == Item.getIdFromItem(event.smelting.getItem())) {
								PacketDispatcher.sendTo(new SyncAchievementsMessage(
										"" + a.getId() + " " + AchievementType.SMELT + " " + r.getRequirementID()),
										(EntityPlayerMP) event.player);
							}
						}
					} else if (a.getWorldId() == 0) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(AchievementType.SMELT)) {
							if (r.getRequirementItemID() == Item.getIdFromItem(event.smelting.getItem())) {
								PacketDispatcher.sendTo(new SyncAchievementsMessage(
										"" + a.getId() + " " + AchievementType.SMELT + " " + r.getRequirementID()),
										(EntityPlayerMP) event.player);
							}
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void pickupEvent(PlayerEvent.ItemPickupEvent event) {
		if (event.pickedUp != null) {
			if (AchievementHandler.getItemNames().containsKey(AchievementType.PICKUP)) {
				for (AchievementPlus a : AchievementHandler.getItemNames().get(AchievementType.PICKUP)
						.get(event.pickedUp.getEntityItem().getDisplayName())) {
					if (a.getWorldId() > 0 && event.player.dimension == a.getWorldId()) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(AchievementType.PICKUP)) {
							if (r.getRequirementEntityName().equals(event.pickedUp.getEntityItem().getDisplayName())) {
								PacketDispatcher.sendTo(new SyncAchievementsMessage(
										"" + a.getId() + " " + AchievementType.PICKUP + " " + r.getRequirementID()),
										(EntityPlayerMP) event.player);
							}
						}
					} else if (a.getWorldId() == 0) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(AchievementType.PICKUP)) {
							if (r.getRequirementEntityName().equals(event.pickedUp.getEntityItem().getDisplayName())) {
								PacketDispatcher.sendTo(new SyncAchievementsMessage(
										"" + a.getId() + " " + AchievementType.PICKUP + " " + r.getRequirementID()),
										(EntityPlayerMP) event.player);
							}
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void killEvent(LivingDeathEvent event) {
		if (event.source != null && event.source.getEntity() != null && event.entity != null
				&& event.source.getEntity() instanceof EntityPlayer) {
			if (AchievementHandler.getEntityNames().containsKey(AchievementType.KILL)) {
				for (AchievementPlus a : AchievementHandler.getEntityNames().get(AchievementType.KILL)
						.get(EntityList.getEntityString(event.entity))) {
					if (a.getWorldId() > 0 && event.source.getEntity().dimension == a.getWorldId()) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(AchievementType.KILL)) {
							if (r.getRequirementEntityName().equals(EntityList.getEntityString(event.entity))) {
								PacketDispatcher.sendTo(new SyncAchievementsMessage(
										"" + a.getId() + " " + AchievementType.KILL + " " + r.getRequirementID()),
										(EntityPlayerMP) event.source.getEntity());
							}
						}
					} else if (a.getWorldId() == 0) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(AchievementType.KILL)) {
							if (r.getRequirementEntityName().equals(EntityList.getEntityString(event.entity))) {
								PacketDispatcher.sendTo(new SyncAchievementsMessage(
										"" + a.getId() + " " + AchievementType.KILL + " " + r.getRequirementID()),
										(EntityPlayerMP) event.source.getEntity());
							}
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void placeBlockEvent(PlaceEvent event) {
		// we are only concerned with placing blocks
		if (event.placedBlock != null && event.player != null) {
			if (AchievementHandler.getItemNames().containsKey(AchievementType.PLACE)) {
				for (AchievementPlus a : AchievementHandler.getItemNames().get(AchievementType.PLACE)
						.get(event.itemInHand.getDisplayName())) {
					if (a.getWorldId() > 0 && event.player.dimension == a.getWorldId()) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(AchievementType.PLACE)) {
							if (r.getRequirementEntityName().equals(event.itemInHand.getDisplayName())) {
								PacketDispatcher.sendTo(new SyncAchievementsMessage(
										"" + a.getId() + " " + AchievementType.PLACE + " " + r.getRequirementID()),
										(EntityPlayerMP) event.player);
							}
						}
					} else if (a.getWorldId() == 0) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(AchievementType.PLACE)) {
							if (r.getRequirementEntityName().equals(event.itemInHand.getDisplayName())) {
								PacketDispatcher.sendTo(new SyncAchievementsMessage(
										"" + a.getId() + " " + AchievementType.PLACE + " " + r.getRequirementID()),
										(EntityPlayerMP) event.player);
							}
						}
					}
				}

			}

		}
	}

	@SubscribeEvent
	public void breakBlockEvent(BreakEvent event) {
		// we are only concerned with placing blocks
		if (event.block != null && event.getPlayer() != null) {
			ItemStack is = new ItemStack(event.block, 1, event.blockMetadata);
			try {
				is.getDisplayName();
			} catch (NullPointerException e) {
				is = new ItemStack(event.block.getItemDropped(0, new Random(), 0), 1, event.blockMetadata);
				try {
					is.getDisplayName();
				} catch (NullPointerException e2) {
					// if this doesnt work nothing will lets return before we
					// cause problems
					event.getPlayer().addChatMessage(new ChatComponentText(
							"Cannot create item stack from block: " + event.block.getLocalizedName()));
					return;
				}
			}
			if (AchievementHandler.getItemNames().containsKey(AchievementType.BREAK)) {
				for (AchievementPlus a : AchievementHandler.getItemNames().get(AchievementType.BREAK)
						.get(is.getDisplayName())) {
					if (a.getWorldId() > 0 && event.getPlayer().dimension == a.getWorldId()) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(AchievementType.BREAK)) {
							if (r.getRequirementEntityName().equals(is.getDisplayName())) {
								PacketDispatcher.sendTo(new SyncAchievementsMessage(
										"" + a.getId() + " " + AchievementType.BREAK + " " + r.getRequirementID()),
										(EntityPlayerMP) event.getPlayer());
							}
						}
					} else if (a.getWorldId() == 0) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(AchievementType.BREAK)) {
							if (r.getRequirementEntityName().equals(is.getDisplayName())) {
								PacketDispatcher.sendTo(new SyncAchievementsMessage(
										"" + a.getId() + " " + AchievementType.BREAK + " " + r.getRequirementID()),
										(EntityPlayerMP) event.getPlayer());
							}
						}
					}
				}
			}
		}
	}

	/*
	 * // how do we know which player brewed the potion
	 * 
	 * @SubscribeEvent public void brewEvent(PotionBrewEvent event) { for (int i
	 * = 0; i < 3; i++) { if (event.getItem(i) != null) { for (AchievementPlus a
	 * : AchievementHandler.getItemNames().get(AchievementType.BREW)
	 * .get(event.getItem(i).getDisplayName())) { for (BaseRequirement r :
	 * a.getRequirements().getRequirementsByType(AchievementType.BREW)) { if
	 * (r.getRequirementEntityName().equals(event.getItem(i).getDisplayName()))
	 * {
	 * 
	 * } } } } } }
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