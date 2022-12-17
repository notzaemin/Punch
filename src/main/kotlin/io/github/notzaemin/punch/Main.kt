package io.github.notzaemin.punch

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin

class Main: JavaPlugin(), Listener {
    private val punchSlot = 4
    private val punchPower = (30).toFloat()
    @EventHandler
    private fun EntityDamageEvent.on() {
        if (entity is Player && cause == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) isCancelled = true
    }
    @EventHandler
    private fun PlayerInteractEvent.on() {
        if (action.isLeftClick && clickedBlock != null && player.inventory.heldItemSlot == punchSlot) {
            clickedBlock!!.world.createExplosion(clickedBlock!!.location, punchPower)
        }
    }
    @EventHandler
    private fun EntityDamageByEntityEvent.on() {
        if (damager is Player && (damager as Player).inventory.heldItemSlot == punchSlot) {
            entity.world.createExplosion(entity.location, punchPower)
        }
    }
    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
        server.scheduler.runTaskTimer(this, Runnable {
            server.onlinePlayers.forEach {
                if (it.inventory.getItem(punchSlot) != null) {
                    for (index in 0..35) {
                        if (index != punchSlot && it.inventory.getItem(index) == null){
                            it.inventory.setItem(index, it.inventory.getItem(punchSlot))
                            break
                        }
                        else if (index == 35) it.world.dropItem(it.location, it.inventory.getItem(punchSlot)!!)
                    }
                    it.inventory.setItem(punchSlot, null)
                }
            }
        }, 0, 1)
    }
}