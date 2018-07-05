package com.iasmar.rome.data.repositories.engineers;

import com.google.common.collect.Lists;
import com.iasmar.rome.data.modules.Engineer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertFalse;


    /**
     * Created by Asmar on 01/7/2018.
     * <p>
     * Unit tests for the implementation of {@link EngineersRepository} the in-memory repository with cache.
     *
     * @author Asmar
     * @version 1
     * @see EngineersRepository
     * @since 1.0
     */
    public class EngineersRepositoryTest {

        // The list of engineers.
        private static List<Engineer> ENGINEERS;

        // The engineers repository.
        private EngineersRepository engineersRepository;
        // The subscriber.
        private TestSubscriber<List<Engineer>> engineersTestSubscriber;

        // The engineers remote data source.
        @Mock
        private RemoteEngineersDataSource engineersRemoteDataSource;

        /**
         * Called before each test start.
         * setup engineers repository(.
         */
        @Before
        public void setupEngineersRepository() {
            // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
            // inject the mocks in the test the initMocks method needs to be called.
            MockitoAnnotations.initMocks(this);

            // Get a reference to the class under test
            engineersRepository = EngineersRepository.getInstance(
                    engineersRemoteDataSource);

            engineersTestSubscriber = new TestSubscriber<>();
            // We subscribe the engineers to 3

            ENGINEERS =  Lists.newArrayList(
                    new Engineer("2", "Tung", null),
                    new Engineer("1", "Nic", null),
                    new Engineer("0", "Bogdan", null));




        }

        /**
         * Called after each test start.
         * destroy engineers repository(.
         */
        @After
        public void destroyRepositoryInstance() {
            EngineersRepository.destroyInstance();
        }


        /**
         * Get engineers repository caches after first subscription when engineers are available in remote storage.
         */
        @Test
        public void getEngineersFirstSubscription_fromRemote() {
            // Given that the remote data source has data available
            setEngineersAvailable(engineersRemoteDataSource, ENGINEERS);


            // When two subscriptions are set
            TestSubscriber<List<Engineer>> testSubscriber1 = new TestSubscriber<>();
            engineersRepository.getEngineers().subscribe(testSubscriber1);


            // Then engineers were only requested once from remote and local sources
            verify(engineersRemoteDataSource).getEngineers();
            assertFalse(engineersRepository.cacheIsDirty);
            testSubscriber1.assertValue(ENGINEERS);
        }

        /**
         * Get engineers with dirty cache engineers are retrieved from remote.
         */
        @Test
        public void getEngineersWithDirtyCache_engineersAreRetrievedFromRemote() {
            // Given that the remote data source has data available
            setEngineersAvailable(engineersRemoteDataSource, ENGINEERS);

            // When calling getEngineers in the repository with dirty cache
            engineersRepository.setCacheDirty(true);
            engineersRepository.getEngineers().subscribe(engineersTestSubscriber);

            assertEquals(engineersRepository.cachedEngineers.size(),ENGINEERS.size());
            // Verify the engineers from the remote data source are returned.
            verify(engineersRemoteDataSource).getEngineers();
            engineersTestSubscriber.assertValue(ENGINEERS);
        }


        /**
         * Set engineers available.
         *
         * @param dataSource the engineers data source.
         * @param engineers the list of engineers.
         */
        private void setEngineersAvailable(EngineersDataSource dataSource, List<Engineer> engineers) {
            when(dataSource.getEngineers()).thenReturn(Flowable.just(engineers).concatWith(Flowable.never()));
        }


    }