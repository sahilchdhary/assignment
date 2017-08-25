package com.expedia.assignment.dashboard.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.expedia.assignment.dashboard.domain.Game;
import com.expedia.assignment.dashboard.domain.GameStatus;
import com.expedia.assignment.dashboard.domain.Team;
import com.expedia.assignment.dashboard.exception.FootballGameException;
import com.expedia.assignment.dashboard.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.expedia.assignment.dashboard.util.CommandPatternConstants.START_GAME_COMMAND_PATTERN;


/**
 * Service to handle Game events
 */
@Service
@RequiredArgsConstructor
public class GameService
{
	private static final String HOME_TEAM = "homeTeam";
	private static final String AWAY_TEAM = "awayTeam";

	private final GameRepository gameRepository;

	/**
	 * Starts the game.
	 *
	 * @param startGameCommandString
	 * 		the start command from user, containing home and away team information
	 */
	public void startGame(final String startGameCommandString)
	{
		if (!gameRepository.getRunningGame().isPresent())
		{
			final Team homeTeam = getTeam(startGameCommandString, HOME_TEAM);
			final Team awayTeam = getTeam(startGameCommandString, AWAY_TEAM);
			gameRepository.setRunningGame(Game.builder().homeTeam(homeTeam).awayTeam(awayTeam).status(GameStatus.RUNNING).build());
			System.out.println(String.format("%s %s %s %s", "Game started:", homeTeam.getName(), "vs.", awayTeam.getName()));
		}
		else
		{
			throw new FootballGameException("Game already in Progress");
		}
	}

	/**
	 * Completes the currently running game.
	 */
	public void endGame()
	{
		if (gameRepository.getRunningGame().isPresent())
		{
			final Game runningGame = gameRepository.getRunningGame().get();
			System.out.println(String.format("%s %s", "Final Score:", runningGame.toString()));
			runningGame.setStatus(GameStatus.COMPLETED);
			gameRepository.endRunningGame();
		}
		else
		{
			throw new FootballGameException("No game currently in progress");
		}
	}

	/**
	 * Method to extract {@link Team} for a given startGame Command and team type
	 *
	 * @param startGameCommandString
	 * 		startGame command
	 * @param teamType
	 * 		the teamType: can be {@link #HOME_TEAM} or {@link #AWAY_TEAM}
	 * @return team
	 */
	private Team getTeam(final String startGameCommandString, final String teamType)
	{
		final Matcher matcher = Pattern.compile(START_GAME_COMMAND_PATTERN).matcher(startGameCommandString);
		matcher.find();
		return Team.builder().name(matcher.group(teamType)).build();
	}
}
