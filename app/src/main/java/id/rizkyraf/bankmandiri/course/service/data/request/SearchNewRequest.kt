package id.rizkyraf.bankmandiri.course.service.data.request

data class SearchNewRequest(
    var query: String,
    var pageNumber: Int = 0
)