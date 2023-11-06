package com.example.timer

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import android.widget.NumberPicker
import com.example.timer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    var isRunning = false
    private var minutes:String? = "00.00.00"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.imgClock.setOnClickListener {
            var dialog = Dialog(this)

            dialog.setContentView(R.layout.dialog)

            var numberPicker = dialog.findViewById<NumberPicker>(R.id.numberPicker)

            numberPicker.minValue=0
            numberPicker.maxValue=5
            dialog.findViewById<Button>(R.id.setTime).setOnClickListener {
                minutes = numberPicker.value.toString()
                binding.clockTime.text = dialog.findViewById<NumberPicker>(R.id.numberPicker).value.toString() + " Mins"
                dialog.dismiss()
            }
            dialog.show()
        }

        binding.btnStart.setOnClickListener {
            if (isRunning){
                isRunning = false
                if (!minutes.equals("00.00.00")){
                    var totalMin = minutes!!.toInt()*60*1000L
                    var countDown = 1000L
                    binding.timerChrono.base = SystemClock.elapsedRealtime()+totalMin
                    binding.timerChrono.format = "%s %s"
                    binding.timerChrono.onChronometerTickListener = Chronometer.OnChronometerTickListener {
                        var elapsedTime = SystemClock.elapsedRealtime() - binding.timerChrono.base
                        if (elapsedTime >= totalMin){
                            binding.timerChrono.stop()

                            isRunning = false

                            binding.btnStart.text = "Run"
                        }

                    }

                }else{
                    isRunning = true
                    binding.timerChrono.base = SystemClock.elapsedRealtime()
                    binding.btnStart.text = "Stop"
                    binding.timerChrono.start()

                }
            }else{
                isRunning = true
                binding.timerChrono.base = SystemClock.elapsedRealtime()
                binding.btnStart.text = "Run"

            }

        }
    }
}