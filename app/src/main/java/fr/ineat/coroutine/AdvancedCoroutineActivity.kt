package fr.ineat.coroutine

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import kotlinx.android.synthetic.main.activity_coroutine.*
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.cancelChildren
import kotlinx.coroutines.experimental.launch

class AdvancedCoroutineActivity : AppCompatActivity() {

    private val job: Job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)

        button.setOnClickListener {
            loadData()
        }
    }

    private fun loadData() {
        job.cancelChildren()
        progressBar.visibility = View.VISIBLE
        launch(UI, parent = job) {
            progressBar.visibility = View.VISIBLE
            label.text = ""

            val text = try {
                val repos = githubService.listRepos("mslimani")
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
        job.cancelChildren()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }


}