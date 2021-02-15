package mg.socolait.data.dto

/**
 * Created by lovasoa_arnaud on 11/12/2017.
 */
data class UserDto(
        var id: Long? = null,
        var utilisateurId: Int = 0,
        var login: String? = null,
        var nom: String? = null,
        var rcId: Int = 0,
        var nomRc: String? = null,
        var clientOdooId: Int = 0,
        var entrepotId: Int = 0,
        var nomEntrepot: String? = null,
        var saleTeamId: Int = 0,
        var token: String? = null
)