package com.sid1722289.schoolhomeorganiser.api

data class MealPlanItem(
    val cookingtime: String,
    val ingredients: List<Ingredient>,
    val kcal: String,
    val prep: List<Prep>,
    val preptime: String,
    val source: String,
    val title: String,
    val type: String,
    val updatedAt: String
)