package sweety.pack.dream

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import sweety.pack.dream.databinding.ActivityMainBinding
import sweety.pack.dream.game.MenuActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var toHundred = 0
    private var handler = Handler()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Thread {
            while (toHundred < 100) {

                handler.post {
                    binding.progressive.progress = toHundred
                    binding.percent.text = "$toHundred%"
                }

                toHundred += 1

                Thread.sleep(50)
            }
        }.start()

        Handler().postDelayed({
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }, 5000)
    }
}