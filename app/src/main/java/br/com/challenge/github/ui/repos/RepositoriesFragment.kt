package br.com.challenge.github.ui.repos

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import br.com.challenge.github.databinding.FragmentRepositoryBinding
import br.com.challenge.github.ui.MainViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class RepositoriesFragment : Fragment(){
    private val viewModel by activityViewModel<MainViewModel>()
    private lateinit var binding: FragmentRepositoryBinding
    private val userListAdapter by lazy {
        RepositoryListAdapter()
    }

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
}