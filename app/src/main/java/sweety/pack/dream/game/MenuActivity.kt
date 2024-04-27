package sweety.pack.dream.game

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import sweety.pack.dream.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playImageButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

        binding.bonusImageButton.setOnClickListener {
            val intent = Intent(this, BonusActivity::class.java)
            startActivity(intent)
        }

        binding.leaderImageButton.setOnClickListener {
            val intent = Intent(this, LeaderActivity::class.java)
            startActivity(intent)
        }

        binding.infoImageButton.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        binding.settingsImageButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}
