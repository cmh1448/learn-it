package github.cmh1448.backend.system.security.service

import github.cmh1448.backend.system.security.model.AuthDetails
import java.util.*

interface UserLoadService<T : AuthDetails> {
    fun loadUserByKey(key: String): Optional<T>
}
