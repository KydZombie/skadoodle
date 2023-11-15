package com.kydzombie.skadoodle.block

import com.kydzombie.skadoodle.Skadoodle
import net.minecraft.block.Material
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.modificationstation.stationapi.api.template.block.TemplateBlock
import net.modificationstation.stationapi.api.template.block.TemplateLogBlock
import net.modificationstation.stationapi.api.util.Identifier
import java.util.*

class CrashLogBlock(identifier: Identifier): TemplateBlock(identifier, Material.WOOD) {
    init {
        setTranslationKey(identifier)
    }

    override fun onUse(world: World?, x: Int, y: Int, z: Int, player: PlayerEntity?): Boolean {
        player?.inventory?.method_671(ItemStack(Skadoodle.stacktraceItem))
        return true
    }
}