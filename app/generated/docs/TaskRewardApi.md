# TaskRewardApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**apiTaskRewardsGetCollection**](TaskRewardApi.md#apiTaskRewardsGetCollection) | **GET** /api/task_rewards | Retrieves the collection of TaskReward resources. |
| [**apiTaskRewardsIdDelete**](TaskRewardApi.md#apiTaskRewardsIdDelete) | **DELETE** /api/task_rewards/{id} | Removes the TaskReward resource. |
| [**apiTaskRewardsIdGet**](TaskRewardApi.md#apiTaskRewardsIdGet) | **GET** /api/task_rewards/{id} | Retrieves a TaskReward resource. |
| [**apiTaskRewardsIdPatch**](TaskRewardApi.md#apiTaskRewardsIdPatch) | **PATCH** /api/task_rewards/{id} | Updates the TaskReward resource. |
| [**apiTaskRewardsPost**](TaskRewardApi.md#apiTaskRewardsPost) | **POST** /api/task_rewards | Creates a TaskReward resource. |


<a id="apiTaskRewardsGetCollection"></a>
# **apiTaskRewardsGetCollection**
> kotlin.collections.List&lt;TaskRewardTaskRewarditemread&gt; apiTaskRewardsGetCollection(page)

Retrieves the collection of TaskReward resources.

Retrieves the collection of TaskReward resources.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = TaskRewardApi()
val page : kotlin.Int = 56 // kotlin.Int | The collection page number
try {
    val result : kotlin.collections.List<TaskRewardTaskRewarditemread> = apiInstance.apiTaskRewardsGetCollection(page)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TaskRewardApi#apiTaskRewardsGetCollection")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TaskRewardApi#apiTaskRewardsGetCollection")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **page** | **kotlin.Int**| The collection page number | [optional] [default to 1] |

### Return type

[**kotlin.collections.List&lt;TaskRewardTaskRewarditemread&gt;**](TaskRewardTaskRewarditemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/ld+json

<a id="apiTaskRewardsIdDelete"></a>
# **apiTaskRewardsIdDelete**
> apiTaskRewardsIdDelete(id)

Removes the TaskReward resource.

Removes the TaskReward resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = TaskRewardApi()
val id : kotlin.String = id_example // kotlin.String | TaskReward identifier
try {
    apiInstance.apiTaskRewardsIdDelete(id)
} catch (e: ClientException) {
    println("4xx response calling TaskRewardApi#apiTaskRewardsIdDelete")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TaskRewardApi#apiTaskRewardsIdDelete")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **kotlin.String**| TaskReward identifier | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a id="apiTaskRewardsIdGet"></a>
# **apiTaskRewardsIdGet**
> TaskRewardTaskRewardreadTaskRewarditemread apiTaskRewardsIdGet(id)

Retrieves a TaskReward resource.

Retrieves a TaskReward resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = TaskRewardApi()
val id : kotlin.String = id_example // kotlin.String | TaskReward identifier
try {
    val result : TaskRewardTaskRewardreadTaskRewarditemread = apiInstance.apiTaskRewardsIdGet(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TaskRewardApi#apiTaskRewardsIdGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TaskRewardApi#apiTaskRewardsIdGet")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **kotlin.String**| TaskReward identifier | |

### Return type

[**TaskRewardTaskRewardreadTaskRewarditemread**](TaskRewardTaskRewardreadTaskRewarditemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/ld+json

<a id="apiTaskRewardsIdPatch"></a>
# **apiTaskRewardsIdPatch**
> TaskRewardTaskRewardreadTaskRewarditemread apiTaskRewardsIdPatch(id, taskRewardTaskRewardwrite)

Updates the TaskReward resource.

Updates the TaskReward resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = TaskRewardApi()
val id : kotlin.String = id_example // kotlin.String | TaskReward identifier
val taskRewardTaskRewardwrite : TaskRewardTaskRewardwrite =  // TaskRewardTaskRewardwrite | The updated TaskReward resource
try {
    val result : TaskRewardTaskRewardreadTaskRewarditemread = apiInstance.apiTaskRewardsIdPatch(id, taskRewardTaskRewardwrite)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TaskRewardApi#apiTaskRewardsIdPatch")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TaskRewardApi#apiTaskRewardsIdPatch")
    e.printStackTrace()
}
```

### Parameters
| **id** | **kotlin.String**| TaskReward identifier | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **taskRewardTaskRewardwrite** | [**TaskRewardTaskRewardwrite**](TaskRewardTaskRewardwrite.md)| The updated TaskReward resource | |

### Return type

[**TaskRewardTaskRewardreadTaskRewarditemread**](TaskRewardTaskRewardreadTaskRewarditemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/merge-patch+json
 - **Accept**: application/json, application/ld+json

<a id="apiTaskRewardsPost"></a>
# **apiTaskRewardsPost**
> TaskRewardTaskRewardreadTaskRewarditemread apiTaskRewardsPost(taskRewardTaskRewardcreateTaskRewardwrite)

Creates a TaskReward resource.

Creates a TaskReward resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = TaskRewardApi()
val taskRewardTaskRewardcreateTaskRewardwrite : TaskRewardTaskRewardcreateTaskRewardwrite =  // TaskRewardTaskRewardcreateTaskRewardwrite | The new TaskReward resource
try {
    val result : TaskRewardTaskRewardreadTaskRewarditemread = apiInstance.apiTaskRewardsPost(taskRewardTaskRewardcreateTaskRewardwrite)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TaskRewardApi#apiTaskRewardsPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TaskRewardApi#apiTaskRewardsPost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **taskRewardTaskRewardcreateTaskRewardwrite** | [**TaskRewardTaskRewardcreateTaskRewardwrite**](TaskRewardTaskRewardcreateTaskRewardwrite.md)| The new TaskReward resource | |

### Return type

[**TaskRewardTaskRewardreadTaskRewarditemread**](TaskRewardTaskRewardreadTaskRewarditemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/ld+json

