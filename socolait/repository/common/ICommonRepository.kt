package mg.socolait.repository.common

import com.j256.ormlite.support.ConnectionSource
import java.sql.SQLException
import java.util.*


interface ICommonRepository<DO, ID> {


    /**
     * Generic method used to get all objects of a particular type. This is the same as lookup up all rows in a table.
     *
     * @return List of populated objects
     */
    fun findAll(): List<DO>


    /**
     * Generic method used to get all objects by page of a particular type. This
     * is the same as lookup up all rows in a table.
     *
     * @param pageNumero  indice de la page a recupere commence a 0 , [0 .... ]
     * @param itemsPerPage nombre de ligne pas page , ( le pas , l offset )
     *
     * @return List of populated objects
     */
    fun findAllPagination(pageNumero: Int, itemsPerPage: Int): List<DO>

    /**
     * Generic method to get an object based on class and identifier. An ObjectRetrievalFailureException Runtime Exception is thrown if nothing is found.
     *
     * @param id
     * the identifier (primary key) of the class
     * @return a populated object
     * @see org.springframework.orm.ObjectRetrievalFailureException
     */
    fun findById(id: ID): DO


    /**
     * Generic method to get all objects based on criteria. An ObjectRetrievalFailureException Runtime Exception is thrown if nothing is found.
     *
     * @param criteria
     * Map with criteria
     * @return a populated object
     * @see org.springframework.orm.ObjectRetrievalFailureException
     */
    fun findAllByCriteria(criteria: Map<String, Any>): List<DO>


    /**
     * Generic method to add an object.
     *
     * @param entity
     * the object to add
     */
    fun insert(entity: DO)

    /**
     * Generic method to add list of object using batch
     * @param items list of object to insert
     */
    fun insertBatch(items: List<DO>)


    /**
     * Generic method to update an object.
     *
     * @param entity
     * the object to save
     */
    fun update(entity: DO)

    /**
     * Generic method to update list of object using batch
     * @param items list of object to update
     */
    fun updateBatch(items: List<DO>)

    /**
     * Remove all data from database
     *
     */
    fun exists(id: ID): Boolean

    /**
     * Remove all data from database
     *
     */
    fun exists(criteria: Map<String, Any>): Boolean

    /**
     * Generic method to delete an object based on class and id
     *
     * @param entity
     * the identifier (primary key) of the class
     */
    fun delete(id: ID)


    /**
     * Remove all data from database
     *
     */
    fun deleteAll()


    /**
     * Generic method used to count all objects a particular type. This is the
     * same as lookup up all rows in a table.
     *
     * @return number of rows in a table
     */
    fun countAllRows(): Long?

    /**
     * for conveniance
     * @return
     */
    val connectionSource: ConnectionSource


    /**
     * Remove by criteria
     *
     */
    fun deleteAllByCriteria(nameColumn: String, value: Any)

    /**
     * Generic method to get all objects based on criteria of a column not null. An ObjectRetrievalFailureException Runtime Exception is thrown if nothing is found.
     * @param nameColumn
     * @return
     */
    fun findAllByCriteriaNullOrNotNull(nameColumn: String, isNull: Boolean): List<DO>

    /**
     * Delete entry in table where this entry is null or not
     * @param isNull true if we want delete all entry null
     * @param nameColumm
     */
    fun deleteAllByCriteriaNullOrNot(nameColumn: String, isNull: Boolean)

    /**
     * Cette fonction renvoi les r�sultat d'une requete avec multiple crit�re s�parer avec "OU"
     * @param listCriteria
     * @return
     * @throws SQLException
     */
    @Throws(SQLException::class)
    fun findAllByMultipleCriteria(listCriteria: List<Map<String, Any>>): ArrayList<DO>

    fun deleteAllByCriteriaMultiple(nameColumn: String, value: Any, nameColumn2: String, value2: Any)

    fun deleteAllByMultipleCriteria(listCriteria: Map<String, Any>)
}
