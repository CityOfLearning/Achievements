package com.dyn.achievements.handlers;

import java.util.Random;

import com.dyn.achievements.achievement.AchievementPlus;
import com.dyn.achievements.achievement.RequirementType;
import com.dyn.achievements.achievement.Requirements.BaseRequirement;
import com.dyn.achievements.achievement.Requirements.LocationRequirement;
import com.dyn.server.packets.PacketDispatcher;
import com.dyn.server.packets.client.SyncAchievementsMessage;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

public class EventHandler {

	@SubscribeEvent
	public void breakBlockEvent(BreakEvent event) {
		// we are only concerned with placing blocks
		if ((event.state.getBlock() != null) && (event.getPlayer() != null)) {
			ItemStack is = new ItemStack(event.state.getBlock(), 1,
					event.state.getBlock().getMetaFromState(event.state));
			try {
				is.getDisplayName();
			} catch (NullPointerException e) {
				is = new ItemStack(event.state.getBlock().getItemDropped(event.state, new Random(), 0), 1,
						event.state.getBlock().getMetaFromState(event.state));
				try {
					is.getDisplayName();
				} catch (NullPointerException e2) {
					// if this doesnt work nothing will lets return before we
					// cause problems
					event.getPlayer().addChatMessage(new ChatComponentText(
							"Cannot create item stack from block: " + event.state.getBlock().getLocalizedName()));
					return;
				}
			}
			if (AchievementManager.getItemNames().containsKey(RequirementType.BREAK)) {
				for (AchievementPlus a : AchievementManager.getItemNames().get(RequirementType.BREAK)
						.get(is.getDisplayName())) {
					if ((a.getWorldId() > 0) && (event.getPlayer().dimension == a.getWorldId())) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(RequirementType.BREAK)) {
							if (!AchievementManager.getPlayersAchievementsRequirementStatus(event.getPlayer(), a,
									RequirementType.BREAK, r.getRequirementID())
									&& r.getRequirementEntityName().equals(is.getDisplayName())) {
								AchievementManager.incrementPlayersAchievementsTotal(event.getPlayer(), a,
										RequirementType.BREAK, r.getRequirementID());
								PacketDispatcher.sendTo(new SyncAchievementsMessage(
										"" + a.getId() + " " + RequirementType.BREAK + " " + r.getRequirementID()),
										(EntityPlayerMP) event.getPlayer());
							}
						}
					} else if (a.getWorldId() == 0) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(RequirementType.BREAK)) {
							if (!AchievementManager.getPlayersAchievementsRequirementStatus(event.getPlayer(), a,
									RequirementType.BREAK, r.getRequirementID())
									&& r.getRequirementEntityName().equals(is.getDisplayName())) {
								AchievementManager.incrementPlayersAchievementsTotal(event.getPlayer(), a,
										RequirementType.BREAK, r.getRequirementID());
								PacketDispatcher.sendTo(new SyncAchievementsMessage(
										"" + a.getId() + " " + RequirementType.BREAK + " " + r.getRequirementID()),
										(EntityPlayerMP) event.getPlayer());
							}
						}
					}
				}
			}
		}
	}

	public boolean contains(Vec3 achPoint1, Vec3 achPoint2, Vec3 playerPoint) {
		Vec3 corner1 = getMinPoint(achPoint1, achPoint2);
		Vec3 corner2 = getMaxPoint(achPoint1, achPoint2);
		return isGreaterEqualThan(corner2, playerPoint) && isLessEqualThan(corner1, playerPoint);
	}

	@SubscribeEvent
	public void craftingEvent(PlayerEvent.ItemCraftedEvent event) {
		if (event.crafting != null) {
			if (AchievementManager.getItemNames().containsKey(RequirementType.CRAFT)) {
				for (AchievementPlus a : AchievementManager.getItemNames().get(RequirementType.CRAFT)
						.get(event.crafting.getDisplayName())) {
					if ((a.getWorldId() > 0) && (event.player.dimension == a.getWorldId())) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(RequirementType.CRAFT)) {
							if (!AchievementManager.getPlayersAchievementsRequirementStatus(event.player, a,
									RequirementType.CRAFT, r.getRequirementID())
									&& r.getRequirementEntityName().equals(event.crafting.getDisplayName())) {
								AchievementManager.incrementPlayersAchievementsTotal(event.player, a,
										RequirementType.CRAFT, r.getRequirementID());
								PacketDispatcher.sendTo(new SyncAchievementsMessage(
										"" + a.getId() + " " + RequirementType.CRAFT + " " + r.getRequirementID()),
										(EntityPlayerMP) event.player);
							}
						}
					} else if (a.getWorldId() == 0) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(RequirementType.CRAFT)) {
							if (!AchievementManager.getPlayersAchievementsRequirementStatus(event.player, a,
									RequirementType.CRAFT, r.getRequirementID())
									&& r.getRequirementEntityName().equals(event.crafting.getDisplayName())) {
								AchievementManager.incrementPlayersAchievementsTotal(event.player, a,
										RequirementType.CRAFT, r.getRequirementID());
								PacketDispatcher.sendTo(new SyncAchievementsMessage(
										"" + a.getId() + " " + RequirementType.CRAFT + " " + r.getRequirementID()),
										(EntityPlayerMP) event.player);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Get the highest XYZ coordinate in OOBB [p1,p2]
	 */
	public Vec3 getMaxPoint(Vec3 p1, Vec3 p2) {
		return new Vec3(Math.max(p1.xCoord, p2.xCoord), Math.max(p1.yCoord, p2.yCoord), Math.max(p1.zCoord, p2.zCoord));
	}

	public Vec3 getMinPoint(Vec3 p1, Vec3 p2) {
		return new Vec3(Math.min(p1.xCoord, p2.xCoord), Math.min(p1.yCoord, p2.yCoord), Math.min(p1.zCoord, p2.zCoord));
	}

	/**
	 * Checks if this point has greater or equal coordinates than another point
	 * on all axes
	 */
	public boolean isGreaterEqualThan(Vec3 origin, Vec3 p) {
		return (origin.xCoord >= p.xCoord) && (origin.yCoord >= p.yCoord) && (origin.zCoord >= p.zCoord);
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

	/**
	 * Checks if this point has less or equal coordinates than another point on
	 * all axes
	 */
	public boolean isLessEqualThan(Vec3 origin, Vec3 p) {
		return (origin.xCoord <= p.xCoord) && (origin.yCoord <= p.yCoord) && (origin.zCoord <= p.zCoord);
	}

	@SubscribeEvent
	public void killEvent(LivingDeathEvent event) {
		if ((event.source != null) && (event.source.getEntity() != null) && (event.entity != null)
				&& (event.source.getEntity() instanceof EntityPlayer)) {
			if (AchievementManager.getEntityNames().containsKey(RequirementType.KILL)) {
				for (AchievementPlus a : AchievementManager.getEntityNames().get(RequirementType.KILL)
						.get(EntityList.getEntityString(event.entity))) {
					if ((a.getWorldId() > 0) && (event.source.getEntity().dimension == a.getWorldId())) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(RequirementType.KILL)) {
							if (!AchievementManager.getPlayersAchievementsRequirementStatus(
									(EntityPlayer) event.source.getEntity(), a, RequirementType.KILL,
									r.getRequirementID())
									&& r.getRequirementEntityName().equals(EntityList.getEntityString(event.entity))) {
								AchievementManager.incrementPlayersAchievementsTotal(
										(EntityPlayer) event.source.getEntity(), a, RequirementType.KILL,
										r.getRequirementID());
								PacketDispatcher.sendTo(new SyncAchievementsMessage(
										"" + a.getId() + " " + RequirementType.KILL + " " + r.getRequirementID()),
										(EntityPlayerMP) event.source.getEntity());
							}
						}
					} else if (a.getWorldId() == 0) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(RequirementType.KILL)) {
							if (!AchievementManager.getPlayersAchievementsRequirementStatus(
									(EntityPlayer) event.source.getEntity(), a, RequirementType.KILL,
									r.getRequirementID())
									&& r.getRequirementEntityName().equals(EntityList.getEntityString(event.entity))) {
								AchievementManager.incrementPlayersAchievementsTotal(
										(EntityPlayer) event.source.getEntity(), a, RequirementType.KILL,
										r.getRequirementID());
								PacketDispatcher.sendTo(new SyncAchievementsMessage(
										"" + a.getId() + " " + RequirementType.KILL + " " + r.getRequirementID()),
										(EntityPlayerMP) event.source.getEntity());
							}
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if ((event.side == Side.SERVER) && (event.player.dimension > 1)) {
			if (AchievementManager.findAchievementByType(RequirementType.LOCATION) != null) {
				for (AchievementPlus a : AchievementManager.findAchievementByType(RequirementType.LOCATION)) {
					if (a.getWorldId() == event.player.dimension) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(RequirementType.LOCATION)) {
							if (r.getTotalAquired() == 0) {
								LocationRequirement lr = (LocationRequirement) r;
								Vec3 achVec1 = new Vec3(lr.x1, lr.y1, lr.z1);
								Vec3 playerVec = new Vec3(event.player.posX, event.player.posY, event.player.posZ);
								if (lr.r >= 0) { // if the requirement is based
													// on
													// the radius from a point
									if (!AchievementManager.getPlayersAchievementsRequirementStatus(event.player, a,
											RequirementType.LOCATION, r.getRequirementID())
											&& (achVec1.distanceTo(playerVec) < lr.r)) {
										AchievementManager.incrementPlayersAchievementsTotal(event.player, a,
												RequirementType.LOCATION, r.getRequirementID());
										event.player.addChatMessage(new ChatComponentText(
												"You found the location: " + lr.getRequirementEntityName()));
										PacketDispatcher.sendTo(
												new SyncAchievementsMessage("" + a.getId() + " "
														+ RequirementType.LOCATION + " " + r.getRequirementID()),
												(EntityPlayerMP) event.player);
									}
								} else { // the requirement is based on a
											// defined
											// area
									Vec3 achVec2 = new Vec3(lr.x2, lr.y2, lr.z2);
									if (!AchievementManager.getPlayersAchievementsRequirementStatus(event.player, a,
											RequirementType.LOCATION, r.getRequirementID())
											&& contains(achVec1, achVec2, playerVec)) {
										AchievementManager.incrementPlayersAchievementsTotal(event.player, a,
												RequirementType.LOCATION, r.getRequirementID());
										event.player.addChatMessage(new ChatComponentText(
												"You found the location: " + lr.getRequirementEntityName()));
										PacketDispatcher.sendTo(
												new SyncAchievementsMessage("" + a.getId() + " "
														+ RequirementType.LOCATION + " " + r.getRequirementID()),
												(EntityPlayerMP) event.player);
									}
								}
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
			if (AchievementManager.getItemNames().containsKey(RequirementType.PICKUP)) {
				for (AchievementPlus a : AchievementManager.getItemNames().get(RequirementType.PICKUP)
						.get(event.pickedUp.getEntityItem().getDisplayName())) {
					if ((a.getWorldId() > 0) && (event.player.dimension == a.getWorldId())) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(RequirementType.PICKUP)) {
							if (!AchievementManager.getPlayersAchievementsRequirementStatus(event.player, a,
									RequirementType.PICKUP, r.getRequirementID())
									&& r.getRequirementEntityName()
											.equals(event.pickedUp.getEntityItem().getDisplayName())) {
								AchievementManager.incrementPlayersAchievementsTotal(event.player, a,
										RequirementType.PICKUP, r.getRequirementID());
								PacketDispatcher.sendTo(new SyncAchievementsMessage(
										"" + a.getId() + " " + RequirementType.PICKUP + " " + r.getRequirementID()),
										(EntityPlayerMP) event.player);
							}
						}
					} else if (a.getWorldId() == 0) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(RequirementType.PICKUP)) {
							if (!AchievementManager.getPlayersAchievementsRequirementStatus(event.player, a,
									RequirementType.PICKUP, r.getRequirementID())
									&& r.getRequirementEntityName()
											.equals(event.pickedUp.getEntityItem().getDisplayName())) {
								AchievementManager.incrementPlayersAchievementsTotal(event.player, a,
										RequirementType.PICKUP, r.getRequirementID());
								PacketDispatcher.sendTo(new SyncAchievementsMessage(
										"" + a.getId() + " " + RequirementType.PICKUP + " " + r.getRequirementID()),
										(EntityPlayerMP) event.player);
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
		if ((event.placedBlock != null) && (event.player != null)) {
			if (AchievementManager.getItemNames().containsKey(RequirementType.PLACE)) {
				for (AchievementPlus a : AchievementManager.getItemNames().get(RequirementType.PLACE)
						.get(event.itemInHand.getDisplayName())) {
					if ((a.getWorldId() > 0) && (event.player.dimension == a.getWorldId())) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(RequirementType.PLACE)) {
							if (!AchievementManager.getPlayersAchievementsRequirementStatus(event.player, a,
									RequirementType.PLACE, r.getRequirementID())
									&& r.getRequirementEntityName().equals(event.itemInHand.getDisplayName())) {
								AchievementManager.incrementPlayersAchievementsTotal(event.player, a,
										RequirementType.PLACE, r.getRequirementID());
								PacketDispatcher.sendTo(new SyncAchievementsMessage(
										"" + a.getId() + " " + RequirementType.PLACE + " " + r.getRequirementID()),
										(EntityPlayerMP) event.player);
							}
						}
					} else if (a.getWorldId() == 0) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(RequirementType.PLACE)) {
							if (!AchievementManager.getPlayersAchievementsRequirementStatus(event.player, a,
									RequirementType.PLACE, r.getRequirementID())
									&& r.getRequirementEntityName().equals(event.itemInHand.getDisplayName())) {
								AchievementManager.incrementPlayersAchievementsTotal(event.player, a,
										RequirementType.PLACE, r.getRequirementID());
								PacketDispatcher.sendTo(new SyncAchievementsMessage(
										"" + a.getId() + " " + RequirementType.PLACE + " " + r.getRequirementID()),
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
			if (AchievementManager.getItemNames().containsKey(RequirementType.SMELT)) {
				for (AchievementPlus a : AchievementManager.getItemNames().get(RequirementType.SMELT)
						.get(event.smelting.getDisplayName())) {
					if ((a.getWorldId() > 0) && (event.player.dimension == a.getWorldId())) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(RequirementType.SMELT)) {
							if (!AchievementManager.getPlayersAchievementsRequirementStatus(event.player, a,
									RequirementType.SMELT, r.getRequirementID())
									&& (r.getRequirementItemID() == Item.getIdFromItem(event.smelting.getItem()))) {
								AchievementManager.incrementPlayersAchievementsTotal(event.player, a,
										RequirementType.SMELT, r.getRequirementID());
								PacketDispatcher.sendTo(new SyncAchievementsMessage(
										"" + a.getId() + " " + RequirementType.SMELT + " " + r.getRequirementID()),
										(EntityPlayerMP) event.player);
							}
						}
					} else if (a.getWorldId() == 0) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(RequirementType.SMELT)) {
							if (!AchievementManager.getPlayersAchievementsRequirementStatus(event.player, a,
									RequirementType.SMELT, r.getRequirementID())
									&& (r.getRequirementItemID() == Item.getIdFromItem(event.smelting.getItem()))) {
								AchievementManager.incrementPlayersAchievementsTotal(event.player, a,
										RequirementType.SMELT, r.getRequirementID());
								PacketDispatcher.sendTo(new SyncAchievementsMessage(
										"" + a.getId() + " " + RequirementType.SMELT + " " + r.getRequirementID()),
										(EntityPlayerMP) event.player);
							}
						}
					}
				}
			}
		}
	}
}