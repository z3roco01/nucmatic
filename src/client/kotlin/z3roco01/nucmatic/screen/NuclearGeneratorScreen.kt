package z3roco01.nucmatic.screen

import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import z3roco01.nucmatic.Nucmatic

/**
 * the screen for the nuclear generator, handles all the rendering of the screen
 * @since 15/10/2024
 */
class NuclearGeneratorScreen(handler: NuclearGeneratorScreenHandler, playerInv: PlayerInventory, title: Text):
    HandledScreen<NuclearGeneratorScreenHandler>(handler, playerInv, title) {
    // identifier for the background texture, hold the path to it
    val BG_TEXTURE = Nucmatic.mkId("textures/gui/container/nuclear_generator.png")

    override fun init() {
        super.init()
        // assure the title will be centered vertically
        this.titleX = (this.backgroundWidth - textRenderer.getWidth(this.title)) / 2
    }

    // renders the background image of the screen
    override fun drawBackground(context: DrawContext, delta: Float, mouseX: Int, mouseY: Int) {
        // get the middle screen coordinates on the x and y
        val centerX = (this.width - this.backgroundWidth) / 2
        val centerY = (this.height - this.backgroundHeight) / 2

        // render the background texture to the screen with the center coords
        context.drawTexture(BG_TEXTURE, centerX, centerY, 0, 0, this.backgroundWidth, this.backgroundHeight)
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(context, mouseX, mouseY, delta)
        this.drawMouseoverTooltip(context, mouseX, mouseY)
    }
}