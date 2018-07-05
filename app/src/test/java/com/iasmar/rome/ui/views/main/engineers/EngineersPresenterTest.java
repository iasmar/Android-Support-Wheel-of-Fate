package com.iasmar.rome.ui.views.main.engineers;

import com.google.common.collect.Lists;
import com.iasmar.rome.data.modules.Engineer;
import com.iasmar.rome.data.modules.ModulesHolder;
import com.iasmar.rome.data.repositories.engineers.EngineersRepository;
import com.iasmar.rome.util.schedulers.BaseSchedulerProvider;
import com.iasmar.rome.util.schedulers.ImmediateSchedulerProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * Unit tests for the implementation of {@link EngineersPresenter}
 *
 * @author Asmar
 * @version 1
 * @see EngineersPresenter
 * @since 1.0
 */
public class EngineersPresenterTest {

    // The list of engineers.
    private static List<Engineer> ENGINEERS;

    // The engineers repository.
    @Mock
    private EngineersRepository engineersRepository;

    // The engineers view.
    @Mock
    private EngineersContract.View engineersView;

    // The scheduler provider.
    private BaseSchedulerProvider schedulerProvider;

    // The engineers presenter.
    private EngineersPresenter engineersPresenter;

    /**
     * Called before each test start.
     * setup engineers presenter.
     */
    @Before
    public void setupEngineersPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Make the sure that all schedulers are immediate.
        schedulerProvider = new ImmediateSchedulerProvider();

        // Get a reference to the class under test
        engineersPresenter = new EngineersPresenter(engineersRepository, engineersView, schedulerProvider);

        // The presenter won't update the view unless it's active.
        when(engineersView.isActive()).thenReturn(true);


        // We subscribe the engineers to 3.
        ENGINEERS = Lists.newArrayList(
                new Engineer("0", "Bogdan", null),
                new Engineer("1", "Nic", null),
                new Engineer("2", "Tung", null));
    }

        @After
    public void destroyRepositoryInstance() {
        EngineersRepository.destroyInstance();
    }
    /**
     * Make sure that set presenter in the view being called.
     */
    @Test
    public void createPresenter_setsThePresenterToView() {
        // Get a reference to the class under test
        engineersPresenter = new EngineersPresenter(engineersRepository, engineersView, schedulerProvider);

        // Then the presenter is set to the view
        verify(engineersView).setPresenter(engineersPresenter);
    }

    /**
     * Load all engineers from the Repository and load them into view.
     */
    @Test
    public void loadAllEngineers_fromRepositoryAndLoadIntoView() {
        // Given an initialized BasketPresenter with initialized ModulesHolder.
        when(engineersRepository.getEngineers()).thenReturn(Flowable.just(ENGINEERS));
        // When loading of ModulesHolder is requested.
        engineersPresenter.loadEngineers(true);

        // Then loading is shown.
        verify(engineersView).setLoading(true);


        // Then show engineers are shown in UI.
        verify(engineersView).showEngineers(any(ModulesHolder.class));

        verify(engineersView).setLoading(false);



    }

    /**
     * Load all active from the Repository and load them into view.
     */
    @Test
    public void loadActiveEngineers_fromRepositoryAndLoadIntoView() {
        // Given an initialized BasketPresenter with initialized engineers
        when(engineersRepository.getEngineers()).thenReturn(Flowable.just(ENGINEERS));
        // When loading of Engineers is requested
        engineersPresenter.loadEngineers(true);

        // Then loading is shown
        verify(engineersView).setLoading(true);



        // Then show engineers are shown in UI
        verify(engineersView).showEngineers(any(ModulesHolder.class));

        // Then loading  is hidden and all engineers are shown in UI
        verify(engineersView).setLoading(false);


    }


    /**
     * Load empty list of all engineers from the Repository and load them into view.
     */
    @Test
    public void loadEmptyAllEngineers_fromRepositoryAndLoadIntoView() {
        // Given an initialized BasketPresenter with initialized ModulesHolder.
        when(engineersRepository.getEngineers()).thenReturn(Flowable.just(Lists.newArrayList()));
        // When loading of ModulesHolder is requested.
        engineersPresenter.loadEngineers(true);

        // Then loading is shown.
        verify(engineersView).setLoading(true);



        // Then show engineers are shown in UI.
        verify(engineersView).showNoEngineers();

        // Then loading  is hidden and all engineers are shown in UI.
        verify(engineersView).setLoading(false);


    }

    /**
     * Load empty list of active engineers from the Repository and load them into view.
     */
    @Test
    public void loadEmptyActiveEngineers_fromRepositoryAndLoadIntoView() {
        // Given an initialized BasketPresenter with initialized ModulesHolder.
        when(engineersRepository.getEngineers()).thenReturn(Flowable.just(Collections.emptyList()));
        // When loading of ModulesHolder is requested.
        engineersPresenter.loadEngineers(true);

        // Then loading is shown.
        verify(engineersView).setLoading(true);



        // Then show engineers are shown in UI.
        verify(engineersView).showNoEngineers();

        // Then loading  is hidden and all engineers are shown in UI.
        verify(engineersView).setLoading(false);



    }


    /**
     * Make sure when any exception appear show loading error being called.
     */
    @Test
    public void errorLoadingEngineers_ShowsError() {
        // Given that no engineers are available in the repository
        when(engineersRepository.getEngineers()).thenReturn(Flowable.error(new Exception("Test Exception")));

        // When engineers are loaded
        engineersPresenter.loadEngineers(true);

        // Then an error message is shown
        verify(engineersView).showError(anyInt());
    }
}