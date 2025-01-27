package nl.martenm.servertutorialplus.objects;

import nl.martenm.servertutorialplus.ServerTutorialPlus;
import nl.martenm.servertutorialplus.language.Lang;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.UUID;

/**
 * This object is used to hold certain data when a player is trying to bind an entity as NPC.
 * @author MartenM
 */
public class TutorialEntitySelector {

    private Player player;
    private String npcId;
    private ServerTutorial tutorial;

    public TutorialEntitySelector(Player player, ServerTutorial tutorial, String npcId){
        this.player = player;
        this.npcId = npcId;
        this.tutorial = tutorial;
    }

    public Player getPlayer() {
        return player;
    }

    public String getNpcId() {
        return npcId;
    }

    public ServerTutorial getTutorial() {
        return tutorial;
    }

    public void create(ServerTutorialPlus plugin, PlayerInteractEntityEvent event){

        if(!(event.getRightClicked() instanceof LivingEntity)){
            player.sendMessage(Lang.NPC_INVALID_ENTITY.toString());
            return;
        }

        if(event.getRightClicked() instanceof HumanEntity){
            HumanEntity humanEntity = (HumanEntity) event.getRightClicked();

            Player target = plugin.getServer().getPlayer(event.getRightClicked().getUniqueId());
            if(target != null){
                player.sendMessage(Lang.NPC_PLAYER_SELECTED.toString());
                return;
            }
        }

        LivingEntity npc = (LivingEntity) event.getRightClicked();
        npc.setAI(false);
        npc.setCanPickupItems(false);
        npc.setGravity(false);
        npc.setCollidable(false);

        plugin.getNpcManager().createNPC(npc, npc.getLocation(), npcId, tutorial.getId());
        plugin.selectingNpc.remove(player.getUniqueId());
    }
}
