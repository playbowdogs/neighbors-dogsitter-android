package com.playbowdogs.neighbors_dogsitter_android.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.playbowdogs.neighbors_dogsitter_android.R
import com.playbowdogs.neighbors_dogsitter_android.data.model.ChosenClip
import com.playbowdogs.neighbors_dogsitter_android.data.model.RecordedClipsResponse
import com.playbowdogs.neighbors_dogsitter_android.utils.GlideApp
import kotlinx.android.synthetic.main.recycler_view_recorded_clips.view.*


class RecordedClipsListAdapter(private val results: ArrayList<RecordedClipsResponse.Result>
) : RecyclerView.Adapter<RecordedClipsListAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            result: RecordedClipsResponse.Result) {
            itemView.apply {
                name.text = result.name
                status.text = result.status
                start.text = result.start
                end.text = result.end
                created_at.text = result.createdAt

                GlideApp.with(itemView.context)
                    .load(R.drawable.ic_movie)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView)

                setOnClickListener {
                    ChosenClip(result)
                    it.findNavController().navigate(R.id.action_recordedClipsListFragment_to_recordedClipDetailFragment)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_recorded_clips, parent, false))

    override fun getItemCount(): Int = results.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(results[position])
    }

    fun addClips(results: List<RecordedClipsResponse.Result>) {
        this.results.apply {
            clear()
            addAll(results)
        }
    }
}