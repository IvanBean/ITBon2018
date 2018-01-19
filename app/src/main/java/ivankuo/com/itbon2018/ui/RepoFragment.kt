package ivankuo.com.itbon2018.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager

import java.util.ArrayList

import javax.inject.Inject

import ivankuo.com.itbon2018.data.model.Repo
import ivankuo.com.itbon2018.data.model.Resource
import ivankuo.com.itbon2018.databinding.RepoFragmentBinding
import ivankuo.com.itbon2018.di.Injectable

class RepoFragment : Fragment(), Injectable {

    companion object {

        val TAG = "Repo"

        fun newInstance(): RepoFragment {
            return RepoFragment()
        }
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit  var binding: RepoFragmentBinding

    private lateinit  var viewModel: RepoViewModel

    private val repoAdapter = RepoAdapter(ArrayList())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = RepoFragmentBinding.inflate(inflater, container, false)

        binding.edtQuery.setOnKeyListener(View.OnKeyListener { view, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                doSearch()
                return@OnKeyListener true
            }
            false
        })

        binding.btnSearch.setOnClickListener { doSearch() }

        binding.recyclerView.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = repoAdapter

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(RepoViewModel::class.java)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
        viewModel.repos.observe(this, Observer { resource -> repoAdapter.swapItems(resource!!.data) })
    }

    private fun doSearch() {
        val query = binding.edtQuery.text.toString()
        viewModel.searchRepo(query)
        dismissKeyboard()
    }

    private fun dismissKeyboard() {
        val view = activity!!.currentFocus
        if (view != null) {
            val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}