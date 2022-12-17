object Dependency {
    object Kotlin {
        const val Version = "1.7.20"
    }
    object Paper {
        const val Version = "1.19.2"
        const val APIVersion = "1.19"
    }
    object CommandAPI {
        const val Version = "8.7.0"
    }
    val Dependencies = arrayListOf(
        "org.jetbrains.kotlin:kotlin-stdlib:${Kotlin.Version}",
        "dev.jorel:commandapi-shade:${CommandAPI.Version}"
    )
    val Libraries = arrayListOf(
        "org.jetbrains.kotlin:kotlin-stdlib:${Kotlin.Version}",
        "dev.jorel:commandapi-shade:${CommandAPI.Version}"
    )
}