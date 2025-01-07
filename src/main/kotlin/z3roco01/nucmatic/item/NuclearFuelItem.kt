package z3roco01.nucmatic.item

/**
 * class used by all items that are nuclear fuel, inherits [RadioactiveItem], so its properties are in this constructor.<br>
 * for all constructor params see [RadioactiveItem]
 * @since 06/01/2025
 * @param energyValue the energy value held in this fuel.<br>coal is 4000 and 1 plank is 750 as a reference.
 * @param burnRate the amount of energy that is burnt per tick when burning.<br>coal is 2.5 e/t and planks are 2.3 e/t in a furnace as reference.
 * @param chance [Float] passed to [RadioactiveItem.chance]
 * @param amplifier [Int] passed to [RadioactiveItem.amplifier]
 * @param duration [IntRange] passed to [RadioactiveItem.duration]
 * @param settings [net.minecraft.item.Item.Settings] passed to [RadioactiveItem]
 */
class NuclearFuelItem(val energyValue: Int, val burnRate: Int, chance: Float, amplifier: Int, duration: IntRange, settings: Settings):
    RadioactiveItem(chance, amplifier, duration, settings) {
}