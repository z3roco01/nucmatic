package z3roco01.nucmatic.item

import net.minecraft.item.ItemStack
import net.minecraft.item.tooltip.TooltipType
import net.minecraft.text.Text
import net.minecraft.util.math.MathHelper

/**
 * class used by all items that are nuclear fuel, inherits [RadioactiveItem], so its properties are in this constructor.<br>
 * for all constructor params see [RadioactiveItem]
 * @since 06/01/2025
 * @param energyValue the energy value held in this fuel.<br>
 * coal is 4000 and 1 plank is 750 as a reference.
 * @param burnRate the amount of energy that is burnt per tick when burning.<br>
 * coal is 2.5 e/t and planks are 2.3 e/t in a furnace as reference.
 * @param chance [Float] passed to [RadioactiveItem.chance]
 * @param amplifier [Int] passed to [RadioactiveItem.amplifier]
 * @param duration [IntRange] passed to [RadioactiveItem.duration]
 */
class NuclearFuelItem(val energyValue: Int, val burnRate: Int, chance: Float, amplifier: Int, duration: IntRange):
    RadioactiveItem(chance, amplifier, duration, Settings().maxDamage(energyValue).maxCount(1)) {
    // used to get the colour of the durability bar
    override fun getItemBarColor(stack: ItemStack): Int {
        // get the current percent of damage that has been used
        val damagePercent = stack.damage.toFloat() / stack.maxDamage
        // return the colour, but decrease the value for the damage on the stack
        return MathHelper.hsvToRgb(0.89722f, 0.49f, 0.96f - (0.5f * damagePercent))
    }

    // adds text to the tooltip, this will add how much energy is remaining in the fuel
    override fun appendTooltip(stack: ItemStack, context: TooltipContext, tooltip: MutableList<Text>, type: TooltipType) {
        // gets the translated text for "Energy remaining" then calculates the energy remaining
        tooltip.add(Text.translatable("item.nucmatic.fuel.tooltip")
            .append(Text.of(": ${stack.maxDamage - stack.damage}/${stack.maxDamage}")).withColor((0xFFED77BE).toInt()))
    }
}