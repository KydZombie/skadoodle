package com.kydzombie.skadoodle

import com.kydzombie.skadoodle.block.CrashLogBlock
import com.kydzombie.skadoodle.item.StackTraceItem
import net.mine_diver.unsafeevents.listener.EventListener
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint
import net.modificationstation.stationapi.api.util.Namespace

object Skadoodle {
    @Entrypoint.Namespace
    lateinit var NAMESPACE: Namespace

    lateinit var crashLogBlock: Block

    @EventListener
    private fun registerBlocks(event: BlockRegistryEvent) {
        crashLogBlock = CrashLogBlock(NAMESPACE.id("crash_log"))
    }

    lateinit var stacktraceItem: Item

    @EventListener
    private fun registerItems(event: ItemRegistryEvent) {
        stacktraceItem = StackTraceItem(NAMESPACE.id("stack_trace"))
    }
}