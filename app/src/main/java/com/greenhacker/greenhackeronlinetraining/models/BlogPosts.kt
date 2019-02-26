package com.greenhacker.greenhackeronlinetraining.models

class BlogPosts {
    var id: String? = null

    var title: String? = null

    internal var category_arr: Array<Category_arr>? = null

    var description: String? = null

    var photo_url: String? = null

    var admin_name: String? = null

}

internal class Category_arr {
    var id: String? = null

    var category_name: String? = null

    override fun toString(): String {
        return "ClassPojo [id = $id, category_name = $category_name]"
    }
}