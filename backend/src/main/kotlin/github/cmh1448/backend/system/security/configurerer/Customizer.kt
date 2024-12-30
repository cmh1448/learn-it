package github.cmh1448.backend.system.security.configurerer

interface Customizer<T> {
    fun customize(t: T)
}