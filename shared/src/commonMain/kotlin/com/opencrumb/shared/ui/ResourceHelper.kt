package com.opencrumb.shared.ui

import opencrumb.shared.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource

fun getDrawableResourceByName(name: String): DrawableResource =
    when (name) {
        // Pizza images
        "pizza_margherita" -> Res.drawable.pizza_margherita
        "pizza_8_hours" -> Res.drawable.pizza_8_hours
        "pizza_same_day" -> Res.drawable.pizza_same_day
        "pizza_extended_fermentation" -> Res.drawable.pizza_extended_fermentation
        "pizza_with_poolish" -> Res.drawable.pizza_with_poolish
        "pizza_marinara" -> Res.drawable.pizza_marinara
        "pizza_margherita_buffalo" -> Res.drawable.pizza_margherita_buffalo
        "pizza_prosciutto_cotto" -> Res.drawable.pizza_prosciutto_cotto
        "pizza_prosciutto_crudo" -> Res.drawable.pizza_prosciutto_crudo
        "pizza_prosciutto_crudo_mushrooms" -> Res.drawable.pizza_prosciutto_crudo_mushrooms
        "pizza_mortadella_pistacchio" -> Res.drawable.pizza_mortadella_pistacchio
        "pizza_broccoli_mushrooms" -> Res.drawable.pizza_broccoli_mushrooms
        "pizza_zucchini_speck" -> Res.drawable.pizza_zucchini_speck

        // Focaccia images
        "focaccia_direct" -> Res.drawable.focaccia_direct
        "focaccia_poolish1" -> Res.drawable.focaccia_poolish1
        "focaccia_poolish2" -> Res.drawable.focaccia_poolish2
        "focaccia_genovese" -> Res.drawable.focaccia_genovese
        "focaccia_rosemary_olives" -> Res.drawable.focaccia_rosemary_olives
        "focaccia_green_olives" -> Res.drawable.focaccia_green_olives
        "focaccia_cherry_tomatoes" -> Res.drawable.focaccia_cherry_tomatoes
        "focaccia_pizza" -> Res.drawable.focaccia_pizza

        // Bread images
        "baguette_direct" -> Res.drawable.baguette_direct
        "baguette_poolish" -> Res.drawable.baguette_poolish
        "ciabatta_biga" -> Res.drawable.ciabatta_biga
        "ciabatta_poolish" -> Res.drawable.ciabatta_poolish
        "white_bread" -> Res.drawable.white_bread

        // Topping images
        "tomato_sauce" -> Res.drawable.tomato_sauce

        // Guide images
        "guide_kneading" -> Res.drawable.guide_kneading
        "guide_stretch_fold" -> Res.drawable.guide_stretch_fold
        "guide_fermentation" -> Res.drawable.guide_fermentation
        "guide_shaping" -> Res.drawable.guide_shaping
        "guide_open_crumb" -> Res.drawable.guide_open_crumb
        "guide_poolish" -> Res.drawable.guide_poolish
        "guide_biga" -> Res.drawable.guide_biga
        "open_crumb_book" -> Res.drawable.open_crumb_book
        
        // Kneading technique images
        "mix_ingredients1" -> Res.drawable.mix_ingredients1
        "mix_ingredients2" -> Res.drawable.mix_ingredients2
        "mix_ingredients3" -> Res.drawable.mix_ingredients3
        "mix_ingredients4" -> Res.drawable.mix_ingredients4
        "quick_kneading1" -> Res.drawable.quick_kneading1
        "quick_kneading2" -> Res.drawable.quick_kneading2
        "quick_kneading3" -> Res.drawable.quick_kneading3
        "quick_kneading4" -> Res.drawable.quick_kneading4
        
        // Stretch and fold images
        "stretch_fold1" -> Res.drawable.stretch_fold1
        "stretch_fold2" -> Res.drawable.stretch_fold2
        "stretch_fold3" -> Res.drawable.stretch_fold3
        "stretch_fold4" -> Res.drawable.stretch_fold4
        "stretch_fold5" -> Res.drawable.stretch_fold5
        "stretch_fold6" -> Res.drawable.stretch_fold6
        "stretch_fold7" -> Res.drawable.stretch_fold7
        "stretch_fold8" -> Res.drawable.stretch_fold8
        "stretch_fold9" -> Res.drawable.stretch_fold9
        
        // Shaping technique images
        "baguette_shaping1" -> Res.drawable.baguette_shaping1
        "baguette_shaping2" -> Res.drawable.baguette_shaping2
        "baguette_shaping3" -> Res.drawable.baguette_shaping3
        "baguette_shaping4" -> Res.drawable.baguette_shaping4
        "baguette_shaping5" -> Res.drawable.baguette_shaping5
        "baguette_shaping6" -> Res.drawable.baguette_shaping6
        "round_loaf_shaping1" -> Res.drawable.round_loaf_shaping1
        "round_loaf_shaping2" -> Res.drawable.round_loaf_shaping2
        "round_loaf_shaping3" -> Res.drawable.round_loaf_shaping3
        "round_loaf_shaping4" -> Res.drawable.round_loaf_shaping4
        "round_loaf_shaping5" -> Res.drawable.round_loaf_shaping5
        "round_loaf_shaping6" -> Res.drawable.round_loaf_shaping6

        // Category icons
        "pizza_icon_top_bar" -> Res.drawable.pizza_icon_top_bar
        "focaccia_icon_top_bar" -> Res.drawable.focaccia_icon_top_bar
        "bread_icon_top_bar" -> Res.drawable.bread_icon_top_bar

        // Default
        else -> Res.drawable.open_crumb_logo
    }
