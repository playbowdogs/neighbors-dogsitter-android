package com.playbowdogs.neighbors_dogsitter_android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.playbowdogs.neighbors_dogsitter_android.R
import com.playbowdogs.neighbors_dogsitter_android.data.model.AccountCamerasModel
import com.playbowdogs.neighbors_dogsitter_android.ui.SharedViewModel
import com.playbowdogs.neighbors_dogsitter_android.utils.GlideApp
import kotlinx.android.synthetic.main.recycler_view_cameras.view.*

class MainAdapter(
    private val results: ArrayList<AccountCamerasModel.Result>,
    private val sharedViewModel: SharedViewModel
) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            result: AccountCamerasModel.Result) {
            itemView.apply {
                transitionName = result.snapshot.url
                recycler_view_cameras_text.text = result.name
                recycler_view_status_text.text = result.status
                recycler_view_card_view.setOnClickListener {
                    val extras = FragmentNavigatorExtras(
                        recycler_view_cameras_image_view to "cameraImage")
                    it.findNavController().navigate(R.id.action_mainFragment_to_cameraDetails, null, null, extras)
                }
                GlideApp.with(itemView.context)
                    .load(result.snapshot.url)
                    .transform(RoundedCorners(8))
                    .into(recycler_view_cameras_image_view)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_cameras, parent, false))

    override fun getItemCount(): Int = results.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(results[position])
        sharedViewModel.chosenResult.value = results[position]
    }

    fun addUsers(results: List<AccountCamerasModel.Result>) {
        this.results.apply {
            clear()
            addAll(results)
        }
    }
}