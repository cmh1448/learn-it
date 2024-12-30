package github.cmh1448.backend.system.security.configurer

class PathPatternConfigurer {
    val includePatternList: MutableList<String> = ArrayList()
    val excludePatternList: MutableList<String> = ArrayList()

    fun includePath(path: String) {
        includePatternList.add(path)
    }

    fun includeAll() {
        includePatternList.add("/**")
    }

    fun excludePath(path: String) {
        excludePatternList.add(path)
    }
}
