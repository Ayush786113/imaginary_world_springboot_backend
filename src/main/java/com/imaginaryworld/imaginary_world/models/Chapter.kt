package com.imaginaryworld.imaginary_world.models

data class Chapter(var title: String, var author: String, var content: List<String>, var thumbnail: String? = null, var story: String? = null)