package com.dyn.achievements.handlers;

import java.util.Random;

import com.dyn.achievements.achievement.AchievementPlus;
import com.dyn.achievements.achievement.AchievementType;
import com.dyn.achievements.achievement.Requirements.BaseRequirement;
import com.dyn.achievements.achievement.Requirements.LocationRequirement;
import com.dyn.item.blocks.BlockChunkLoader;
import com.dyn.server.ServerMod;
import com.dyn.server.packets.PacketDispatcher;
import com.dyn.server.packets.client.SyncAchievementsMessage;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
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
			if (event.state.getBlock() instanceof BlockChunkLoader) {
				if (!(ServerMod.proxy.getOpLevel(event.getPlayer().getGameProfile()) > 0)) {
					event.setCanceled(true);
					return;
				}
			}
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
			if (AchievementHandler.getItemNames().containsKey(AchievementType.BREAK)) {
				for (AchievementPlus a : AchievementHandler.getItemNames().get(AchievementType.BREAK)
						.get(is.getDisplayName())) {
					if ((a.getWorldId() > 0) && (event.getPlayer().dimension == a.getWorldId())) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(AchievementType.BREAK)) {
							if (r.getRequirementEntityName().equals(is.getDisplayName())) {
								AchievementHandler.incrementPlayersAchievementsTotal(event.getPlayer(), a, r);
								PacketDispatcher.sendTo(new SyncAchievementsMessage(
										"" + a.getId() + " " + AchievementType.BREAK + " " + r.getRequirementID()),
										(EntityPlayerMP) event.getPlayer());
							}
						}
					} else if (a.getWorldId() == 0) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(AchievementType.BREAK)) {
							if (r.getRequirementEntityName().equals(is.getDisplayName())) {
								AchievementHandler.incrementPlayersAchievementsTotal(event.getPlayer(), a, r);
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

	@SubscribeEvent
	public void craftingEvent(PlayerEvent.ItemCraftedEvent event) {
		if (event.crafting != null) {
			if (AchievementHandler.getItemNames().containsKey(AchievementType.CRAFT)) {
				for (AchievementPlus a : AchievementHandler.getItemNames().get(AchievementType.CRAFT)
						.get(event.crafting.getDisplayName())) {
					if ((a.getWorldId() > 0) && (event.player.dimension == a.getWorldId())) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(AchievementType.CRAFT)) {
							if (r.getRequirementEntityName().equals(event.crafting.getDisplayName())) {
								AchievementHandler.incrementPlayersAchievementsTotal(event.player, a, r);
								PacketDispatcher.sendTo(new SyncAchievementsMessage(
										"" + a.getId() + " " + AchievementType.CRAFT + " " + r.getRequirementID()),
										(EntityPlayerMP) event.player);
							}
						}
					} else if (a.getWorldId() == 0) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(AchievementType.CRAFT)) {
							if (r.getRequirementEntityName().equals(event.crafting.getDisplayName())) {
								AchievementHandler.incrementPlayersAchievementsTotal(event.player, a, r);
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
	public void killEvent(LivingDeathEvent event) {
		if ((event.source != null) && (event.source.getEntity() != null) && (event.entity != null)
				&& (event.source.getEntity() instanceof EntityPlayer)) {
			if (AchievementHandler.getEntityNames().containsKey(AchievementType.KILL)) {
				for (AchievementPlus a : AchievementHandler.getEntityNames().get(AchievementType.KILL)
						.get(EntityList.getEntityString(event.entity))) {
					if ((a.getWorldId() > 0) && (event.source.getEntity().dimension == a.getWorldId())) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(AchievementType.KILL)) {
							if (r.getRequirementEntityName().equals(EntityList.getEntityString(event.entity))) {
								AchievementHandler.incrementPlayersAchievementsTotal(
										(EntityPlayer) event.source.getEntity(), a, r);
								PacketDispatcher.sendTo(new SyncAchievementsMessage(
										"" + a.getId() + " " + AchievementType.KILL + " " + r.getRequirementID()),
										(EntityPlayerMP) event.source.getEntity());
							}
						}
					} else if (a.getWorldId() == 0) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(AchievementType.KILL)) {
							if (r.getRequirementEntityName().equals(EntityList.getEntityString(event.entity))) {
								AchievementHandler.incrementPlayersAchievementsTotal(
										(EntityPlayer) event.source.getEntity(), a, r);
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
	public void pickupEvent(PlayerEvent.ItemPickupEvent event) {
		if (event.pickedUp != null) {
			if (AchievementHandler.getItemNames().containsKey(AchievementType.PICKUP)) {
				for (AchievementPlus a : AchievementHandler.getItemNames().get(AchievementType.PICKUP)
						.get(event.pickedUp.getEntityItem().getDisplayName())) {
					if ((a.getWorldId() > 0) && (event.player.dimension == a.getWorldId())) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(AchievementType.PICKUP)) {
							if (r.getRequirementEntityName().equals(event.pickedUp.getEntityItem().getDisplayName())) {
								AchievementHandler.incrementPlayersAchievementsTotal(event.player, a, r);
								PacketDispatcher.sendTo(new SyncAchievementsMessage(
										"" + a.getId() + " " + AchievementType.PICKUP + " " + r.getRequirementID()),
										(EntityPlayerMP) event.player);
							}
						}
					} else if (a.getWorldId() == 0) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(AchievementType.PICKUP)) {
							if (r.getRequirementEntityName().equals(event.pickedUp.getEntityItem().getDisplayName())) {
								AchievementHandler.incrementPlayersAchievementsTotal(event.player, a, r);
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
	public void placeBlockEvent(PlaceEvent event) {
		// we are only concerned with placing blocks
		if ((event.placedBlock != null) && (event.player != null)) {
			if (event.placedBlock instanceof BlockChunkLoader) {
				if (!(ServerMod.proxy.getOpLevel(event.player.getGameProfile()) > 0)) {
					event.setCanceled(true);
					event.player.inventory.consumeInventoryItem(Item.getItemFromBlock((Block) event.placedBlock));
					return;
				}
			}
			if (AchievementHandler.getItemNames().containsKey(AchievementType.PLACE)) {
				for (AchievementPlus a : AchievementHandler.getItemNames().get(AchievementType.PLACE)
						.get(event.itemInHand.getDisplayName())) {
					if ((a.getWorldId() > 0) && (event.player.dimension == a.getWorldId())) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(AchievementType.PLACE)) {
							if (r.getRequirementEntityName().equals(event.itemInHand.getDisplayName())) {
								AchievementHandler.incrementPlayersAchievementsTotal(event.player, a, r);
								PacketDispatcher.sendTo(new SyncAchievementsMessage(
										"" + a.getId() + " " + AchievementType.PLACE + " " + r.getRequirementID()),
										(EntityPlayerMP) event.player);
							}
						}
					} else if (a.getWorldId() == 0) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(AchievementType.PLACE)) {
							if (r.getRequirementEntityName().equals(event.itemInHand.getDisplayName())) {
								AchievementHandler.incrementPlayersAchievementsTotal(event.player, a, r);
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
	public void smeltingEvent(PlayerEvent.ItemSmeltedEvent event) {
		if (event.smelting != null) {
			if (AchievementHandler.getItemNames().containsKey(AchievementType.SMELT)) {
				for (AchievementPlus a : AchievementHandler.getItemNames().get(AchievementType.SMELT)
						.get(event.smelting.getDisplayName())) {
					if ((a.getWorldId() > 0) && (event.player.dimension == a.getWorldId())) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(AchievementType.SMELT)) {
							if (r.getRequirementItemID() == Item.getIdFromItem(event.smelting.getItem())) {
								AchievementHandler.incrementPlayersAchievementsTotal(event.player, a, r);
								PacketDispatcher.sendTo(new SyncAchievementsMessage(
										"" + a.getId() + " " + AchievementType.SMELT + " " + r.getRequirementID()),
										(EntityPlayerMP) event.player);
							}
						}
					} else if (a.getWorldId() == 0) {
						for (BaseRequirement r : a.getRequirements().getRequirementsByType(AchievementType.SMELT)) {
							if (r.getRequirementItemID() == Item.getIdFromItem(event.smelting.getItem())) {
								AchievementHandler.incrementPlayersAchievementsTotal(event.player, a, r);
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

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if(event.side == Side.SERVER && event.player.dimension > 1){
			for(AchievementPlus a: AchievementHandler.findAchievementByType(AchievementType.LOCATION)){
				if(a.getWorldId() == event.player.dimension){
					 for(BaseRequirement r: a.getRequirements().getRequirementsByType(AchievementType.LOCATION)){
						 if(r.getTotalAquired() ==0){
							 LocationRequirement lr = (LocationRequirement) r;
							 Vec3i achVec = new Vec3i(lr.x, lr.y, lr.z);
							 Vec3i playerVec = new Vec3i(event.player.posX, event.player.posY, event.player.posZ);
							 if(playerVec.distanceSq(achVec) < lr.r * lr.r){//roots are super slow so take the product
								 PacketDispatcher.sendTo(new SyncAchievementsMessage(
											"" + a.getId() + " " + AchievementType.LOCATION + " " + r.getRequirementID()),
											(EntityPlayerMP) event.player);
							 } 
						 }
					 }
				}
			}
		}
	}

}