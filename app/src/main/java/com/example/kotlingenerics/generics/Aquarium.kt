package com.example.kotlingenerics.generics

fun main() {
    genericsExample()
}
fun genericsExample() {
    val aquarium = Aquarium(TapWater())
    print("Does the system need processing?: " + aquarium.waterSupply.needsProcessing + "\n")
    aquarium.waterSupply.addChemicalCleaners()
    print("The system has been cleaned, does it need processing?: " + aquarium.waterSupply.needsProcessing + "\n")

    val cleaner = TapWaterCleaner()
    val aquarium2 = Aquarium(TapWater())
    aquarium2.addWater(cleaner)
    isWaterClean(aquarium2)

    print(aquarium2.waterSupply.isOfType<TapWater>())
    print(aquarium2.hasWaterSupplyOfType<TapWater>())

}

open class WaterSupply(var needsProcessing: Boolean)

class TapWater : WaterSupply(true) {
    fun addChemicalCleaners() {
        needsProcessing = false
    }
}

class FishStoreWater : WaterSupply(false)

class LakeWater : WaterSupply(true) {
    fun filter() {
        needsProcessing = false
    }
}

               // (out) meaning we only use this generic type for output
class Aquarium<out T: WaterSupply>(val waterSupply: T) {
    fun addWater(cleaner: Cleaner<T>) {
        if (waterSupply.needsProcessing) {
            cleaner.clean(waterSupply)
        }
        print("water added")
    }
}

// to use (is) keyword we must use the inline and reified keywords
inline fun <reified R: WaterSupply>  Aquarium<*>.hasWaterSupplyOfType() = waterSupply is R

fun addItemTo(aquarium: Aquarium<WaterSupply>) = print("item added")

                 // (in) meaning that we only use this generic type for input
interface Cleaner<in T: WaterSupply> {
    fun clean(waterSupply: T)
}

class TapWaterCleaner : Cleaner<TapWater> {
    override fun clean(waterSupply: TapWater) = waterSupply.addChemicalCleaners()
}

    // generic constraint        // generic argument
fun <T: WaterSupply> isWaterClean(aquarium: Aquarium<T>) {
    println("aquarium water is clean: ${!aquarium.waterSupply.needsProcessing}")
}

inline fun <reified T: WaterSupply> WaterSupply.isOfType() = this is T

