package com.example.myplaylist.helpers

import android.animation.ObjectAnimator
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.TextUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myplaylist.R

/**
 * Created by Kailash Suthar on 04/9/2020.
 */

class HelperMethod {

    companion object {

        /**
         * Check internet connection is available is not
         */
        fun networkConnected(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val nw = connectivityManager.activeNetwork ?: return false
                val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
                return when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    //for other device how are able to connect with Ethernet
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    //for check internet over Bluetooth
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                    else -> false
                }
            } else {
                val nwInfo = connectivityManager.activeNetworkInfo ?: return false
                return nwInfo.isConnected
            }
        }

        /**
         * Load image from image url
         */
        fun ImageView.loadImage(imageUrl: String?) {
            Glide.with(context)
                .load(imageUrl).apply(RequestOptions().fitCenter())
                //.placeholder(ContextCompat.getDrawable(view.context, R.drawable.placeholder))
                .error(ContextCompat.getDrawable(context, R.drawable.placeholder))
                .into(this)
        }

        /**
         * Convert youtube duration in required format
         */
        fun getDuration(timestamp: String): String {
            val time = timestamp.replace("PT", "")
                .replace("H", ":")
                .replace("M", ":")
                .replace("S", "")
            val duration = StringBuffer()
            val components = time.split(":")
            for (component in components) {
                if (duration.isEmpty()) {
                    duration.append(if (component.length < 2) "0$component" else component)
                } else {
                    duration.append(":")
                    duration.append(if (component.length < 2) "0$component" else component)
                }
            }
            return duration.toString()
        }

        /**
         * Handle TextView expend and collapse
         */
        fun TextView.expandText() {
            val MAX_LINE_COUNT = 4;
            this.setOnClickListener {
                if (this.maxLines == MAX_LINE_COUNT) {
                    // collapsed - expand it
                    this.ellipsize = null
                    this.maxLines = Int.MAX_VALUE
                } else {
                    // expanded - collapse it
                    this.ellipsize = TextUtils.TruncateAt.END
                    this.maxLines = MAX_LINE_COUNT
                }
                val animation =
                    ObjectAnimator.ofInt(this, "maxLines", this.maxLines)
                animation.setDuration(200).start()
            }
        }
    }
}