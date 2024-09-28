package z3roco01.nucmatic.item

import net.minecraft.entity.Entity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.world.World

/**
 * class for all radioactive items, will give [RadiationPoisoningEffect] with a chance% chance
 * @since 28/09/2024
 * @param chance the decimal percent chance of getting [RadiationPoisoningEffect] per tick
 * @param amplifier the amplifier used when [RadiationPoisoningEffect] is applied
 * @param settings the item settings for this item
 */
class RadioactiveItem(chance: Float, amplifier: Int, settings: Settings): Item(settings) {
    override fun inventoryTick(stack: ItemStack?, world: World?, entity: Entity?, slot: Int, selected: Boolean) {
        super.inventoryTick(stack, world, entity, slot, selected)
    }
}