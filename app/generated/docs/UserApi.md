# UserApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**apiUsersGetCollection**](UserApi.md#apiUsersGetCollection) | **GET** /api/users | Retrieves the collection of User resources. |
| [**apiUsersIdDelete**](UserApi.md#apiUsersIdDelete) | **DELETE** /api/users/{id} | Removes the User resource. |
| [**apiUsersIdGet**](UserApi.md#apiUsersIdGet) | **GET** /api/users/{id} | Retrieves a User resource. |
| [**apiUsersIdPatch**](UserApi.md#apiUsersIdPatch) | **PATCH** /api/users/{id} | Updates the User resource. |
| [**apiUsersPost**](UserApi.md#apiUsersPost) | **POST** /api/users | Creates a User resource. |


<a id="apiUsersGetCollection"></a>
# **apiUsersGetCollection**
> kotlin.collections.List&lt;UserUseritemread&gt; apiUsersGetCollection(page)

Retrieves the collection of User resources.

Retrieves the collection of User resources.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = UserApi()
val page : kotlin.Int = 56 // kotlin.Int | The collection page number
try {
    val result : kotlin.collections.List<UserUseritemread> = apiInstance.apiUsersGetCollection(page)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UserApi#apiUsersGetCollection")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#apiUsersGetCollection")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **page** | **kotlin.Int**| The collection page number | [optional] [default to 1] |

### Return type

[**kotlin.collections.List&lt;UserUseritemread&gt;**](UserUseritemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/ld+json

<a id="apiUsersIdDelete"></a>
# **apiUsersIdDelete**
> apiUsersIdDelete(id)

Removes the User resource.

Removes the User resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = UserApi()
val id : kotlin.String = id_example // kotlin.String | User identifier
try {
    apiInstance.apiUsersIdDelete(id)
} catch (e: ClientException) {
    println("4xx response calling UserApi#apiUsersIdDelete")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#apiUsersIdDelete")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **kotlin.String**| User identifier | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a id="apiUsersIdGet"></a>
# **apiUsersIdGet**
> UserUserreadUseritemread apiUsersIdGet(id)

Retrieves a User resource.

Retrieves a User resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = UserApi()
val id : kotlin.String = id_example // kotlin.String | User identifier
try {
    val result : UserUserreadUseritemread = apiInstance.apiUsersIdGet(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UserApi#apiUsersIdGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#apiUsersIdGet")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **kotlin.String**| User identifier | |

### Return type

[**UserUserreadUseritemread**](UserUserreadUseritemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/ld+json

<a id="apiUsersIdPatch"></a>
# **apiUsersIdPatch**
> UserUserreadUseritemread apiUsersIdPatch(id, userUserwrite)

Updates the User resource.

Updates the User resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = UserApi()
val id : kotlin.String = id_example // kotlin.String | User identifier
val userUserwrite : UserUserwrite =  // UserUserwrite | The updated User resource
try {
    val result : UserUserreadUseritemread = apiInstance.apiUsersIdPatch(id, userUserwrite)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UserApi#apiUsersIdPatch")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#apiUsersIdPatch")
    e.printStackTrace()
}
```

### Parameters
| **id** | **kotlin.String**| User identifier | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **userUserwrite** | [**UserUserwrite**](UserUserwrite.md)| The updated User resource | |

### Return type

[**UserUserreadUseritemread**](UserUserreadUseritemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/merge-patch+json
 - **Accept**: application/json, application/ld+json

<a id="apiUsersPost"></a>
# **apiUsersPost**
> UserUserreadUseritemread apiUsersPost(userUsercreateUserwrite)

Creates a User resource.

Creates a User resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = UserApi()
val userUsercreateUserwrite : UserUsercreateUserwrite =  // UserUsercreateUserwrite | The new User resource
try {
    val result : UserUserreadUseritemread = apiInstance.apiUsersPost(userUsercreateUserwrite)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UserApi#apiUsersPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#apiUsersPost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **userUsercreateUserwrite** | [**UserUsercreateUserwrite**](UserUsercreateUserwrite.md)| The new User resource | |

### Return type

[**UserUserreadUseritemread**](UserUserreadUseritemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/ld+json

