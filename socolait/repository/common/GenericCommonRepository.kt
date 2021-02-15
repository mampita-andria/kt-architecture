package mg.socolait.repository.common

import android.content.Context
import com.j256.ormlite.dao.RuntimeExceptionDao
import com.j256.ormlite.stmt.DeleteBuilder
import com.j256.ormlite.support.ConnectionSource
import mg.socolait.common.exceptions.TechnicalException
import java.sql.SQLException
import java.util.*

abstract class GenericCommonRepository<DO, ID> : ICommonRepository<DO, ID> {

    @Transient
    var dao: RuntimeExceptionDao<DO, ID>? = null
        protected set

    protected fun initDatabase(context: Context) {
        if (databaseHelper != null) {
            return
        }
        databaseHelper = DatabaseHelper(context)
    }

    override val connectionSource: ConnectionSource
        get() = databaseHelper!!.connectionSource

    override fun insertBatch(items: List<DO>) {
        dao!!.callBatchTasks {
            for (c in items) {
                insert(c)
            }
            null
        }
    }

    override fun updateBatch(items: List<DO>) {
        dao!!.callBatchTasks {
            for (c in items) {
                update(c)
            }
            null
        }
    }

    override fun findAll(): List<DO> = dao!!.queryForAll()

    override fun findAllByCriteria(criteria: Map<String, Any>): List<DO> = dao!!.queryForFieldValues(criteria)

    override fun insert(entity: DO) {
        if (entity != null) {
            if (dao!!.create(entity) == 0) {
                throw TechnicalException(INSERT_ERROR_MESSAGE + entity.toString())
            }
        }
    }

    override fun update(entity: DO) {
        if (entity != null) {
            if (dao!!.update(entity) == 0) {
                throw TechnicalException(UPDATE_ERROR_MESSAGE + entity.toString())
            }
        }
    }

    override fun findAllPagination(pageNumero: Int, itemsPerPage: Int): List<DO> {

        val queryBuilder = dao!!.queryBuilder()
        try {
            queryBuilder
                    .limit(java.lang.Long.valueOf(itemsPerPage.toLong()))
                    .offset(java.lang.Long.valueOf(((pageNumero - 1) * itemsPerPage).toLong()))

            val preparedQuery = queryBuilder.prepare()
            return dao!!.query(preparedQuery)

        } catch (e: SQLException) {
            throw TechnicalException(e)
        }

    }

    override fun findById(id: ID): DO = dao!!.queryForId(id)

    override fun exists(id: ID): Boolean = dao!!.idExists(id)

    override fun exists(criteria: Map<String, Any>): Boolean = false

    override fun delete(id: ID) {
        if (dao!!.deleteById(id) == 0) {
            throw TechnicalException(DELETE_ERROR_MESSAGE + id.toString())
        }

    }

    override fun deleteAll() {
        try {
            dao!!.deleteBuilder().delete()
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
    }


    override fun countAllRows(): Long? = dao!!.countOf()

    override fun findAllByCriteriaNullOrNotNull(nameColumn: String, isNull: Boolean): List<DO> {

        val queryBuilder = dao!!.queryBuilder()

        try {
            if (isNull) {
                queryBuilder.where().isNull(nameColumn)
            } else {
                queryBuilder.where().isNotNull(nameColumn)
            }
            val preparedQuery = queryBuilder.prepare()
            return dao!!.query(preparedQuery)

        } catch (e: SQLException) {
            throw TechnicalException(e)
        }

    }

    override fun deleteAllByCriteria(nameColumn: String, value: Any) {
        try {
            val deleteBuilder: DeleteBuilder<DO, ID> = dao!!.deleteBuilder()
            deleteBuilder.where().eq(nameColumn, value)
            deleteBuilder.delete()
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
    }

    override fun deleteAllByCriteriaMultiple(nameColumn: String, value: Any, nameColumn2: String, value2: Any) {
        try {
            val deleteBuilder: DeleteBuilder<DO, ID> = dao!!.deleteBuilder()
            deleteBuilder.where().eq(nameColumn, value).and().eq(nameColumn2, value2)
            deleteBuilder.delete()
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
    }

    override fun deleteAllByCriteriaNullOrNot(nameColumn: String, isNull: Boolean) {
        try {
            val deleteBuilder: DeleteBuilder<DO, ID> = dao!!.deleteBuilder()
            if (isNull) {
                deleteBuilder.where().isNull(nameColumn)
            } else {
                deleteBuilder.where().isNotNull(nameColumn)
            }
            deleteBuilder.delete()

        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
    }

    @Throws(SQLException::class)
    override fun findAllByMultipleCriteria(listCriteria: List<Map<String, Any>>): ArrayList<DO> {
        val finalResult = ArrayList<DO>()
        for (criteria in listCriteria) {
            val result = findAllByCriteria(criteria)
            finalResult.addAll(result)
        }
        return finalResult
    }

    override fun deleteAllByMultipleCriteria(listCriteria: Map<String, Any>) {
        try {
            val deleteBuilder: DeleteBuilder<DO, ID> = dao!!.deleteBuilder()

            listCriteria.forEach { criteria ->
                deleteBuilder.where().eq(criteria.key, criteria.value)
            }

            deleteBuilder.delete()

        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
    }

    companion object {

        var databaseHelper: DatabaseHelper? = null

        val INSERT_ERROR_MESSAGE = "la valeur n'a pu être insérée"
        val UPDATE_ERROR_MESSAGE = "la valeur n'a pu être mise à jour"
        val DELETE_ERROR_MESSAGE = "la valeur n'a pu être supprimée"
    }
}






