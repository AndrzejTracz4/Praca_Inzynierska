# StatisticApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**apiStatisticsGetCollection**](StatisticApi.md#apiStatisticsGetCollection) | **GET** /api/statistics | Retrieves the collection of Statistic resources. |
| [**apiStatisticsIdDelete**](StatisticApi.md#apiStatisticsIdDelete) | **DELETE** /api/statistics/{id} | Removes the Statistic resource. |
| [**apiStatisticsIdGet**](StatisticApi.md#apiStatisticsIdGet) | **GET** /api/statistics/{id} | Retrieves a Statistic resource. |
| [**apiStatisticsIdPatch**](StatisticApi.md#apiStatisticsIdPatch) | **PATCH** /api/statistics/{id} | Updates the Statistic resource. |
| [**apiStatisticsPost**](StatisticApi.md#apiStatisticsPost) | **POST** /api/statistics | Creates a Statistic resource. |


<a id="apiStatisticsGetCollection"></a>
# **apiStatisticsGetCollection**
> kotlin.collections.List&lt;StatisticStatisticitemread&gt; apiStatisticsGetCollection(page)

Retrieves the collection of Statistic resources.

Retrieves the collection of Statistic resources.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = StatisticApi()
val page : kotlin.Int = 56 // kotlin.Int | The collection page number
try {
    val result : kotlin.collections.List<StatisticStatisticitemread> = apiInstance.apiStatisticsGetCollection(page)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatisticApi#apiStatisticsGetCollection")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatisticApi#apiStatisticsGetCollection")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **page** | **kotlin.Int**| The collection page number | [optional] [default to 1] |

### Return type

[**kotlin.collections.List&lt;StatisticStatisticitemread&gt;**](StatisticStatisticitemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/ld+json

<a id="apiStatisticsIdDelete"></a>
# **apiStatisticsIdDelete**
> apiStatisticsIdDelete(id)

Removes the Statistic resource.

Removes the Statistic resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = StatisticApi()
val id : kotlin.String = id_example // kotlin.String | Statistic identifier
try {
    apiInstance.apiStatisticsIdDelete(id)
} catch (e: ClientException) {
    println("4xx response calling StatisticApi#apiStatisticsIdDelete")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatisticApi#apiStatisticsIdDelete")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **kotlin.String**| Statistic identifier | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a id="apiStatisticsIdGet"></a>
# **apiStatisticsIdGet**
> StatisticStatisticreadStatisticitemread apiStatisticsIdGet(id)

Retrieves a Statistic resource.

Retrieves a Statistic resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = StatisticApi()
val id : kotlin.String = id_example // kotlin.String | Statistic identifier
try {
    val result : StatisticStatisticreadStatisticitemread = apiInstance.apiStatisticsIdGet(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatisticApi#apiStatisticsIdGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatisticApi#apiStatisticsIdGet")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **kotlin.String**| Statistic identifier | |

### Return type

[**StatisticStatisticreadStatisticitemread**](StatisticStatisticreadStatisticitemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/ld+json

<a id="apiStatisticsIdPatch"></a>
# **apiStatisticsIdPatch**
> StatisticStatisticreadStatisticitemread apiStatisticsIdPatch(id, statisticStatisticwrite)

Updates the Statistic resource.

Updates the Statistic resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = StatisticApi()
val id : kotlin.String = id_example // kotlin.String | Statistic identifier
val statisticStatisticwrite : StatisticStatisticwrite =  // StatisticStatisticwrite | The updated Statistic resource
try {
    val result : StatisticStatisticreadStatisticitemread = apiInstance.apiStatisticsIdPatch(id, statisticStatisticwrite)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatisticApi#apiStatisticsIdPatch")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatisticApi#apiStatisticsIdPatch")
    e.printStackTrace()
}
```

### Parameters
| **id** | **kotlin.String**| Statistic identifier | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **statisticStatisticwrite** | [**StatisticStatisticwrite**](StatisticStatisticwrite.md)| The updated Statistic resource | |

### Return type

[**StatisticStatisticreadStatisticitemread**](StatisticStatisticreadStatisticitemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/merge-patch+json
 - **Accept**: application/json, application/ld+json

<a id="apiStatisticsPost"></a>
# **apiStatisticsPost**
> StatisticStatisticreadStatisticitemread apiStatisticsPost(statisticStatisticcreateStatisticwrite)

Creates a Statistic resource.

Creates a Statistic resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = StatisticApi()
val statisticStatisticcreateStatisticwrite : StatisticStatisticcreateStatisticwrite =  // StatisticStatisticcreateStatisticwrite | The new Statistic resource
try {
    val result : StatisticStatisticreadStatisticitemread = apiInstance.apiStatisticsPost(statisticStatisticcreateStatisticwrite)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatisticApi#apiStatisticsPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatisticApi#apiStatisticsPost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **statisticStatisticcreateStatisticwrite** | [**StatisticStatisticcreateStatisticwrite**](StatisticStatisticcreateStatisticwrite.md)| The new Statistic resource | |

### Return type

[**StatisticStatisticreadStatisticitemread**](StatisticStatisticreadStatisticitemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/ld+json

