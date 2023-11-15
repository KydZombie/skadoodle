package com.kydzombie.skadoodle.item

import net.modificationstation.stationapi.api.template.item.TemplateItem
import net.modificationstation.stationapi.api.util.Identifier

class StackTraceItem(identifier: Identifier): TemplateItem(identifier) {
    init {
        setTranslationKey(identifier)
    }
}