package github.cmh1448.backend.system.security.configurer

interface Customizer<T> {
    fun customize(t: T)
}