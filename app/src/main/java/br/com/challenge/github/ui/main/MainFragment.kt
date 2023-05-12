package br.com.challenge.github.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import br.com.challenge.github.data.dto.UserDTO
import br.com.challenge.github.databinding.FragmentMainBinding
import br.com.challenge.github.ui.MainViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class MainFragment : Fragment(), UserListAdapter.ItemClickListener {

    private lateinit var binding: FragmentMainBinding
    private val userListAdapter by lazy {
        UserListAdapter(this)
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

        viewModel.showProgress.observe(viewLifecycleOwner) {
            binding.progressCircular.isVisible = it
        }

        viewModel.loadUsers()
    }

    override fun onItemClick(user: UserDTO) {
        viewModel.setSelectedUser(user)
        navigateToRepositoryFragment()
    }

    private fun navigateToRepositoryFragment() {
        val direction = MainFragmentDirections.actionMainFragmentToRepositoriesFragment()
        findNavController().navigate(direction)
    }
}