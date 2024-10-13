# CategoryStatisticApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**apiCategoryStatisticsGetCollection**](CategoryStatisticApi.md#apiCategoryStatisticsGetCollection) | **GET** /api/category_statistics | Retrieves the collection of CategoryStatistic resources. |
| [**apiCategoryStatisticsIdDelete**](CategoryStatisticApi.md#apiCategoryStatisticsIdDelete) | **DELETE** /api/category_statistics/{id} | Removes the CategoryStatistic resource. |
| [**apiCategoryStatisticsIdGet**](CategoryStatisticApi.md#apiCategoryStatisticsIdGet) | **GET** /api/category_statistics/{id} | Retrieves a CategoryStatistic resource. |
| [**apiCategoryStatisticsIdPatch**](CategoryStatisticApi.md#apiCategoryStatisticsIdPatch) | **PATCH** /api/category_statistics/{id} | Updates the CategoryStatistic resource. |
| [**apiCategoryStatisticsPost**](CategoryStatisticApi.md#apiCategoryStatisticsPost) | **POST** /api/category_statistics | Creates a CategoryStatistic resource. |


<a id="apiCategoryStatisticsGetCollection"></a>
# **apiCategoryStatisticsGetCollection**
> kotlin.collections.List&lt;CategoryStatisticCategoryStatisticitemread&gt; apiCategoryStatisticsGetCollection(page)

Retrieves the collection of CategoryStatistic resources.

Retrieves the collection of CategoryStatistic resources.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = CategoryStatisticApi()
val page : kotlin.Int = 56 // kotlin.Int | The collection page number
try {
    val result : kotlin.collections.List<CategoryStatisticCategoryStatisticitemread> = apiInstance.apiCategoryStatisticsGetCollection(page)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CategoryStatisticApi#apiCategoryStatisticsGetCollection")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CategoryStatisticApi#apiCategoryStatisticsGetCollection")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **page** | **kotlin.Int**| The collection page number | [optional] [default to 1] |

### Return type

[**kotlin.collections.List&lt;CategoryStatisticCategoryStatisticitemread&gt;**](CategoryStatisticCategoryStatisticitemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/ld+json

<a id="apiCategoryStatisticsIdDelete"></a>
# **apiCategoryStatisticsIdDelete**
> apiCategoryStatisticsIdDelete(id)

Removes the CategoryStatistic resource.

Removes the CategoryStatistic resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = CategoryStatisticApi()
val id : kotlin.String = id_example // kotlin.String | CategoryStatistic identifier
try {
    apiInstance.apiCategoryStatisticsIdDelete(id)
} catch (e: ClientException) {
    println("4xx response calling CategoryStatisticApi#apiCategoryStatisticsIdDelete")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CategoryStatisticApi#apiCategoryStatisticsIdDelete")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **kotlin.String**| CategoryStatistic identifier | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a id="apiCategoryStatisticsIdGet"></a>
# **apiCategoryStatisticsIdGet**
> CategoryStatisticCategoryStatisticreadCategoryStatisticitemread apiCategoryStatisticsIdGet(id)

Retrieves a CategoryStatistic resource.

Retrieves a CategoryStatistic resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = CategoryStatisticApi()
val id : kotlin.String = id_example // kotlin.String | CategoryStatistic identifier
try {
    val result : CategoryStatisticCategoryStatisticreadCategoryStatisticitemread = apiInstance.apiCategoryStatisticsIdGet(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CategoryStatisticApi#apiCategoryStatisticsIdGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CategoryStatisticApi#apiCategoryStatisticsIdGet")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **kotlin.String**| CategoryStatistic identifier | |

### Return type

[**CategoryStatisticCategoryStatisticreadCategoryStatisticitemread**](CategoryStatisticCategoryStatisticreadCategoryStatisticitemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/ld+json

<a id="apiCategoryStatisticsIdPatch"></a>
# **apiCategoryStatisticsIdPatch**
> CategoryStatisticCategoryStatisticreadCategoryStatisticitemread apiCategoryStatisticsIdPatch(id, categoryStatisticCategoryStatisticwrite)

Updates the CategoryStatistic resource.

Updates the CategoryStatistic resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = CategoryStatisticApi()
val id : kotlin.String = id_example // kotlin.String | CategoryStatistic identifier
val categoryStatisticCategoryStatisticwrite : CategoryStatisticCategoryStatisticwrite =  // CategoryStatisticCategoryStatisticwrite | The updated CategoryStatistic resource
try {
    val result : CategoryStatisticCategoryStatisticreadCategoryStatisticitemread = apiInstance.apiCategoryStatisticsIdPatch(id, categoryStatisticCategoryStatisticwrite)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CategoryStatisticApi#apiCategoryStatisticsIdPatch")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CategoryStatisticApi#apiCategoryStatisticsIdPatch")
    e.printStackTrace()
}
```

### Parameters
| **id** | **kotlin.String**| CategoryStatistic identifier | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **categoryStatisticCategoryStatisticwrite** | [**CategoryStatisticCategoryStatisticwrite**](CategoryStatisticCategoryStatisticwrite.md)| The updated CategoryStatistic resource | |

### Return type

[**CategoryStatisticCategoryStatisticreadCategoryStatisticitemread**](CategoryStatisticCategoryStatisticreadCategoryStatisticitemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/merge-patch+json
 - **Accept**: application/json, application/ld+json

<a id="apiCategoryStatisticsPost"></a>
# **apiCategoryStatisticsPost**
> CategoryStatisticCategoryStatisticreadCategoryStatisticitemread apiCategoryStatisticsPost(categoryStatisticCategoryStatisticcreateCategoryStatisticwrite)

Creates a CategoryStatistic resource.

Creates a CategoryStatistic resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = CategoryStatisticApi()
val categoryStatisticCategoryStatisticcreateCategoryStatisticwrite : CategoryStatisticCategoryStatisticcreateCategoryStatisticwrite =  // CategoryStatisticCategoryStatisticcreateCategoryStatisticwrite | The new CategoryStatistic resource
try {
    val result : CategoryStatisticCategoryStatisticreadCategoryStatisticitemread = apiInstance.apiCategoryStatisticsPost(categoryStatisticCategoryStatisticcreateCategoryStatisticwrite)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CategoryStatisticApi#apiCategoryStatisticsPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CategoryStatisticApi#apiCategoryStatisticsPost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **categoryStatisticCategoryStatisticcreateCategoryStatisticwrite** | [**CategoryStatisticCategoryStatisticcreateCategoryStatisticwrite**](CategoryStatisticCategoryStatisticcreateCategoryStatisticwrite.md)| The new CategoryStatistic resource | |

### Return type

[**CategoryStatisticCategoryStatisticreadCategoryStatisticitemread**](CategoryStatisticCategoryStatisticreadCategoryStatisticitemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/ld+json

