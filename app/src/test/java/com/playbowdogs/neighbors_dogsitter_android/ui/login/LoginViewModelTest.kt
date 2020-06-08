package com.playbowdogs.neighbors_dogsitter_android.ui.login

import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.playbowdogs.neighbors_dogsitter_android.data.api.PBApiService
import com.playbowdogs.neighbors_dogsitter_android.data.repository.LoginRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class LoginViewModelTest {
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val scope: CoroutineScope = CoroutineScope(Job() + testCoroutineDispatcher)

    @MockK
    private lateinit var apiService: PBApiService
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor
    private lateinit var repo: LoginRepository
    private lateinit var viewModel: LoginViewModel
    private lateinit var authTokenObserver: Observer<String>

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testCoroutineDispatcher)

        apiService = mockk()
        sharedPreferences = mockk()
        sharedPreferencesEditor = mockk()
        repo = LoginRepository(apiService, sharedPreferences, sharedPreferencesEditor)
        viewModel = LoginViewModel(repo, scope)
        authTokenObserver = mockk()
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun `Check auth token for blank string`() = runBlockingTest {

        viewModel.saveAuthToken("testToken")

        delay(5000)

        assert(viewModel.sharedPrefAuthToken.value == null)

//        viewModel.sharedPrefAuthToken.observeForever {
//            if (it == null) {
//                return@observeForever
//            }
//
//            assert(it == "testToken")
//        }
    }
}