package fr.ineat.coroutine

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        simpleButton.setOnClickListener {
            startActivity(Intent(this, SimpleCoroutineActivity::class.java))
        }

        advancedButton.setOnClickListener {
            startActivity(Intent(this, AdvancedCoroutineActivity::class.java))
        }

        actorButton.setOnClickListener {
            startActivity(Intent(this, ActorActivity::class.java))
        }
    }
}
