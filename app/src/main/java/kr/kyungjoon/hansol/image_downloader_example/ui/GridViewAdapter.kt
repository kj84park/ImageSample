package kr.kyungjoon.hansol.image_downloader_example.ui

import android.app.Activity
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.kyungjoon.hansol.image_downloader_example.R
import kr.kyungjoon.hansol.image_downloader_example.cache.ImageCache

class GridViewAdapter(private val view: MainView, private val layoutResourceId: Int, private val cache: ImageCache, private val imageLinks : List<String>) : ArrayAdapter<String>(view.getContext(), layoutResourceId, imageLinks) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var row = convertView
        val holder: ViewHolder

        if (row == null) {
            val inflater = (view.getContext() as Activity).layoutInflater
            row = inflater.inflate(layoutResourceId, parent, false)
            holder = ViewHolder()
            holder.image = row.findViewById(R.id.image_item) as ImageView
            row.tag = holder
        } else {
            holder = row.tag as ViewHolder
        }

        val imageLink = imageLinks[position]
        val cachedBitmap = cache.getBitmap(imageLink)

        if (cachedBitmap == null) {
            view.getApiService(imageLink).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({ it ->
                it.let {
                    val bitmap = BitmapFactory.decodeByteArray(Base64.decode(it.toByteArray(), Base64.URL_SAFE), 0, Base64.decode(it.toByteArray(), Base64.URL_SAFE).size)
                    cache.addBitmap(imageLink, bitmap)
                    holder.image?.setImageBitmap(bitmap)
                }
            }, {})
        } else {
            holder.image?.setImageBitmap(cachedBitmap)
        }
        return row!!
    }

    internal class ViewHolder {
        var image: ImageView? = null
    }
}