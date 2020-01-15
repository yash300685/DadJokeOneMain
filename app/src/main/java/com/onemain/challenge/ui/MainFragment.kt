package com.onemain.challenge.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.onemain.challenge.R
import com.onemain.challenge.enums.Status
import com.onemain.challenge.models.JokesUIModel
import com.onemain.challenge.result.JokeResult
import com.onemain.challenge.viewmodel.MainViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module
import androidx.recyclerview.widget.DividerItemDecoration
import com.xwray.groupie.OnItemClickListener
import android.app.SearchManager
import android.content.Context
import androidx.appcompat.widget.SearchView


val fragmentModule= module { factory { MainFragment() } }
class MainFragment : Fragment() {
    private  val viewModel:MainViewModel by viewModel()
    private val SHARE_URL="https://icanhazdadjoke.com/j/"
    private var searchView: SearchView? = null
    private var queryTextListener: SearchView.OnQueryTextListener? = null


    private val adapter by lazy { GroupAdapter<GroupieViewHolder>() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        getAllJokes()
        jokeList.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        jokeList.adapter=adapter
        adapter.setOnItemClickListener(onItemClick)
        jokeList.layoutManager= LinearLayoutManager(activity)
        refresh.setOnRefreshListener {
            refreshJokes()
        }
    }


    private fun getAllJokes()
    {
        val liveData=viewModel.getAllJokes()

        liveData.observe(viewLifecycleOwner,
            Observer<JokeResult<Any>> { result ->
                when(result.status) {
                    Status.SUCCESS-> {
                        val jokes=(result.value as List<JokesUIModel>)
                        adapter.clear()
                        for( joke in jokes) {
                            adapter.add(JokeItem(joke.joke,joke.id))
                        }
                    }
                    Status.FAILURE-> {

                        Toast.makeText(activity, (result.value as String), Toast.LENGTH_LONG).show()
                    }


                }
            })
    }

    private fun refreshJokes()
    {
        val liveData=viewModel.refreshJokes()

        liveData.observe(viewLifecycleOwner,
            Observer<JokeResult<Any>> { result ->
                when(result.status) {
                    Status.SUCCESS-> {
                        progress.visibility=View.GONE
                        val jokes=(result.value as List<JokesUIModel>)
                        adapter.clear()
                        for( joke in jokes) {
                            adapter.add(JokeItem(joke.joke,joke.id))
                        }
                        refresh.isRefreshing=false
                    }
                    Status.FAILURE-> {

                        Toast.makeText(activity, (result.value as String), Toast.LENGTH_LONG).show()
                        refresh.isRefreshing=false
                    }

                    Status.LOADING-> {
                        progress.visibility=View.VISIBLE
                    }

                }
            })
    }

    private fun searchJoke( text:String)
    {
        val liveData=viewModel.searchJoke(text)

        liveData.observe(viewLifecycleOwner,
            Observer<JokeResult<Any>> { result ->
                when(result.status) {
                    Status.SUCCESS-> {
                        progress.visibility=View.GONE
                        val jokes=(result.value as List<JokesUIModel>)

                        adapter.clear()
                        for( joke in jokes) {
                            adapter.add(JokeItem(joke.joke,joke.id))
                        }

                    }
                    Status.FAILURE-> {

                        Toast.makeText(activity, (result.value as String), Toast.LENGTH_LONG).show()
                    }

                    Status.LOADING-> {
                        progress.visibility=View.VISIBLE
                    }

                }
            })
    }

    private val onItemClick = OnItemClickListener { item, _ ->
        if (item is JokeItem) {
            shareJoke(SHARE_URL+item.jokeId)
        }
    }

    private fun shareJoke(jokeUrl:String)
    {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, jokeUrl)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchManager = activity!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        if (searchItem != null) {
            searchView = searchItem.actionView as SearchView
        }
        if (searchView != null) {
            searchView!!.setSearchableInfo(searchManager.getSearchableInfo(activity!!.componentName))

            queryTextListener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    return true
                }

                override fun onQueryTextSubmit(query: String): Boolean {

                    searchJoke(query)
                    return true
                }
            }
            searchView!!.setOnQueryTextListener(queryTextListener)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }
}
