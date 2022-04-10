package tear.of.sun.utils

import tear.of.sun.entity.DataEntity

interface ItemClickListener {
    fun itemClick(item:DataEntity, position: Int)
}