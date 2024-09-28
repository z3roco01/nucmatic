package z3roco01.nucmatic.item

import net.minecraft.component.type.FoodComponent
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import z3roco01.nucmatic.entity.effect.RadiationPoisoningEffect
import java.util.*

/**
 * Class for the stem cell item, used to treat radiation poisoning caused damage.
 * semi based in real life ( stem cells are used in severe cases to promote restoration of bone marrow function ).
 * @since 28/09/2024
 */
/*
 * super constructor has item set tings with an food component that lets the player eat the item
 * but it does not give any nutrition or saturation and takes double the time to eat
 */
class StemCellItem : Item(Settings().food(
    FoodComponent(0, 0f, true, 3.2f, Optional.empty(), listOf()))) {

    // called after the player has eaten the item, will remove all health removed from radiation poisoning
    override fun finishUsing(stack: ItemStack, world: World, user: LivingEntity): ItemStack {
        // do not run this on the client
        if(world.isClient) return stack

        // remove the health modifier applied by radiation poisoning
        user.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)!!
            .removeModifier(RadiationPoisoningEffect.RADIATION_POISONING_MODIFIER_ID)

        // stack that gets returned, will just decrement one from the original stack
        val retStack = stack.copy()
        retStack.decrement(1)

        return retStack
    }
}