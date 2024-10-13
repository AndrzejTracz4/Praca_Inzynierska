# RewardItemApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**apiRewardItemsGetCollection**](RewardItemApi.md#apiRewardItemsGetCollection) | **GET** /api/reward_items | Retrieves the collection of RewardItem resources. |
| [**apiRewardItemsIdDelete**](RewardItemApi.md#apiRewardItemsIdDelete) | **DELETE** /api/reward_items/{id} | Removes the RewardItem resource. |
| [**apiRewardItemsIdGet**](RewardItemApi.md#apiRewardItemsIdGet) | **GET** /api/reward_items/{id} | Retrieves a RewardItem resource. |
| [**apiRewardItemsIdPatch**](RewardItemApi.md#apiRewardItemsIdPatch) | **PATCH** /api/reward_items/{id} | Updates the RewardItem resource. |
| [**apiRewardItemsPost**](RewardItemApi.md#apiRewardItemsPost) | **POST** /api/reward_items | Creates a RewardItem resource. |


<a id="apiRewardItemsGetCollection"></a>
# **apiRewardItemsGetCollection**
> kotlin.collections.List&lt;RewardItemRewardItemitemread&gt; apiRewardItemsGetCollection(page)

Retrieves the collection of RewardItem resources.

Retrieves the collection of RewardItem resources.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = RewardItemApi()
val page : kotlin.Int = 56 // kotlin.Int | The collection page number
try {
    val result : kotlin.collections.List<RewardItemRewardItemitemread> = apiInstance.apiRewardItemsGetCollection(page)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling RewardItemApi#apiRewardItemsGetCollection")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling RewardItemApi#apiRewardItemsGetCollection")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **page** | **kotlin.Int**| The collection page number | [optional] [default to 1] |

### Return type

[**kotlin.collections.List&lt;RewardItemRewardItemitemread&gt;**](RewardItemRewardItemitemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/ld+json

<a id="apiRewardItemsIdDelete"></a>
# **apiRewardItemsIdDelete**
> apiRewardItemsIdDelete(id)

Removes the RewardItem resource.

Removes the RewardItem resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = RewardItemApi()
val id : kotlin.String = id_example // kotlin.String | RewardItem identifier
try {
    apiInstance.apiRewardItemsIdDelete(id)
} catch (e: ClientException) {
    println("4xx response calling RewardItemApi#apiRewardItemsIdDelete")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling RewardItemApi#apiRewardItemsIdDelete")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **kotlin.String**| RewardItem identifier | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a id="apiRewardItemsIdGet"></a>
# **apiRewardItemsIdGet**
> RewardItemRewardItemreadRewardItemitemread apiRewardItemsIdGet(id)

Retrieves a RewardItem resource.

Retrieves a RewardItem resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = RewardItemApi()
val id : kotlin.String = id_example // kotlin.String | RewardItem identifier
try {
    val result : RewardItemRewardItemreadRewardItemitemread = apiInstance.apiRewardItemsIdGet(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling RewardItemApi#apiRewardItemsIdGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling RewardItemApi#apiRewardItemsIdGet")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **kotlin.String**| RewardItem identifier | |

### Return type

[**RewardItemRewardItemreadRewardItemitemread**](RewardItemRewardItemreadRewardItemitemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/ld+json

<a id="apiRewardItemsIdPatch"></a>
# **apiRewardItemsIdPatch**
> RewardItemRewardItemreadRewardItemitemread apiRewardItemsIdPatch(id, rewardItemRewardItemwrite)

Updates the RewardItem resource.

Updates the RewardItem resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = RewardItemApi()
val id : kotlin.String = id_example // kotlin.String | RewardItem identifier
val rewardItemRewardItemwrite : RewardItemRewardItemwrite =  // RewardItemRewardItemwrite | The updated RewardItem resource
try {
    val result : RewardItemRewardItemreadRewardItemitemread = apiInstance.apiRewardItemsIdPatch(id, rewardItemRewardItemwrite)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling RewardItemApi#apiRewardItemsIdPatch")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling RewardItemApi#apiRewardItemsIdPatch")
    e.printStackTrace()
}
```

### Parameters
| **id** | **kotlin.String**| RewardItem identifier | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **rewardItemRewardItemwrite** | [**RewardItemRewardItemwrite**](RewardItemRewardItemwrite.md)| The updated RewardItem resource | |

### Return type

[**RewardItemRewardItemreadRewardItemitemread**](RewardItemRewardItemreadRewardItemitemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/merge-patch+json
 - **Accept**: application/json, application/ld+json

<a id="apiRewardItemsPost"></a>
# **apiRewardItemsPost**
> RewardItemRewardItemreadRewardItemitemread apiRewardItemsPost(rewardItemRewardItemcreateRewardItemwrite)

Creates a RewardItem resource.

Creates a RewardItem resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = RewardItemApi()
val rewardItemRewardItemcreateRewardItemwrite : RewardItemRewardItemcreateRewardItemwrite =  // RewardItemRewardItemcreateRewardItemwrite | The new RewardItem resource
try {
    val result : RewardItemRewardItemreadRewardItemitemread = apiInstance.apiRewardItemsPost(rewardItemRewardItemcreateRewardItemwrite)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling RewardItemApi#apiRewardItemsPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling RewardItemApi#apiRewardItemsPost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **rewardItemRewardItemcreateRewardItemwrite** | [**RewardItemRewardItemcreateRewardItemwrite**](RewardItemRewardItemcreateRewardItemwrite.md)| The new RewardItem resource | |

### Return type

[**RewardItemRewardItemreadRewardItemitemread**](RewardItemRewardItemreadRewardItemitemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/ld+json

