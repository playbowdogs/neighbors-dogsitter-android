package com.playbowdogs.neighbors_dogsitter_android.data.repository

import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.playbowdogs.neighbors_dogsitter_android.data.api.PBApiService
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class LoginRepositoryTest {
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var apiService: PBApiService
    @MockK
    private lateinit var sharedPreferences: SharedPreferences
    @MockK
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor
    @MockK
    private lateinit var repo: LoginRepository


    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        apiService = mockk()
        sharedPreferences = mockk()
        sharedPreferencesEditor = mockk()
        repo = LoginRepository(apiService, sharedPreferences, sharedPreferencesEditor)
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun getLogin() {
    }

    @Test
    fun returnAuthToken() {
    }

    @Test
    fun saveAuthToken() {
        val returnedAuth = runBlocking {
            repo.saveAuthToken("testToken")
        }
        assert(returnedAuth == "testToken")
    }
}