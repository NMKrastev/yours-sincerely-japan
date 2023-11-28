package com.yourssincerelyjapan.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShutdownHandlerTest {

    @Mock
    private SessionRegistry sessionRegistry;

    @Mock
    private ShutdownHandler shutdownHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        shutdownHandler = new ShutdownHandler(sessionRegistry);
    }

    @Test
    void onShutdown() {

        when(sessionRegistry.getAllPrincipals()).thenReturn(Collections.singletonList("user1"));

        // Mock getAllSessions method
        SessionInformation sessionInformation = mock(SessionInformation.class);
        when(sessionRegistry.getAllSessions("user1", false))
                .thenReturn(Collections.singletonList(sessionInformation));

        // Test onShutdown method
        shutdownHandler.onShutdown();

        // Verify that sessionRegistry.getAllPrincipals is called
        verify(sessionRegistry, times(1)).getAllPrincipals();
        // Verify that sessionRegistry.getAllSessions is called
        verify(sessionRegistry, times(1)).getAllSessions("user1", false);
        // Verify that SessionInformation.expireNow is called
        verify(sessionInformation, times(1)).expireNow();
    }
}