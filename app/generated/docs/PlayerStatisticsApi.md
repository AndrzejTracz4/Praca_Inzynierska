# PlayerStatisticsApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**apiPlayerStatisticsGetCollection**](PlayerStatisticsApi.md#apiPlayerStatisticsGetCollection) | **GET** /api/player_statistics | Retrieves the collection of PlayerStatistics resources. |
| [**apiPlayerStatisticsIdDelete**](PlayerStatisticsApi.md#apiPlayerStatisticsIdDelete) | **DELETE** /api/player_statistics/{id} | Removes the PlayerStatistics resource. |
| [**apiPlayerStatisticsIdGet**](PlayerStatisticsApi.md#apiPlayerStatisticsIdGet) | **GET** /api/player_statistics/{id} | Retrieves a PlayerStatistics resource. |
| [**apiPlayerStatisticsIdPatch**](PlayerStatisticsApi.md#apiPlayerStatisticsIdPatch) | **PATCH** /api/player_statistics/{id} | Updates the PlayerStatistics resource. |
| [**apiPlayerStatisticsPost**](PlayerStatisticsApi.md#apiPlayerStatisticsPost) | **POST** /api/player_statistics | Creates a PlayerStatistics resource. |


<a id="apiPlayerStatisticsGetCollection"></a>
# **apiPlayerStatisticsGetCollection**
> kotlin.collections.List&lt;PlayerStatisticsPlayerStatisticsitemread&gt; apiPlayerStatisticsGetCollection(page)

Retrieves the collection of PlayerStatistics resources.

Retrieves the collection of PlayerStatistics resources.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = PlayerStatisticsApi()
val page : kotlin.Int = 56 // kotlin.Int | The collection page number
try {
    val result : kotlin.collections.List<PlayerStatisticsPlayerStatisticsitemread> = apiInstance.apiPlayerStatisticsGetCollection(page)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PlayerStatisticsApi#apiPlayerStatisticsGetCollection")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PlayerStatisticsApi#apiPlayerStatisticsGetCollection")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **page** | **kotlin.Int**| The collection page number | [optional] [default to 1] |

### Return type

[**kotlin.collections.List&lt;PlayerStatisticsPlayerStatisticsitemread&gt;**](PlayerStatisticsPlayerStatisticsitemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/ld+json

<a id="apiPlayerStatisticsIdDelete"></a>
# **apiPlayerStatisticsIdDelete**
> apiPlayerStatisticsIdDelete(id)

Removes the PlayerStatistics resource.

Removes the PlayerStatistics resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = PlayerStatisticsApi()
val id : kotlin.String = id_example // kotlin.String | PlayerStatistics identifier
try {
    apiInstance.apiPlayerStatisticsIdDelete(id)
} catch (e: ClientException) {
    println("4xx response calling PlayerStatisticsApi#apiPlayerStatisticsIdDelete")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PlayerStatisticsApi#apiPlayerStatisticsIdDelete")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **kotlin.String**| PlayerStatistics identifier | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a id="apiPlayerStatisticsIdGet"></a>
# **apiPlayerStatisticsIdGet**
> PlayerStatisticsPlayerStatisticsreadPlayerStatisticsitemread apiPlayerStatisticsIdGet(id)

Retrieves a PlayerStatistics resource.

Retrieves a PlayerStatistics resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = PlayerStatisticsApi()
val id : kotlin.String = id_example // kotlin.String | PlayerStatistics identifier
try {
    val result : PlayerStatisticsPlayerStatisticsreadPlayerStatisticsitemread = apiInstance.apiPlayerStatisticsIdGet(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PlayerStatisticsApi#apiPlayerStatisticsIdGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PlayerStatisticsApi#apiPlayerStatisticsIdGet")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **kotlin.String**| PlayerStatistics identifier | |

### Return type

[**PlayerStatisticsPlayerStatisticsreadPlayerStatisticsitemread**](PlayerStatisticsPlayerStatisticsreadPlayerStatisticsitemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/ld+json

<a id="apiPlayerStatisticsIdPatch"></a>
# **apiPlayerStatisticsIdPatch**
> PlayerStatisticsPlayerStatisticsreadPlayerStatisticsitemread apiPlayerStatisticsIdPatch(id, playerStatisticsPlayerStatisticswrite)

Updates the PlayerStatistics resource.

Updates the PlayerStatistics resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = PlayerStatisticsApi()
val id : kotlin.String = id_example // kotlin.String | PlayerStatistics identifier
val playerStatisticsPlayerStatisticswrite : PlayerStatisticsPlayerStatisticswrite =  // PlayerStatisticsPlayerStatisticswrite | The updated PlayerStatistics resource
try {
    val result : PlayerStatisticsPlayerStatisticsreadPlayerStatisticsitemread = apiInstance.apiPlayerStatisticsIdPatch(id, playerStatisticsPlayerStatisticswrite)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PlayerStatisticsApi#apiPlayerStatisticsIdPatch")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PlayerStatisticsApi#apiPlayerStatisticsIdPatch")
    e.printStackTrace()
}
```

### Parameters
| **id** | **kotlin.String**| PlayerStatistics identifier | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **playerStatisticsPlayerStatisticswrite** | [**PlayerStatisticsPlayerStatisticswrite**](PlayerStatisticsPlayerStatisticswrite.md)| The updated PlayerStatistics resource | |

### Return type

[**PlayerStatisticsPlayerStatisticsreadPlayerStatisticsitemread**](PlayerStatisticsPlayerStatisticsreadPlayerStatisticsitemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/merge-patch+json
 - **Accept**: application/json, application/ld+json

<a id="apiPlayerStatisticsPost"></a>
# **apiPlayerStatisticsPost**
> PlayerStatisticsPlayerStatisticsreadPlayerStatisticsitemread apiPlayerStatisticsPost(playerStatisticsPlayerStatisticscreatePlayerStatisticswrite)

Creates a PlayerStatistics resource.

Creates a PlayerStatistics resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = PlayerStatisticsApi()
val playerStatisticsPlayerStatisticscreatePlayerStatisticswrite : PlayerStatisticsPlayerStatisticscreatePlayerStatisticswrite =  // PlayerStatisticsPlayerStatisticscreatePlayerStatisticswrite | The new PlayerStatistics resource
try {
    val result : PlayerStatisticsPlayerStatisticsreadPlayerStatisticsitemread = apiInstance.apiPlayerStatisticsPost(playerStatisticsPlayerStatisticscreatePlayerStatisticswrite)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PlayerStatisticsApi#apiPlayerStatisticsPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PlayerStatisticsApi#apiPlayerStatisticsPost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **playerStatisticsPlayerStatisticscreatePlayerStatisticswrite** | [**PlayerStatisticsPlayerStatisticscreatePlayerStatisticswrite**](PlayerStatisticsPlayerStatisticscreatePlayerStatisticswrite.md)| The new PlayerStatistics resource | |

### Return type

[**PlayerStatisticsPlayerStatisticsreadPlayerStatisticsitemread**](PlayerStatisticsPlayerStatisticsreadPlayerStatisticsitemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/ld+json

