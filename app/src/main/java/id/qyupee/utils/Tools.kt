package id.qyupee.utils

import android.view.View
import android.widget.ImageView
import com.github.ybq.android.spinkit.SpinKitView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import id.qyupee.R

//
// Created by Fathur Radhy
// on May 2019-05-26.
//
object Tools {

    fun displayImageFromUrl(img: ImageView, spinKit: SpinKitView?, url: String, errorImage: Int = R.drawable.image_error) {
        if (url == "") {
            img.setImageResource(errorImage)
            spinKit?.visibility = View.GONE
        } else {
            try {
                spinKit?.visibility = View.VISIBLE
                Picasso.get()
                    .load(url)
                    .resize(1000, 500)
                    .onlyScaleDown()
                    .centerInside()
                    .into(img, object : Callback {
                        override fun onSuccess() {
                            if (spinKit != null) spinKit.visibility = View.GONE
                        }

                        override fun onError(e: java.lang.Exception?) {
                            spinKit?.visibility = View.GONE
                            img.setImageResource(errorImage)
                        }
                    })
            } catch (e: Exception) {
            }

        }

    }

}