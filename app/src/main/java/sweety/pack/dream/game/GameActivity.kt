package sweety.pack.dream.game

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.GridView
import sweety.pack.dream.GridViewAdapter
import sweety.pack.dream.R
import sweety.pack.dream.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding

    private lateinit var mGrid: GridView
    private lateinit var mAdapter: GridViewAdapter

    private lateinit var sharedPreferences: SharedPreferences

    private var sizeGrid: Int = 0
    private var sizeGrid2: Int = 0

    private var gameProgress: Int = 0
    private var coins: Int = 0
    private var lives: Int = 0

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mGrid = binding.field
        mGrid.isEnabled = true

        setupView()

        binding.homeImage.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }

        sharedPreferences = getSharedPreferences("level", Context.MODE_PRIVATE)
        gameProgress = sharedPreferences.getInt("level_now", 1)
        binding.level.text = "Level: $gameProgress"
        coins = sharedPreferences.getInt("coins_now", 100)
        binding.textMoney.text = coins.toString()
        lives = sharedPreferences.getInt("lives_now", 10)
        binding.textLives.text = lives.toString()

        when (gameProgress) {
            1 -> {
                sizeGrid = 2
                sizeGrid2 = 1
            }
            2 -> {
                sizeGrid = 2
                sizeGrid2 = 2
            }
            3 -> {
                sizeGrid = 2
                sizeGrid2 = 3
            }
            4 -> {
                sizeGrid = 2
                sizeGrid2 = 4
            }
            5 -> {
                sizeGrid = 2
                sizeGrid2 = 5
            }
            6 -> {
                sizeGrid = 2
                sizeGrid2 = 6
            }
            7 -> {
                sizeGrid = 2
                sizeGrid2 = 7
            }
            8 -> {
                sizeGrid = 2
                sizeGrid2 = 8
            }
            9 -> {
                sizeGrid = 2
                sizeGrid2 = 9
            }
            10 -> {
                sizeGrid = 3
                sizeGrid2 = 2
            }
            11 -> {
                sizeGrid = 3
                sizeGrid2 = 4
            }
            12 -> {
                sizeGrid = 3
                sizeGrid2 = 6
            }
            13 -> {
                sizeGrid = 4
                sizeGrid2 = 1
            }
            14 -> {
                sizeGrid = 4
                sizeGrid2 = 2
            }
            15 -> {
                sizeGrid = 4
                sizeGrid2 = 3
            }
            16 -> {
                sizeGrid = 4
                sizeGrid2 = 4
            }
            17 -> {
                sizeGrid = 5
                sizeGrid2 = 2
            }
            18 -> {
                sizeGrid = 6
                sizeGrid2 = 1
            }
            19 -> {
                sizeGrid = 6
                sizeGrid2 = 2
            }
            20 -> {
                sizeGrid = 6
                sizeGrid2 = 3
            }
            21 -> {
                sizeGrid = 7
                sizeGrid2 = 2
            }
            22 -> {
                sizeGrid = 8
                sizeGrid2 = 1
            }
        }

        mAdapter = GridViewAdapter(this, sizeGrid, sizeGrid2)
        mGrid.adapter = mAdapter

        mGrid.setOnItemClickListener { _, _, position, _ ->
            mAdapter.checkOpenCells()
            if (mAdapter.openCell(position)) {
                lives -= 1
                binding.textLives.text = lives.toString()
                if (lives <= 0) {
                    binding.field.visibility = View.INVISIBLE
                    binding.level.visibility = View.INVISIBLE
                    lives = 10
                    sharedPreferences.edit().putInt("lives_now", lives).apply()
                    binding.count.text = "Attempts are over"
                    coins -= 5
                    sharedPreferences.edit().putInt("coins_now", coins).apply()
                    binding.bonus.setImageResource(R.drawable.button_try)
                    binding.imageBonus.setImageResource(R.drawable.balls)
                    binding.imageBonus.visibility = View.VISIBLE
                    binding.count.visibility = View.VISIBLE
                    binding.bonus.visibility = View.VISIBLE

                    binding.bonus.setOnClickListener {
                        recreate()
                    }
                }
            }

            if (mAdapter.checkGameOver()) {
                binding.field.visibility = View.INVISIBLE
                binding.level.visibility = View.INVISIBLE
                if(gameProgress == 22) {
                    gameProgress = 1
                } else gameProgress++
                coins += 15
                lives += 4
                sharedPreferences.edit().putInt("coins_now", coins).apply()
                sharedPreferences.edit().putInt("level_now", gameProgress).apply()
                sharedPreferences.edit().putInt("lives_now", lives).apply()
                binding.count.text = "+4"
                binding.bonus.setImageResource(R.drawable.button_next)
                binding.imageBonus.setImageResource(R.drawable.berry)
                binding.imageBonus.visibility = View.VISIBLE
                binding.count.visibility = View.VISIBLE
                binding.bonus.visibility = View.VISIBLE

                binding.bonus.setOnClickListener {
                    recreate()
                }
            }
        }
    }

    private fun setupView(){
        binding.field.visibility = View.VISIBLE
        binding.level.visibility = View.VISIBLE
        binding.imageBonus.visibility = View.INVISIBLE
        binding.count.visibility = View.INVISIBLE
        binding.bonus.visibility = View.INVISIBLE
    }
}