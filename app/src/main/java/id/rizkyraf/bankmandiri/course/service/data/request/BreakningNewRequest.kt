package id.rizkyraf.bankmandiri.course.service.data.request

data class BreakningNewRequest(
    var countryCode: String,
    var pageNumber: Int = 0,
    var source: String,
)