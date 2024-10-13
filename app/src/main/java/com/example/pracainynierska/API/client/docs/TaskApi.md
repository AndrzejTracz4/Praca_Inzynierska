# TaskApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**apiTasksGetCollection**](TaskApi.md#apiTasksGetCollection) | **GET** /api/tasks | Retrieves the collection of Task resources. |
| [**apiTasksIdDelete**](TaskApi.md#apiTasksIdDelete) | **DELETE** /api/tasks/{id} | Removes the Task resource. |
| [**apiTasksIdGet**](TaskApi.md#apiTasksIdGet) | **GET** /api/tasks/{id} | Retrieves a Task resource. |
| [**apiTasksIdPatch**](TaskApi.md#apiTasksIdPatch) | **PATCH** /api/tasks/{id} | Updates the Task resource. |
| [**apiTasksPost**](TaskApi.md#apiTasksPost) | **POST** /api/tasks | Creates a Task resource. |


<a id="apiTasksGetCollection"></a>
# **apiTasksGetCollection**
> kotlin.collections.List&lt;TaskTaskitemread&gt; apiTasksGetCollection(page)

Retrieves the collection of Task resources.

Retrieves the collection of Task resources.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = TaskApi()
val page : kotlin.Int = 56 // kotlin.Int | The collection page number
try {
    val result : kotlin.collections.List<TaskTaskitemread> = apiInstance.apiTasksGetCollection(page)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TaskApi#apiTasksGetCollection")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TaskApi#apiTasksGetCollection")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **page** | **kotlin.Int**| The collection page number | [optional] [default to 1] |

### Return type

[**kotlin.collections.List&lt;TaskTaskitemread&gt;**](TaskTaskitemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/ld+json

<a id="apiTasksIdDelete"></a>
# **apiTasksIdDelete**
> apiTasksIdDelete(id)

Removes the Task resource.

Removes the Task resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = TaskApi()
val id : kotlin.String = id_example // kotlin.String | Task identifier
try {
    apiInstance.apiTasksIdDelete(id)
} catch (e: ClientException) {
    println("4xx response calling TaskApi#apiTasksIdDelete")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TaskApi#apiTasksIdDelete")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **kotlin.String**| Task identifier | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a id="apiTasksIdGet"></a>
# **apiTasksIdGet**
> TaskTaskreadTaskitemread apiTasksIdGet(id)

Retrieves a Task resource.

Retrieves a Task resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = TaskApi()
val id : kotlin.String = id_example // kotlin.String | Task identifier
try {
    val result : TaskTaskreadTaskitemread = apiInstance.apiTasksIdGet(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TaskApi#apiTasksIdGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TaskApi#apiTasksIdGet")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **kotlin.String**| Task identifier | |

### Return type

[**TaskTaskreadTaskitemread**](TaskTaskreadTaskitemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/ld+json

<a id="apiTasksIdPatch"></a>
# **apiTasksIdPatch**
> TaskTaskreadTaskitemread apiTasksIdPatch(id, taskTaskwrite)

Updates the Task resource.

Updates the Task resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = TaskApi()
val id : kotlin.String = id_example // kotlin.String | Task identifier
val taskTaskwrite : TaskTaskwrite =  // TaskTaskwrite | The updated Task resource
try {
    val result : TaskTaskreadTaskitemread = apiInstance.apiTasksIdPatch(id, taskTaskwrite)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TaskApi#apiTasksIdPatch")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TaskApi#apiTasksIdPatch")
    e.printStackTrace()
}
```

### Parameters
| **id** | **kotlin.String**| Task identifier | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **taskTaskwrite** | [**TaskTaskwrite**](TaskTaskwrite.md)| The updated Task resource | |

### Return type

[**TaskTaskreadTaskitemread**](TaskTaskreadTaskitemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/merge-patch+json
 - **Accept**: application/json, application/ld+json

<a id="apiTasksPost"></a>
# **apiTasksPost**
> TaskTaskreadTaskitemread apiTasksPost(taskTaskcreateTaskwrite)

Creates a Task resource.

Creates a Task resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = TaskApi()
val taskTaskcreateTaskwrite : TaskTaskcreateTaskwrite =  // TaskTaskcreateTaskwrite | The new Task resource
try {
    val result : TaskTaskreadTaskitemread = apiInstance.apiTasksPost(taskTaskcreateTaskwrite)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TaskApi#apiTasksPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TaskApi#apiTasksPost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **taskTaskcreateTaskwrite** | [**TaskTaskcreateTaskwrite**](TaskTaskcreateTaskwrite.md)| The new Task resource | |

### Return type

[**TaskTaskreadTaskitemread**](TaskTaskreadTaskitemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/ld+json

