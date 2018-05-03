package fr.ineat.coroutine

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_coroutine.*
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.channels.*
import java.util.concurrent.TimeUnit

class ActorActivity : AppCompatActivity() {

    private val actor = actor<String>(context = UI){
        // when message sending
        for (value in channel) {
            Log.d("Actor", "ici")
            label.text = ""
            progressBar.visibility = View.VISIBLE
            delay(2, TimeUnit.SECONDS)
            val text = try {
                val repos = githubService.listRepos("ineat")
                        .await()
                        .map { it.name }
                TextUtils.join("\n", repos)
            } catch (e: Exception) {
                "Une erreur est survenue"
            }

            progressBar.visibility = View.GONE
            label.text = text
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)

        button.setOnClickListener {
            actor.offer("ineat")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        actor.close()
    }


}
