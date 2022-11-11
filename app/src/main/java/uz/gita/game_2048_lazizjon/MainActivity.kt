package uz.gita.game_2048_lazizjon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import uz.gita.game_2048_lazizjon.mvp.views.PlayView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

            val playFragment = PlayView()
            openScreenAddStack(playFragment)

    }


     fun openScreenAddStack(comingFragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerView, comingFragment)
            .addToBackStack(comingFragment.javaClass.name)
            .commit()
    }

     fun openScreen(comingFragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerView, comingFragment)
            .commit()
    }

    fun closeScreen() {
        supportFragmentManager.popBackStack()
    }
}