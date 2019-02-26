package com.greenhacker.greenhackeronlinetraining.activitys

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.greenhacker.greenhackeronlinetraining.R

class SlashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slash_screen)
        val td = Thread(Runnable {
            try {
                /*firstText();*/
                //secondText();
                Thread.sleep(1700)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } finally {
                handleLoginClick()
                finish()
            }
        })
        td.start()

    }

    /*
    private void firstText() {
        TypeWriter tw = (TypeWriter) findViewById(R.id.tv);
        tw.setText("");
        tw.setCharacterDelay(90);
        tw.animateText("Green Hacker...");
    }*/
    private fun handleLoginClick() {
        val i = Intent()
        i.setClass(this, MainActivity::class.java!!)
        startActivity(i)
        overridePendingTransition(R.anim.entry_anim, R.anim.exit_anim)
    }
}
