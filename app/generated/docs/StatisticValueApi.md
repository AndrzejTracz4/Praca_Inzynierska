# StatisticValueApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**apiStatisticValuesGetCollection**](StatisticValueApi.md#apiStatisticValuesGetCollection) | **GET** /api/statistic_values | Retrieves the collection of StatisticValue resources. |
| [**apiStatisticValuesIdDelete**](StatisticValueApi.md#apiStatisticValuesIdDelete) | **DELETE** /api/statistic_values/{id} | Removes the StatisticValue resource. |
| [**apiStatisticValuesIdGet**](StatisticValueApi.md#apiStatisticValuesIdGet) | **GET** /api/statistic_values/{id} | Retrieves a StatisticValue resource. |
| [**apiStatisticValuesIdPatch**](StatisticValueApi.md#apiStatisticValuesIdPatch) | **PATCH** /api/statistic_values/{id} | Updates the StatisticValue resource. |
| [**apiStatisticValuesPost**](StatisticValueApi.md#apiStatisticValuesPost) | **POST** /api/statistic_values | Creates a StatisticValue resource. |


<a id="apiStatisticValuesGetCollection"></a>
# **apiStatisticValuesGetCollection**
> kotlin.collections.List&lt;StatisticValueStatisticValueitemread&gt; apiStatisticValuesGetCollection(page)

Retrieves the collection of StatisticValue resources.

Retrieves the collection of StatisticValue resources.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = StatisticValueApi()
val page : kotlin.Int = 56 // kotlin.Int | The collection page number
try {
    val result : kotlin.collections.List<StatisticValueStatisticValueitemread> = apiInstance.apiStatisticValuesGetCollection(page)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatisticValueApi#apiStatisticValuesGetCollection")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatisticValueApi#apiStatisticValuesGetCollection")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **page** | **kotlin.Int**| The collection page number | [optional] [default to 1] |

### Return type

[**kotlin.collections.List&lt;StatisticValueStatisticValueitemread&gt;**](StatisticValueStatisticValueitemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/ld+json

<a id="apiStatisticValuesIdDelete"></a>
# **apiStatisticValuesIdDelete**
> apiStatisticValuesIdDelete(id)

Removes the StatisticValue resource.

Removes the StatisticValue resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = StatisticValueApi()
val id : kotlin.String = id_example // kotlin.String | StatisticValue identifier
try {
    apiInstance.apiStatisticValuesIdDelete(id)
} catch (e: ClientException) {
    println("4xx response calling StatisticValueApi#apiStatisticValuesIdDelete")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatisticValueApi#apiStatisticValuesIdDelete")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **kotlin.String**| StatisticValue identifier | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a id="apiStatisticValuesIdGet"></a>
# **apiStatisticValuesIdGet**
> StatisticValueStatisticValuereadStatisticValueitemread apiStatisticValuesIdGet(id)

Retrieves a StatisticValue resource.

Retrieves a StatisticValue resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = StatisticValueApi()
val id : kotlin.String = id_example // kotlin.String | StatisticValue identifier
try {
    val result : StatisticValueStatisticValuereadStatisticValueitemread = apiInstance.apiStatisticValuesIdGet(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatisticValueApi#apiStatisticValuesIdGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatisticValueApi#apiStatisticValuesIdGet")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **kotlin.String**| StatisticValue identifier | |

### Return type

[**StatisticValueStatisticValuereadStatisticValueitemread**](StatisticValueStatisticValuereadStatisticValueitemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/ld+json

<a id="apiStatisticValuesIdPatch"></a>
# **apiStatisticValuesIdPatch**
> StatisticValueStatisticValuereadStatisticValueitemread apiStatisticValuesIdPatch(id, statisticValueStatisticValuewrite)

Updates the StatisticValue resource.

Updates the StatisticValue resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = StatisticValueApi()
val id : kotlin.String = id_example // kotlin.String | StatisticValue identifier
val statisticValueStatisticValuewrite : StatisticValueStatisticValuewrite =  // StatisticValueStatisticValuewrite | The updated StatisticValue resource
try {
    val result : StatisticValueStatisticValuereadStatisticValueitemread = apiInstance.apiStatisticValuesIdPatch(id, statisticValueStatisticValuewrite)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatisticValueApi#apiStatisticValuesIdPatch")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatisticValueApi#apiStatisticValuesIdPatch")
    e.printStackTrace()
}
```

### Parameters
| **id** | **kotlin.String**| StatisticValue identifier | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **statisticValueStatisticValuewrite** | [**StatisticValueStatisticValuewrite**](StatisticValueStatisticValuewrite.md)| The updated StatisticValue resource | |

### Return type

[**StatisticValueStatisticValuereadStatisticValueitemread**](StatisticValueStatisticValuereadStatisticValueitemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/merge-patch+json
 - **Accept**: application/json, application/ld+json

<a id="apiStatisticValuesPost"></a>
# **apiStatisticValuesPost**
> StatisticValueStatisticValuereadStatisticValueitemread apiStatisticValuesPost(statisticValueStatisticValuecreateStatisticValuewrite)

Creates a StatisticValue resource.

Creates a StatisticValue resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = StatisticValueApi()
val statisticValueStatisticValuecreateStatisticValuewrite : StatisticValueStatisticValuecreateStatisticValuewrite =  // StatisticValueStatisticValuecreateStatisticValuewrite | The new StatisticValue resource
try {
    val result : StatisticValueStatisticValuereadStatisticValueitemread = apiInstance.apiStatisticValuesPost(statisticValueStatisticValuecreateStatisticValuewrite)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StatisticValueApi#apiStatisticValuesPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StatisticValueApi#apiStatisticValuesPost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **statisticValueStatisticValuecreateStatisticValuewrite** | [**StatisticValueStatisticValuecreateStatisticValuewrite**](StatisticValueStatisticValuecreateStatisticValuewrite.md)| The new StatisticValue resource | |

### Return type

[**StatisticValueStatisticValuereadStatisticValueitemread**](StatisticValueStatisticValuereadStatisticValueitemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/ld+json

