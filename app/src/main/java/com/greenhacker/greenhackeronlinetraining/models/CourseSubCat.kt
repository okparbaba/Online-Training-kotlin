package com.greenhacker.greenhackeronlinetraining.models

class CourseSubCat {

    var id: String? = null

    var price: String? = null

    var category_name: String? = null

    var teacher_name: String? = null

    var description: String? = null

    var category_id: String? = null

    var type: String? = null

    var photo: String? = null

    override fun toString(): String {
        return "ClassPojo [id = $id, price = $price, category_name = $category_name, teacher_name = $teacher_name, description = $description, category_id = $category_id, type = $type, photo = $photo]"
    }
}
