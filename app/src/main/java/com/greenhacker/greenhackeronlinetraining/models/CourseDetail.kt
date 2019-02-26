package com.greenhacker.greenhackeronlinetraining.models

class CourseDetail {
    var id: String? = null

    var course_teacher: String? = null

    var main_category_name: String? = null

    var course_price: String? = null

    var course_photo_url: String? = null

    var course_name: String? = null

    var course_description: String? = null

    var course_photo: String? = null

    var course_type: String? = null

    override fun toString(): String {
        return "ClassPojo [id = $id, course_teacher = $course_teacher, main_category_name = $main_category_name, course_price = $course_price, course_photo_url = $course_photo_url, course_name = $course_name, course_description = $course_description, course_photo = $course_photo, course_type = $course_type]"
    }
}