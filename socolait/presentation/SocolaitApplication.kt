package mg.socolait.presentation

import android.support.multidex.MultiDexApplication
import mg.socolait.R
import mg.socolait.presentation.view.font.navigation.FontOverride
import org.androidannotations.annotations.EApplication

/**
 * Created by mampita_andria on 20/11/2017.
 */
@EApplication
open class SocolaitApplication: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        FontOverride.setDefaultFont(this, "MONOSPACE", getString(R.string.light))
    }
}