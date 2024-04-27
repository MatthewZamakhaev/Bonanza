package sweety.pack.dream.game

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import sweety.pack.dream.databinding.ActivityLeaderBinding

class LeaderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLeaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.homeButton1.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}