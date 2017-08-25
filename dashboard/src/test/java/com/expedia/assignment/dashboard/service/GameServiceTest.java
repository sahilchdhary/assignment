package com.expedia.assignment.dashboard.service;

import java.util.Optional;

import com.expedia.assignment.dashboard.domain.Game;
import com.expedia.assignment.dashboard.exception.FootballGameException;
import com.expedia.assignment.dashboard.repository.GameRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
public class GameServiceTest
{
	@InjectMocks
	private GameService gameService;

	@Mock
	private GameRepository gameRepository;
	@Mock
	private Game runningGame;

	@Before
	public void setUp()
	{
		when(gameRepository.getRunningGame()).thenReturn(Optional.empty());
	}

	@Test
	public void shouldStartGame()
	{
		//When
		final String startStringCommand = "Start: 'England' vs. 'Germany'";
		gameService.startGame(startStringCommand);
		//Then
		verify(gameRepository).setRunningGame(any(Game.class));
	}

	@Test(expected = FootballGameException.class)
	public void shouldNotStartAlreadyRunningGame()
	{
		//Given
		when(gameRepository.getRunningGame()).thenReturn(Optional.of(runningGame));
		//When
		final String startStringCommand = "Start: 'England' vs. 'Germany'";
		gameService.startGame(startStringCommand);
		//Then
		verify(gameRepository, never()).setRunningGame(any(Game.class));
	}

	@Test
	public void shouldEndGame()
	{
		//Given
		when(gameRepository.getRunningGame()).thenReturn(Optional.of(runningGame));
		//When
		gameService.endGame();
		//Then
		verify(gameRepository).endRunningGame();
	}

	@Test(expected = FootballGameException.class)
	public void shouldThrowExceptionOnEndCommand()
	{
		//When
		gameService.endGame();
		//Then
		verify(gameRepository, never()).endRunningGame();
	}

}
