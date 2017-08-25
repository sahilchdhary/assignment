package com.expedia.assignment.dashboard.service;

import com.expedia.assignment.dashboard.DashboardApplication;
import com.expedia.assignment.dashboard.domain.Game;
import com.expedia.assignment.dashboard.repository.GameRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DashboardApplication.class)
public class CommandIntegrationTest
{
	private static final String START_COMMAND = "Start: 'England' vs. 'Germany'";
	private static final String UPDATE_SCORE_ROONEY_GOAL1_COMMAND = "11 'England' Rooney";
	private static final String UPDATE_SCORE_ROONEY_GOAL2_COMMAND = "25 'England' Rooney";
	private static final String UPDATE_SCORE_GERD_GOAL1_COMMAND = "55 'Germany' Gerd";
	private static final String PRINT_SCORE_COMMAND = "print";
	private static final String END = "End";

	@Autowired
	private CommandService commandService;
	@Autowired
	private GameRepository gameRepository;

	@Test
	public void testStartUpdatePrintEndCommand()
	{
		commandService.executeCommand(START_COMMAND);
		final Game runningGame = gameRepository.getRunningGame().get();
		assertEquals("England", runningGame.getHomeTeam().getName());
		assertEquals("Germany", runningGame.getAwayTeam().getName());
		assertEquals(0, runningGame.getHomeTeam().getGoals().size());

		commandService.executeCommand(UPDATE_SCORE_ROONEY_GOAL1_COMMAND);
		assertEquals(1, runningGame.getHomeTeam().getGoals().size());
		assertEquals("Rooney", runningGame.getHomeTeam().getGoals().get(0).getPlayer().getName());

		commandService.executeCommand(UPDATE_SCORE_ROONEY_GOAL2_COMMAND);
		assertEquals(2, runningGame.getHomeTeam().getGoals().size());
		assertTrue(runningGame.getHomeTeam().getGoals().stream().allMatch(goal -> "Rooney".equals(goal.getPlayer().getName())));

		commandService.executeCommand(UPDATE_SCORE_GERD_GOAL1_COMMAND);
		assertEquals(1, runningGame.getAwayTeam().getGoals().size());
		assertEquals("Gerd", runningGame.getAwayTeam().getGoals().get(0).getPlayer().getName());

		commandService.executeCommand(PRINT_SCORE_COMMAND);
		assertEquals("England 2 (Rooney 11' Rooney 25') vs. Germany 1 (Gerd 55')",
				gameRepository.getRunningGame().get().toString());

		commandService.executeCommand(END);
		assertFalse(gameRepository.getRunningGame().isPresent());
	}
}
