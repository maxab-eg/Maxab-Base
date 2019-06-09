package com.itsmart.helpers

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.squareup.picasso.Picasso


object ImageDownloader {

    /**
     * This method is used to download image from server.
     * called when get images from server.
     *
     * @param imageUrl    the url of image
     * @param placeHolder for shown until get image
     * @param imageView   to display it
     */
    fun downloadImage(imageUrl: String?, placeHolder: Int, imageView: ImageView) {

        if (imageUrl != null) {
            try {
                if (placeHolder != -1)
                    Picasso.get().load(imageUrl)
                        .placeholder(placeHolder)
                        .into(imageView)
                else
                    Picasso.get().load(imageUrl)
                        .into(imageView)
            } catch (e: Exception) {
                if (placeHolder != -1)
                    imageView.setImageResource(placeHolder)
            } catch (error: Error) {
                if (placeHolder != -1)
                    imageView.setImageResource(placeHolder)
            }

        } else
            imageView.setImageResource(placeHolder)

    }


    /**
     * This method is used to download image from server.
     * called when get images from server.
     *
     * @param imageUrl    the url of image
     * @param placeHolder for shown until get image
     * @param imageView   to display it
     */
    fun downloadImage(imageUrl: String?, placeHolder: Drawable, imageView: ImageView) {

        if (imageUrl != null) {
            try {
                Picasso.get().load(imageUrl)
                    .placeholder(placeHolder)
                    .into(imageView)
            } catch (e: Exception) {
                imageView.setImageDrawable(placeHolder)
            } catch (error: Error) {
                imageView.setImageDrawable(placeHolder)
            }

        } else
            imageView.setImageDrawable(placeHolder)

    }


}
