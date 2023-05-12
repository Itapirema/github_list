package br.com.challenge.github.ui.repos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.challenge.github.data.dto.RepositoryDTO
import br.com.challenge.github.databinding.LayoutRepositoryListItemBinding

class RepositoryListAdapter(
    private val itemClickListener: ItemClickListener? = null
): ListAdapter<RepositoryDTO, RepositoryListAdapter.RepositoryViewHolder>(RepositoryDiffCallback) {

    interface ItemClickListener {
        fun onItemClick(repos: RepositoryDTO)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RepositoryViewHolder(LayoutRepositoryListItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(getItem(position), itemClickListener)
    }

    class RepositoryViewHolder(private val binding: LayoutRepositoryListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(repos: RepositoryDTO, itemClickListener: ItemClickListener?) {
            binding.apply {
                txtFullName.text = repos.full_name
                txtLanguage.text = repos.language
                txtLicense.text = repos.license?.name
                txtDescription.text = repos.description
                root.setOnClickListener{
                    itemClickListener?.onItemClick(repos)
                }
            }
        }
    }

    object RepositoryDiffCallback: DiffUtil.ItemCallback<RepositoryDTO>() {
        override fun areItemsTheSame(oldItem: RepositoryDTO, newItem: RepositoryDTO): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: RepositoryDTO, newItem: RepositoryDTO): Boolean {
            return oldItem.id == newItem.id
        }
    }
}