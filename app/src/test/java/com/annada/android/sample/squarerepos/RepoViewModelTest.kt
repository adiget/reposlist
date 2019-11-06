package com.annada.android.sample.squarerepos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.annada.android.sample.squarerepos.db.entities.Repo
import com.annada.android.sample.squarerepos.ui.RepoViewModel
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentCaptor
import org.mockito.Mockito

@RunWith(JUnit4::class)
class RepoViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var sut: RepoViewModel

    @Before
    @Throws(Exception::class)
    fun setUp() {
        sut = RepoViewModel()
    }

    @Test
    fun `test members updated when model changes`() {
        val repo = Repo(1L, "dummy_name", "dummy_desc")
        sut.bind(repo)

        Assert.assertEquals(
            "repository name should be dummy_name",
            "dummy_name",
            sut.getRepoName().value
        )
        Assert.assertEquals(
            "repository description should be dummy_desc",
            "dummy_desc",
            sut.getRepoDesc().value
        )
    }

    @Test
    fun `test livedata notifies when it is observed`() {
        //Observe LiveData
        val observer = mock<Observer<String>>()
        sut.getRepoName().observeForever(observer)
        sut.getRepoDesc().observeForever(observer)

        val argumentCaptor = ArgumentCaptor.forClass(String::class.java)

        val repo = Repo(1L, "dummy_name1", "dummy_desc1")
        sut.bind(repo)

        Assert.assertEquals(
            "repository name should be dummy_name1",
            "dummy_name1",
            sut.getRepoName().value
        )

        Assert.assertEquals(
            "repository description should be dummy_desc1",
            "dummy_desc1",
            sut.getRepoDesc().value
        )

        //Check LiveData observer
        argumentCaptor.run {
            Mockito.verify(observer, Mockito.times(2)).onChanged(capture())
        }
    }

}