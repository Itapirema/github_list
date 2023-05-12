package br.com.challenge.github.ui.main

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import br.com.challenge.github.R
import br.com.challenge.github.data.dto.UserDTO
import br.com.challenge.github.databinding.FragmentMainBinding
import br.com.challenge.github.ui.MainViewModel
import com.google.android.material.snackbar.Snackbar
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
        setupMenu()

        binding.rvUserList.adapter = userListAdapter

        viewModel.userList.observe(viewLifecycleOwner) {
            userListAdapter.setData(it)
        }

        viewModel.showProgress.observe(viewLifecycleOwner) {
            binding.progressCircular.isVisible = it
        }

        viewModel.loadUsers() { error ->
            showError(error)
        }
    }

    override fun onItemClick(user: UserDTO) {
        viewModel.setSelectedUser(user)
        navigateToRepositoryFragment()
    }

    private fun navigateToRepositoryFragment() {
        val direction = MainFragmentDirections.actionMainFragmentToRepositoriesFragment()
        findNavController().navigate(direction)
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {}

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_main, menu)
                setupSearchView(menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem) = true
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupSearchView(menu: Menu) {
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    userListAdapter.filter.filter(newText)
                    return false
                }
            })
        }
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}