package com.cursosant.insurance.policiesModule.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cursosant.insurance.R
import com.cursosant.insurance.databinding.ItemPoliciesLoadStateBinding

class PoliciesLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<PoliciesLoadStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPoliciesLoadStateBinding.inflate(layoutInflater, parent, false)
        return LoadStateViewHolder(binding, retry)
    }

    class LoadStateViewHolder(
        private val binding: ItemPoliciesLoadStateBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener { retry() }
        }

        fun bind(loadState: LoadState) {
            val isLoading = loadState is LoadState.Loading
            val isError = loadState is LoadState.Error

            binding.progressBar.isVisible = isLoading
            binding.btnRetry.isVisible = isError
            binding.tvErrorMessage.isVisible = isError

            if (isError) {
                val errorState = loadState as? LoadState.Error
                val errorMessage = errorState?.error?.localizedMessage
                    ?: binding.root.context.getString(R.string.policies_error)
                binding.tvErrorMessage.text = errorMessage
            }
        }
    }
}
