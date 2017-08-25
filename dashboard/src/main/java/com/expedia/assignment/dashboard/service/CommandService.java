package com.expedia.assignment.dashboard.service;

import com.expedia.assignment.dashboard.exception.FootballGameException;
import com.expedia.assignment.dashboard.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.expedia.assignment.dashboard.util.CommandPatternConstants.END_GAME_COMMAND_PATTERN;
import static com.expedia.assignment.dashboard.util.CommandPatternConstants.PRINT_SCORE_COMMAND_PATTERN;
import static com.expedia.assignment.dashboard.util.CommandPatternConstants.START_GAME_COMMAND_PATTERN;
import static com.expedia.assignment.dashboard.util.CommandPatternConstants.UPDATE_SCORE_COMMAND_PATTERN;


/**
 * Service to handle user input commands
 */
@Service
@RequiredArgsConstructor
public class CommandService
{
	private final GameService gameService;
	private final ScoreService scoreService;
	private final GameRepository gameRepository;

	public void executeCommand(final String inputStringCommand)
	{
		if (inputStringCommand.matches(START_GAME_COMMAND_PATTERN))
		{
			gameService.startGame(inputStringCommand);
		}
		else if (inputStringCommand.matches(UPDATE_SCORE_COMMAND_PATTERN))
		{
			scoreService.updateScore(inputStringCommand);
		}
		else if (inputStringCommand.matches(PRINT_SCORE_COMMAND_PATTERN))
		{
			scoreService.printScore();
		}
		else if (inputStringCommand.matches(END_GAME_COMMAND_PATTERN))
		{
			gameService.endGame();
		}
		else if (gameRepository.getRunningGame().isPresent())
		{
			throw new FootballGameException("input error - please type 'print' for game details");
		}
		else
		{
			throw new FootballGameException(
					"input error - please start a game through typing 'Start: '<Name of Home Team>' vs. '<Name of Away Team>'");
		}
	}
}
