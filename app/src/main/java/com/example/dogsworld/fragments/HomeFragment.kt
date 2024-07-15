package com.example.dogsworld.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.example.dogsworld.R
import com.example.dogsworld.activities.HomeActivity
import com.example.dogsworld.adapters.HomeAdapter
import com.example.dogsworld.api.ApiClient
import com.example.dogsworld.data.Dog
import com.example.dogsworld.databinding.FragmentHomeBinding
import com.example.dogsworld.interfaces.ItemClickListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() , ItemClickListener {

    private lateinit var mRvHome : RecyclerView
    private lateinit var mPbLoading : ProgressBar
    private lateinit var mRvAdapter : HomeAdapter
    private lateinit var fragmentHomeBinding : FragmentHomeBinding
    private var dogCounter = 0
    private var isLoading = false;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        fragmentHomeBinding = DataBindingUtil.inflate<ViewDataBinding>(
//            inflater, R.layout.fragment_home, container, false
//        ) as FragmentHomeBinding
//        return fragmentHomeBinding.root
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPbLoading = view.findViewById(R.id.pbLoading)
        mRvHome = view.findViewById(R.id.rvHome)
        mRvHome.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        mRvAdapter = HomeAdapter(((requireActivity()) as HomeActivity).dogList , requireActivity(), this);
        mRvHome.adapter = mRvAdapter

        setListeners()
//        checkFetchLogic()
    }

    override fun onStart() {
        super.onStart()
        checkFetchLogic()
    }
    private fun checkFetchLogic() {
        if(((requireActivity()) as HomeActivity).dogList.size == 0){
            fetchDogs()
        }
    }

    private fun setListeners() {
        // for limiting the scroll to one page at a time
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(mRvHome);

        mRvHome.addOnScrollListener(object : OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?

                if (!isLoading) {
                    if (linearLayoutManager != null &&
                        linearLayoutManager.findLastCompletelyVisibleItemPosition() == ((requireActivity()) as HomeActivity).dogList.size - 1) {
                        //bottom of list!
                        fetchDogs()
                        isLoading = true
                    }
                }
            }
        })
    }

    private fun fetchDogs() {
        mPbLoading.visibility = VISIBLE
        val call = ApiClient.apiService.getDogImage()

        call.enqueue(object : Callback<Dog> {
            override fun onResponse(call: Call<Dog>, response: Response<Dog>) {
                if (response.isSuccessful) {
                    val post = response.body()
                    if (post != null) {
                        ((requireActivity()) as HomeActivity).dogList.add(post)
                        dogCounter ++;

                        if(dogCounter == 10){
                            dogCounter = 0
                            mRvAdapter.notifyItemRangeInserted(((requireActivity()) as HomeActivity).dogList.size -10,10)
                            mPbLoading.visibility = GONE
                            isLoading = false
                        }else{
                            fetchDogs()
                        }
                    }
                } else {
                    mPbLoading.visibility = GONE
                    isLoading = false
                    Toast.makeText(requireContext(), "Fetch was not successful", Toast.LENGTH_LONG).show();
                    // Handle error
                }
            }

            override fun onFailure(call: Call<Dog>, t: Throwable) {
                mPbLoading.visibility = GONE
                isLoading = false
                // Handle failure
                Toast.makeText(requireContext(), "Fetch error: " + t.message, Toast.LENGTH_LONG).show();
            }
        })
    }

    fun refresh(){
        mRvAdapter.notifyDataSetChanged()
    }

    override fun onItemAddedToCart(pos: Int, dog: Dog) {
        var aDog = ((requireActivity()) as HomeActivity).dogList[pos]
        aDog.cartCounter = aDog.cartCounter +1
        ((requireActivity()) as HomeActivity).dogList[pos]= aDog
        mRvAdapter.notifyItemChanged(pos, aDog)
        (requireActivity() as HomeActivity).setListInStorage()
    }

    override fun onItemRemovedFromCart(pos: Int, dog: Dog) {
        var aDog = ((requireActivity()) as HomeActivity).dogList[pos]
        if(aDog.cartCounter > 0) {
            aDog.cartCounter = aDog.cartCounter - 1
            ((requireActivity()) as HomeActivity).dogList[pos] = aDog
            mRvAdapter.notifyItemChanged(pos, aDog)
            (requireActivity() as HomeActivity).setListInStorage()
        }else{
            Toast.makeText(requireActivity(), "Can't remove what you don't have!", Toast.LENGTH_SHORT).show()
        }
    }
}