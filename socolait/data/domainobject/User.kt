package mg.socolait.data.domainobject

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

/**
 * Created by lovasoa_arnaud on 11/12/2017.
 */
@DatabaseTable(tableName = "user")
data class User(
        @DatabaseField(generatedId = true, unique = true)
        var id: Long? = null,
        @DatabaseField(columnName = "user_id")
        var utilisateurId: Int = 0,
        @DatabaseField
        var login: String? = null,
        @DatabaseField
        var token: String? = null,
        @DatabaseField
        var nom: String? = null,
        @DatabaseField(columnName = "rc_id")
        var rcId: Int = 0,
        @DatabaseField(columnName = "rc_name")
        var nomRc: String? = null,
        @DatabaseField(columnName = "client_odoo_id")
        var clientOdooId: Int = 0,
        @DatabaseField(columnName = "repository_id")
        var entrepotId: Int = 0,
        @DatabaseField(columnName = "repository_name")
        var nomEntrepot: String? = null,
        @DatabaseField(columnName = "sale_team_id")
        var saleTeamId: Int = 0
)