package github.cmh1448.backend.system.security.configurer

fun interface Customizer<T> {
    fun customize(t: T)
}