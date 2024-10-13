# PlayerApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**apiPlayersGetCollection**](PlayerApi.md#apiPlayersGetCollection) | **GET** /api/players | Retrieves the collection of Player resources. |
| [**apiPlayersIdDelete**](PlayerApi.md#apiPlayersIdDelete) | **DELETE** /api/players/{id} | Removes the Player resource. |
| [**apiPlayersIdGet**](PlayerApi.md#apiPlayersIdGet) | **GET** /api/players/{id} | Retrieves a Player resource. |
| [**apiPlayersIdPatch**](PlayerApi.md#apiPlayersIdPatch) | **PATCH** /api/players/{id} | Updates the Player resource. |
| [**apiPlayersPost**](PlayerApi.md#apiPlayersPost) | **POST** /api/players | Creates a Player resource. |


<a id="apiPlayersGetCollection"></a>
# **apiPlayersGetCollection**
> kotlin.collections.List&lt;PlayerPlayeritemread&gt; apiPlayersGetCollection(page)

Retrieves the collection of Player resources.

Retrieves the collection of Player resources.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = PlayerApi()
val page : kotlin.Int = 56 // kotlin.Int | The collection page number
try {
    val result : kotlin.collections.List<PlayerPlayeritemread> = apiInstance.apiPlayersGetCollection(page)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PlayerApi#apiPlayersGetCollection")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PlayerApi#apiPlayersGetCollection")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **page** | **kotlin.Int**| The collection page number | [optional] [default to 1] |

### Return type

[**kotlin.collections.List&lt;PlayerPlayeritemread&gt;**](PlayerPlayeritemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/ld+json

<a id="apiPlayersIdDelete"></a>
# **apiPlayersIdDelete**
> apiPlayersIdDelete(id)

Removes the Player resource.

Removes the Player resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = PlayerApi()
val id : kotlin.String = id_example // kotlin.String | Player identifier
try {
    apiInstance.apiPlayersIdDelete(id)
} catch (e: ClientException) {
    println("4xx response calling PlayerApi#apiPlayersIdDelete")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PlayerApi#apiPlayersIdDelete")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **kotlin.String**| Player identifier | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a id="apiPlayersIdGet"></a>
# **apiPlayersIdGet**
> PlayerPlayerreadPlayeritemread apiPlayersIdGet(id)

Retrieves a Player resource.

Retrieves a Player resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = PlayerApi()
val id : kotlin.String = id_example // kotlin.String | Player identifier
try {
    val result : PlayerPlayerreadPlayeritemread = apiInstance.apiPlayersIdGet(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PlayerApi#apiPlayersIdGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PlayerApi#apiPlayersIdGet")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **kotlin.String**| Player identifier | |

### Return type

[**PlayerPlayerreadPlayeritemread**](PlayerPlayerreadPlayeritemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/ld+json

<a id="apiPlayersIdPatch"></a>
# **apiPlayersIdPatch**
> PlayerPlayerreadPlayeritemread apiPlayersIdPatch(id, playerPlayerwrite)

Updates the Player resource.

Updates the Player resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = PlayerApi()
val id : kotlin.String = id_example // kotlin.String | Player identifier
val playerPlayerwrite : PlayerPlayerwrite =  // PlayerPlayerwrite | The updated Player resource
try {
    val result : PlayerPlayerreadPlayeritemread = apiInstance.apiPlayersIdPatch(id, playerPlayerwrite)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PlayerApi#apiPlayersIdPatch")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PlayerApi#apiPlayersIdPatch")
    e.printStackTrace()
}
```

### Parameters
| **id** | **kotlin.String**| Player identifier | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **playerPlayerwrite** | [**PlayerPlayerwrite**](PlayerPlayerwrite.md)| The updated Player resource | |

### Return type

[**PlayerPlayerreadPlayeritemread**](PlayerPlayerreadPlayeritemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/merge-patch+json
 - **Accept**: application/json, application/ld+json

<a id="apiPlayersPost"></a>
# **apiPlayersPost**
> PlayerPlayerreadPlayeritemread apiPlayersPost(playerPlayercreatePlayerwrite)

Creates a Player resource.

Creates a Player resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = PlayerApi()
val playerPlayercreatePlayerwrite : PlayerPlayercreatePlayerwrite =  // PlayerPlayercreatePlayerwrite | The new Player resource
try {
    val result : PlayerPlayerreadPlayeritemread = apiInstance.apiPlayersPost(playerPlayercreatePlayerwrite)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PlayerApi#apiPlayersPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PlayerApi#apiPlayersPost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **playerPlayercreatePlayerwrite** | [**PlayerPlayercreatePlayerwrite**](PlayerPlayercreatePlayerwrite.md)| The new Player resource | |

### Return type

[**PlayerPlayerreadPlayeritemread**](PlayerPlayerreadPlayeritemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/ld+json

