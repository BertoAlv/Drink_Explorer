package com.alberto.drinkexplorer.ui.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import com.alberto.drinkexplorer.R
import com.alberto.drinkexplorer.data.model.Drink
import com.alberto.drinkexplorer.databinding.ActivityDrinkInfoBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DrinkInfoActivity : BaseCocktailActivity() {

    private lateinit var binding : ActivityDrinkInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDrinkInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var drink = intent.extras?.getString("DRINK")
        if (drink != null) {
            Log.d("Comprobacion","Drink contiene valor $drink")
            viewModel.onGoingDetail(drink)
        }

        viewModel.isLoading.observe(this){
            binding.progress.isVisible = it
        }

        initView()
    }

    private fun initView() {
        viewModel.drinkInformation.observe(this){
            drink ->
            binding.tvDrinkName.text = drink.name
            Picasso.get().load(drink.image).into(binding.ivDrinkImage)
            binding.tvGlass.text = drink.glass
            binding.tvInstructions.text = drink.instructions
            val ingredients = mutableListOf<String>()
            for (i in 1..9) {
                val ingredient = drink.getIngredient(i)
                if (ingredient != null) {
                    ingredients.add(ingredient)
                }
            }
            val ingredientList = ingredients.mapIndexedNotNull { index, ingredient ->
                val measure = drink.getMeasure(index + 1)
                if (measure != null) {
                    "$measure - $ingredient"
                } else {
                    null
                }
            }
            binding.tvIngredients.text = ingredientList.joinToString("\n")
        }
    }

    fun Drink.getIngredient(index: Int): String? {
        return when (index) {
            1 -> ingredient1
            2 -> ingredient2
            3 -> ingredient3
            4 -> ingredient4
            5 -> ingredient5
            6 -> ingredient6
            7 -> ingredient7
            8 -> ingredient8
            9 -> ingredient9
            else -> null
        }
    }

    fun Drink.getMeasure(index: Int): String? {
        return when (index) {
            1 -> measure1
            2 -> measure2
            3 -> measure3
            4 -> measure4
            5 -> measure5
            6 -> measure6
            7 -> measure7
            8 -> measure8
            9 -> measure9
            else -> null
        }
    }

    override fun onBackPressed() {
        finish()
    }
}