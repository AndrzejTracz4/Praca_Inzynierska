# CategoryApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**apiCategoriesGetCollection**](CategoryApi.md#apiCategoriesGetCollection) | **GET** /api/categories | Retrieves the collection of Category resources. |
| [**apiCategoriesIdDelete**](CategoryApi.md#apiCategoriesIdDelete) | **DELETE** /api/categories/{id} | Removes the Category resource. |
| [**apiCategoriesIdGet**](CategoryApi.md#apiCategoriesIdGet) | **GET** /api/categories/{id} | Retrieves a Category resource. |
| [**apiCategoriesIdPatch**](CategoryApi.md#apiCategoriesIdPatch) | **PATCH** /api/categories/{id} | Updates the Category resource. |
| [**apiCategoriesPost**](CategoryApi.md#apiCategoriesPost) | **POST** /api/categories | Creates a Category resource. |


<a id="apiCategoriesGetCollection"></a>
# **apiCategoriesGetCollection**
> kotlin.collections.List&lt;CategoryCategoryitemread&gt; apiCategoriesGetCollection(page)

Retrieves the collection of Category resources.

Retrieves the collection of Category resources.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = CategoryApi()
val page : kotlin.Int = 56 // kotlin.Int | The collection page number
try {
    val result : kotlin.collections.List<CategoryCategoryitemread> = apiInstance.apiCategoriesGetCollection(page)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CategoryApi#apiCategoriesGetCollection")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CategoryApi#apiCategoriesGetCollection")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **page** | **kotlin.Int**| The collection page number | [optional] [default to 1] |

### Return type

[**kotlin.collections.List&lt;CategoryCategoryitemread&gt;**](CategoryCategoryitemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/ld+json

<a id="apiCategoriesIdDelete"></a>
# **apiCategoriesIdDelete**
> apiCategoriesIdDelete(id)

Removes the Category resource.

Removes the Category resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = CategoryApi()
val id : kotlin.String = id_example // kotlin.String | Category identifier
try {
    apiInstance.apiCategoriesIdDelete(id)
} catch (e: ClientException) {
    println("4xx response calling CategoryApi#apiCategoriesIdDelete")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CategoryApi#apiCategoriesIdDelete")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **kotlin.String**| Category identifier | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a id="apiCategoriesIdGet"></a>
# **apiCategoriesIdGet**
> CategoryCategoryreadCategoryitemread apiCategoriesIdGet(id)

Retrieves a Category resource.

Retrieves a Category resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = CategoryApi()
val id : kotlin.String = id_example // kotlin.String | Category identifier
try {
    val result : CategoryCategoryreadCategoryitemread = apiInstance.apiCategoriesIdGet(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CategoryApi#apiCategoriesIdGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CategoryApi#apiCategoriesIdGet")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **kotlin.String**| Category identifier | |

### Return type

[**CategoryCategoryreadCategoryitemread**](CategoryCategoryreadCategoryitemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/ld+json

<a id="apiCategoriesIdPatch"></a>
# **apiCategoriesIdPatch**
> CategoryCategoryreadCategoryitemread apiCategoriesIdPatch(id, categoryCategorywriteCategoryupdate)

Updates the Category resource.

Updates the Category resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = CategoryApi()
val id : kotlin.String = id_example // kotlin.String | Category identifier
val categoryCategorywriteCategoryupdate : CategoryCategorywriteCategoryupdate =  // CategoryCategorywriteCategoryupdate | The updated Category resource
try {
    val result : CategoryCategoryreadCategoryitemread = apiInstance.apiCategoriesIdPatch(id, categoryCategorywriteCategoryupdate)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CategoryApi#apiCategoriesIdPatch")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CategoryApi#apiCategoriesIdPatch")
    e.printStackTrace()
}
```

### Parameters
| **id** | **kotlin.String**| Category identifier | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **categoryCategorywriteCategoryupdate** | [**CategoryCategorywriteCategoryupdate**](CategoryCategorywriteCategoryupdate.md)| The updated Category resource | |

### Return type

[**CategoryCategoryreadCategoryitemread**](CategoryCategoryreadCategoryitemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/merge-patch+json
 - **Accept**: application/json, application/ld+json

<a id="apiCategoriesPost"></a>
# **apiCategoriesPost**
> CategoryCategoryreadCategoryitemread apiCategoriesPost(categoryCategorycreateCategorywrite)

Creates a Category resource.

Creates a Category resource.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = CategoryApi()
val categoryCategorycreateCategorywrite : CategoryCategorycreateCategorywrite =  // CategoryCategorycreateCategorywrite | The new Category resource
try {
    val result : CategoryCategoryreadCategoryitemread = apiInstance.apiCategoriesPost(categoryCategorycreateCategorywrite)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CategoryApi#apiCategoriesPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CategoryApi#apiCategoriesPost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **categoryCategorycreateCategorywrite** | [**CategoryCategorycreateCategorywrite**](CategoryCategorycreateCategorywrite.md)| The new Category resource | |

### Return type

[**CategoryCategoryreadCategoryitemread**](CategoryCategoryreadCategoryitemread.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/ld+json

