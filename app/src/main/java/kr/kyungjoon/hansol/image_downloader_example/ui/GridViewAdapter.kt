package kr.kyungjoon.hansol.image_downloader_example.ui

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import kr.kyungjoon.hansol.image_downloader_example.R

class GridViewAdapter(private val view: MainView, private val layoutResourceId: Int, private val listCount : Int)
    : ArrayAdapter<String>(view.getContext(), layoutResourceId) {

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

        view.getImage(position,holder.image)

        row?.let {
            val button  = it.findViewById<ImageButton>(R.id.image_item)
            button.setOnClickListener {
                view.getDetailedImage(position)
            }
        }
        return row!!
    }

    override fun getCount(): Int {
        return listCount
    }

    internal class ViewHolder {
        var image: ImageView? = null
    }
}