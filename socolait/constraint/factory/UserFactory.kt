package mg.socolait.constraint.factory.db

import mg.socolait.constraint.factory.common.GenericFactory
import mg.socolait.constraint.factory.common.GenericListFactory
import mg.socolait.data.domainobject.User
import mg.socolait.data.dto.UserDto

/**
 * Created by lovasoa_arnaud on 11/12/2017.
 */
object UserFactory : GenericFactory<User, UserDto>, GenericListFactory<User, UserDto> {

    override fun toDomainObject(value: UserDto): User = User(
            id = value.id,
            utilisateurId = value.utilisateurId,
            login = value.login,
            token = value.token,
            nom = value.nom,
            rcId = value.rcId,
            nomRc = value.nomRc,
            clientOdooId = value.clientOdooId,
            entrepotId = value.entrepotId,
            nomEntrepot = value.nomEntrepot,
            saleTeamId = value.saleTeamId
    )

    override fun toDomainObjects(list: List<UserDto>): List<User> = list.map { toDomainObject(it) }

    override fun toDataTransferObject(value: User): UserDto = UserDto(
            id = value.id,
            utilisateurId = value.utilisateurId,
            login = value.login,
            token = value.token,
            nom = value.nom,
            rcId = value.rcId,
            nomRc = value.nomRc,
            clientOdooId = value.clientOdooId,
            entrepotId = value.entrepotId,
            nomEntrepot = value.nomEntrepot,
            saleTeamId = value.saleTeamId
    )

    override fun toDataTransferObjects(list: List<User>): List<UserDto> = list.map { toDataTransferObject(it) }

}