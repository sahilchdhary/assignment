package com.expedia.assignment.dashboard.service;

import java.util.Optional;

import com.expedia.assignment.dashboard.domain.Game;
import com.expedia.assignment.dashboard.domain.GameStatus;
import com.expedia.assignment.dashboard.domain.Team;
import com.expedia.assignment.dashboard.exception.FootballGameException;
import com.expedia.assignment.dashboard.repository.GameRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
public class ScoreServiceTest
{
	@InjectMocks
	private ScoreService scoreService;

	@Mock
	private GameRepository gameRepository;

	private Team england;
	private Team germany;

	@Before
	public void setUp()
	{
		england = Team.builder().name("England").build();
		germany = Team.builder().name("Germany").build();
		final Game runningGame = Game.builder().homeTeam(england).awayTeam(germany).status(GameStatus.RUNNING).build();
		when(gameRepository.getRunningGame()).thenReturn(Optional.of(runningGame));
	}

	@Test
	public void shouldUpdateScoreOnHomeTeam()
	{
		//When
		final String updateScoreCommandString = "11 'England' Rooney";
		scoreService.updateScore(updateScoreCommandString);
		//Then
		assertEquals(1, england.getGoals().size());
		assertEquals("Rooney", england.getGoals().get(0).getPlayer().getName());
	}

	@Test
	public void shouldUpdateScoreOnAwayTeam()
	{
		//When
		final String updateScoreCommandString = "11 'Germany' Gerd";
		scoreService.updateScore(updateScoreCommandString);
		//Then
		assertEquals(1, germany.getGoals().size());
		assertEquals("Gerd", germany.getGoals().get(0).getPlayer().getName());
	}

	@Test(expected = FootballGameException.class)
	public void shouldNotUpdateScoreOnInvalidTeam()
	{
		//When
		final String updateScoreCommandString = "11 'Brazil' Ronaldo";
		scoreService.updateScore(updateScoreCommandString);
	}

	@Test(expected = FootballGameException.class)
	public void shouldNotUpdateScoreOnNoRunningGame()
	{
		//Given
		when(gameRepository.getRunningGame()).thenReturn(Optional.empty());
		//When
		final String updateScoreCommandString = "11 'England' Rooney";
		scoreService.updateScore(updateScoreCommandString);
	}

	@Test(expected = FootballGameException.class)
	public void shouldNotPrintScore()
	{
		//Given
		when(gameRepository.getRunningGame()).thenReturn(Optional.empty());
		//When
		scoreService.printScore();
	}

}
