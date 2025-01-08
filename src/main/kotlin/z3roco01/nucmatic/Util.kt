package z3roco01.nucmatic

/**
 * various helper functions
 * @since 07/01/2025
 */
object Util {
    // from https://stackoverflow.com/questions/44386394/is-there-any-method-in-kotlin-which-allow-me-to-translate-a-value-from-a-range-i
    /**
     * maps a number from one range to another
     * @param number the number to be remapped
     * @param original the original range of the number
     * @param target the new range of the number
     * @return the remapped number
     */
    fun map(number: Int, original: IntRange, target: IntRange): Int {
        val ratio = number.toFloat() / (original.last - original.first)
        return (ratio * (target.last - target.first)).toInt()
    }
}