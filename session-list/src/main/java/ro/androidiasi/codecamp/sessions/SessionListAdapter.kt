package ro.androidiasi.codecamp.sessions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.subjects.PublishSubject
import ro.androidiasi.codecamp.sessions.databinding.ItemSessionBinding

/**
 * Created by andrei.
 */
class SessionListAdapter(
        context: Context,
        private val onItemClick: (sessionId: String) -> Unit
) : RecyclerView.Adapter<SessionListAdapter.SessionItemViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    private val items: MutableList<SessionItemViewModel> = ArrayList()

    val favoriteSubject = PublishSubject.create<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionItemViewHolder {
        val binding = ItemSessionBinding.inflate(layoutInflater, parent, false)
        return SessionItemViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SessionItemViewHolder, position: Int) {
        val binding = holder.binding
        val item = items[position]
        binding.root.setOnClickListener {
            onItemClick(items[position].name)
        }
        binding.name.text = item.name
        binding.time.text = item.time
        binding.star.setOnCheckedChangeListener { buttonView, isChecked ->
            favoriteSubject.onNext(item.name)
        }
        binding.star.visibility = if (item.starLoading) View.INVISIBLE else View.VISIBLE
        binding.starLoading.visibility = if (item.starLoading) View.VISIBLE else View.GONE
    }

    fun update(items: List<SessionItemViewModel>) {
        this.items.clear()
        this.items.addAll(items)
        this.notifyDataSetChanged()
    }

    fun updateToggle(name: String) {
        var index = items.indexOfFirst { it.name == name }
        if (index != -1) {
            var viewModel = items[index].copy(
                    starLoading = false
            )
            items[index] = viewModel
            notifyItemChanged(index)
        }
    }

    fun updateToggleLoading(name: String, loading: Boolean) {
        var index = items.indexOfFirst { it.name == name }
        if (index != -1) {
            var viewModel = items[index].copy(
                    starLoading = loading
            )
            items[index] = viewModel
            notifyItemChanged(index)
        }
    }

    class SessionItemViewHolder(val binding: ItemSessionBinding) : RecyclerView.ViewHolder(binding.root)

}