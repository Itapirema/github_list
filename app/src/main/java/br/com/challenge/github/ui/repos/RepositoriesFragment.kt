package br.com.challenge.github.ui.repos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import br.com.challenge.github.data.dto.RepositoryDTO
import br.com.challenge.github.databinding.FragmentRepositoryBinding
import br.com.challenge.github.ui.MainViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class RepositoriesFragment : Fragment(), RepositoryListAdapter.ItemClickListener {

    private lateinit var binding: FragmentRepositoryBinding
    private val userListAdapter by lazy {
        RepositoryListAdapter(this)
    }

    companion object {
        fun newInstance() = RepositoriesFragment()
    }

    private val viewModel by activityViewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepositoryBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.rvRepositoryList.adapter = userListAdapter

        viewModel.repositoryList.observe(viewLifecycleOwner) {
            userListAdapter.submitList(it)
        }

        viewModel.showProgress.observe(viewLifecycleOwner) {
            binding.progressCircular.isVisible = it
        }

        viewModel.selectedUser.observe(viewLifecycleOwner) { user ->
            user.login?.let {
                viewModel.loadUserRepositories(it)
            }
        }
    }

    override fun onItemClick(repos: RepositoryDTO) {
        Toast.makeText(requireContext(), "Name: ${repos.full_name}", Toast.LENGTH_SHORT).show()
    }
}