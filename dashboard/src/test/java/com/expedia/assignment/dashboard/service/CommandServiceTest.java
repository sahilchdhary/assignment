package com.expedia.assignment.dashboard.service;

import java.util.Optional;

import com.expedia.assignment.dashboard.domain.Game;
import com.expedia.assignment.dashboard.exception.FootballGameException;
import com.expedia.assignment.dashboard.repository.GameRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
public class CommandServiceTest
{
	@InjectMocks
	private CommandService commandService;

	@Mock
	private GameService gameService;
	@Mock
	private GameRepository gameRepository;
	@Mock
	private ScoreService scoreService;
	@Mock
	public Game runningGame;

	@Test
	public void shouldStartGame()
	{
		//When
		final String startStringCommand = "Start: 'England' vs. 'Germany'";
		commandService.executeCommand(startStringCommand);
		//Then
		verify(gameService).startGame(startStringCommand);
	}

	@Test
	public void shouldUpdateScore()
	{
		//When
		final String updateStringCommand = "11 'England' Beckham";
		commandService.executeCommand(updateStringCommand);
		//Then
		verify(scoreService).updateScore(updateStringCommand);
	}

	@Test
	public void shouldPrintScore()
	{
		//When
		final String printStringCommand = "print";
		commandService.executeCommand(printStringCommand);
		//Then
		verify(scoreService).printScore();
	}

	@Test
	public void shouldEndGame()
	{
		//When
		final String endStringCommand = "End";
		commandService.executeCommand(endStringCommand);
		//Then
		verify(gameService).endGame();
	}

	@Test(expected = FootballGameException.class)
	public void shouldThrowExceptionOnInvalidCommand()
	{
		//Given
		when(gameRepository.getRunningGame()).thenReturn(Optional.of(runningGame));
		//When
		final String printStringCommand = "Swim";
		commandService.executeCommand(printStringCommand);
		//Then
		verify(scoreService, never()).printScore();
	}

}
