# SettingsApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**apiSettingsGetCollection**](SettingsApi.md#apiSettingsGetCollection) | **GET** /api/settings | Retrieves the collection of Settings resources. |
| [**apiSettingsIdDelete**](SettingsApi.md#apiSettingsIdDelete) | **DELETE** /api/settings/{id} | Removes the Settings resource. |
| [**apiSettingsIdGet**](SettingsApi.md#apiSettingsIdGet) | **GET** /api/settings/{id} | Retrieves a Settings resource. |
| [**apiSettingsIdPatch**](SettingsApi.md#apiSettingsIdPatch) | **PATCH** /api/settings/{id} | Updates the Settings resource. |
| [**apiSettingsPost**](SettingsApi.md#apiSettingsPost) | **POST** /api/settings | Creates a Settings resource. |


<a id="apiSettingsGetCollection"></a>
# **apiSettingsGetCollection**
> kotlin.collections.List&lt;SettingsSettingsitemread&gt; apiSettingsGetCollection(page)

Retrieves the collection of Settings resources.

Retrieves the collection of Settings resources.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = SettingsApi()
val page : kotlin.Int = 56 // kotlin.Int | The collection page number
try {
    val result : kotlin.collections.List<SettingsSettingsitemread> = apiInstance.apiSettingsGetCollection(page)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling SettingsApi#apiSettingsGetCollection")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling SettingsApi#apiSettingsGetCollection")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **page** | **kotlin.Int**| The collection page number | [optional] [default to 1] |

### Return type

[**kotlin.collections.List&lt;SettingsSettingsitemread&gt;**](SettingsSettingsitemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/ld+json

<a id="apiSettingsIdDelete"></a>
# **apiSettingsIdDelete**
> apiSettingsIdDelete(id)

Removes the Settings resource.

Removes the Settings resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = SettingsApi()
val id : kotlin.String = id_example // kotlin.String | Settings identifier
try {
    apiInstance.apiSettingsIdDelete(id)
} catch (e: ClientException) {
    println("4xx response calling SettingsApi#apiSettingsIdDelete")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling SettingsApi#apiSettingsIdDelete")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **kotlin.String**| Settings identifier | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a id="apiSettingsIdGet"></a>
# **apiSettingsIdGet**
> SettingsSettingsreadSettingsitemread apiSettingsIdGet(id)

Retrieves a Settings resource.

Retrieves a Settings resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = SettingsApi()
val id : kotlin.String = id_example // kotlin.String | Settings identifier
try {
    val result : SettingsSettingsreadSettingsitemread = apiInstance.apiSettingsIdGet(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling SettingsApi#apiSettingsIdGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling SettingsApi#apiSettingsIdGet")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **kotlin.String**| Settings identifier | |

### Return type

[**SettingsSettingsreadSettingsitemread**](SettingsSettingsreadSettingsitemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/ld+json

<a id="apiSettingsIdPatch"></a>
# **apiSettingsIdPatch**
> SettingsSettingsreadSettingsitemread apiSettingsIdPatch(id, settingsSettingswrite)

Updates the Settings resource.

Updates the Settings resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = SettingsApi()
val id : kotlin.String = id_example // kotlin.String | Settings identifier
val settingsSettingswrite : SettingsSettingswrite =  // SettingsSettingswrite | The updated Settings resource
try {
    val result : SettingsSettingsreadSettingsitemread = apiInstance.apiSettingsIdPatch(id, settingsSettingswrite)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling SettingsApi#apiSettingsIdPatch")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling SettingsApi#apiSettingsIdPatch")
    e.printStackTrace()
}
```

### Parameters
| **id** | **kotlin.String**| Settings identifier | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **settingsSettingswrite** | [**SettingsSettingswrite**](SettingsSettingswrite.md)| The updated Settings resource | |

### Return type

[**SettingsSettingsreadSettingsitemread**](SettingsSettingsreadSettingsitemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/merge-patch+json
 - **Accept**: application/json, application/ld+json

<a id="apiSettingsPost"></a>
# **apiSettingsPost**
> SettingsSettingsreadSettingsitemread apiSettingsPost(settingsSettingscreateSettingswrite)

Creates a Settings resource.

Creates a Settings resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = SettingsApi()
val settingsSettingscreateSettingswrite : SettingsSettingscreateSettingswrite =  // SettingsSettingscreateSettingswrite | The new Settings resource
try {
    val result : SettingsSettingsreadSettingsitemread = apiInstance.apiSettingsPost(settingsSettingscreateSettingswrite)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling SettingsApi#apiSettingsPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling SettingsApi#apiSettingsPost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **settingsSettingscreateSettingswrite** | [**SettingsSettingscreateSettingswrite**](SettingsSettingscreateSettingswrite.md)| The new Settings resource | |

### Return type

[**SettingsSettingsreadSettingsitemread**](SettingsSettingsreadSettingsitemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/ld+json

