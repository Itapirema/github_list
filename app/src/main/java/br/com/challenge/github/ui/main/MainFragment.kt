package br.com.challenge.github.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.add
import br.com.challenge.github.R
import br.com.challenge.github.data.dto.UserDTO
import br.com.challenge.github.databinding.FragmentMainBinding
import br.com.challenge.github.ui.MainViewModel
import br.com.challenge.github.ui.repos.RepositoriesFragment
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class MainFragment : Fragment(), UserListAdapter.ItemClickListener {

    private lateinit var binding: FragmentMainBinding
    private val userListAdapter by lazy {
        UserListAdapter(this)
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel by activityViewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvUserList.adapter = userListAdapter

        viewModel.userList.observe(viewLifecycleOwner) {
            userListAdapter.submitList(it)
        }

        viewModel.loadUsers()
    }

    override fun onItemClick(user: UserDTO) {
        viewModel.setSelectedUser(user)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, RepositoriesFragment.newInstance())
            .commitNow()
    }
}