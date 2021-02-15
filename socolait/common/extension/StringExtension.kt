package mg.socolait.common.extension

/**
 * Created by lovasoa_arnaud on 20/12/2017.
 */
fun String.removeWhiteSpace(): String {
    return this.replace("\\s+".toRegex(), "")
}

fun String.getFileNameFromUrl(): String {
    val parts = this.split("/")
    return parts[parts.size - 1]
}