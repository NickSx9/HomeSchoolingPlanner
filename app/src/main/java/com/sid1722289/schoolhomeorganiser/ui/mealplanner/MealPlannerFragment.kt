package com.sid1722289.schoolhomeorganiser.ui.mealplanner

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sid1722289.schoolhomeorganiser.ApiRequest
import com.sid1722289.schoolhomeorganiser.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.jsonbin.io/"

class MealPlannerFragment : Fragment() {

    private lateinit var mealPlannerViewModel: MealPlannerViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mealPlannerViewModel =
            ViewModelProvider(this).get(MealPlannerViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_mealplanner, container, false)
        val textView: TextView = root.findViewById(R.id.text_mealplanner)
        mealPlannerViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        try{
            val progressBar: ProgressBar = root.findViewById(R.id.progressBar)
                progressBar.visibility = View.VISIBLE
            // Tile One
            val oneImage: ImageView = root.findViewById(R.id.image_card_One)
            val oneTitle: TextView = root.findViewById(R.id.tv_card_One)
            val oneKcal: TextView = root.findViewById(R.id.kcal_card_One)
            val onePrep: TextView = root.findViewById(R.id.prepTime_card_One)
            val oneCookTime: TextView = root.findViewById(R.id.cookingTime_card_One)
            val oneStageOne: TextView = root.findViewById(R.id.stageOne_CardOne)
            // Tile Two
            val twoImage: ImageView = root.findViewById(R.id.image_card_Two)
            val twoTitle: TextView = root.findViewById(R.id.tv_card_Two)
            val twoKcal: TextView = root.findViewById(R.id.kcal_card_Two)
            val twoPrep: TextView = root.findViewById(R.id.prepTime_card_Two)
            val twoCookTime: TextView = root.findViewById(R.id.cookingTime_card_Two)
            val twoStageOne: TextView = root.findViewById(R.id.stageOne_CardTwo)
            // Tile Three
            val threeImage: ImageView = root.findViewById(R.id.image_card_Three)
            val threeTitle: TextView = root.findViewById(R.id.tv_card_Three)
            val threeKcal: TextView = root.findViewById(R.id.kcal_card_Three)
            val threePrep: TextView = root.findViewById(R.id.prepTime_card_Three)
            val threeCookTime: TextView = root.findViewById(R.id.cookingTime_card_Three)
            val threeStageOne: TextView = root.findViewById(R.id.stageOne_CardThree)
            // Tile Four
            val fourImage: ImageView = root.findViewById(R.id.image_card_Four)
            val fourTitle: TextView = root.findViewById(R.id.tv_card_Four)
            val fourKcal: TextView = root.findViewById(R.id.kcal_card_Four)
            val fourPrep: TextView = root.findViewById(R.id.prepTime_card_Four)
            val fourCookTime: TextView = root.findViewById(R.id.cookingTime_card_Four)
            val fourStageOne: TextView = root.findViewById(R.id.stageOne_CardFour)
            val api = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiRequest::class.java)
            GlobalScope.launch(Dispatchers.IO) {
                val response = api.getMealData().awaitResponse()
                var one: String? = ""
                var two: String? = ""
                var three: String? = ""
                var four: String? = ""
                if (response.isSuccessful) {
                    var data = response.body()!!
                    withContext(Dispatchers.Main) {
                        progressBar.visibility = View.GONE
                        //tile one
                        oneImage.setImageResource(R.drawable.veggie_shepherds_pie)
                        oneTitle.text = data[0].title
                        oneKcal.text = "Kcal: "+ data[0].kcal
                        onePrep.text = "Prep time: "+ data[0].preptime
                        oneCookTime.text = "Cooking: "+ data[0].cookingtime
                        for(data in data[0].ingredients) {
                            one += data.ingredient + "\n"
                        }
                        oneStageOne.text = one
                        //tile two
                        twoImage.setImageResource(R.drawable.veggie_chilli)
                        twoTitle.text = data[1].title
                        twoKcal.text = "Kcal: "+ data[1].kcal
                        twoPrep.text = "Prep time: "+ data[1].preptime
                        twoCookTime.text = "Cooking: "+ data[1].cookingtime
                        for(data in data[1].ingredients) {
                            two += data.ingredient + "\n"
                        }
                        twoStageOne.text = two
                        //tile three
                        threeImage.setImageResource(R.drawable.chicken_skewers)
                        threeTitle.text = data[2].title
                        threeKcal.text = "Kcal: "+ data[2].kcal
                        threePrep.text = "Prep time: "+ data[2].preptime
                        threeCookTime.text = "Cooking: "+ data[2].cookingtime
                        for(data in data[2].ingredients) {
                            three += data.ingredient + "\n"
                        }
                        threeStageOne.text = three
                        //tile four
                        fourImage.setImageResource(R.drawable.bbq_chicken_pizza)
                        fourTitle.text = data[3].title
                        fourKcal.text = "Kcal: "+ data[3].kcal
                        fourPrep.text = "Prep time: "+ data[3].preptime
                        fourCookTime.text = "Cooking: "+ data[3].cookingtime
                        for(data in data[3].ingredients) {
                            four += data.ingredient + "\n"
                        }
                        fourStageOne.text = four
                    }
                }
            }
    }catch (e: Exception){
        Toast.makeText(activity as Context, "Something went wrong...", Toast.LENGTH_SHORT).show()
    }
        return root
    }
}

