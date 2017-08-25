package com.expedia.assignment.dashboard.service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.expedia.assignment.dashboard.domain.Game;
import com.expedia.assignment.dashboard.domain.Goal;
import com.expedia.assignment.dashboard.domain.Player;
import com.expedia.assignment.dashboard.domain.Team;
import com.expedia.assignment.dashboard.exception.FootballGameException;
import com.expedia.assignment.dashboard.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.expedia.assignment.dashboard.util.CommandPatternConstants.UPDATE_SCORE_COMMAND_PATTERN;


/**
 * Service to handle scores related events
 */
@Service
@RequiredArgsConstructor
public class ScoreService
{
	private static final String SCORE_MINUTE = "scoreMinute";
	private static final String TEAM_NAME = "teamName";
	private static final String PLAYER_NAME = "playerName";
	private static final String NO_GAME_IN_PROGRESS = "No game currently in progress";

	private final GameRepository gameRepository;

	/**
	 * Updates score of the currently running game
	 *
	 * @param updateScoreCommandString
	 * 		string representing goal minute, scoring team name and player information
	 */
	public void updateScore(final String updateScoreCommandString)
	{
		if (gameRepository.getRunningGame().isPresent())
		{
			final Game runningGame = gameRepository.getRunningGame().get();
			final Map<String, String> goalInfoMap = getGoalInfo(updateScoreCommandString);
			final Team team = getTeam(runningGame, goalInfoMap.get(TEAM_NAME));
			updateScore(goalInfoMap.get(SCORE_MINUTE), team, goalInfoMap.get(PLAYER_NAME));
		}
		else
		{
			throw new FootballGameException(NO_GAME_IN_PROGRESS);
		}
	}

	/**
	 * Prints the score for the currently running {@link Game}
	 */
	public void printScore()
	{
		if (gameRepository.getRunningGame().isPresent())
		{
			System.out.println(gameRepository.getRunningGame().get().toString());
		}
		else
		{
			throw new FootballGameException(NO_GAME_IN_PROGRESS);
		}
	}

	/**
	 * Gets either {@link Game#homeTeam} or {@link Game#awayTeam} for the given {@link Team#name} and {@link Game}
	 *
	 * @param runningGame
	 * 		the {@link Game}
	 * @param teamName
	 * 		the {@link Team#name} which scored the goal
	 * @return the team
	 */
	private Team getTeam(final Game runningGame, final String teamName)
	{
		Team team;
		if (teamName.equals(runningGame.getAwayTeam().getName()))
		{
			team = runningGame.getAwayTeam();
		}
		else if (teamName.equals(runningGame.getHomeTeam().getName()))
		{
			team = runningGame.getHomeTeam();
		}
		else
		{
			throw new FootballGameException("Team: " + teamName + " is not currently playing");
		}
		return team;
	}

	/**
	 * Updates score of the currently playing {@link Team}
	 *
	 * @param scoreMinute
	 * 		the scoreMinute
	 * @param team
	 * 		the {@link Team} which scored the {@link Goal}
	 * @param playerName
	 * 		the {@link Player#name}
	 */
	private void updateScore(final String scoreMinute, final Team team, final String playerName)
	{
		final Player player = Player.builder().name(playerName).build();
		final Goal goal = Goal.builder().scoreMinute(Integer.parseInt(scoreMinute)).player(player).build();
		team.getGoals().add(goal);
	}

	/**
	 * Extracts {@link Goal#scoreMinute},{@link Team#name} and {@link Player#name} information
	 *
	 * @param updateScoreCommandString
	 * 		string representing goal minute, scoring team name and player information
	 * @return map populated with goal,team and player name
	 */
	private Map<String, String> getGoalInfo(final String updateScoreCommandString)
	{
		final Map<String, String> goalInfoMap = new HashMap<>();
		final Matcher matcher = Pattern.compile(UPDATE_SCORE_COMMAND_PATTERN).matcher(updateScoreCommandString);
		matcher.find();
		goalInfoMap.put(SCORE_MINUTE, matcher.group(SCORE_MINUTE));
		goalInfoMap.put(TEAM_NAME, matcher.group(TEAM_NAME));
		goalInfoMap.put(PLAYER_NAME, matcher.group(PLAYER_NAME));

		return goalInfoMap;
	}
}
