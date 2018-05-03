package fr.ineat.coroutine

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import kotlinx.android.synthetic.main.activity_coroutine.*
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI

/**
 * Created by mehdisli on 03/05/2018.
 */

class SimpleCoroutineActivity : AppCompatActivity() {

    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)

        button.setOnClickListener {
            loadData()
        }
    }

    private fun loadData() {
        // Remove last job if exist
        job?.cancel()

        // start new job
        job = launch(UI) {
            progressBar.visibility = View.VISIBLE
            label.text = ""
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

    override fun onPause() {
        super.onPause()
        job?.cancel()
    }

}