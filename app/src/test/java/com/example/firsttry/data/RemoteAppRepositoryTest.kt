package com.example.firsttry.data

import com.example.firsttry.data.mapper.AppDetailsMapper
import com.example.firsttry.data.mapper.AppMapper
import com.example.firsttry.data.network.ApiFactory
import com.example.firsttry.data.repository.RemoteAppRepository
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import java.util.concurrent.TimeUnit

class RemoteAppRepositoryTest {
    private lateinit var server: MockWebServer
    private lateinit var repository: RemoteAppRepository
    private val details = """{"id":"app-42","name":"Server App","description":"Full description","category":"Education","iconUrl":"https://example.com/icon.png","developer":"Developer","ageRating":3,"size":8.2,"screenshotUrlList":["https://example.com/screen.png"]}"""

    @Before fun setUp() {
        server = MockWebServer()
        server.start()
        repository = RemoteAppRepository(ApiFactory.create(server.url("/").toString()), AppMapper(), AppDetailsMapper())
    }

    @After fun tearDown() { server.shutdown() }

    @Test fun catalogUsesGetAndDisplaysOnlyServerData() = runTest {
        server.enqueue(MockResponse().setBody("""[{"id":"remote-id","name":"From network","description":"Remote description","category":"Music","iconUrl":"https://example.com/image.png"}]"""))
        val apps = repository.getApps()
        val request = server.takeRequest(1, TimeUnit.SECONDS)!!
        assertEquals("GET", request.method)
        assertEquals("/catalog", request.path)
        assertEquals(1, apps.size)
        assertEquals("remote-id", apps.single().id)
        assertEquals("From network", apps.single().name)
        assertEquals("https://example.com/image.png", apps.single().iconUrl)
    }

    @Test fun detailsUsesRequestedIdAndMapsAllFields() = runTest {
        server.enqueue(MockResponse().setBody(details))
        val app = repository.getAppDetails("app-42")
        assertEquals("/app-42", server.takeRequest(1, TimeUnit.SECONDS)!!.path)
        assertEquals("app-42", app.id)
        assertEquals("Full description", app.description)
        assertEquals("Developer", app.developer)
        assertEquals(3, app.ageRating)
        assertEquals(8.2, app.size!!, 0.001)
        assertEquals(listOf("https://example.com/screen.png"), app.screenshotUrls)
        assertEquals(1, server.requestCount)
    }

    @Test fun detailsFallsBackOnlyAfterNotFound() = runTest {
        server.enqueue(MockResponse().setResponseCode(404))
        server.enqueue(MockResponse().setBody(details))
        assertEquals("app-42", repository.getAppDetails("app-42").id)
        assertEquals("/app-42", server.takeRequest(1, TimeUnit.SECONDS)!!.path)
        assertEquals("/catalog/app-42", server.takeRequest(1, TimeUnit.SECONDS)!!.path)
    }

    @Test fun serverFailureIsNotReplacedWithLocalData() = runTest {
        server.enqueue(MockResponse().setResponseCode(500))
        try {
            repository.getApps()
            fail("Expected HTTP failure")
        } catch (exception: HttpException) {
            assertEquals(500, exception.code())
        }
    }

    @Test fun detailsDoesNotHideServerFailure() = runTest {
        server.enqueue(MockResponse().setResponseCode(503))
        try {
            repository.getAppDetails("app-42")
            fail("Expected HTTP failure")
        } catch (exception: HttpException) {
            assertEquals(503, exception.code())
            assertEquals(1, server.requestCount)
        }
    }

    @Test fun emptyCatalogIsValid() = runTest {
        server.enqueue(MockResponse().setBody("[]"))
        assertTrue(repository.getApps().isEmpty())
    }

    @Test fun invalidJsonFailsInsteadOfShowingLocalCatalog() = runTest {
        server.enqueue(MockResponse().setBody("not json"))
        var failed = false
        try { repository.getApps() } catch (_: Exception) { failed = true }
        assertTrue(failed)
    }
}
