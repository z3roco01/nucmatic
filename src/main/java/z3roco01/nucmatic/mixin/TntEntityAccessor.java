package z3roco01.nucmatic.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * mixes in code into the TntEntity class, since the causingEntity property is private, we create an accessor for it
 * @since 22/01/2025
 */
@Mixin(TntEntity.class)
public interface TntEntityAccessor {
    // accessor for causingEntity, allows settings it
    @Accessor("causingEntity")
    void setCausingEntity(LivingEntity causingEntity);
}
