package sweety.pack.dream.game

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import sweety.pack.dream.R
import sweety.pack.dream.databinding.ActivityBonusBinding
import java.util.*

class BonusActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBonusBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferences1: SharedPreferences
    private var lastAttemptTime: Long = 0

    private var oldGrad = 0
    private var fullGrad = 0

    private var lives: Int = 0

    private val numbers = arrayOf(
        "5", "7", "3", "5", "10", "2", "1", "15")

    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBonusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("bonus_prefs", Context.MODE_PRIVATE)
        sharedPreferences1 = getSharedPreferences("level", Context.MODE_PRIVATE)
        lastAttemptTime = sharedPreferences.getLong("last_attempt_time", 0)
        lives = sharedPreferences.getInt("lives_now", 10)

        checkMillis()

        binding.homeImage.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.bonus.setOnClickListener {
            if (binding.checker.visibility == View.VISIBLE) {
                if (System.currentTimeMillis() - lastAttemptTime >= 24 * 60 * 60 * 1000) {
                    startRotation()
                } else {
                    binding.circle.setImageResource(R.drawable.balls)
                    binding.checker.visibility = View.INVISIBLE
                    binding.timer.visibility = View.INVISIBLE
                    binding.bonus.setImageResource(R.drawable.button_ok)
                    val remainingTimeMillis = (lastAttemptTime + 24 * 60 * 60 * 1000) - System.currentTimeMillis()
                    binding.textView.text = "Time to next bonus: " + formatTime(remainingTimeMillis)
                }
            } else {
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun checkMillis(){
        if (System.currentTimeMillis() - lastAttemptTime < 24 * 60 * 60 * 1000) {
            binding.circle.setImageResource(R.drawable.balls)
            binding.checker.visibility = View.INVISIBLE
            binding.timer.visibility = View.INVISIBLE
            binding.bonus.setImageResource(R.drawable.button_ok)
            val remainingTimeMillis = (lastAttemptTime + 24 * 60 * 60 * 1000) - System.currentTimeMillis()
            binding.textView.text = "Time to next bonus: " + formatTime(remainingTimeMillis)
        }
    }

    private fun startRotation() {
        oldGrad = fullGrad % 360
        fullGrad = Random().nextInt(3600) + 720

        val rotate = RotateAnimation(
            oldGrad.toFloat(), fullGrad.toFloat(),
            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
            RotateAnimation.RELATIVE_TO_SELF, 0.5f
        )
        rotate.duration = 3600
        rotate.fillAfter = true
        rotate.interpolator = DecelerateInterpolator()
        rotate.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                binding.textView.text = ""
            }

            override fun onAnimationEnd(animation: Animation) {
                val result = determinePrize(360 - (fullGrad % 360))
                lives += result.toInt()
                binding.timer.text = "+$result"
                binding.timer.visibility = View.VISIBLE
                Log.d("TAGRESULT", result)
                binding.circle.setImageResource(R.drawable.berry)
                binding.circle.rotation = (-(fullGrad % 360)).toFloat()
                binding.checker.visibility = View.INVISIBLE
                binding.textView.text = "Congratulations!"
                binding.bonus.setImageResource(R.drawable.button_ok)
                Handler().postDelayed({
                    binding.circle.setImageResource(R.drawable.balls)
                    binding.timer.visibility = View.INVISIBLE
                    lastAttemptTime = System.currentTimeMillis()
                    sharedPreferences.edit().putLong("last_attempt_time", lastAttemptTime).apply()
                    sharedPreferences1.edit().putInt("lives_now", lives).apply()
                    startCountdownTimer()
                }, 3000)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        binding.circle.startAnimation(rotate)
    }

    private fun determinePrize(gradient: Int): String {
        val sectorSize = 360 / numbers.size
        val sectorIndex = gradient / sectorSize
        return numbers[sectorIndex]
    }

    private fun startCountdownTimer(durationMillis: Long = 24 * 60 * 60 * 1000) {
        countDownTimer?.cancel()

        countDownTimer = object : CountDownTimer(durationMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.textView.text = "Time to next bonus: " + formatTime(millisUntilFinished)
            }

            override fun onFinish() {
            }
        }
        countDownTimer?.start()
    }

    private fun formatTime(millis: Long): String {
        val hours = millis / (1000 * 60 * 60)
        val minutes = millis % (1000 * 60 * 60) / (1000 * 60)
        return String.format("%02d:%02d", hours, minutes)
    }

    override fun onDestroy() {
        countDownTimer?.cancel()
        super.onDestroy()
    }
}