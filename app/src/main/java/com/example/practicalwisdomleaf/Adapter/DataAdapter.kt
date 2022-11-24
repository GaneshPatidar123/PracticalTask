package com.example.practicalwisdomleaf.Adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practicalwisdomleaf.MainActivity
import com.example.practicalwisdomleaf.R
import com.example.practicalwisdomleaf.databinding.DialogdetailsBinding
import com.example.practicalwisdomleaf.model.ResponseData


class DataAdapter(private val mList: List<ResponseData>,private val contact:Context) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]
        // Downlaod Image With Permisson
        if (contact is MainActivity) {
            (contact as MainActivity).checkPermissions(ItemsViewModel.download_url,ItemsViewModel.id)
        }
        // Set All Data in List
        holder.tvTitle.text = ItemsViewModel.author
        val layoutParams = FrameLayout.LayoutParams(ItemsViewModel.width /10, ItemsViewModel.height/10)
        holder.imageView.setLayoutParams(layoutParams)
        Glide.with(contact)
            .load(ItemsViewModel.download_url)
            .into(holder.imageView)
     // Dialog Show in click of Image View
        holder.imageView.setOnClickListener {
               showDailog(contact,ItemsViewModel.author)
        }
    }

    private fun showDailog(contact: Context, author: String) {
        val dialog = Dialog(contact)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        lateinit var mBinding: DialogdetailsBinding
        mBinding = DataBindingUtil.inflate(
            LayoutInflater.from(contact),
            R.layout.dialogdetails,
            null,
            false
        )
        dialog.setContentView(mBinding.root)
        val window = dialog.window
        val wlp = window?.attributes
        wlp?.gravity = Gravity.CENTER
        window?.attributes = wlp
        dialog.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        dialog.setCancelable(true)
        if (!dialog.isShowing)
            dialog.show()
        mBinding.tvTitle.visibility=View.VISIBLE
        mBinding.tvTitleMsg.text = author

        mBinding.btnok.setOnClickListener {
            dialog.dismiss()

        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.ivImage)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
    }


}