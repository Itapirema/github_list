package br.com.challenge.github.ui.repos

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import br.com.challenge.github.R
import br.com.challenge.github.data.dto.UserDTO
import br.com.challenge.github.databinding.FragmentRepositoryBinding
import br.com.challenge.github.ui.MainViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class RepositoriesFragment : Fragment() {
    private val viewModel by activityViewModel<MainViewModel>()
    private lateinit var binding: FragmentRepositoryBinding
    private val userListAdapter by lazy {
        RepositoryListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepositoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.rvRepositoryList.adapter = userListAdapter

        viewModel.user.observe(viewLifecycleOwner) { user ->
            user?.let{
                bindUserInfo(it)
            }
        }

        viewModel.repositoryList.observe(viewLifecycleOwner) {
            userListAdapter.submitList(it)
        }

        viewModel.showProgress.observe(viewLifecycleOwner) {
            binding.progressCircular.isVisible = it
        }

        viewModel.selectedUser.observe(viewLifecycleOwner) { user ->
            user?.login?.let {
                viewModel.loadUserByName(it) { error ->
                    showError(error)
                }
                viewModel.loadUserRepositories(it) { error ->
                    showError(error)
                }
            }
        }
    }

    private fun bindUserInfo(user: UserDTO) {
        binding.apply {
            Glide.with(requireContext()).load(user.avatarUrl).into(imgUser)
            txtName.text = user.name
            txtUserName.text = user.login
            txtCompany.text = getString(R.string.company_label, user.company)
            txtLocation.text = getString(R.string.location_label, user.location)
        }
    }

    private fun clearUserInfo() {
        binding.apply {
            imgUser.visibility = View.INVISIBLE
            txtName.clear()
            txtUserName.clear()
            txtCompany.clear()
            txtLocation.clear()
        }
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearRepositoryList()
        viewModel.clearUser()
        clearUserInfo()
    }

    private fun TextView.clear() {
        text = ""
    }
}