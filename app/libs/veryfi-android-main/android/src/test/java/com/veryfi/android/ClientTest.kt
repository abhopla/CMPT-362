package com.veryfi.android

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler.ExecutorWorker
import io.reactivex.plugins.RxJavaPlugins
import org.json.JSONObject
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.spy
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit


class ClientTest {

    //credentials
    private val clientId = "clientId"
    private val clientSecret = "clientSecret"
    private val username = "username"
    private val apiKey = "apiKey"
    private val receiptPath = "receipt.jpeg"

    private var client = VeryfiClientFactory.createClient(clientId, clientSecret, username, apiKey)

    private val mockResponses =
        true // Change to “false” if you want to test your personal credential

    @Test
    fun getDocumentsTest() {
        if (mockResponses) {
            client = spy(VeryfiClientFactory.createClient(clientId, clientSecret, username, apiKey))
            val bufferedReader = getFileAsBufferedReader("getDocuments.json")
            doReturn(bufferedReader).`when`(client).connect(anyOrNull())
        }
        client.getDocuments({ jsonString ->
            val jsonResponse = JSONObject(jsonString)
            assertEquals(2, jsonResponse.length())
        }, { errorMessage ->
            Assert.fail(errorMessage)
        })
    }

    @Test
    fun getDocumentTest() {
        if (mockResponses) {
            client = spy(VeryfiClientFactory.createClient(clientId, clientSecret, username, apiKey))
            val documentId = 31727276
            val bufferedReader = getFileAsBufferedReader("getDocument.json")
            doReturn(bufferedReader).`when`(client).connect(anyOrNull())
            client.getDocument(documentId.toString(), { jsonDocumentString ->
                val jsonDocumentResponse = JSONObject(jsonDocumentString)
                assertEquals(documentId, jsonDocumentResponse.getInt("id"))
            }, {errorMessage ->
                Assert.fail(errorMessage)
            })
        } else {
            client.getDocuments({ jsonString ->
                val jsonResponse = JSONObject(jsonString)
                val jsonDocuments = jsonResponse.getJSONArray("documents")
                if (jsonDocuments.length() < 1) {
                    Assert.fail("No documents in your account")
                    return@getDocuments
                }
                val documentId = jsonDocuments.getJSONObject(0).getInt("id")
                client.getDocument(documentId.toString(), { jsonDocumentString ->
                    val jsonDocumentResponse = JSONObject(jsonDocumentString)
                    assertEquals(documentId, jsonDocumentResponse.getInt("id"))
                }, { errorMessage ->
                    Assert.fail(errorMessage)
                })
            }, { errorMessage ->
                Assert.fail(errorMessage)
            })
        }
    }

    @Test
    fun processDocumentTest() {
        val categories: List<String> = listOf("Advertising & Marketing", "Automotive")
        if (mockResponses) {
            client = spy(VeryfiClientFactory.createClient(clientId, clientSecret, username, apiKey))
            val bufferedReader = getFileAsBufferedReader("processDocument.json")
            doReturn(bufferedReader).`when`(client).connect(anyOrNull())
        }
        this::class.java.classLoader?.let {
            val classLoader: ClassLoader = it
            val inputStream: InputStream = classLoader.getResourceAsStream(receiptPath)
            client.processDocument(inputStream, receiptPath, categories, true, null, {jsonString ->
                val jsonResponse = JSONObject(jsonString)
                assertEquals("In-n-out Burger", jsonResponse.getJSONObject("vendor").getString("name"))
            }, { errorMessage ->
                Assert.fail(errorMessage)
            })
        } ?: run {
            Assert.fail("Can't get class loader")
        }
    }

    @Test
    fun updateDocumentTest() {
        val notes: String
        val parameters = JSONObject()
        if (mockResponses) {
            client = spy(VeryfiClientFactory.createClient(clientId, clientSecret, username, apiKey))
            val bufferedReader = getFileAsBufferedReader("updateDocument.json")
            notes = "Note updated"
            parameters.put("notes", notes)
            val documentId = 31727276
            doReturn(bufferedReader).`when`(client).connect(anyOrNull())
            client.updateDocument(documentId.toString(), parameters, { jsonUpdateString ->
                val jsonUpdateResponse = JSONObject(jsonUpdateString)
                assertEquals(notes, jsonUpdateResponse.getString("notes"))
            }, { errorMessage ->
                Assert.fail(errorMessage)
            })
        } else {
            notes = generateRandomString()
            parameters.put("notes", notes)
            client.getDocuments({ jsonString ->
                val jsonResponse = JSONObject(jsonString)
                val jsonDocuments = jsonResponse.getJSONArray("documents")
                if (jsonDocuments.length() < 1) {
                    Assert.fail("No documents in your account")
                    return@getDocuments
                }
                val documentId = jsonDocuments.getJSONObject(0).getInt("id")
                client.updateDocument(documentId.toString(), parameters, { jsonUpdateString ->
                    val jsonUpdateResponse = JSONObject(jsonUpdateString)
                    assertEquals(notes, jsonUpdateResponse.getString("notes"))
                }, { errorMessage ->
                    Assert.fail(errorMessage)
                })
            }, { errorMessage ->
                Assert.fail(errorMessage)
            })
        }
    }

    @Test
    fun processDocumentUrlTest() {
        if (mockResponses) {
            client = spy(VeryfiClientFactory.createClient(clientId, clientSecret, username, apiKey))
            val bufferedReader = getFileAsBufferedReader("processDocument.json")
            doReturn(bufferedReader).`when`(client).connect(anyOrNull())
        }
        val url = "https://veryfi-testing-public.s3.us-west-2.amazonaws.com/receipt.jpg"
        client.processDocumentUrl(
            url,
            null,
            null,
            false,
            1,
            true,
            null,
            null,
            { jsonUrlString ->
                val jsonURLResponse = JSONObject(jsonUrlString)
                assertEquals("In-n-out Burger", jsonURLResponse.getJSONObject("vendor").getString("name"))
            }, { errorMessage ->
                Assert.fail(errorMessage)
            }
        )
    }

    @Test
    fun deleteDocumentTest() {
        if (mockResponses) {
            client = spy(VeryfiClientFactory.createClient(clientId, clientSecret, username, apiKey))
            val bufferedReader = getFileAsBufferedReader("deleteDocument.json")
            doReturn(bufferedReader).`when`(client).connect(anyOrNull())
            val id = 46247222
            client.deleteDocument(id.toString(), { jsonDeleteString ->
                val jsonDeleteResponse = JSONObject(jsonDeleteString)
                val jsonExpectedDeleteResponse =
                    JSONObject("{status:ok,message:Document has been deleted}")
                assertEquals(
                    jsonExpectedDeleteResponse.getString("status"),
                    jsonDeleteResponse.getString("status")
                )
                assertEquals(
                    jsonExpectedDeleteResponse.getString("message"),
                    jsonDeleteResponse.getString("message")
                )
            }, { errorMessage ->
                Assert.fail(errorMessage)
            })
        } else {
            val url = "https://veryfi-testing-public.s3.us-west-2.amazonaws.com/receipt.jpg"
            val categories: List<String> = listOf("Advertising & Marketing", "Automotive")
            client.processDocumentUrl(
                url,
                null,
                categories,
                false,
                1,
                true,
                null,
                null,
                { jsonUrlString ->
                    val jsonURLResponse = JSONObject(jsonUrlString)
                    val id = jsonURLResponse.getInt("id")
                    client.deleteDocument(id.toString(), { jsonDeleteString ->
                        val jsonDeleteResponse = JSONObject(jsonDeleteString)
                        val jsonExpectedDeleteResponse =
                            JSONObject("{status:ok,message:Document has been deleted}")
                        assertEquals(
                            jsonExpectedDeleteResponse.getString("status"),
                            jsonDeleteResponse.getString("status")
                        )
                        assertEquals(
                            jsonExpectedDeleteResponse.getString("message"),
                            jsonDeleteResponse.getString("message")
                        )
                    }, { errorMessage ->
                        Assert.fail(errorMessage)
                    })
                }, { errorMessage ->
                    Assert.fail(errorMessage)
                }
            )
        }
    }

    @Test
    fun processBadCredentialsTest() {

        client = VeryfiClientFactory.createClient(
            "badClientId",
            "badClientSecret",
            "badUsername",
            "badApiKey"
        )
        client.getDocuments({ jsonString ->
            val jsonResponse = JSONObject(jsonString)
            assertEquals("fail", jsonResponse.getString("status"))
        }, { errorMessage ->
            Assert.fail(errorMessage)
        })
    }

    private fun getFileAsBufferedReader(fileName: String): BufferedReader? {
        this::class.java.classLoader?.let {
            val classLoader: ClassLoader = it
            val inputStream: InputStream = classLoader.getResourceAsStream(fileName)
            return BufferedReader(InputStreamReader(inputStream, "UTF-8"))
        }
        return null
    }

    private fun generateRandomString(): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..10)
            .map { allowedChars.random() }
            .joinToString("")
    }

    @Before
    fun setUpRxSchedulers() {
        val immediate: Scheduler = object : Scheduler() {
            override fun scheduleDirect(
                @NonNull run: Runnable,
                delay: Long,
                @NonNull unit: TimeUnit
            ): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorWorker { obj: Runnable -> obj.run() }
            }
        }
        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
    }
}
