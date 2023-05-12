package br.com.challenge.github.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.challenge.github.data.dto.UserDTO
import br.com.challenge.github.databinding.LayoutUserListItemBinding
import com.bumptech.glide.Glide

class UserListAdapter(
    private val itemClickListener: ItemClickListener? = null
) : ListAdapter<UserDTO, UserListAdapter.UserViewHolder>(UserDiffCallback), Filterable {

    private var baseList = mutableListOf<UserDTO>()

    interface ItemClickListener {
        fun onItemClick(user: UserDTO)
    }

    fun setData(baseList: List<UserDTO>) {
        this.baseList.addAll(baseList)
        submitList(baseList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UserViewHolder(LayoutUserListItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position), itemClickListener)
    }

    class UserViewHolder(private val binding: LayoutUserListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserDTO, itemClickListener: ItemClickListener?) {
            binding.apply {
                txtUserName.text = user.login
                user.avatarUrl?.let {
                    Glide.with(root.context).load(it).centerCrop().into(imgUser)
                }
                root.setOnClickListener {
                    itemClickListener?.onItemClick(user)
                }
            }
        }
    }

    object UserDiffCallback : DiffUtil.ItemCallback<UserDTO>() {
        override fun areItemsTheSame(oldItem: UserDTO, newItem: UserDTO): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: UserDTO, newItem: UserDTO): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<UserDTO>()

                if (constraint == null || constraint.isEmpty()) {
                    filteredList.addAll(baseList)
                } else {
                    for (item in baseList) {
                        if (item.login?.lowercase()
                                ?.startsWith(constraint.toString().lowercase()) == true
                        ) {
                            filteredList.add(item)
                        }
                    }
                }

                val results = FilterResults()
                results.values = filteredList

                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                submitList(results?.values as MutableList<UserDTO>)
            }
        }
    }
}