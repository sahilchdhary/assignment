package com.expedia.assignment.dashboard.service;

import com.expedia.assignment.dashboard.exception.FootballGameException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
public class CommandServiceTest
{
	@InjectMocks
	private CommandService commandService;

	@Mock
	private GameService gameService;
	@Mock
	private ScoreService scoreService;

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
		//When
		final String printStringCommand = "Swim";
		commandService.executeCommand(printStringCommand);
		//Then
		verify(scoreService, never()).printScore();
	}

}
